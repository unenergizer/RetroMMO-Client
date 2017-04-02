package com.retrommo.client.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.retrommo.client.RetroMMO;
import com.retrommo.client.assets.Assets;
import com.retrommo.client.util.GraphicsUtils;
import com.retrommo.iocommon.wire.client.LoginInfo;

import lombok.Getter;
import lombok.Setter;

/*********************************************************************************
 *
 * OWNER: Robert Andrew Brown & Joseph Rugh
 * PROGRAMMER: Robert Andrew Brown & Joseph Rugh
 * PROJECT: RetroMMO-Client
 * DATE: 3/26/2017
 * _______________________________________________________________________________
 *
 * Copyright © 2017 RetroMMO.com. All Rights Reserved.
 *
 * No part of this project and/or code and/or source code and/or source may be 
 * reproduced, distributed, or transmitted in any form or by any means, 
 * including photocopying, recording, or other electronic or mechanical methods, 
 * without the prior written permission of the owner.
 */
@Getter
@Setter
public class LoginScreen extends ScreenAdapter {

    private final RetroMMO retroMMO;
    private final Batch batch;
    private final Stage stage;

    private final AssetManager assetManager;
    private Texture background;
    private TextField accountField;
    private TextField passwordField;

    private LoginInfo loginInfo;
    private volatile boolean switchScreens = false;

    public LoginScreen(RetroMMO retroMMO) {
        this.retroMMO = retroMMO;
        batch = retroMMO.getBatch();
        stage = new Stage(new ScreenViewport());
        assetManager = retroMMO.getAssetManager();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        background = assetManager.get(Assets.graphics.LOGIN_BACKGROUND);

        // setup display table
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(false);
        stage.addActor(table);

        // temporary until asset manager is implemented
        Skin skin = new Skin(Gdx.files.internal(Assets.userInterface.UI_SKIN));

        // create widgets
        Label nameLabel = new Label("RetroMMO v" + RetroMMO.GAME_VERSION, skin);
        Label accountLabel = new Label("Account", skin);
        Label passwordLabel = new Label("Password", skin);

        accountField = new TextField(null, skin);
        accountField.setFocusTraversal(false);
        accountField.setMaxLength(12);

        passwordField = new TextField(null, skin);
        passwordField.setFocusTraversal(false);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('#');
        passwordField.setMaxLength(16);

        TextButton loginButton = new TextButton("Login", skin);
        loginButton.pad(3, 10, 3, 10);

        TextButton registerButton = new TextButton("Register", skin);
        registerButton.pad(3, 10, 3, 10);

        // add widgets to table
        table.add(nameLabel).colspan(2).pad(0, 0, 30, 0);
        table.row().pad(10);
        table.add(accountLabel);
        table.add(accountField).uniform();
        table.row().pad(10);
        table.add(passwordLabel);
        table.add(passwordField).uniform();
        table.row().pad(10);
        table.add(loginButton);
        table.add(registerButton);

        // setup event listeners
        accountField.setTextFieldListener(new AccountInput());
        passwordField.setTextFieldListener(new PasswordInput());

        // login to network
        loginButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                attemptLogin();
            }
        });

        // opens up web page for player registration
        registerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.net.openURI("http://retrommo.com/login/login");
            }
        });
    }

    @Override
    public void render(float delta) {
        GraphicsUtils.clearScreen();

        batch.begin();
        batch.draw(background, 0, 0, stage.getWidth(), stage.getHeight());
        batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        // If the login was successful, this boolean will become true.
        if (switchScreens) {
            retroMMO.setScreen(ScreenTypes.GAME);
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
        // switching screens, dispose of login screen
        dispose();
    }

    @Override
    public void dispose() {
        System.out.println("[Dispose] LoginScreen disposed.");
        background.dispose();
        stage.dispose();
    }

    /**
     * This will start Netty and attempt to login to the network.
     */
    private void attemptLogin() {
        Gdx.input.setOnscreenKeyboardVisible(false); // close the android keyboard
        loginInfo = new LoginInfo(accountField.getText(), passwordField.getText());

        // Start our network connection.
        retroMMO.startNetty();

        // Clear password filed.
        passwordField.setText("");
    }

    /**
     * If the network is up and a login error occurs, we will handle those messages here.
     *
     * @param isAuthenticated      True if the accountName/password combo is accurate.
     * @param isVersionCheckPassed True if the game-client version matches the server version.
     */
    public void showLoginInfo(boolean isAuthenticated, boolean isVersionCheckPassed) {
        if (isAuthenticated && isVersionCheckPassed) {
            System.out.println("Login success!!");
            return;
        }

        if (!isAuthenticated) {
            System.out.println("Incorrect AccountName/Password combination!");
        }

        if (!isVersionCheckPassed) {
            System.out.println("Version mismatch! Please upgrade your game-client!");
        }
    }

    /*****************************************************************
     * !!! TEXT FIELD LISTENERS !!!
     *
     * WARNING!!! Watch for following characters...
     * Backspace = \b
     * Enter = \n
     * Tab = \t
     */

    private class AccountInput implements TextField.TextFieldListener {
        @Override
        public void keyTyped(TextField textField, char c) {
            // user hit enter/tab/etc, lets move to next text field
            if (c == '\n' || c == '\r' || c == '\t') {
                stage.setKeyboardFocus(passwordField);
            }
        }
    }

    private class PasswordInput implements TextField.TextFieldListener {
        @Override
        public void keyTyped(TextField textField, char c) {
            if (c == '\t') return; // cancel tab
            if (c == '\n' || c == '\r') { // user hit enter, try login
                attemptLogin();
            }
        }
    }
}

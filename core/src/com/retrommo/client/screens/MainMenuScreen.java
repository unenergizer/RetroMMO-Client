package com.retrommo.client.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
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
import com.retrommo.client.netty.SetupClient;
import com.retrommo.client.util.GraphicsUtils;
import com.retrommo.iocommon.LoginInfo;

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
public class MainMenuScreen implements Screen {

    private RetroMMO retroMMO;
    private Stage stage;

    private Texture background;

    private TextField accountField;
    private TextField passwordField;

    private LoginInfo loginInfo;
    private boolean nettyStarted = false;

    private volatile boolean switchScreens = false;

    public MainMenuScreen(RetroMMO retroMMO) {
        this.retroMMO = retroMMO;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        background = new Texture(Gdx.files.internal("background/Badlands.png"));

        // setup display table
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(false);
        stage.addActor(table);

        // temporary until asset manager is implemented
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        // create widgets
        Label nameLabel = new Label("RetroMMO v0.1.0", skin);
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

        // opens up web page for registering
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

        retroMMO.getBatch().begin();
        retroMMO.getBatch().draw(background, 0, 0, stage.getWidth(), stage.getHeight());
        retroMMO.getBatch().end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        if (switchScreens) {
            retroMMO.setGameScreen(new GameScreen(retroMMO));
            retroMMO.setScreen(retroMMO.getGameScreen());
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        // switching screens, dispose of login screen
        dispose();
    }

    @Override
    public void dispose() {
        System.out.println("[Dispose] MainMenuScreen disposed.");
        background.dispose();
        stage.dispose();
    }

    /**
     * This will start Netty and attempt to login to the network.
     */
    private void attemptLogin() {
        Gdx.input.setOnscreenKeyboardVisible(false); // close the android keyboard
        loginInfo = new LoginInfo(accountField.getText(), passwordField.getText());

        // start netty
        if (!nettyStarted) {
            nettyStarted = true;
            try {
                new Thread(new SetupClient(retroMMO, RetroMMO.SERVER, 1337)).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //clear password filed
        passwordField.setText("");
    }

    /**
     * If the network is up and a login error occurs, we will handle those messages here.
     *
     * @param isAuthenticated      True if the accountName/password combo is accurate.
     * @param isVersionCheckPassed True if the game-client version matches the server version.
     */
    public void showLoginError(boolean isAuthenticated, boolean isVersionCheckPassed) {
        System.out.println("Login Error!!");
        System.out.println("Authenticated: " + isAuthenticated + " VersionCheckPassed: " + isVersionCheckPassed);
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
                return;
            }
        }
    }

    private class PasswordInput implements TextField.TextFieldListener {
        @Override
        public void keyTyped(TextField textField, char c) {
            if (c == '\t') return; // cancel tab
            if (c == '\n' || c == '\r') { // user hit enter, try login
                attemptLogin();
                return;
            }
        }
    }
}

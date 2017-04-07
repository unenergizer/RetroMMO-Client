package com.retrommo.client.screens;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.retrommo.client.Constants;
import com.retrommo.client.RetroMMO;
import com.retrommo.client.assets.Assets;
import com.retrommo.client.ecs.ECS;
import com.retrommo.client.ecs.EntityFactory;
import com.retrommo.client.screens.input.KeyboardInput;
import com.retrommo.client.screens.menus.ChatBox;
import com.retrommo.client.util.GraphicsUtils;
import com.retrommo.iocommon.enums.ClientStates;
import com.retrommo.iocommon.wire.client.ClientState;

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
public class GameScreen implements Screen {

    private final RetroMMO retroMMO;
    // ASSETS
    private final AssetManager assetManager;
    // ENTITY
    private final ECS ecs;
    private final World world;
    private final EntityFactory entityFactory;
    private boolean gameScreenReady = false;
    // USER INTERFACE
    private Stage stage;
    private ChatBox chatBox;
    // MAP
    private OrthographicCamera camera;
    private ScreenViewport viewport;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    // INPUT
    private InputMultiplexer inputMultiplexer;
    private KeyboardInput keyboardInput;

    public GameScreen(RetroMMO retroMMO) {
        this.retroMMO = retroMMO;

        // Camera and UI
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        camera.update();
        stage = new Stage(viewport = new ScreenViewport());

        // Input
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(keyboardInput = new KeyboardInput());
        Gdx.input.setInputProcessor(inputMultiplexer);

        // Assets
        assetManager = retroMMO.getAssetManager();

        // Entities
        ecs = new ECS(retroMMO);
        world = ecs.getWorld();
        entityFactory = new EntityFactory(retroMMO, ecs);
    }

    @Override
    public void show() {

        System.out.println("[GameScreen] Creating main game screen.");
        Skin skin = new Skin(Gdx.files.internal(Assets.userInterface.UI_SKIN));

        chatBox = new ChatBox(retroMMO, this, stage, skin, false);
        chatBox.create();

        // map
        map = new TmxMapLoader().load(Assets.maps.TEST_MAP); //TODO: LOAD WITH ASSET MANAGER!
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        // send server new game state
        ClientState state = new ClientState();
        state.setState(ClientStates.GAME_READY);
        retroMMO.sendNetworkData(state);
    }

    @Override
    public void render(float delta) {

        if (!gameScreenReady) {
            gameScreenReady = true;
        }
        GraphicsUtils.clearScreen();

        // render the map
        mapRenderer.setView(camera);
        mapRenderer.render();

        // batch goes here
        world.setDelta(delta);
        world.process();

        // draw ui
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
        map.dispose();
        mapRenderer.dispose();
    }
}

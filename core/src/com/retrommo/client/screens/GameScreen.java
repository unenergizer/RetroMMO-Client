package com.retrommo.client.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.retrommo.client.RetroMMO;
import com.retrommo.client.ecs.components.PositionComponent;
import com.retrommo.client.ecs.components.RotationComponent;
import com.retrommo.client.ecs.components.ScaleComponent;
import com.retrommo.client.ecs.components.SizeComponent;
import com.retrommo.client.ecs.components.TextureComponent;
import com.retrommo.client.ecs.systems.EntityBuilder;
import com.retrommo.client.ecs.systems.RenderSystem;
import com.retrommo.client.managers.MainPlayer;
import com.retrommo.client.screens.input.KeyboardInput;
import com.retrommo.client.screens.menus.ChatBox;
import com.retrommo.client.util.GraphicsUtils;

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

    private RetroMMO retroMMO;
    private final int WIDTH = 1080;
    private final int HEIGHT = WIDTH / 16 * 9;

    // menus
    private Stage stage;
    private ChatBox chatBox;

    // map
    private OrthographicCamera camera;
    private ScreenViewport viewport;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;

    // input
    private InputMultiplexer inputMultiplexer;

    // asset and entities
    private AssetManager assetManager;
    private Engine engine;
    private SpriteBatch batch;

    // remove later
    private String playerImg = "player/player.png";

    private EntityBuilder entityBuilder;

    private MainPlayer mainPlayer;

    public GameScreen(RetroMMO retroMMO) {
        this.retroMMO = retroMMO;
        mainPlayer = retroMMO.getMainPlayer();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH, HEIGHT);
        camera.update();

        stage = new Stage(viewport = new ScreenViewport());

        // input
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(new KeyboardInput());
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void show() {

        assetManager = new AssetManager();

        // load assets here
        assetManager.load(playerImg, Texture.class);

        assetManager.finishLoading();

        // create ashley engine
        engine = new Engine();

        batch = new SpriteBatch();

        engine.addSystem(new RenderSystem(viewport, batch));

        entityBuilder = new EntityBuilder(assetManager, engine);
        mainPlayer.setEntity(entityBuilder.makeEntity(350, 350, 25, 25, playerImg));

        System.out.println("[GameScreen] Creating main game screen.");
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        chatBox = new ChatBox(retroMMO, this, stage, skin, false);
        chatBox.create();

        // map
        map = new TmxMapLoader().load("maps/fields.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);
    }

    @Override
    public void render(float delta) {
        GraphicsUtils.clearScreen();

        // render the map
        mapRenderer.setView(camera);
        mapRenderer.render();

        // batch goes here
        engine.update(Gdx.graphics.getDeltaTime());

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
        assetManager.dispose();
        batch.dispose();
    }
}

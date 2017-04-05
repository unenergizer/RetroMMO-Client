package com.retrommo.client.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.retrommo.client.RetroMMO;
import com.retrommo.client.assets.Assets;
import com.retrommo.client.util.GraphicsUtils;

/*********************************************************************************
 *
 * OWNER: Robert Andrew Brown & Joseph Rugh
 * PROGRAMMER: Robert Andrew Brown & Joseph Rugh
 * PROJECT: RetroMMO-Client
 * DATE: 4/1/2017
 * _______________________________________________________________________________
 *
 * Copyright © 2017 RetroMMO.com. All Rights Reserved.
 *
 * No part of this project and/or code and/or source code and/or source may be 
 * reproduced, distributed, or transmitted in any form or by any means, 
 * including photocopying, recording, or other electronic or mechanical methods, 
 * without the prior written permission of the owner.
 */
public class LoadingScreen extends ScreenAdapter {

    private final RetroMMO retroMMO;
    private final AssetManager assetManager;
    private final Batch batch;
    private final Stage stage;
    private Texture background;

    private int currentStage = 0;

    public LoadingScreen(RetroMMO retroMMO) {
        this.retroMMO = retroMMO;
        assetManager = retroMMO.getAssetManager();
        stage = new Stage(new ScreenViewport());
        batch = retroMMO.getBatch();
    }

    @Override
    public void show() {
        // begin loading quick graphics (for loading screen)
        assetManager.load(Assets.graphics.LOGIN_BACKGROUND, Texture.class);
        assetManager.finishLoading();

        // initialize loading screen graphics
        background = assetManager.get(Assets.graphics.LOGIN_BACKGROUND);
    }

    @Override
    public void render(float delta) {
        GraphicsUtils.clearScreen();

        // draw background
        batch.begin();
        batch.draw(background, 0, 0, stage.getWidth(), stage.getHeight());
        batch.end();

        // display loading bar here
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        // Currently loading assets have finished. Either must switch
        // screens and/or start loading in new assets
        if (assetManager.update()) {
            switch (currentStage) {
                case 0:
                    loadAllGraphics();
                    break;
                case 1:
                    loadAllAudio();
                    break;
                case 2:
                    // Looks like were all finished!
                    assetManager.finishLoading();
                    // After going through all asset types switch screens
                    retroMMO.setScreen(ScreenTypes.LOGIN);

                    break;
            }
            currentStage++;
        }

        // Current progress of the currently loading asserts
        float currentProgress = assetManager.getProgress();

        // Use the progress to update the progress bar respectively

    }

    private void loadAllGraphics() {
        // BACKGROUNDS
        //assetManager.load(Assets.graphics.LOGIN_BACKGROUND, Texture.class);

        // ENTITIES
        assetManager.load(Assets.graphics.TEMP_PLAYER_IMG, Texture.class);
    }

    private void loadAllAudio() {
        // TODO: LOAD AUDIO
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}

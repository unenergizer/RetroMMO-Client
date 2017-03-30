package com.retrommo.client;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.retrommo.client.managers.MainPlayer;
import com.retrommo.client.netty.listeners.AuthSuccessListener;
import com.retrommo.client.netty.listeners.ChatListener;
import com.retrommo.client.netty.listeners.EntityMoveListener;
import com.retrommo.client.netty.listeners.ListenerManager;
import com.retrommo.client.screens.GameScreen;
import com.retrommo.client.screens.MainMenuScreen;

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
public class RetroMMO extends Game {

    private SpriteBatch batch;
    private ListenerManager listenerManager;
    private MainMenuScreen mainMenuScreen;
    private GameScreen gameScreen;
    private MainPlayer mainPlayer;

    public static final String SERVER = "24.72.165.55";

    @Override
    public void create() {
        // setup manager classes
        mainPlayer = new MainPlayer(this);
        loadListeners();

        // load settings
        // load assets
        batch = new SpriteBatch();
        mainMenuScreen = new MainMenuScreen(this);
        setScreen(mainMenuScreen);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        if (mainPlayer.getChannel() != null) mainPlayer.getChannel().close();
    }

    private void loadListeners() {
        listenerManager = new ListenerManager();

        listenerManager.addListener(new AuthSuccessListener(this));
        listenerManager.addListener(new ChatListener(this));
        listenerManager.addListener(new EntityMoveListener(this));
    }
}

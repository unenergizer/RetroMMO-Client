package com.retrommo.client;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.retrommo.client.ecs.EntityManager;
import com.retrommo.client.netty.NettySetup;
import com.retrommo.client.netty.listeners.AuthSuccessListener;
import com.retrommo.client.netty.listeners.ChatListener;
import com.retrommo.client.netty.listeners.EntityMoveListener;
import com.retrommo.client.netty.listeners.EntityReceivedListener;
import com.retrommo.client.netty.listeners.NetworkListenerManager;
import com.retrommo.client.screens.GameScreen;
import com.retrommo.client.screens.LoadingScreen;
import com.retrommo.client.screens.LoginScreen;
import com.retrommo.client.screens.ScreenTypes;

import io.netty.channel.Channel;
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

    // GENERAL
    public static final String GAME_VERSION = "0.1.0";
    public static final float WIDTH = 1080;
    public static final float HEIGHT = WIDTH / 16 * 9;

    // NETWORKING
    private final String serverAddress = "24.72.165.55";
    private final int serverPort = 1337;
    private final NetworkListenerManager networkListenerManager;
    private boolean nettyStarted = false;
    private Channel channel;

    // ASSETS
    private SpriteBatch batch;
    private AssetManager assetManager;

    // SCREENS
    private LoadingScreen loadingScreen;
    private LoginScreen loginScreen;
    private GameScreen gameScreen;

    // ENTITIES
    private EntityManager entityManager;

    public RetroMMO() {
        networkListenerManager = new NetworkListenerManager();
        assetManager = new AssetManager();
        entityManager = new EntityManager(this);

        loadNetworkListeners();
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(ScreenTypes.LOADING);
    }

    @Override
    public void dispose() {
        System.out.println("Disposing RETROMMO");
        batch.dispose();
        assetManager.dispose();
        stopNetty();
    }

    /**
     * This will setup our client to receive network
     * objects coming from the server.
     */
    private void loadNetworkListeners() {
        networkListenerManager.addListener(new AuthSuccessListener(this));
        networkListenerManager.addListener(new ChatListener(this));
        networkListenerManager.addListener(new EntityMoveListener(this));
        networkListenerManager.addListener(new EntityReceivedListener(this));
    }

    /**
     * This is used to change the game screen.
     *
     * @param screenTypes The type of screen we want to change to.
     */
    public void setScreen(ScreenTypes screenTypes) {
        switch (screenTypes) {
            case LOADING:
                if (loadingScreen == null) loadingScreen = new LoadingScreen(this);
                setScreen(loadingScreen);
                break;
            case LOGIN:
                if (loginScreen == null) loginScreen = new LoginScreen(this);
                setScreen(loginScreen);
                break;
            case GAME:
                if (gameScreen == null) gameScreen = new GameScreen(this);
                setScreen(gameScreen);
                break;
        }
    }

    /**
     * Sends objects over the network to the server.
     *
     * @param object The object to send to the server.
     */
    public void sendNetworkData(Object object) {
        channel.writeAndFlush(object);
    }

    /**
     * This will start Netty and connect us to the server.
     */
    public void startNetty() {
        if (!nettyStarted) {
            nettyStarted = true;
            try {
                new Thread(new NettySetup(this, serverAddress, serverPort)).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This will disconnect the player from the
     * server and send them to the login screen.
     */
    public void disconnectClient() {
        stopNetty();
        setScreen(ScreenTypes.LOGIN);
    }

    /**
     * This will close the server connection.
     */
    public void stopNetty() {
        nettyStarted = false;
        if (channel == null) return;
        channel.close();
        channel = null;
    }
}

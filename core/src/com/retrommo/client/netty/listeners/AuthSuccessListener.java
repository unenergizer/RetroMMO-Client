package com.retrommo.client.netty.listeners;

import com.retrommo.client.RetroMMO;
import com.retrommo.client.netty.ObjectListener;
import com.retrommo.client.netty.ObjectType;
import com.retrommo.client.screens.MainMenuScreen;
import com.retrommo.iocommon.AuthSuccess;

import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;

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
@AllArgsConstructor
public class AuthSuccessListener implements ObjectListener {

    private RetroMMO retroMMO;

    @ObjectType(getType = AuthSuccess.class)
    public void onLogin(AuthSuccess authSuccess, ChannelHandlerContext ctx) {

        boolean isAuthenticated = authSuccess.isLoginSuccess();
        boolean isVersionCheckPassed = authSuccess.isVersionCheckPassed();

        if (isAuthenticated && isVersionCheckPassed) {
            retroMMO.getMainMenuScreen().setSwitchScreens(true);
        } else {
            ctx.close();
            MainMenuScreen mainMenuScreen = retroMMO.getMainMenuScreen();
            mainMenuScreen.showLoginError(isAuthenticated, isVersionCheckPassed);
            mainMenuScreen.setNettyStarted(false);
        }
    }
}

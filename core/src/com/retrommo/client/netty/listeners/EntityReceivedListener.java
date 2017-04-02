package com.retrommo.client.netty.listeners;

import com.retrommo.client.RetroMMO;
import com.retrommo.client.assets.Assets;
import com.retrommo.client.netty.ObjectListener;
import com.retrommo.client.netty.ObjectType;
import com.retrommo.iocommon.wire.server.SendEntityData;

import lombok.AllArgsConstructor;

import static com.retrommo.iocommon.enums.EntityTypes.PLAYER;

/*********************************************************************************
 *
 * OWNER: Robert Andrew Brown & Joseph Rugh
 * PROGRAMMER: Robert Andrew Brown & Joseph Rugh
 * PROJECT: RetroMMO-Client
 * DATE: 3/28/2017
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
public class EntityReceivedListener implements ObjectListener {

    private RetroMMO retroMMO;

    @ObjectType(getType = SendEntityData.class)
    public void onEntityReceived(SendEntityData sendEntityData) {

        //TODO: READ FROM JSON FILE AND PROPERLY CREATE ENTITIES

        String imagePath = "";

        switch (sendEntityData.getEntityType()) {
            case PLAYER:
                imagePath = Assets.graphics.TEMP_PLAYER_IMG; //TODO: REMOVE
                break;
        }

        //TODO: REMOVE
        retroMMO.getGameScreen().getEntityFactory().makeEntity(
                sendEntityData.getX(),
                sendEntityData.getY(),
                25,//width
                25,//height
                imagePath, sendEntityData.getServerEntityId());
    }
}

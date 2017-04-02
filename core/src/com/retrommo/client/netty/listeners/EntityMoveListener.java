package com.retrommo.client.netty.listeners;

import com.retrommo.client.RetroMMO;
import com.retrommo.client.ecs.components.PositionComponent;
import com.retrommo.client.ecs.components.ServerIdComponent;
import com.retrommo.client.netty.ObjectListener;
import com.retrommo.client.netty.ObjectType;
import com.retrommo.client.screens.GameScreen;
import com.retrommo.iocommon.wire.client.EntityMove;

import lombok.AllArgsConstructor;

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
public class EntityMoveListener implements ObjectListener {

    private RetroMMO retroMMO;

    @ObjectType(getType = EntityMove.class)
    public void onEntityMove(EntityMove entityMove) {

        GameScreen gameScreen = retroMMO.getGameScreen();

        ServerIdComponent serverPlayerId = gameScreen.getEcs().getServerIdMapper().get(gameScreen.getClientPlayerId());

        // Is the player
        if (entityMove.getEntityId() == serverPlayerId.getServerId()) {

            System.out.println("ecs: " + gameScreen.getEcs());
            System.out.println("position mapper: " + gameScreen.getEcs().getPositionMapper());
            System.out.println("client serverId: " + gameScreen.getClientPlayerId());

            PositionComponent position = gameScreen.getEcs().getPositionMapper().get(gameScreen.getClientPlayerId());
            position.setX(entityMove.getX());
            position.setY(entityMove.getY());
        } else {
            // not the player
        }
    }
}

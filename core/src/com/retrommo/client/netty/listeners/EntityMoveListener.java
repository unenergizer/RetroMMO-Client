package com.retrommo.client.netty.listeners;

import com.retrommo.client.RetroMMO;
import com.retrommo.client.ecs.components.PositionComponent;
import com.retrommo.client.managers.MainPlayer;
import com.retrommo.client.managers.MainPlayer;
import com.retrommo.client.netty.ObjectListener;
import com.retrommo.client.netty.ObjectType;
import com.retrommo.client.screens.GameScreen;
import com.retrommo.iocommon.ChatMessage;
import com.retrommo.iocommon.EntityMove;

import java.util.UUID;

import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
public class EntityMoveListener implements ObjectListener {
    private RetroMMO retroMMO;

    public EntityMoveListener(RetroMMO retroMMO) {
        this.retroMMO = retroMMO;
    }

    @ObjectType(getType = EntityMove.class)
    public void onEntityMove(EntityMove entityMove, ChannelHandlerContext ctx) {
        MainPlayer mainMainPlayer = retroMMO.getMainPlayer();
        UUID mainPlayerUUID = mainMainPlayer.getUuid();

        if (mainPlayerUUID == entityMove.getUuid()) {
            // update main player profile move info
            // probably wont be needed later...
            mainMainPlayer.setX(entityMove.getX());
            mainMainPlayer.setY(entityMove.getY());
            mainMainPlayer.getEntity().getComponent(PositionComponent.class).x = entityMove.getX();
            mainMainPlayer.getEntity().getComponent(PositionComponent.class).y = entityMove.getY();
        } else {

            // TODO: MOVE THIS SHIT (later)

            // all other entities


            // if entity does not exist (on this client) then use the entity builder to add them
            // otherwise get the entity from the ECS  and update it

        }

    }
}

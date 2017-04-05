package com.retrommo.client.ecs.systems;

import com.artemis.Aspect;
import com.artemis.systems.IteratingSystem;
import com.retrommo.client.RetroMMO;
import com.retrommo.client.ecs.ECS;
import com.retrommo.client.ecs.components.Player;
import com.retrommo.client.ecs.components.PositionComponent;
import com.retrommo.client.ecs.components.RotationComponent;
import com.retrommo.client.ecs.components.ScaleComponent;
import com.retrommo.client.ecs.components.SizeComponent;
import com.retrommo.client.ecs.components.TextureComponent;
import com.retrommo.client.screens.input.KeyboardInput;
import com.retrommo.iocommon.wire.client.EntityMove;

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
public class LocalPlayerMoveSystem extends IteratingSystem {

    private final RetroMMO retroMMO;
    private final ECS ecs;

    public LocalPlayerMoveSystem(RetroMMO retroMMO, ECS ecs) {
        super(Aspect.all(PositionComponent.class, RotationComponent.class, ScaleComponent.class, SizeComponent.class, TextureComponent.class));
        this.retroMMO = retroMMO;
        this.ecs = ecs;
    }

    @Override
    protected void process(int entityId) {
        int serverID = retroMMO.getEntityManager().getClientData().getServerID();
        int localID = retroMMO.getEntityManager().getClientData().getLocalID();

        PositionComponent position = ecs.getPositionMapper().get(localID);
        float x = position.getX();
        float y = position.getY();

        KeyboardInput input = retroMMO.getGameScreen().getKeyboardInput();
        EntityMove entityMove = new EntityMove();
        entityMove.setEntityId(serverID);
        entityMove.setMapId(0);

        entityMove.setX(x + input.getXmovement());
        entityMove.setY(y + input.getYmovement());

        position.setX(x + input.getXmovement());
        position.setY(y + input.getYmovement());

        retroMMO.sendNetworkData(entityMove);

//        System.out.println("Moving entity...");
//        System.out.println("ServerID: " + serverID);
//        System.out.println("LocalID: " + localID);
//        System.out.println("x: " + x + input.getXmovement());
//        System.out.println("y: " + y + input.getYmovement());
    }
}

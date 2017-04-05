package com.retrommo.client.netty.listeners;

import com.retrommo.client.RetroMMO;
import com.retrommo.client.ecs.components.PositionComponent;
import com.retrommo.client.netty.ObjectListener;
import com.retrommo.client.netty.ObjectType;
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

        int serverID = entityMove.getEntityId();
        float x = entityMove.getX();
        float y = entityMove.getY();

        if (serverID == retroMMO.getEntityManager().getClientData().getServerID()) return;

        int localID = retroMMO.getEntityManager().serverIDtoLocalID(serverID);

//        System.out.println("Moving entity...");
//        System.out.println("ServerID: " + serverID);
//        System.out.println("LocalID: " + localID);
//        System.out.println("x: " + x);
//        System.out.println("y: " + y);

        PositionComponent position = retroMMO.getGameScreen().getEcs().getPositionMapper().get(localID);
        position.setX(x);
        position.setY(y);
    }
}

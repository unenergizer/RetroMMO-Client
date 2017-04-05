package com.retrommo.client.ecs;

import com.retrommo.client.RetroMMO;
import com.retrommo.client.assets.Assets;
import com.retrommo.iocommon.enums.EntityTypes;
import com.retrommo.iocommon.wire.server.SendEntityData;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

/*********************************************************************************
 *
 * OWNER: Robert Andrew Brown & Joseph Rugh
 * PROGRAMMER: Robert Andrew Brown & Joseph Rugh
 * PROJECT: RetroMMO-Client
 * DATE: 4/2/2017
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
public class EntityManager {

    private final RetroMMO retroMMO;

    private final Map<Integer, EntityData> serverIDtoEntityData = new HashMap<>();
    private final Map<Integer, EntityData> localIDtoEntityData = new HashMap<>();

    private EntityData clientData;

    public EntityManager(RetroMMO retroMMO) {
        this.retroMMO = retroMMO;
    }

    public int serverIDtoLocalID(int serverID) {
        if (clientData.getServerID() == serverID) {
            return clientData.getLocalID(); // local player
        }
        return serverIDtoEntityData.get(serverID).getLocalID(); // online player/
    }

    /**
     * Here we are creating entities and adding them to the above HashMaps for
     * reference. We do this because we need to keep track of entity server ID's.
     *
     * @param serverEntityData Data from the server that describes our entity.
     */
    public void addEntity(SendEntityData serverEntityData) {
        // TODO: Sort out the types of entities and associated graphics for each..
        String imagePath = Assets.graphics.TEMP_PLAYER_IMG;

        int serverID = serverEntityData.getServerEntityID();
        EntityTypes type = serverEntityData.getEntityType();
        float x = serverEntityData.getX();
        float y = serverEntityData.getY();

        int localID = retroMMO.getGameScreen().getEntityFactory().makeEntity(
                x,
                y,
                25,//width
                25,//height
                imagePath, serverID);

        System.out.println("Setting up entity...");
        System.out.println("ServerID: " + serverID);
        System.out.println("LocalID: " + localID);

        // Setup HashMaps
        EntityData localEntityData = new EntityData(serverID, localID, type);

        if (type == EntityTypes.CLIENT_PLAYER) {
            // If the entity type is Client_Player then set up our local player.
            clientData = localEntityData;
        } else {
            // Else we add all entities to our HashMaps for easy reference.
            serverIDtoEntityData.put(serverID, localEntityData);
            localIDtoEntityData.put(localID, localEntityData);
        }
    }

    /**
     * This will remove a entity from the HashMap reference via the Server ID.
     * Note: if used improperly this could remove the wrong entity or not remove
     * any entity at all! Make sure to only use a SERVER ID here!!!
     *
     * @param serverID A ID assigned by the Server that represents the entity we
     *                 will remove.
     */
    public void removeEntityViaServerID(int serverID) {
        removeEntity(serverID, serverIDtoEntityData.get(serverID).getLocalID());
    }

    /**
     * This will remove a entity from the HashMap reference via the Local Client ID.
     * Note: if used improperly this could remove the wrong entity or not remove
     * any entity at all! Make sure to only use a LOCAL CLIENT ID here!!!
     *
     * @param localID A ID assigned by the game client to represent a server entity
     *                that we will remove.
     */
    public void removeEntityViaLocalID(int localID) {
        removeEntity(localIDtoEntityData.get(localID).getServerID(), localID);
    }

    /**
     * This method will remove reference of a Server and Local entity.
     *
     * @param serverID The ID sent to us from the Server that represents
     *                 the entity we will remove.
     * @param localID  The ID assigned to an entity created locally.
     */
    private void removeEntity(int serverID, int localID) {

        //TODO: Remove entity from ECS!!

        serverIDtoEntityData.remove(serverID);
        localIDtoEntityData.remove(localID);
    }
}

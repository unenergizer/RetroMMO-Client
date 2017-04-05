package com.retrommo.client.ecs;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.assets.AssetManager;
import com.retrommo.client.RetroMMO;
import com.retrommo.client.ecs.components.PositionComponent;
import com.retrommo.client.ecs.components.RotationComponent;
import com.retrommo.client.ecs.components.ScaleComponent;
import com.retrommo.client.ecs.components.ServerIdComponent;
import com.retrommo.client.ecs.components.SizeComponent;
import com.retrommo.client.ecs.components.TextureComponent;

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
public class EntityFactory {

    private final RetroMMO retroMMO;
    private final AssetManager assetManager;
    private final ECS ecs;

    public EntityFactory(RetroMMO retroMMO, ECS ecs) {
        this.retroMMO = retroMMO;
        assetManager = retroMMO.getAssetManager();
        this.ecs = ecs;
    }

    public int makeEntity(float x, float y, float width, float height, String imagePath, int serverEntityId) {
        return makeEntity(x, y, width, height, imagePath, 0f, 1, 1, serverEntityId);
    }

    public int makeEntity(float x, float y, float width, float height, String imagePath, float rotation, float scaleX, float scaleY, int serverEntityId) {

        World world = ecs.getWorld();

        int entityId = world.create();
        Entity entity = world.getEntity(entityId);

        PositionComponent position = ecs.createComponent(entity, ecs.getPositionMapper());
        position.setX(x);
        position.setY(y);

        SizeComponent size = ecs.createComponent(entity, ecs.getSizeMapper());
        size.setWidth(width);
        size.setHeight(height);

        TextureComponent texture = ecs.createComponent(entity, ecs.getTextureMapper());
        texture.texture = assetManager.get(imagePath);

        RotationComponent rotationComponent = ecs.createComponent(entity, ecs.getRotationMapper());
        rotationComponent.setRotation(rotation);

        ScaleComponent scaleComponent = ecs.createComponent(entity, ecs.getScaleMapper());
        scaleComponent.setScaleX(scaleX);
        scaleComponent.setScaleY(scaleY);

        ServerIdComponent serverIdComponent = ecs.createComponent(entity, ecs.getServerIdMapper());
        serverIdComponent.setServerId(serverEntityId);

        return entityId;
    }
}

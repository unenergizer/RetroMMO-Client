package com.retrommo.client.ecs.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.assets.AssetManager;
import com.retrommo.client.ecs.components.PositionComponent;
import com.retrommo.client.ecs.components.RotationComponent;
import com.retrommo.client.ecs.components.ScaleComponent;
import com.retrommo.client.ecs.components.SizeComponent;
import com.retrommo.client.ecs.components.TextureComponent;

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
public class EntityBuilder {

    private final AssetManager assetManager;
    private final Engine engine;

    public Entity makeEntity(float x, float y, float width, float height, String imagePath) {
        return makeEntity(x, y, width, height, imagePath, 0f, 1, 1);
    }

    public Entity makeEntity(float x, float y, float width, float height, String imagePath, float rotation, float scaleX, float scaleY) {

        PositionComponent position = new PositionComponent();
        position.x = x;
        position.y = y;

        SizeComponent size = new SizeComponent();
        size.width = width;
        size.height = height;

        TextureComponent texture = new TextureComponent();
        texture.texture = assetManager.get(imagePath);

        RotationComponent rotationComponent = new RotationComponent();
        rotationComponent.rotation = rotation;

        ScaleComponent scaleComponent = new ScaleComponent();
        scaleComponent.scaleX = scaleX;
        scaleComponent.scaleY = scaleY;

        Entity entity = new Entity();
        entity.add(position);
        entity.add(size);
        entity.add(texture);
        entity.add(rotationComponent);
        entity.add(scaleComponent);

        engine.addEntity(entity);

        return entity;
    }
}

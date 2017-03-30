package com.retrommo.client.ecs.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
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
public class RenderSystem extends EntitySystem {

    private static final Family FAMILY = Family.all(
            PositionComponent.class,
            SizeComponent.class,
            TextureComponent.class,
            RotationComponent.class,
            ScaleComponent.class
    ).get();

    private static final ComponentMapper<PositionComponent> POSITION = ComponentMapper.getFor(PositionComponent.class);
    private static final ComponentMapper<SizeComponent> SIZE = ComponentMapper.getFor(SizeComponent.class);
    private static final ComponentMapper<TextureComponent> TEXTURE = ComponentMapper.getFor(TextureComponent.class);
    private static final ComponentMapper<RotationComponent> ROTATIONS = ComponentMapper.getFor(RotationComponent.class);
    private static final ComponentMapper<ScaleComponent> SCALE = ComponentMapper.getFor(ScaleComponent.class);

    private Viewport viewport;
    private SpriteBatch batch;

    private ImmutableArray<Entity> entities;

    public RenderSystem(Viewport viewport, SpriteBatch batch) {
        this.viewport = viewport;
        this.batch = batch;
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(FAMILY);
    }

    @Override
    public void update(float deltaTime) {
        // called every frame
        viewport.apply();

        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        draw();
        batch.end();
    }

    private void draw() {
        for (Entity entity : entities) {
            PositionComponent position = POSITION.get(entity);
            SizeComponent size = SIZE.get(entity);
            TextureComponent texture = TEXTURE.get(entity);
            RotationComponent rotationComponent = ROTATIONS.get(entity);
            ScaleComponent scaleComponent = SCALE.get(entity);

            Texture myTexture = texture.texture;

            batch.draw(myTexture,
                    position.x, position.y,
                    position.x / 2 - (size.width /2f), position.y / 2 - (size.height / 2f),
                    size.width, size.height,
                    scaleComponent.scaleX, scaleComponent.scaleY,
                    rotationComponent.rotation,
                    1, 1,
                    myTexture.getWidth(), myTexture.getHeight(),
                    false, false
            );
        }
    }
}

package com.retrommo.client.ecs.systems;

import com.artemis.Aspect;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.retrommo.client.ecs.ECS;
import com.retrommo.client.ecs.components.PositionComponent;
import com.retrommo.client.ecs.components.RotationComponent;
import com.retrommo.client.ecs.components.ScaleComponent;
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
public class RenderSystem extends IteratingSystem {

    private final ECS ecs;
    private final ScreenViewport viewport;
    private final SpriteBatch batch;

    public RenderSystem(ECS ecs, ScreenViewport viewport, SpriteBatch batch) {
        super(Aspect.all(PositionComponent.class, RotationComponent.class, ScaleComponent.class, SizeComponent.class, TextureComponent.class));
        this.ecs = ecs;
        this.viewport = viewport;
        this.batch = batch;
    }

    @Override
    protected void process(int entityId) {
        viewport.apply();

        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        draw(entityId);
        batch.end();
    }

    private void draw(int entityId) {

        PositionComponent position = ecs.getPositionMapper().get(entityId);
        SizeComponent size = ecs.getSizeMapper().get(entityId);
        ScaleComponent scale = ecs.getScaleMapper().get(entityId);
        Texture texture = ecs.getTextureMapper().get(entityId).texture;

        batch.draw(texture,
                position.getX(), position.getY(),
                position.getX() / 2 - (size.getWidth() / 2f), position.getY() / 2 - (size.getHeight() / 2f),
                size.getWidth(), size.getHeight(),
                scale.getScaleX(), scale.getScaleY(),
                ecs.getRotationMapper().get(entityId).getRotation(),
                1, 1,
                texture.getWidth(), texture.getHeight(),
                false, false
        );
    }
}

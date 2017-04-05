package com.retrommo.client.ecs;

import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.retrommo.client.RetroMMO;
import com.retrommo.client.ecs.components.LocalPlayer;
import com.retrommo.client.ecs.components.Player;
import com.retrommo.client.ecs.components.PositionComponent;
import com.retrommo.client.ecs.components.RotationComponent;
import com.retrommo.client.ecs.components.ScaleComponent;
import com.retrommo.client.ecs.components.ServerIdComponent;
import com.retrommo.client.ecs.components.SizeComponent;
import com.retrommo.client.ecs.components.TextureComponent;
import com.retrommo.client.ecs.systems.LocalPlayerMoveSystem;
import com.retrommo.client.ecs.systems.RenderSystem;

import lombok.Getter;

/*********************************************************************************
 *
 * OWNER: Robert Andrew Brown & Joseph Rugh
 * PROGRAMMER: Robert Andrew Brown & Joseph Rugh
 * PROJECT: RetroMMO-Client
 * DATE: 3/31/2017
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
public class ECS {

    private final RetroMMO retroMMO;
    private final WorldConfiguration config;
    private final World world;

    private final ComponentMapper<PositionComponent> positionMapper;
    private final ComponentMapper<RotationComponent> rotationMapper;
    private final ComponentMapper<ScaleComponent> scaleMapper;
    private final ComponentMapper<SizeComponent> sizeMapper;
    private final ComponentMapper<TextureComponent> textureMapper;
    private final ComponentMapper<ServerIdComponent> serverIdMapper;
    private final ComponentMapper<Player> playerDataMapper;
    private final ComponentMapper<LocalPlayer> localPlayerDataMapper;

    public ECS(RetroMMO retroMMO) {
        this.retroMMO = retroMMO;

        config = new WorldConfigurationBuilder()
                .with(
                        new RenderSystem(retroMMO, this),
                        new LocalPlayerMoveSystem(retroMMO, this)
                )
                .build();

        world = new World(config);


        positionMapper = new ComponentMapper<>(PositionComponent.class, world);
        rotationMapper = new ComponentMapper<>(RotationComponent.class, world);
        scaleMapper = new ComponentMapper<>(ScaleComponent.class, world);
        sizeMapper = new ComponentMapper<>(SizeComponent.class, world);
        textureMapper = new ComponentMapper<>(TextureComponent.class, world);
        serverIdMapper = new ComponentMapper<>(ServerIdComponent.class, world);
        playerDataMapper = new ComponentMapper<>(Player.class, world);
        localPlayerDataMapper = new ComponentMapper<>(LocalPlayer.class, world);
    }

    public <T extends Component> T createComponent(Entity entity, ComponentMapper<T> componentMapper) {
        T component = componentMapper.create(entity.getId());
        entity.edit().add(component);
        return component;
    }
}

package com.retrommo.client.managers;


import com.badlogic.ashley.core.Entity;
import com.retrommo.client.RetroMMO;

import java.util.UUID;

import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;

/*********************************************************************************
 *
 * OWNER: Robert Andrew Brown & Joseph Rugh
 * PROGRAMMER: Robert Andrew Brown & Joseph Rugh
 * PROJECT: RetroMMO-Client
 * DATE: 3/26/2017
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
public class MainPlayer {

    private RetroMMO retroMMO;
    private Channel channel;
    private UUID uuid;
    private float x, y;
    private Entity entity;

    public MainPlayer(RetroMMO retroMMO) {
        this.retroMMO = retroMMO;
    }


}

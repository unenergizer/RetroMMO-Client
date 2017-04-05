package com.retrommo.client.ecs;

import com.retrommo.iocommon.enums.EntityTypes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/*********************************************************************************
 *
 * OWNER: Robert Andrew Brown & Joseph Rugh
 * PROGRAMMER: Robert Andrew Brown & Joseph Rugh
 * PROJECT: RetroMMO-Client
 * DATE: 4/3/2017
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
@AllArgsConstructor
public class EntityData {
    private final int serverID;
    private final int localID;
    private final EntityTypes entityType;
}

package com.retrommo.client.screens.menus;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.retrommo.client.RetroMMO;

/*********************************************************************************
 *
 * OWNER: Robert Andrew Brown & Joseph Rugh
 * PROGRAMMER: Robert Andrew Brown & Joseph Rugh
 * PROJECT: RetroMMO-Client
 * DATE: 3/27/2017
 * _______________________________________________________________________________
 *
 * Copyright © 2017 RetroMMO.com. All Rights Reserved.
 *
 * No part of this project and/or code and/or source code and/or source may be 
 * reproduced, distributed, or transmitted in any form or by any means, 
 * including photocopying, recording, or other electronic or mechanical methods, 
 * without the prior written permission of the owner.
 */

abstract class AbstractMenu extends Table {

    final RetroMMO retroMMO;
    final Screen screen;
    final Stage stage;
    final Skin skin;
    final boolean debug;

    AbstractMenu(RetroMMO retroMMO, Screen screen, Stage stage, Skin skin, boolean debug) {
        this.retroMMO = retroMMO;
        this.screen = screen;
        this.stage = stage;
        this.skin = skin;
        this.debug = debug;
    }

    public abstract void create();

    public abstract void hide();
}

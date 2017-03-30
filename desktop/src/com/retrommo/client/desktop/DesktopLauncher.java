package com.retrommo.client.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.retrommo.client.RetroMMO;

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
public class DesktopLauncher {

    private static final int WIDTH = 1080;
    private static final int HEIGHT = WIDTH / 16 * 9;

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "RetroMMO";
		config.width = WIDTH;
		config.height = HEIGHT;
        config.addIcon("icon-128.png", Files.FileType.Internal);
        config.addIcon("icon-32.png", Files.FileType.Internal);
        config.addIcon("icon-16.png", Files.FileType.Internal);
		new LwjglApplication(new RetroMMO(), config);
	}
}

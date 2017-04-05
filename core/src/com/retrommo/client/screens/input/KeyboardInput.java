package com.retrommo.client.screens.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

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
public class KeyboardInput implements InputProcessor {
    //private final float moveSpeed = 105;
    private final float moveSpeed = 1;
    public boolean north, south, east, west, northWest, northEast, southWest, southEast;

    public float getYmovement() {
        if (north || northWest || northEast) {
            return moveSpeed;
        } else if (south || southWest || southEast) {
            return -moveSpeed;
        } else {
            return 0;
        }
    }

    public float getXmovement() {
        if (west || northWest || southWest) {
            return -moveSpeed;
        } else if (east || northEast || southEast) {
            return moveSpeed;
        } else {
            return 0;
        }
    }

    @Override
    public boolean keyDown(int keycode) {

        // get if diagonal movement
        if (keycode == Input.Keys.W && keycode == Input.Keys.A) {
            northWest = true;
        } else if (keycode == Input.Keys.W && keycode == Input.Keys.D) {
            northEast = true;
        } else if (keycode == Input.Keys.S && keycode == Input.Keys.A) {
            southWest = true;
        } else if (keycode == Input.Keys.S && keycode == Input.Keys.D) {
            southEast = true;
        }

        // if moving diagonally, then don't execute past the following line.
        if (northWest || northEast || southWest || southEast) return false;

        // calculate single direction movement
        if (keycode == Input.Keys.W) {
            north = true;
        } else if (keycode == Input.Keys.S) {
            south = true;
        } else if (keycode == Input.Keys.D) {
            east = true;
        } else if (keycode == Input.Keys.A) {
            west = true;
        }
        System.out.println("keydown");

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        // get if diagonal movement
        if (keycode == Input.Keys.W && keycode == Input.Keys.A) {
            northWest = false;
        } else if (keycode == Input.Keys.W && keycode == Input.Keys.D) {
            northEast = false;
        } else if (keycode == Input.Keys.S && keycode == Input.Keys.A) {
            southWest = false;
        } else if (keycode == Input.Keys.S && keycode == Input.Keys.D) {
            southEast = false;
        }

        // if moving diagonally, then don't execute past the following line.
        if (northWest || northEast || southWest || southEast) return false;

        // calculate single direction movement
        if (keycode == Input.Keys.W) {
            north = false;
        } else if (keycode == Input.Keys.S) {
            south = false;
        } else if (keycode == Input.Keys.D) {
            east = false;
        } else if (keycode == Input.Keys.A) {
            west = false;
        }

        System.out.println("keyup");

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}

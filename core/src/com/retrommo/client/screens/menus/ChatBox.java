package com.retrommo.client.screens.menus;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.retrommo.client.RetroMMO;
import com.retrommo.iocommon.wire.global.ChatMessage;

import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
public class ChatBox extends AbstractMenu {

    private float height = 200;
    private float width = 300;
    private TextArea chatArea;
    private ScrollPane scrollPane;
    private TextField chatField;
    private ChatMessage chatMessage;

    public ChatBox(RetroMMO retroMMO, Screen screen, Stage stage, Skin skin, boolean debug) {
        super(retroMMO, screen, stage, skin, debug);

        chatMessage = new ChatMessage();
    }

    @Override
    public void create() {
        this.setWidth(width);
        this.setHeight(height);
        this.pad(0, 10, 10, 0);

        setDebug(debug);
        stage.addActor(this);

        chatArea = new TextArea(null, skin);
        scrollPane = new ScrollPane(chatArea, skin);
        chatField = new TextField(null, skin);
        chatField.setFocusTraversal(false);
        chatField.setTextFieldListener(new ChatInput());

        this.add(scrollPane).expandX().expandY().fill();
        this.row().padTop(3);
        this.add(chatField).expandX().fill();
    }

    @Override
    public void hide() {
        setVisible(false);
    }

    private class ChatInput implements TextField.TextFieldListener {
        @Override
        public void keyTyped(TextField textField, char c) {
            if (c == '\t') return; // cancel tab
            if (c == '\n' || c == '\r') { //user hit enter

                String msg = chatField.getText();

                chatMessage.setMessage(""); // clear previous chat message
                chatMessage.setMessage(msg);

                // send message down the wire
                retroMMO.sendNetworkData(chatMessage);

                // clear typed text for next message
                chatField.setText("");

                // clear input focus after message sent
                stage.setKeyboardFocus(null);
            }
        }
    }
}

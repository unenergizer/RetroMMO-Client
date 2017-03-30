package com.retrommo.client.netty;

import com.retrommo.client.RetroMMO;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

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
public class ObjectEchoClientHandler extends ChannelInboundHandlerAdapter {

    private RetroMMO retroMMO;

    public ObjectEchoClientHandler(RetroMMO retroMMO) {
        this.retroMMO = retroMMO;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // save channel for later
        retroMMO.getMainPlayer().setChannel(ctx.channel());

        // send login info on netty first connect
        ctx.writeAndFlush(retroMMO.getMainMenuScreen().getLoginInfo());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        retroMMO.getListenerManager().runListeners(msg, ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
    }
}
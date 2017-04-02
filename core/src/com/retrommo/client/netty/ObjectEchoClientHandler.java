package com.retrommo.client.netty;

import com.retrommo.client.RetroMMO;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.AllArgsConstructor;

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
@AllArgsConstructor
class ObjectEchoClientHandler extends ChannelInboundHandlerAdapter {

    private final RetroMMO retroMMO;

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // Send login information
        ctx.writeAndFlush(retroMMO.getLoginScreen().getLoginInfo());

        // The channel is now active, save the channel for use later.
        retroMMO.setChannel(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        retroMMO.getNetworkListenerManager().runListeners(msg);
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
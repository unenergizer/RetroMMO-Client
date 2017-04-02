package com.retrommo.client.ecs.components;

import com.artemis.Component;

import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerIdComponent extends Component {
    private int serverId;
    private Channel channel;
}

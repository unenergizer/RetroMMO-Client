package com.retrommo.client.ecs.components;

import com.artemis.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PositionComponent extends Component {
    private float x, y;
}

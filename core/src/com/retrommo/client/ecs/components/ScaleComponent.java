package com.retrommo.client.ecs.components;

import com.artemis.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScaleComponent extends Component {
    private float scaleX = 1;
    private float scaleY = 1;
}

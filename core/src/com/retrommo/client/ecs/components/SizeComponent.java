package com.retrommo.client.ecs.components;

import com.artemis.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SizeComponent extends Component {

    private float width;
    private float height;
}

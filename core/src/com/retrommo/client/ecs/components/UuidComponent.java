package com.retrommo.client.ecs.components;

import com.badlogic.ashley.core.Component;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UuidComponent implements Component {
    private UUID uuid;
}

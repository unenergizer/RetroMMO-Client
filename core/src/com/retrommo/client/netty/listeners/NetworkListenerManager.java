package com.retrommo.client.netty.listeners;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

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

public class NetworkListenerManager {

    private final Map<EventMethodPair, com.retrommo.client.netty.ObjectListener> listeners = new HashMap<>();

    public void addListener(com.retrommo.client.netty.ObjectListener listener) {

        boolean annotationFound = false;
        Method[] methods = listener.getClass().getMethods();
        for (Method method : methods) {
            com.retrommo.client.netty.ObjectType[] eventTypes = method.getAnnotationsByType(com.retrommo.client.netty.ObjectType.class);
            if (eventTypes.length == 0) continue;

            if (eventTypes.length > 1)
                throw new RuntimeException("Cannot contain more than one eventType annotation.");

            annotationFound = true;

            Class<?>[] params = method.getParameterTypes();

            if (params.length > 1)
                throw new RuntimeException("Cannot contain more than one parameter in: " + listener);

            if (!eventTypes[0].getType().equals(params[0]))
                throw new RuntimeException("Annotation to parameter mismatch.");

            listeners.put(new EventMethodPair(eventTypes[0], method), listener);
        }

        if (!annotationFound)
            throw new RuntimeException("Failed to find annotation for: " + listener);
    }

    public void runListeners(Object object) {

        boolean packetFound = false;

        for (EventMethodPair it : listeners.keySet()) {
            if (object.getClass().equals(it.getObjectType().getType())) {
                packetFound = true;
                try {
                    System.out.println(object.getClass().getName());
                    it.getMethod().invoke(listeners.get(it), object);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        if (!packetFound) {
            throw new RuntimeException("Could not find the object type: " + object);
        }
    }

    @AllArgsConstructor
    @Getter
    private class EventMethodPair {
        private final com.retrommo.client.netty.ObjectType objectType;
        private final Method method;
    }
}

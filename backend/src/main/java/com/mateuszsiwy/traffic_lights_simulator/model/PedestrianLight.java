package com.mateuszsiwy.traffic_lights_simulator.model;

import lombok.Getter;

public class PedestrianLight {
    public enum State {
        RED, GREEN
    }

    @Getter
    private State state;
    @Getter
    private final Direction direction;
    private final int greenDuration = 2;
    private int timer = 0;

    public PedestrianLight(Direction direction) {
        this.direction = direction;
        this.state = State.RED;
    }

    public void setState(State state) {
        this.state = state;
        this.timer = 0;
    }

    public boolean isGreen() {
        return state == State.GREEN;
    }

    public void update() {
        timer++;
        if (timer >= greenDuration && state == State.GREEN) {
            setState(State.RED);
        }
    }
}
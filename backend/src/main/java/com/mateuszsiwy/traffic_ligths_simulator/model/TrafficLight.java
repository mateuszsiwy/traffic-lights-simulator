package com.mateuszsiwy.traffic_ligths_simulator.model;

import lombok.Getter;

public class TrafficLight {
    public enum State {
        RED, YELLOW, GREEN, GREEN_ARROW;
    }
    @Getter
    private State state;
    @Getter
    private Direction direction;
    private int greenDuration = 20;
    private int yellowDuration = 2;
    private int timer = 0;

    public TrafficLight(Direction direction) {
        this.direction = direction;
        this.state = State.RED;
    }
    public void setState(State state) {
        this.state = state;
        this.timer = 0;
    }
    public boolean isGreen() {
        return state == State.GREEN || state == State.GREEN_ARROW;
    }
    public void update() {
        timer++;
        if (timer >= greenDuration && state == State.GREEN) {
            setState(State.YELLOW);
            timer = 0;
        } else if(timer >= yellowDuration && state == State.YELLOW) {
            setState(State.RED);
            timer = 0;
        } else if(timer >= greenDuration && state == State.GREEN_ARROW) {
            setState(State.RED);
            timer = 0;
        }
    }
    public void adjustGreenLightDuration(int numOfCars) {
        this.greenDuration = Math.min(this.greenDuration, numOfCars * 5);
    }
}

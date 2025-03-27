package com.mateuszsiwy.traffic_lights_simulator.model;

import lombok.Getter;
import lombok.Setter;

public class Vehicle {
    public enum Turn {
        STRAIGHT, LEFT, RIGHT;
    }
    @Getter
    private String vehicleId;
    @Getter
    private Direction startRoad;
    @Getter
    private Direction endRoad;
    @Getter
    @Setter
    private int weight = 1;

    public Vehicle(String vehicleId, Direction start, Direction end) {
        this.startRoad = start;
        this.endRoad = end;
        this.vehicleId = vehicleId;
    }
    public Turn getTurn(){
        if (endRoad == startRoad.getOpposite()) {
            return Turn.STRAIGHT;
        }else if((startRoad == Direction.NORTH && endRoad == Direction.EAST) ||
                (startRoad == Direction.EAST && endRoad == Direction.SOUTH) ||
                (startRoad == Direction.SOUTH && endRoad == Direction.WEST) ||
                (startRoad == Direction.WEST && endRoad == Direction.NORTH)) {
            return Turn.LEFT;
        }else {
            return Turn.RIGHT;
        }
    }
    public void increaseWeight(){
        weight*=2;
    }
}

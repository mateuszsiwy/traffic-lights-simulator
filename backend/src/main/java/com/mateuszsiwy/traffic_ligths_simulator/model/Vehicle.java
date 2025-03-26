package com.mateuszsiwy.traffic_ligths_simulator.model;

import lombok.Getter;

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

    public Vehicle(Direction start, Direction end, String vehicleId) {
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
}

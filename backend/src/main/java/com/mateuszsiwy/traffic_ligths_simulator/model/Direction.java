package com.mateuszsiwy.traffic_ligths_simulator.model;

public enum Direction {
    NORTH, SOUTH, EAST, WEST;

    public Direction getOpposite() {
        switch (this) {
            case NORTH:
                return SOUTH;
            case SOUTH:
                return NORTH;
            case EAST:
                return WEST;
            case WEST:
                return EAST;
            default:
                throw new IllegalStateException("Unexpected direction: " + this);
        }
    }
}

package com.mateuszsiwy.traffic_lights_simulator.model;

public enum PedestrianDirection {
    NORTH_EAST, NORTH_WEST, SOUTH_EAST, SOUTH_WEST;

    public static PedestrianDirection fromRoadDirections(Direction origin, Direction target) {
        if (origin == Direction.NORTH && target == Direction.EAST) return NORTH_EAST;
        if (origin == Direction.NORTH && target == Direction.WEST) return NORTH_WEST;
        if (origin == Direction.SOUTH && target == Direction.EAST) return SOUTH_EAST;
        if (origin == Direction.SOUTH && target == Direction.WEST) return SOUTH_WEST;
        if (origin == Direction.EAST && target == Direction.NORTH) return NORTH_EAST;
        if (origin == Direction.EAST && target == Direction.SOUTH) return SOUTH_EAST;
        if (origin == Direction.WEST && target == Direction.NORTH) return NORTH_WEST;
        if (origin == Direction.WEST && target == Direction.SOUTH) return SOUTH_WEST;

        throw new IllegalArgumentException("Invalid pedestrian crossing from " + origin + " to " + target);
    }

    public Direction getAssociatedRoad() {
        switch (this) {
            case NORTH_EAST:
            case NORTH_WEST:
                return Direction.NORTH;
            case SOUTH_EAST:
            case SOUTH_WEST:
                return Direction.SOUTH;
            default:
                throw new IllegalStateException("Unexpected pedestrian direction: " + this);
        }
    }
    public Direction getCrosswalkDirection() {
        switch (this) {
            case NORTH_EAST:
            case SOUTH_EAST:
                return Direction.EAST;
            case NORTH_WEST:
            case SOUTH_WEST:
                return Direction.WEST;
            default:
                throw new IllegalStateException("Unexpected pedestrian direction: " + this);
        }
    }
}
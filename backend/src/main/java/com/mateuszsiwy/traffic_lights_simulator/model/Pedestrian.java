package com.mateuszsiwy.traffic_lights_simulator.model;

import lombok.Getter;
import lombok.Setter;

public class Pedestrian {
    @Getter
    private final String pedestrianId;
    @Getter
    private final PedestrianDirection origin;
    @Getter
    private final PedestrianDirection destination;
    @Getter
    @Setter
    private int weight = 1;
    @Getter
    private boolean hasCrossedHorizontal = false;
    @Getter
    private boolean hasCrossedVertical = false;

    public Pedestrian(String pedestrianId, PedestrianDirection origin, PedestrianDirection destination) {
        this.pedestrianId = pedestrianId;
        this.origin = origin;
        this.destination = destination;
    }

    // Determine if this pedestrian needs to cross horizontally (East-West)
    public boolean needsToGoHorizontal() {
        return (origin == PedestrianDirection.NORTH_EAST && destination == PedestrianDirection.NORTH_WEST) ||
               (origin == PedestrianDirection.NORTH_WEST && destination == PedestrianDirection.NORTH_EAST) ||
               (origin == PedestrianDirection.SOUTH_EAST && destination == PedestrianDirection.SOUTH_WEST) ||
               (origin == PedestrianDirection.SOUTH_WEST && destination == PedestrianDirection.SOUTH_EAST);
    }

    public boolean needsToGoVertical() {
        return (origin == PedestrianDirection.NORTH_EAST && destination == PedestrianDirection.SOUTH_EAST) ||
               (origin == PedestrianDirection.SOUTH_EAST && destination == PedestrianDirection.NORTH_EAST) ||
               (origin == PedestrianDirection.NORTH_WEST && destination == PedestrianDirection.SOUTH_WEST) ||
               (origin == PedestrianDirection.SOUTH_WEST && destination == PedestrianDirection.NORTH_WEST) ||

               (origin == PedestrianDirection.NORTH_EAST && destination == PedestrianDirection.SOUTH_WEST) ||
               (origin == PedestrianDirection.SOUTH_WEST && destination == PedestrianDirection.NORTH_EAST) ||
               (origin == PedestrianDirection.NORTH_WEST && destination == PedestrianDirection.SOUTH_EAST) ||
               (origin == PedestrianDirection.SOUTH_EAST && destination == PedestrianDirection.NORTH_WEST);
    }

    public Direction getHorizontalCrosswalkDirection() {
        if (origin == PedestrianDirection.NORTH_EAST || origin == PedestrianDirection.NORTH_WEST) {
            return Direction.NORTH;
        } else {
            return Direction.SOUTH;
        }
    }

    public Direction getVerticalCrosswalkDirection() {
        if (origin == PedestrianDirection.NORTH_EAST || origin == PedestrianDirection.SOUTH_EAST) {
            return Direction.EAST;
        } else {
            return Direction.WEST;
        }
    }

    public void markHorizontalCrossed() {
        this.hasCrossedHorizontal = true;
    }

    public void markVerticalCrossed() {
        this.hasCrossedVertical = true;
    }

    public boolean hasCompletedCrossing() {
        return (!needsToGoHorizontal() || hasCrossedHorizontal) &&
               (!needsToGoVertical() || hasCrossedVertical);
    }

    public void increaseWeight() {
        weight *= 2;
    }
}
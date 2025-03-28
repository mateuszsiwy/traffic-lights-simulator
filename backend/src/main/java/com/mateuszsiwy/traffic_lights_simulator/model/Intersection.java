package com.mateuszsiwy.traffic_lights_simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Intersection {
    private final Map<Direction, Road> roads;
    private final Map<Direction, Crosswalk> crosswalks;

    public Intersection() {
        roads = Map.of(
                Direction.NORTH, new Road(Direction.NORTH),
                Direction.SOUTH, new Road(Direction.SOUTH),
                Direction.EAST, new Road(Direction.EAST),
                Direction.WEST, new Road(Direction.WEST)
        );

        crosswalks = new HashMap<>();
        for (Direction dir : Direction.values()) {
            crosswalks.put(dir, new Crosswalk(dir));
        }
    }

    public Road getRoad(Direction direction) {
        return roads.get(direction);
    }

    public Crosswalk getCrosswalk(Direction direction) {
        return crosswalks.get(direction);
    }

    public Map<Direction, Road> getRoads() {
        return roads;
    }

    public Map<Direction, Crosswalk> getCrosswalks() {
        return crosswalks;
    }

    public void addVehicle(Vehicle vehicle) {
        roads.get(vehicle.getStartRoad()).addVehicle(vehicle);
    }

    public void addPedestrian(Pedestrian pedestrian) {
        if (pedestrian.needsToGoHorizontal()) {
            Direction horizontalDir = pedestrian.getHorizontalCrosswalkDirection();
            crosswalks.get(horizontalDir).addPedestrian(pedestrian);
        }

        if (pedestrian.needsToGoVertical()) {
            Direction verticalDir = pedestrian.getVerticalCrosswalkDirection();
            crosswalks.get(verticalDir).addPedestrian(pedestrian);
        }
    }

    public IntersectionStepResult step() {
        List<Vehicle> leftVehicles = new ArrayList<>();
        List<Pedestrian> crossedPedestrians = new ArrayList<>();

        for (Road road : roads.values()) {
            leftVehicles.addAll(road.processTraffic());
        }

        for (Crosswalk crosswalk : crosswalks.values()) {
            crossedPedestrians.addAll(crosswalk.processCrossing());
        }

        for (Road road : roads.values()) {
            road.getTrafficLight().update();
        }

        for (Crosswalk crosswalk : crosswalks.values()) {
            crosswalk.getPedestrianLight().update();
        }

        for (Road road : roads.values()) {
            road.updateVehicleWeights();
        }

        for (Crosswalk crosswalk : crosswalks.values()) {
            crosswalk.updatePedestrianWeights();
        }

        return new IntersectionStepResult(leftVehicles, crossedPedestrians);
    }
}
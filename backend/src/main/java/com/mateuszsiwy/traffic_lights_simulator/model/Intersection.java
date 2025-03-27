package com.mateuszsiwy.traffic_lights_simulator.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Intersection {
    private Map<Direction, Road> roads;

    public Intersection(){
        roads = Map.of(
                Direction.NORTH, new Road(Direction.NORTH),
                Direction.SOUTH, new Road(Direction.SOUTH),
                Direction.EAST, new Road(Direction.EAST),
                Direction.WEST, new Road(Direction.WEST)
        );
    }
    public Road getRoad(Direction direction){
        return roads.get(direction);
    }
    public Map<Direction, Road> getRoads(){
        return roads;
    }
    public void addVehicle(Vehicle vehicle){
        roads.get(vehicle.getStartRoad()).addVehicle(vehicle);
    }
    public List<Vehicle> step(){
        List<Vehicle> leftVehicles = new ArrayList<>();

        for(Road road : roads.values()){
            leftVehicles.addAll(road.processTraffic());
        }
        for (Road road : roads.values()) {
            road.getTrafficLight().update();
        }
        for (Road road: roads.values()){
            road.updateVehicleWeights();
        }
        return leftVehicles;
    }
}

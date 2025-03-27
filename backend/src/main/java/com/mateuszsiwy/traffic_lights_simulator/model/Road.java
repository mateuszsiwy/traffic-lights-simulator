package com.mateuszsiwy.traffic_lights_simulator.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Road {
    @Getter
    private Direction direction;
    @Getter
    private TrafficLight trafficLight;
    @Getter
    private Queue<Vehicle> vehicles;

    public Road(Direction direction) {
        this.direction = direction;
        this.trafficLight = new TrafficLight(direction);
        this.vehicles = new LinkedList<>();
    }
    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }
    public int getNumberOfVehicles() {
        return vehicles.size();
    }
    public int getTotalWeight() {
        return vehicles.stream().mapToInt(Vehicle::getWeight).sum();
    }
    public void updateVehicleWeights() {
        vehicles.forEach(Vehicle::increaseWeight);
    }
    public List<Vehicle> processTraffic(){
        List<Vehicle> leftVehicles = new ArrayList<>();

        int maxVehiclesForGreenLight = 1;
        if(trafficLight.isGreen() && !vehicles.isEmpty()){
            int vehiclesToProcess = Math.min(vehicles.size(), maxVehiclesForGreenLight);
            for(int i = 0; i < vehiclesToProcess; i++){
                Vehicle vehicle = vehicles.poll();
                if(vehicle != null){
                    leftVehicles.add(vehicle);
                }
            }
        }

        return leftVehicles;
    }
}

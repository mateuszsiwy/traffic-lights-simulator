package com.mateuszsiwy.traffic_lights_simulator.simulation;

import com.mateuszsiwy.traffic_lights_simulator.model.Direction;
import com.mateuszsiwy.traffic_lights_simulator.model.Intersection;
import com.mateuszsiwy.traffic_lights_simulator.model.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class Simulation {
    private Intersection intersection;
    private IntelligentTrafficLights intelligentTrafficLights;
    @Getter
    private List<Map<String, List<String>>> stepStatuses;
    public Simulation(){
        intersection = new Intersection();
        intelligentTrafficLights = new IntelligentTrafficLights();
        stepStatuses = new ArrayList<>();
    }
    public void addVehicle(String vehicleId, String startRoad, String endRoad) {
        Direction start = Direction.valueOf(startRoad.toUpperCase());
        Direction end = Direction.valueOf(endRoad.toUpperCase());
        Vehicle vehicle = new Vehicle(vehicleId, start, end);
        intersection.addVehicle(vehicle);
    }
    public void step(){
        intelligentTrafficLights.updateTrafficLights(intersection);
        List<Vehicle> leftVehicles = intersection.step();
        List<String> leftVehiclesIds = leftVehicles.stream().map(Vehicle::getVehicleId).toList();
        Map<String, List<String>> stepStatus = new HashMap<>();
        stepStatus.put("leftVehicles", leftVehiclesIds);
        stepStatuses.add(stepStatus);
    }

}

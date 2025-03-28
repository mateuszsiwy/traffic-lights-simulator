package com.mateuszsiwy.traffic_lights_simulator.simulation;

import com.mateuszsiwy.traffic_lights_simulator.model.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class Simulation {
    @Getter
    private Intersection intersection;
    private IntelligentTrafficLights intelligentTrafficLights;
    @Getter
    private List<Map<String, List<String>>> stepStatuses;
    public Simulation(){
        intersection = new Intersection();
        intelligentTrafficLights = new IntelligentTrafficLights();
        stepStatuses = new ArrayList<>();
    }
    public void addPedestrian(String pedestrianId, String startRoad, String endRoad) {
        PedestrianDirection start = PedestrianDirection.valueOf(startRoad.toUpperCase());
        PedestrianDirection end = PedestrianDirection.valueOf(endRoad.toUpperCase());
        Pedestrian pedestrian = new Pedestrian(pedestrianId, start, end);
        intersection.addPedestrian(pedestrian);
    }
    public void addVehicle(String vehicleId, String startRoad, String endRoad) {
        Direction start = Direction.valueOf(startRoad.toUpperCase());
        Direction end = Direction.valueOf(endRoad.toUpperCase());
        Vehicle vehicle = new Vehicle(vehicleId, start, end);
        intersection.addVehicle(vehicle);
    }
    public void step(){
        intelligentTrafficLights.updateTrafficLights(intersection);
        IntersectionStepResult result = intersection.step();
        List<String> leftVehiclesIds = result.getLeftVehicles().stream().map(Vehicle::getVehicleId).collect(Collectors.toList());
        List<String> crossedPedestriansIds = result.getCrossedPedestrians().stream().map(Pedestrian::getPedestrianId).toList();
        Map<String, List<String>> stepStatus = new HashMap<>();
        stepStatus.put("leftVehicles", leftVehiclesIds);
        stepStatus.put("crossedPedestrians", crossedPedestriansIds);
        stepStatuses.add(stepStatus);
    }

}

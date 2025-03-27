package com.mateuszsiwy.traffic_lights_simulator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class IntersectionStepResult {
    private List<Vehicle> leftVehicles;
    private List<Pedestrian> crossedPedestrians;
}
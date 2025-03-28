package com.mateuszsiwy.traffic_lights_simulator.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class IntersectionTest {
    private Intersection intersection;

    @BeforeEach
    public void setup(){
        intersection = new Intersection();
    }

    @Test
    void givenVehicle_whenAddVehicle_thenVehicleIsAddedToRoad() {
        Vehicle vehicle = new Vehicle("vehicleTest", Direction.NORTH, Direction.SOUTH);
        intersection.addVehicle(vehicle);
        assert(intersection.getRoad(Direction.NORTH).getVehicles().contains(vehicle));
    }

    @Test
    void givenGreenLightAndVehicle_whenStep_thenVehicleLeavesIntersection() {
        Vehicle vehicle = new Vehicle("vehicleTest", Direction.NORTH, Direction.SOUTH);
        Road road = intersection.getRoad(Direction.NORTH);
        TrafficLight trafficLight = road.getTrafficLight();
        trafficLight.setState(TrafficLight.State.GREEN);

        intersection.addVehicle(vehicle);
        IntersectionStepResult intersectionStepResult = intersection.step();
        List<Vehicle> leftVehicles = intersectionStepResult.getLeftVehicles();
        assertFalse(road.getVehicles().contains(vehicle));
        assertTrue(leftVehicles.contains(vehicle));
    }
}

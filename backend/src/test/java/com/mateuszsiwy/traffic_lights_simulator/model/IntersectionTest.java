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
    void testAddVehicle(){
        Vehicle vehicle = new Vehicle("vehicleTest", Direction.NORTH, Direction.SOUTH);
        intersection.addVehicle(vehicle);
        assert(intersection.getRoad(Direction.NORTH).getVehicles().contains(vehicle));
    }

    @Test
    void testStep(){
        Vehicle vehicle = new Vehicle("vehicleTest", Direction.NORTH, Direction.SOUTH);
        Road road = intersection.getRoad(Direction.NORTH);
        TrafficLight trafficLight = road.getTrafficLight();
        trafficLight.setState(TrafficLight.State.GREEN);

        intersection.addVehicle(vehicle);
        List<Vehicle> leftVehicles = intersection.step();
        assertFalse(road.getVehicles().contains(vehicle));
        assertTrue(leftVehicles.contains(vehicle));
    }
}

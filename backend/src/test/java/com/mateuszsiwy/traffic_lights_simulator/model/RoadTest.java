package com.mateuszsiwy.traffic_lights_simulator.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RoadTest {
    private Road road;
    @BeforeEach
    public void setUp() {
        road = new Road(Direction.NORTH);
    }

    @Test
    public void testAddVehicle() {
        Vehicle vehicle = new Vehicle("vehicleTest", Direction.NORTH, Direction.SOUTH);
        road.addVehicle(vehicle);
        assert(road.getVehicles().contains(vehicle));
    }
    @Test
    public void testGetNumberOfVehicles() {
        Vehicle vehicle = new Vehicle("vehicleTest", Direction.NORTH, Direction.SOUTH);
        Vehicle vehicle2 = new Vehicle("vehicleTest2", Direction.NORTH, Direction.SOUTH);
        road.addVehicle(vehicle);
        road.addVehicle(vehicle2);
        assert(road.getNumberOfVehicles() == 2);
    }
    @Test
    public void testProcessTraffic() {
        Vehicle vehicle = new Vehicle("vehicleTest", Direction.NORTH, Direction.SOUTH);
        Vehicle vehicle2 = new Vehicle("vehicleTest2", Direction.NORTH, Direction.SOUTH);
        road.getTrafficLight().setState(TrafficLight.State.GREEN);
        road.addVehicle(vehicle);
        road.addVehicle(vehicle2);
        assert(road.processTraffic().size() == 1);
        assert(road.processTraffic().size() == 1);
        assert(road.processTraffic().isEmpty()); // checking if processTraffic method processes at most one vehicle
    }
}

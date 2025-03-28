package com.mateuszsiwy.traffic_lights_simulator.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {

    @Test
    void testGetTurnStraight() {
        Vehicle vehicle = new Vehicle("1", Direction.NORTH, Direction.SOUTH);
        assertEquals(Vehicle.Turn.STRAIGHT, vehicle.getTurn());
    }

    @Test
    void testGetTurnLeft() {
        Vehicle vehicle = new Vehicle("1", Direction.NORTH, Direction.EAST);
        assertEquals(Vehicle.Turn.LEFT, vehicle.getTurn());
    }

    @Test
    void testGetTurnRight() {
        Vehicle vehicle = new Vehicle("1", Direction.NORTH, Direction.WEST);
        assertEquals(Vehicle.Turn.RIGHT, vehicle.getTurn());
    }

    @Test
    void testIncreaseWeight() {
        Vehicle vehicle = new Vehicle("1", Direction.NORTH, Direction.SOUTH);
        vehicle.increaseWeight();
        assertEquals(2, vehicle.getWeight());
    }
}

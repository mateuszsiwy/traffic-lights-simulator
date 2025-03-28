package com.mateuszsiwy.traffic_lights_simulator.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PedestrianDirectionTest {

    @Test
    void testFromRoadDirections() {
        assertEquals(PedestrianDirection.NORTH_EAST, PedestrianDirection.fromRoadDirections(Direction.NORTH, Direction.EAST));
    }

    @Test
    void testGetAssociatedRoad() {
        assertEquals(Direction.NORTH, PedestrianDirection.NORTH_EAST.getAssociatedRoad());
    }

    @Test
    void testGetCrosswalkDirection() {
        assertEquals(Direction.EAST, PedestrianDirection.NORTH_EAST.getCrosswalkDirection());
    }
}

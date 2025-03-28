package com.mateuszsiwy.traffic_lights_simulator.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PedestrianTest {

    @Test
    void testNeedsToGoHorizontal() {
        Pedestrian pedestrian = new Pedestrian("1", PedestrianDirection.NORTH_EAST, PedestrianDirection.NORTH_WEST);
        assertTrue(pedestrian.needsToGoHorizontal());
    }

    @Test
    void testNeedsToGoVertical() {
        Pedestrian pedestrian = new Pedestrian("1", PedestrianDirection.NORTH_EAST, PedestrianDirection.SOUTH_EAST);
        assertTrue(pedestrian.needsToGoVertical());
    }

    @Test
    void testIncreaseWeight() {
        Pedestrian pedestrian = new Pedestrian("1", PedestrianDirection.NORTH_EAST, PedestrianDirection.NORTH_WEST);
        pedestrian.increaseWeight();
        assertEquals(2, pedestrian.getWeight());
    }

    @Test
    void testHasCompletedCrossing() {
        Pedestrian pedestrian = new Pedestrian("1", PedestrianDirection.NORTH_EAST, PedestrianDirection.NORTH_WEST);
        pedestrian.markHorizontalCrossed();
        assertTrue(pedestrian.hasCompletedCrossing());
    }
}

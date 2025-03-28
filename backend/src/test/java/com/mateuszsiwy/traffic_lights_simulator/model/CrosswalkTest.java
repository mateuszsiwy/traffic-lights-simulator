package com.mateuszsiwy.traffic_lights_simulator.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CrosswalkTest {
    private Crosswalk crosswalk;
    private Pedestrian pedestrian;

    @BeforeEach
    void setUp() {
        crosswalk = new Crosswalk(Direction.NORTH);
        pedestrian = new Pedestrian("1", PedestrianDirection.NORTH_EAST, PedestrianDirection.NORTH_WEST);
    }

    @Test
    void testAddPedestrian() {
        crosswalk.addPedestrian(pedestrian);
        assertEquals(1, crosswalk.getNumberOfPedestrians());
    }

    @Test
    void testGetTotalWeight() {
        crosswalk.addPedestrian(pedestrian);
        assertEquals(1, crosswalk.getTotalWeight());
    }

    @Test
    void testUpdatePedestrianWeights() {
        crosswalk.addPedestrian(pedestrian);
        crosswalk.updatePedestrianWeights();
        assertEquals(2, pedestrian.getWeight());
    }

    @Test
    void testProcessCrossing() {
        crosswalk.addPedestrian(pedestrian);
        crosswalk.getPedestrianLight().setState(PedestrianLight.State.GREEN);
        assertEquals(1, crosswalk.processCrossing().size());
    }
}

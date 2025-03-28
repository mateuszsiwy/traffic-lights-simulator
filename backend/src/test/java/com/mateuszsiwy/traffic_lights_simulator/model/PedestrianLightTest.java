package com.mateuszsiwy.traffic_lights_simulator.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PedestrianLightTest {
    private PedestrianLight pedestrianLight;

    @BeforeEach
    void setUp() {
        pedestrianLight = new PedestrianLight(Direction.NORTH);
    }

    @Test
    void testSetState() {
        pedestrianLight.setState(PedestrianLight.State.GREEN);
        assertEquals(PedestrianLight.State.GREEN, pedestrianLight.getState());
    }

    @Test
    void testIsGreen() {
        pedestrianLight.setState(PedestrianLight.State.GREEN);
        assertTrue(pedestrianLight.isGreen());
    }

    @Test
    void testUpdate() {
        pedestrianLight.setState(PedestrianLight.State.GREEN);
        pedestrianLight.update();
        pedestrianLight.update();
        assertEquals(PedestrianLight.State.RED, pedestrianLight.getState());
    }
}

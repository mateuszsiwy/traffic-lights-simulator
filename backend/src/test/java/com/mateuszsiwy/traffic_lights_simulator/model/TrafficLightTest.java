package com.mateuszsiwy.traffic_lights_simulator.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrafficLightTest {
    private TrafficLight trafficLight;

    @BeforeEach
    void setUp() {
        trafficLight = new TrafficLight(Direction.NORTH);
    }

    @Test
    void testSetState() {
        trafficLight.setState(TrafficLight.State.GREEN);
        assertEquals(TrafficLight.State.GREEN, trafficLight.getState());
    }

    @Test
    void testIsGreen() {
        trafficLight.setState(TrafficLight.State.GREEN);
        assertTrue(trafficLight.isGreen());
    }

    @Test
    void testUpdate() {
        trafficLight.setState(TrafficLight.State.GREEN);
        trafficLight.update();
        assertEquals(TrafficLight.State.YELLOW, trafficLight.getState());
    }

    @Test
    void testAdjustGreenLightDuration() {
        trafficLight.adjustGreenLightDuration(3);
        trafficLight.setState(TrafficLight.State.GREEN);
        for (int i = 0; i < 3 * 5; i++) {
            trafficLight.update();
        }
        assertEquals(TrafficLight.State.RED, trafficLight.getState());
    }
}

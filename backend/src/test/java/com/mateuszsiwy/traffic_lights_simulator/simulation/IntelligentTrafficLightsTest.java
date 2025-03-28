package com.mateuszsiwy.traffic_lights_simulator.simulation;

import com.mateuszsiwy.traffic_lights_simulator.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class IntelligentTrafficLightsTest {
    private IntelligentTrafficLights intelligentTrafficLights;
    private Intersection intersection;

    @BeforeEach
    public void setUp() {
        intelligentTrafficLights = new IntelligentTrafficLights();
        intersection = new Intersection();
    }

    @Test
    public void givenVehicles_whenUpdateTrafficLights_thenTrafficLightsAreUpdated() {
        intersection.getRoad(Direction.NORTH).addVehicle(new Vehicle("vehicle2", Direction.NORTH, Direction.SOUTH));

        intelligentTrafficLights.updateTrafficLights(intersection);

        assertEquals(TrafficLight.State.GREEN, intersection.getRoad(Direction.NORTH).getTrafficLight().getState());
    }

    @Test
    public void givenPedestrians_whenUpdateTrafficLights_thenPedestrianLightsAreUpdated() {
        Crosswalk crosswalk = new Crosswalk(Direction.NORTH);
        crosswalk.addPedestrian(new Pedestrian("pedestrian1", PedestrianDirection.NORTH_EAST, PedestrianDirection.SOUTH_EAST));
        intersection.getCrosswalks().put(Direction.NORTH, crosswalk);

        intelligentTrafficLights.updateTrafficLights(intersection);

        assertEquals(PedestrianLight.State.GREEN, crosswalk.getPedestrianLight().getState());
    }
}

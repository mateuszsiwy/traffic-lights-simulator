package com.mateuszsiwy.traffic_lights_simulator.simulation;

import com.mateuszsiwy.traffic_lights_simulator.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class SimulationTest {
    private Simulation simulation;

    @BeforeEach
    public void setUp() {
        simulation = new Simulation();
    }

    @Test
    public void givenVehicle_whenAddVehicle_thenVehicleIsAddedToIntersection() {
        simulation.addVehicle("vehicle1", "NORTH", "SOUTH");
        assertEquals(1, simulation.getIntersection().getRoad(Direction.NORTH).getNumberOfVehicles());
    }

    @Test
    public void givenPedestrian_whenAddPedestrian_thenPedestrianIsAddedToCrosswalk() {
        simulation.addPedestrian("pedestrian1", "NORTH_EAST", "SOUTH_EAST");
        assertEquals(1, simulation.getIntersection().getCrosswalk(Direction.EAST).getNumberOfPedestrians());
    }

    @Test
    public void givenVehiclesAndPedestrians_whenStep_thenStepStatusesAreUpdated() {
        simulation.addVehicle("vehicle1", "NORTH", "SOUTH");
        simulation.addPedestrian("pedestrian1", "NORTH_EAST", "SOUTH_EAST");

        simulation.step();

        List<Map<String, List<String>>> stepStatuses = simulation.getStepStatuses();
        assertFalse(stepStatuses.isEmpty());
    }
}

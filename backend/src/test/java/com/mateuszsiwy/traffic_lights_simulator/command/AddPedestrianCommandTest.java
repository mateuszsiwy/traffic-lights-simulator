package com.mateuszsiwy.traffic_lights_simulator.command;

import com.mateuszsiwy.traffic_lights_simulator.simulation.Simulation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class AddPedestrianCommandTest {
    private Simulation simulation;
    private AddPedestrianCommand command;

    @BeforeEach
    public void setUp() {
        simulation = mock(Simulation.class);
        command = new AddPedestrianCommand("pedestrian1", "NORTH_EAST", "SOUTH_EAST");
    }

    @Test
    public void givenCommand_whenExecute_thenPedestrianIsAdded() {
        command.execute(simulation);
        verify(simulation, times(1)).addPedestrian("pedestrian1", "NORTH_EAST", "SOUTH_EAST");
    }
}

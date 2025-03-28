package com.mateuszsiwy.traffic_lights_simulator.command;

import com.mateuszsiwy.traffic_lights_simulator.simulation.Simulation;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

public class StepCommandTest {

    @Test
    public void testExecute() {
        Simulation simulation = mock(Simulation.class);
        StepCommand command = new StepCommand();

        command.execute(simulation);

        verify(simulation, times(1)).step();
    }
}

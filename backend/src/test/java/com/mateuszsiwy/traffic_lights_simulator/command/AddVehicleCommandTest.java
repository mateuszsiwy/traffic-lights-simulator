package com.mateuszsiwy.traffic_lights_simulator.command;

import com.mateuszsiwy.traffic_lights_simulator.simulation.Simulation;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

public class AddVehicleCommandTest {

    @Test
    public void testExecute() {
        Simulation simulation = mock(Simulation.class);
        AddVehicleCommand command = new AddVehicleCommand("vehicle1", "start", "end");

        command.execute(simulation);

        verify(simulation, times(1)).addVehicle("vehicle1", "start", "end");
    }
}

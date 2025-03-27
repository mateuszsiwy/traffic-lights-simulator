package com.mateuszsiwy.traffic_lights_simulator.command;

import com.mateuszsiwy.traffic_lights_simulator.simulation.Simulation;

public class StepCommand implements Command {
    @Override
    public void execute(Simulation simulation) {
        simulation.step();
    }
}

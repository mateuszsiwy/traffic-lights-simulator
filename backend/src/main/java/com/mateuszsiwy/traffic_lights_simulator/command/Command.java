package com.mateuszsiwy.traffic_lights_simulator.command;

import com.mateuszsiwy.traffic_lights_simulator.simulation.Simulation;

public interface Command {
    void execute(Simulation simulation);
}

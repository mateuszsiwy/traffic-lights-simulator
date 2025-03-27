package com.mateuszsiwy.traffic_lights_simulator.command;

import com.mateuszsiwy.traffic_lights_simulator.simulation.Simulation;

public class AddPedestrianCommand implements Command {
    private final String pedestrianId;
    private final String origin;
    private final String destination;

    public AddPedestrianCommand(String pedestrianId, String origin, String destination) {
        this.pedestrianId = pedestrianId;
        this.origin = origin;
        this.destination = destination;
    }

    @Override
    public void execute(Simulation simulation) {
        simulation.addPedestrian(pedestrianId, origin, destination);
    }
}
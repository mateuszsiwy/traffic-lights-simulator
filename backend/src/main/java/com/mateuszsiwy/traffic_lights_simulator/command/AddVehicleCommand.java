package com.mateuszsiwy.traffic_lights_simulator.command;

import com.mateuszsiwy.traffic_lights_simulator.simulation.Simulation;

public class AddVehicleCommand implements Command {
    private final String vehicleId;
    private final String start;
    private final String end;

    public AddVehicleCommand(String vehicleId, String start, String end) {
        this.vehicleId = vehicleId;
        this.start = start;
        this.end = end;
    }

    @Override
    public void execute(Simulation simulation) {
        simulation.addVehicle(vehicleId, start, end);
    }

}

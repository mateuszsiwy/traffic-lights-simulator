package com.mateuszsiwy.traffic_lights_simulator.service;

import com.mateuszsiwy.traffic_lights_simulator.command.AddPedestrianCommand;
import com.mateuszsiwy.traffic_lights_simulator.command.AddVehicleCommand;
import com.mateuszsiwy.traffic_lights_simulator.command.Command;
import com.mateuszsiwy.traffic_lights_simulator.command.StepCommand;
import com.mateuszsiwy.traffic_lights_simulator.model.*;
import com.mateuszsiwy.traffic_lights_simulator.simulation.Simulation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SimulationService {

    private Simulation currentSimulation;

    public Simulation runSimulation(List<Command> commands) {
        currentSimulation = new Simulation();

        for (Command command : commands) {
            command.execute(currentSimulation);
        }

        return currentSimulation;
    }
    public List<Command> convertToCommandList(List<Map<String, Object>> commandsMap) {
        List<Command> commands = new ArrayList<>();

        for (Map<String, Object> commandMap : commandsMap) {
            String type = (String) commandMap.get("type");

            if ("addVehicle".equals(type)) {
                String vehicleId = (String) commandMap.get("vehicleId");
                String startRoad = (String) commandMap.get("startRoad");
                String endRoad = (String) commandMap.get("endRoad");
                commands.add(new AddVehicleCommand(vehicleId, startRoad, endRoad));
            } else if ("addPedestrian".equals(type)) {
                String pedestrianId = (String) commandMap.get("pedestrianId");
                String origin = (String) commandMap.get("origin");
                String destination = (String) commandMap.get("destination");
                commands.add(new AddPedestrianCommand(pedestrianId, origin, destination));
            } else if ("step".equals(type)) {
                commands.add(new StepCommand());
            }
        }

        return commands;
    }

    public Map<String, Object> getCurrentState() {
        if (currentSimulation == null) {
            return Map.of("error", "No simulation running");
        }

        Map<String, Object> state = new HashMap<>();
        Intersection intersection = currentSimulation.getIntersection();

        Map<String, Object> roads = new HashMap<>();
        for (Direction dir : Direction.values()) {
            Road road = intersection.getRoad(dir);
            Map<String, Object> roadInfo = new HashMap<>();
            roadInfo.put("vehicleCount", road.getNumberOfVehicles());
            roadInfo.put("weight", road.getTotalWeight());
            roadInfo.put("trafficLightState", road.getTrafficLight().getState().toString());
            roads.put(dir.toString(), roadInfo);
        }
        state.put("roads", roads);

        Map<String, Object> crosswalks = new HashMap<>();
        for (Direction dir : Direction.values()) {
            Crosswalk crosswalk = intersection.getCrosswalk(dir);
            Map<String, Object> crosswalkInfo = new HashMap<>();
            crosswalkInfo.put("pedestrianCount", crosswalk.getNumberOfPedestrians());
            crosswalkInfo.put("weight", crosswalk.getTotalWeight());
            crosswalkInfo.put("lightState", crosswalk.getPedestrianLight().getState().toString());
            crosswalks.put(dir.toString(), crosswalkInfo);
        }
        state.put("crosswalks", crosswalks);

        state.put("stepStatuses", currentSimulation.getStepStatuses());

        return state;
    }
}
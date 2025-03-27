package com.mateuszsiwy.traffic_lights_simulator.io;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mateuszsiwy.traffic_lights_simulator.command.AddVehicleCommand;
import com.mateuszsiwy.traffic_lights_simulator.command.Command;
import com.mateuszsiwy.traffic_lights_simulator.command.StepCommand;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonReader {
    public List<Command> readCommands(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(new File(json));
        JsonNode commands = root.get("commands");
        List <Command> commandList = new ArrayList<>();

        for (JsonNode command : commands) {
            String type = command.get("type").asText();

            switch (type) {
                case "addVehicle":
                    String vehicleId = command.get("vehicleId").asText();
                    String start = command.get("startRoad").asText();
                    String end = command.get("endRoad").asText();
                    commandList.add(new AddVehicleCommand(vehicleId, start, end));
                    break;
                case "step":
                    commandList.add(new StepCommand());
                    break;
                default:
                    throw new IllegalArgumentException("Unknown command type: " + type);
            }
        }
        return commandList;
    }
}

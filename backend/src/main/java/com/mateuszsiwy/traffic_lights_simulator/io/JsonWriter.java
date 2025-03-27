package com.mateuszsiwy.traffic_lights_simulator.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonWriter {
    public void writeResults(List<Map<String, List<String>>> stepStatuses, String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode rootNode = mapper.createObjectNode();
        ArrayNode stepStatusesNode = mapper.createArrayNode();

        for (Map<String, List<String>> stepStatus : stepStatuses) {
            ObjectNode stepStatusNode = mapper.createObjectNode();
            ArrayNode leftVehiclesNode = mapper.createArrayNode();

            for (String vehicleId : stepStatus.get("leftVehicles")) {
                leftVehiclesNode.add(vehicleId);
            }

            stepStatusNode.set("leftVehicles", leftVehiclesNode);
            ArrayNode crossedPedestriansNode = mapper.createArrayNode();
            for (String pedestrianId : stepStatus.get("crossedPedestrians")) {
                crossedPedestriansNode.add(pedestrianId);
            }
            stepStatusNode.set("crossedPedestrians", crossedPedestriansNode);
            stepStatusesNode.add(stepStatusNode);
        }

        rootNode.set("stepStatuses", stepStatusesNode);
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), rootNode);
    }
}

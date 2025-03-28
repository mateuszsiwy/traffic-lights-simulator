package com.mateuszsiwy.traffic_lights_simulator.controller;

import com.mateuszsiwy.traffic_lights_simulator.command.Command;
import com.mateuszsiwy.traffic_lights_simulator.dto.SimulationCommandsDto;
import com.mateuszsiwy.traffic_lights_simulator.dto.SimulationResultDto;
import com.mateuszsiwy.traffic_lights_simulator.io.JsonReader;
import com.mateuszsiwy.traffic_lights_simulator.io.JsonWriter;
import com.mateuszsiwy.traffic_lights_simulator.model.*;
import com.mateuszsiwy.traffic_lights_simulator.simulation.Simulation;
import com.mateuszsiwy.traffic_lights_simulator.service.SimulationService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/simulation")
@CrossOrigin(origins = "*")
public class SimulationController {

    private final SimulationService simulationService;
    private final JsonReader jsonReader;

    public SimulationController(SimulationService simulationService, JsonReader jsonReader) {
        this.simulationService = simulationService;
        this.jsonReader = jsonReader;
    }

    @PostMapping("/upload")
    public List<?> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        Path tempDir = Files.createTempDirectory("");
        File tempFile = tempDir.resolve(file.getOriginalFilename()).toFile();
        file.transferTo(tempFile);

        List<Command> commands = jsonReader.readCommands(tempFile.getAbsolutePath());
        Simulation simulation = new Simulation();
        for (Command command : commands) {
            command.execute(simulation);
        }

        return simulation.getStepStatuses();
    }

    @PostMapping("/run")
    public ResponseEntity<SimulationResultDto> runSimulation(@RequestBody SimulationCommandsDto commands) {
        List<Command> commandList = simulationService.convertToCommandList(commands.getCommands());
        Simulation simulation = simulationService.runSimulation(commandList);

        List<Map<String, List<String>>> fullStepStatuses = new ArrayList<>();
        for (Map<String, List<String>> stepStatus : simulation.getStepStatuses()) {
            Map<String, List<String>> fullStatus = new HashMap<>(stepStatus);

            for (Direction dir : Direction.values()) {
                Road road = simulation.getIntersection().getRoad(dir);

                List<String> vehicles = road.getVehicles().stream()
                    .map(Vehicle::getVehicleId)
                    .collect(Collectors.toList());
                fullStatus.put("roads_" + dir.toString().toLowerCase(), vehicles);

                fullStatus.put("traffic_light_" + dir.toString().toLowerCase(),
                    List.of(road.getTrafficLight().getState().toString()));

                Crosswalk crosswalk = simulation.getIntersection().getCrosswalk(dir);
                List<String> pedestrians = crosswalk.getPedestrians().stream()
                    .map(Pedestrian::getPedestrianId)
                    .collect(Collectors.toList());
                fullStatus.put("crosswalk_" + dir.toString().toLowerCase(), pedestrians);

                fullStatus.put("pedestrian_light_" + dir.toString().toLowerCase(),
                    List.of(crosswalk.getPedestrianLight().getState().toString()));
            }

            fullStepStatuses.add(fullStatus);
        }

        return ResponseEntity.ok(new SimulationResultDto(fullStepStatuses));
    }


//    @PostMapping("/upload")
//    public ResponseEntity<SimulationResultDto> uploadAndRun(@RequestParam("file") MultipartFile file) throws IOException {
//        File tempFile = File.createTempFile("simulation", ".json");
//        Files.write(tempFile.toPath(), file.getBytes());
//
//        List<Command> commands = jsonReader.readCommands(tempFile.getAbsolutePath());
//        Simulation simulation = simulationService.runSimulation(commands);
//
//        tempFile.delete();
//
//        return ResponseEntity.ok(new SimulationResultDto(simulation.getStepStatuses()));
//    }

    @GetMapping("/state")
    public ResponseEntity<Map<String, Object>> getIntersectionState() {
        return ResponseEntity.ok(simulationService.getCurrentState());
    }
}
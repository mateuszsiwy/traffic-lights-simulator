package com.mateuszsiwy.traffic_lights_simulator.controller;

import com.mateuszsiwy.traffic_lights_simulator.command.Command;
import com.mateuszsiwy.traffic_lights_simulator.dto.SimulationCommandsDto;
import com.mateuszsiwy.traffic_lights_simulator.dto.SimulationResultDto;
import com.mateuszsiwy.traffic_lights_simulator.io.JsonReader;
import com.mateuszsiwy.traffic_lights_simulator.io.JsonWriter;
import com.mateuszsiwy.traffic_lights_simulator.simulation.Simulation;
import com.mateuszsiwy.traffic_lights_simulator.service.SimulationService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/run")
    public ResponseEntity<SimulationResultDto> runSimulation(@RequestBody SimulationCommandsDto commands) {
        List<Command> commandList = simulationService.convertToCommandList(commands.getCommands());

        Simulation simulation = simulationService.runSimulation(commandList);
        return ResponseEntity.ok(new SimulationResultDto(simulation.getStepStatuses()));
    }


    @PostMapping("/upload")
    public ResponseEntity<SimulationResultDto> uploadAndRun(@RequestParam("file") MultipartFile file) throws IOException {
        File tempFile = File.createTempFile("simulation", ".json");
        Files.write(tempFile.toPath(), file.getBytes());

        List<Command> commands = jsonReader.readCommands(tempFile.getAbsolutePath());
        Simulation simulation = simulationService.runSimulation(commands);

        tempFile.delete();

        return ResponseEntity.ok(new SimulationResultDto(simulation.getStepStatuses()));
    }

    @GetMapping("/state")
    public ResponseEntity<Map<String, Object>> getIntersectionState() {
        return ResponseEntity.ok(simulationService.getCurrentState());
    }
}
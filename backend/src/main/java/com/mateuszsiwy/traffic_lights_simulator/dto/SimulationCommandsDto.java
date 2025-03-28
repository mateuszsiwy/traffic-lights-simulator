package com.mateuszsiwy.traffic_lights_simulator.dto;

import com.mateuszsiwy.traffic_lights_simulator.command.Command;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class SimulationCommandsDto {
    private List<Map<String, Object>> commands;
}
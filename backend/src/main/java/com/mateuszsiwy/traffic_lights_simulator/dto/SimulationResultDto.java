package com.mateuszsiwy.traffic_lights_simulator.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class SimulationResultDto {
    private List<Map<String, List<String>>> stepStatuses;
}
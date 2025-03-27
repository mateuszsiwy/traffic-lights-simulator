package com.mateuszsiwy.traffic_lights_simulator.simulation;

import com.mateuszsiwy.traffic_lights_simulator.model.*;

import java.util.Map;

public class IntelligentTrafficLights {

    public void updateTrafficLights(Intersection intersection) {
        Map<Direction, Road> roads = intersection.getRoads();
        Map<Direction, Crosswalk> crosswalks = intersection.getCrosswalks();

        Direction vehicleDirectionWithHighestWeight = null;
        int maxVehicleWeight = -1;

        for (Map.Entry<Direction, Road> entry : roads.entrySet()) {
            Road road = entry.getValue();
            int weight = road.getTotalWeight();

            if (weight > maxVehicleWeight) {
                maxVehicleWeight = weight;
                vehicleDirectionWithHighestWeight = entry.getKey();
            }

        }

        Direction pedestrianDirectionWithHighestWeight = null;
        int maxPedestrianWeight = -1;

        for (Map.Entry<Direction, Crosswalk> entry : crosswalks.entrySet()) {
            Crosswalk crosswalk = entry.getValue();
            int weight = crosswalk.getTotalWeight();

            if (weight > maxPedestrianWeight) {
                maxPedestrianWeight = weight;
                pedestrianDirectionWithHighestWeight = entry.getKey();
            }

        }

        boolean allVehicleLightsRed = true;
        for (Road road : roads.values()) {
            if (road.getTrafficLight().isGreen()) {
                allVehicleLightsRed = false;
                break;
            }
        }

        boolean allPedestrianLightsRed = true;
        for (Crosswalk crosswalk : crosswalks.values()) {
            if (crosswalk.getPedestrianLight().isGreen()) {
                allPedestrianLightsRed = false;
                break;
            }
        }
        if (allVehicleLightsRed && allPedestrianLightsRed) {
            if (maxPedestrianWeight > maxVehicleWeight && pedestrianDirectionWithHighestWeight != null) {
                crosswalks.get(pedestrianDirectionWithHighestWeight).getPedestrianLight().setState(PedestrianLight.State.GREEN);

                Direction oppositeDirection = pedestrianDirectionWithHighestWeight.getOpposite();
                if (crosswalks.get(oppositeDirection).getNumberOfPedestrians() > 0) {
                    crosswalks.get(oppositeDirection).getPedestrianLight().setState(PedestrianLight.State.GREEN);
                }
            } else if (vehicleDirectionWithHighestWeight != null) {
                roads.get(vehicleDirectionWithHighestWeight).getTrafficLight().setState(TrafficLight.State.GREEN);

                Direction oppositeDirection = vehicleDirectionWithHighestWeight.getOpposite();
                if (roads.get(oppositeDirection).getNumberOfVehicles() > 0) {
                    roads.get(oppositeDirection).getTrafficLight().setState(TrafficLight.State.GREEN);
                }
            }
        }
    }
}

package com.mateuszsiwy.traffic_lights_simulator.simulation;

import com.mateuszsiwy.traffic_lights_simulator.model.Direction;
import com.mateuszsiwy.traffic_lights_simulator.model.Intersection;
import com.mateuszsiwy.traffic_lights_simulator.model.Road;
import com.mateuszsiwy.traffic_lights_simulator.model.TrafficLight;

import java.util.Map;

public class IntelligentTrafficLights {

    public void updateTrafficLights(Intersection intersection) {
        Map<Direction, Road> roads = intersection.getRoads();

        Direction directionWithHighestWeight = null;
        int maxWeight = -1;

        for (Map.Entry<Direction, Road> entry : roads.entrySet()) {
            Road road = entry.getValue();
            int weight = road.getTotalWeight();

            if (weight > maxWeight) {
                maxWeight = weight;
                directionWithHighestWeight = entry.getKey();
            }

            road.getTrafficLight().adjustGreenLightDuration(road.getNumberOfVehicles());
        }

        boolean allRed = true;
        for (Road road : roads.values()) {
            if(road.getTrafficLight().isGreen()){
                allRed = false;
                break;
            }
        }
        if (allRed && directionWithHighestWeight != null) {
            roads.get(directionWithHighestWeight).getTrafficLight().setState(TrafficLight.State.GREEN);

            Direction oppositeDirection = directionWithHighestWeight.getOpposite();
            if (roads.get(oppositeDirection).getNumberOfVehicles() > 0) {
                roads.get(oppositeDirection).getTrafficLight().setState(TrafficLight.State.GREEN);
            }
        }
    }
}

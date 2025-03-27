package com.mateuszsiwy.traffic_lights_simulator.simulation;

import com.mateuszsiwy.traffic_lights_simulator.model.Direction;
import com.mateuszsiwy.traffic_lights_simulator.model.Intersection;
import com.mateuszsiwy.traffic_lights_simulator.model.Road;
import com.mateuszsiwy.traffic_lights_simulator.model.TrafficLight;

import java.util.Map;

public class IntelligentTrafficLights {

    public void updateTrafficLigths(Intersection intersection) {
        Map<Direction, Road> roads = intersection.getRoads();

        Direction directionWithMostVehicles = null;
        int maxVehicles = -1;

        for (Map.Entry<Direction, Road> entry : roads.entrySet()) {
            Road road = entry.getValue();
            int numOfVehicles = road.getNumberOfVehicles();

            if (numOfVehicles > maxVehicles) {
                maxVehicles = numOfVehicles;
                directionWithMostVehicles = entry.getKey();
            }

            road.getTrafficLight().adjustGreenLightDuration(numOfVehicles);
        }

        boolean allRed = true;
        for (Road road : roads.values()) {
            if(road.getTrafficLight().isGreen()){
                allRed = false;
                break;
            }
        }
        if (allRed && directionWithMostVehicles != null) {
            roads.get(directionWithMostVehicles).getTrafficLight().setState(TrafficLight.State.GREEN);

            Direction oppositeDirection = directionWithMostVehicles.getOpposite();
            if (roads.get(oppositeDirection).getNumberOfVehicles() > 0) {
                roads.get(oppositeDirection).getTrafficLight().setState(TrafficLight.State.GREEN);
            }
        }
    }
}

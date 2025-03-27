package com.mateuszsiwy.traffic_lights_simulator.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Crosswalk {
    @Getter
    private final Direction direction;
    @Getter
    private final PedestrianLight pedestrianLight;
    @Getter
    private final Queue<Pedestrian> pedestrians;

    public Crosswalk(Direction direction) {
        this.direction = direction;
        this.pedestrianLight = new PedestrianLight(direction);
        this.pedestrians = new LinkedList<>();
    }

    public void addPedestrian(Pedestrian pedestrian) {
        pedestrians.add(pedestrian);
    }

    public int getNumberOfPedestrians() {
        return pedestrians.size();
    }

    public int getTotalWeight() {
        return pedestrians.stream().mapToInt(Pedestrian::getWeight).sum();
    }

    public void updatePedestrianWeights() {
        pedestrians.forEach(Pedestrian::increaseWeight);
    }

    public List<Pedestrian> processCrossing() {
        List<Pedestrian> crossedPedestrians = new ArrayList<>();

        if (pedestrianLight.isGreen() && !pedestrians.isEmpty()) {
            int maxPedestriansPerCycle = 3;
            List<Pedestrian> processedPedestrians = new ArrayList<>();

            int processedCount = 0;
            for (Pedestrian pedestrian : pedestrians) {
                if (processedCount >= maxPedestriansPerCycle) {
                    break;
                }

                if (direction == Direction.NORTH || direction == Direction.SOUTH) {
                    pedestrian.markHorizontalCrossed();
                } else {
                    pedestrian.markVerticalCrossed();
                }

                processedPedestrians.add(pedestrian);
                processedCount++;

                if (pedestrian.hasCompletedCrossing()) {
                    crossedPedestrians.add(pedestrian);
                }
            }

            pedestrians.removeAll(processedPedestrians);


        }

        return crossedPedestrians;
    }
}
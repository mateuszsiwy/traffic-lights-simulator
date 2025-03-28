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
            int maxPedestriansPerCycle = 1;
            int processedCount = 0;
            List<Pedestrian> toRemove = new ArrayList<>();

            for (Pedestrian pedestrian : pedestrians) {
                if (processedCount >= maxPedestriansPerCycle) {
                    break;
                }

                boolean shouldProcess = false;

                if ((direction == Direction.NORTH || direction == Direction.SOUTH) &&
                    pedestrian.needsToGoHorizontal() &&
                    pedestrian.getHorizontalCrosswalkDirection() == direction) {
                    pedestrian.markHorizontalCrossed();
                    shouldProcess = true;
                } else if ((direction == Direction.EAST || direction == Direction.WEST) &&
                           pedestrian.needsToGoVertical() &&
                           pedestrian.getVerticalCrosswalkDirection() == direction) {
                    pedestrian.markVerticalCrossed();
                    shouldProcess = true;
                }

                if (shouldProcess) {
                    processedCount++;

                    if (pedestrian.hasCompletedCrossing()) {
                        toRemove.add(pedestrian);
                        crossedPedestrians.add(pedestrian);
                    }
                }
            }

            pedestrians.removeAll(toRemove);
        }

        return crossedPedestrians;
    }
}
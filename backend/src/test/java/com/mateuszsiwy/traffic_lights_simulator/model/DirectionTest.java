package com.mateuszsiwy.traffic_lights_simulator.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DirectionTest {
    private Direction direction;
    @BeforeEach
    public void setUp(){
        direction = Direction.NORTH;
    }
    @Test
    public void testGetOpposite() {
        assert(direction.getOpposite() == Direction.SOUTH);
        direction = Direction.SOUTH;
        assert(direction.getOpposite() == Direction.NORTH);
        direction = Direction.EAST;
        assert(direction.getOpposite() == Direction.WEST);
        direction = Direction.WEST;
        assert(direction.getOpposite() == Direction.EAST);
    }
}

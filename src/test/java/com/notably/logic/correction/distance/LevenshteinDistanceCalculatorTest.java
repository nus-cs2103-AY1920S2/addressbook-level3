package com.notably.logic.correction.distance;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class LevenshteinDistanceCalculatorTest {
    private static final LevenshteinDistanceCalculator calculator = new LevenshteinDistanceCalculator();

    @Test
    public void calculateDistance() {
        final String FIRST = "intention";
        final String SECOND = "execution";
        final int EXPECTED_DISTANCE = 8;

        int distance = calculator.calculateDistance(FIRST, SECOND);

        assertEquals(EXPECTED_DISTANCE, distance);
    }
}


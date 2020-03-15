package com.notably.logic.correction.distance;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class LevenshteinDistanceCalculatorTest {
    private static final LevenshteinDistanceCalculator calculator = new LevenshteinDistanceCalculator();

    @Test
    public void calculateDistance() {
        final String first = "intention";
        final String second = "execution";
        final int expectedDistance = 8;

        int distance = calculator.calculateDistance(first, second);

        assertEquals(expectedDistance, distance);
    }
}


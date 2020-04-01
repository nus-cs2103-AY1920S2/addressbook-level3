package com.notably.logic.correction.distance;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class LevenshteinDistanceCalculatorTest {
    @Test
    public void calculateDistance_caseSensitive() {
        final String first = "intention";
        final String second = "execution";
        final int expectedDistance = 8;

        LevenshteinDistanceCalculator calculator = new LevenshteinDistanceCalculator(true);
        int distance = calculator.calculateDistance(first, second);

        assertEquals(expectedDistance, distance);
    }

    @Test
    public void calculateDistance_caseInsensitive() {
        final String first = "INTENTION";
        final String second = "intention";
        final int expectedDistance = 0;

        LevenshteinDistanceCalculator calculator = new LevenshteinDistanceCalculator(false);
        int distance = calculator.calculateDistance(first, second);

        assertEquals(expectedDistance, distance);
    }
}


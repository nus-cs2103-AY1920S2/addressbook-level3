package com.notably.logic.correction;

import java.util.Objects;

import com.notably.logic.correction.distance.EditDistanceCalculator;

/**
 * Utility class for {@link CorrectionEngine} implementations.
 */
public class CorrectionEngineUtil {
    /**
     * Calculates the forward matching distance between to {@link String}s.
     *
     * @param editDistanceCalculator Edit distance calculator
     * @param input Input string
     * @param reference Reference string
     * @param forwardMatchingThreshold Threshold for forward matching
     * @return Forward matching distance between the two components
     */
    public static int calculateForwardMatchingDistance(EditDistanceCalculator editDistanceCalculator,
            String input, String reference, int forwardMatchingThreshold) {
        Objects.requireNonNull(input);
        Objects.requireNonNull(reference);

        if (input.length() > reference.length()) {
            throw new IllegalArgumentException("\"input\" cannot be longer than \"reference\"");
        }

        if (forwardMatchingThreshold < 0) {
            throw new IllegalArgumentException("\"forwardMatchingThreshold\" cannot be less than zero");
        }

        if (input.length() < forwardMatchingThreshold) {
            if (reference.toLowerCase().startsWith(input)) {
                return 0;
            }
            return editDistanceCalculator.calculateDistance(input, reference);
        }

        int forwardMatchingDistance = Integer.MAX_VALUE;
        for (int stopIndex = 0; stopIndex <= reference.length(); stopIndex++) {
            int currentForwardMatchingDistance =
                    editDistanceCalculator.calculateDistance(input, reference.substring(0, stopIndex));
            if (currentForwardMatchingDistance < forwardMatchingDistance) {
                forwardMatchingDistance = currentForwardMatchingDistance;
            }
        }

        return forwardMatchingDistance;
    }
}


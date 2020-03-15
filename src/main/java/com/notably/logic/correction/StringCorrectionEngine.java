package com.notably.logic.correction;

import static java.util.Objects.requireNonNull;

import java.util.List;

import com.notably.logic.correction.distance.EditDistanceCalculator;
import com.notably.logic.correction.distance.LevenshteinDistanceCalculator;

/**
 * Represents a correction engine that works on {@link String}s.
 */
public class StringCorrectionEngine implements CorrectionEngine<String> {
    private final EditDistanceCalculator editDistanceCalculator;
    private final List<String> options;
    private final int distanceThreshold;

    public StringCorrectionEngine(List<String> options, int distanceThreshold) {
        this(new LevenshteinDistanceCalculator(), options, distanceThreshold);
    }

    public StringCorrectionEngine(EditDistanceCalculator editDistanceCalculator,
            List<String> options, int distanceThreshold) {
        requireNonNull(editDistanceCalculator);
        requireNonNull(options);

        if (options.isEmpty()) {
            throw new IllegalArgumentException("\"options\" must contain at least one element");
        }

        if (distanceThreshold < 0) {
            throw new IllegalArgumentException("\"distanceThreshold\" must be greater than 0");
        }

        this.editDistanceCalculator = editDistanceCalculator;
        this.options = options;
        this.distanceThreshold = distanceThreshold;
    }

    /**
     * Corrects a given string.
     *
     * @param uncorrected Uncorrected string
     * @return Result of the correction
     */
    @Override
    public CorrectionResult<String> correct(String uncorrected) {
        requireNonNull(uncorrected);

        String closestString = null;
        int closestDistance = Integer.MAX_VALUE;
        for (String option : options) {
            int distance = editDistanceCalculator.calculateDistance(uncorrected, option);
            if (distance < closestDistance) {
                closestString = option;
                closestDistance = distance;
            }
        }

        if (closestDistance > distanceThreshold) {
            return new CorrectionResult<>(CorrectionStatus.FAILED, null);
        }

        if (closestString.equals(uncorrected)) {
            return new CorrectionResult<>(CorrectionStatus.UNCHANGED, uncorrected);
        }

        return new CorrectionResult<String>(CorrectionStatus.CORRECTED, closestString);
    }
}


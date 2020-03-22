package com.notably.logic.correction;

import java.util.List;
import java.util.Objects;

import com.notably.commons.core.path.AbsolutePath;
import com.notably.logic.correction.distance.EditDistanceCalculator;
import com.notably.logic.correction.distance.LevenshteinDistanceCalculator;

/**
 * Represents a correction engine that works on {@link AbsolutePath}s
 */
public class AbsolutePathCorrectionEngine implements CorrectionEngine<AbsolutePath> {
    private final EditDistanceCalculator editDistanceCalculator;
    private final List<AbsolutePath> possiblePaths;
    private final int distanceThreshold;

    public AbsolutePathCorrectionEngine(List<AbsolutePath> possiblePaths, int distanceThreshold) {
        this(new LevenshteinDistanceCalculator(), possiblePaths, distanceThreshold);
    }

    public AbsolutePathCorrectionEngine(EditDistanceCalculator editDistanceCalculator,
            List<AbsolutePath> possiblePaths, int distanceThreshold) {
        Objects.requireNonNull(editDistanceCalculator);
        Objects.requireNonNull(possiblePaths);

        if (possiblePaths.isEmpty()) {
            throw new IllegalArgumentException("\"options\" must contain at least one element");
        }

        if (distanceThreshold < 0) {
            throw new IllegalArgumentException("\"distanceThreshold\" must be greater than 0");
        }

        this.editDistanceCalculator = editDistanceCalculator;
        this.possiblePaths = possiblePaths;
        this.distanceThreshold = distanceThreshold;
    }

    /**
     * Corrects a given path.
     *
     * @param uncorrected An uncorrected path
     * @return Result of the correction
     */
    @Override
    public CorrectionResult<AbsolutePath> correct(AbsolutePath uncorrected) {
        Objects.requireNonNull(uncorrected);

        AbsolutePath closestPath = null;
        int closestDistance = Integer.MAX_VALUE;
        for (AbsolutePath possiblePath : possiblePaths) {
            int distance = calculatePathDistance(uncorrected, possiblePath);
            if (distance < closestDistance) {
                closestPath = possiblePath;
                closestDistance = distance;
            }
        }

        if (closestDistance > distanceThreshold) {
            return new CorrectionResult<>(CorrectionStatus.FAILED);
        }

        if (closestPath.equals(uncorrected)) {
            return new CorrectionResult<>(CorrectionStatus.UNCHANGED, uncorrected);
        }

        return new CorrectionResult<>(CorrectionStatus.CORRECTED, closestPath);
    }

    /**
     * Calculate the edit distance between two {@link AbsolutePath}s.
     *
     * @param firstPath First {@link AbsolutePath}
     * @param secondPath Second {@link AbsolutePath}
     * @return Edit distance between {@code firstPath} and {@code secondPath}
     */
    private int calculatePathDistance(AbsolutePath firstPath, AbsolutePath secondPath) {
        Objects.requireNonNull(firstPath);
        Objects.requireNonNull(secondPath);

        List<String> firstComponents = firstPath.getComponents();
        List<String> secondComponents = secondPath.getComponents();

        // Calculate the cumulative distance between the two paths component-by-component.
        int i = 0;
        int distance = 0;
        while (i < firstComponents.size() && i < secondComponents.size()) {
            distance += editDistanceCalculator.calculateDistance(firstComponents.get(i), secondComponents.get(i));
            i++;
        }

        // If one path is longer than another, increase cumulative distance by the size of
        // each extra component's length.
        while (i < firstComponents.size()) {
            distance += firstComponents.get(i).length();
            i++;
        }
        while (i < secondComponents.size()) {
            distance += secondComponents.get(i).length();
            i++;
        }

        return distance;
    }
}


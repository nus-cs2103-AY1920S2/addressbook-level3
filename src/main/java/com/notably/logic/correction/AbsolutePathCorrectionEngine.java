package com.notably.logic.correction;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.stream.Collectors;

import com.notably.commons.path.AbsolutePath;
import com.notably.logic.correction.distance.EditDistanceCalculator;
import com.notably.logic.correction.distance.LevenshteinDistanceCalculator;
import com.notably.model.Model;
import com.notably.model.block.BlockTree;
import com.notably.model.block.BlockTreeItem;

/**
 * Represents a correction engine that works on {@link AbsolutePath}s
 */
public class AbsolutePathCorrectionEngine implements CorrectionEngine<AbsolutePath> {
    private final EditDistanceCalculator editDistanceCalculator;
    private final Model model;
    private final int distanceThreshold;

    public AbsolutePathCorrectionEngine(Model model, int distanceThreshold) {
        this(new LevenshteinDistanceCalculator(false), model, distanceThreshold);
    }

    public AbsolutePathCorrectionEngine(EditDistanceCalculator editDistanceCalculator,
            Model model, int distanceThreshold) {
        Objects.requireNonNull(editDistanceCalculator);
        Objects.requireNonNull(model);

        if (distanceThreshold < 0) {
            throw new IllegalArgumentException("\"distanceThreshold\" must be greater than 0");
        }

        this.editDistanceCalculator = editDistanceCalculator;
        this.model = model;
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

        List<AbsolutePath> possiblePaths = getPossiblePaths();

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
     * Generates all possible paths from the app's {@link BlockTree}
     *
     * @return List of all possible paths
     */
    private List<AbsolutePath> getPossiblePaths() {
        List<AbsolutePath> possiblePaths = new ArrayList<>();

        Queue<AbsolutePath> pathQueue = new LinkedList<>();
        pathQueue.offer(AbsolutePath.fromString("/"));

        while (!pathQueue.isEmpty()) {
            AbsolutePath currentPath = pathQueue.poll();

            List<BlockTreeItem> childrenBlocks = model.getBlockTree().get(currentPath).getBlockChildren();
            List<AbsolutePath> childrenPaths = childrenBlocks
                    .stream()
                    .map(item -> {
                        List<String> combinedComponents = new ArrayList<>(currentPath.getComponents());
                        combinedComponents.add(item.getTitle().getText());
                        return AbsolutePath.fromComponents(combinedComponents);
                    })
                    .collect(Collectors.toList());
            pathQueue.addAll(childrenPaths);

            possiblePaths.add(currentPath);
        }

        return possiblePaths;
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


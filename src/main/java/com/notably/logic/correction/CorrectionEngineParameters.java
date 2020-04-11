package com.notably.logic.correction;

/**
 * Parameters container for the {@link CorrectionEngine} implementation classes.
 */
public class CorrectionEngineParameters {
    private static final int DEFAULT_DISTANCE_THRESHOLD = 2;
    private static final boolean DEFAULT_FORWARD_MATCHING = false;
    private static final int DEFAULT_FORWARD_MATCHING_THRESHOLD = 2;

    private int distanceThreshold = DEFAULT_DISTANCE_THRESHOLD;
    private boolean forwardMatching = DEFAULT_FORWARD_MATCHING;
    private int forwardMatchingThreshold = DEFAULT_FORWARD_MATCHING_THRESHOLD;

    public int getDistanceThreshold() {
        return distanceThreshold;
    }

    public CorrectionEngineParameters setDistanceThreshold(int distanceThreshold) {
        if (distanceThreshold < 0) {
            throw new IllegalArgumentException("\"distanceThreshold\" cannot be less than zero");
        }

        this.distanceThreshold = distanceThreshold;
        return this;
    }

    public boolean isForwardMatching() {
        return forwardMatching;
    }

    public CorrectionEngineParameters setForwardMatching(boolean forwardMatching) {
        this.forwardMatching = forwardMatching;
        return this;
    }

    public int getForwardMatchingThreshold() {
        return forwardMatchingThreshold;
    }

    public CorrectionEngineParameters setForwardMatchingThreshold(int forwardMatchingThreshold) {
        if (forwardMatchingThreshold < 0) {
            throw new IllegalArgumentException("\"forwardMatchingThreshold\" cannot be less than zero");
        }

        this.forwardMatchingThreshold = forwardMatchingThreshold;
        return this;
    }
}


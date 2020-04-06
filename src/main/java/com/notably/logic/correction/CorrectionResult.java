package com.notably.logic.correction;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * Represents the result of a correction.
 */
public class CorrectionResult<T> {
    private final CorrectionStatus correctionStatus;
    private final List<T> correctedItems;

    /**
     * Creates a correction result without any corrected item.
     *
     * @param correctionStatus Status of the result
     */
    public CorrectionResult(CorrectionStatus correctionStatus) {
        this(correctionStatus, List.of());
    }

    /**
     * Creates a correction result.
     *
     * @param correctionStatus Status of the result
     * @param correctedItems List of corrected items
     */
    public CorrectionResult(CorrectionStatus correctionStatus, List<T> correctedItems) {
        Objects.requireNonNull(correctionStatus);
        Objects.requireNonNull(correctedItems);

        this.correctionStatus = correctionStatus;
        this.correctedItems = correctedItems;
    }

    /**
     * Gets the status of the correction.
     *
     * @return Status of the correction
     */
    public CorrectionStatus getCorrectionStatus() {
        return correctionStatus;
    }

    /**
     * Gets the list of corrected items.
     *
     * @return List of corrected items
     */
    public List<T> getCorrectedItems() {
        return correctedItems;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CorrectionResult)) {
            return false;
        }

        CorrectionResult<?> another = (CorrectionResult<?>) object;
        return Objects.equals(correctionStatus, another.correctionStatus)
                && Objects.equals(new HashSet<>(correctedItems), new HashSet<>(another.correctedItems));
    }

    @Override
    public int hashCode() {
        return Objects.hash(correctionStatus, correctedItems);
    }
}


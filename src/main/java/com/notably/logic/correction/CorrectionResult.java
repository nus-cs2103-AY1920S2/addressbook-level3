package com.notably.logic.correction;

import static java.util.Objects.hash;
import static java.util.Objects.requireNonNull;

import java.util.Optional;

/**
 * Represents the result of a correction.
 */
public class CorrectionResult<T> {
    private final CorrectionStatus correctionStatus;
    private final T correctedItem;

    /**
     * Creates a correction result.
     *
     * @param correctionStatus Status of the result
     * @param correctedItem Item after correction
     */
    public CorrectionResult(CorrectionStatus correctionStatus, T correctedItem) {
        requireNonNull(correctionStatus);

        this.correctionStatus = correctionStatus;
        this.correctedItem = correctedItem;
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
     * Gets the corrected item.
     * If no corrected item, {@code Optional.empty()} will be returned.
     *
     * @return Corrected item, or {@code Optional.empty()} if there is none
     */
    Optional<T> getCorrectedItem() {
        return Optional.ofNullable(correctedItem);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CorrectionResult)) {
            return false;
        }

        CorrectionResult<?> another = (CorrectionResult<?>) object;
        return correctionStatus.equals(another.correctionStatus)
                && correctedItem.equals(another.correctedItem);
    }

    @Override
    public int hashCode() {
        return hash(correctionStatus, correctedItem);
    }
}


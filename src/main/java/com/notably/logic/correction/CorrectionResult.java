package com.notably.logic.correction;

import java.util.Optional;

/**
 * Represents the result of a correction.
 */
interface CorrectionResult<T> {
    /**
     * Gets the status of the correction.
     *
     * @return The status of the correction
     */
    CorrectionStatus getCorrectionStatus();
    /**
     * Gets the corrected item.
     * If correction cannot be done, {@code Optional.empty()} will be returned.
     * 
     * @return The corrected item, or {@code Optional.empty()} if correction fails
     */
    Optional<T> getCorrectedItem();
}

package com.notably.logic.correction;

import java.util.Optional;

/**
 * Represents the result of a correction.
 */
interface CorrectionResult<T> {
    CorrectionStatus getCorrectionStatus();
    Optional<T> getCorrectedItem();
}

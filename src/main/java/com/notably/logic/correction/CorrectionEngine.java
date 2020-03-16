package com.notably.logic.correction;

/**
 * Represents a correction engine.
 */
interface CorrectionEngine<T> {
    /**
     * Corrects a given item.
     *
     * @param uncorrected Uncorrected input
     * @return Result of the correction
     */
    CorrectionResult<T> correct(T uncorrected);
}

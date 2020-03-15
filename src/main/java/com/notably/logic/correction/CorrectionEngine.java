package com.notably.logic.correction;

/**
 * Represents a correction engine.
 * This is the class to interact with when doing corrections.
 */
interface CorrectionEngine<T> {
    CorrectionResult<T> correct(T actual);
}

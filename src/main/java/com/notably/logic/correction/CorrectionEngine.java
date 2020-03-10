package com.notably.logic.correction;

import java.util.List;

/**
 * Represents the correction engine instance.
 * This is the class to with when doing string corrections.
 */
interface CorrectionEngine {
    CorrectionResult correct(String actual, List<String> possibilities);
}

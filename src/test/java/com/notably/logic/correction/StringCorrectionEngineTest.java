package com.notably.logic.correction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

public class StringCorrectionEngineTest {
    private static final List<String> OPTIONS = List.of("open", "new", "help");

    @Test
    public void correct_constructWithEmptyOptions_exceptionThrown() {
        final List<String> emptyOptions = List.of();
        final int distanceThreshold = 5;

        assertThrows(IllegalArgumentException.class, () ->
                new StringCorrectionEngine(emptyOptions, distanceThreshold));
    }

    @Test
    public void correct_constructWithNegativeDistanceThreshold_exceptionThrown() {
        final int negativeDistanceThreshold = -1;

        assertThrows(IllegalArgumentException.class, () ->
                new StringCorrectionEngine(OPTIONS, negativeDistanceThreshold));
    }

    @Test
    public void correct_withinDistanceThreshold_correctionDone() {
        final int distanceThreshold = 2;
        final StringCorrectionEngine correctionEngine = new StringCorrectionEngine(OPTIONS, distanceThreshold);
        final String uncorrectedInput = "nrw";

        final String expectedCorrectedItem = "new";
        final CorrectionStatus expectedCorrectedStatus = CorrectionStatus.CORRECTED;
        final CorrectionResult<String> expectedCorrectionResult = new CorrectionResult<>(
                expectedCorrectedStatus, expectedCorrectedItem);

        CorrectionResult<String> correctionResult = correctionEngine.correct(uncorrectedInput);
        assertEquals(expectedCorrectionResult, correctionResult);
    }

    @Test
    public void correct_exceedDistanceThreshold_correctionFailed() {
        final int distanceThreshold = 1;
        final StringCorrectionEngine correctionEngine = new StringCorrectionEngine(OPTIONS, distanceThreshold);
        final String uncorrectedInput = "opne";

        final CorrectionStatus expectedCorrectedStatus = CorrectionStatus.FAILED;
        final CorrectionResult<String> expectedCorrectionResult = new CorrectionResult<>(expectedCorrectedStatus);

        CorrectionResult<String> correctionResult = correctionEngine.correct(uncorrectedInput);
        assertEquals(expectedCorrectionResult, correctionResult);
    }

    @Test
    public void correct_exactlyMatchOption_noCorrection() {
        final int distanceThreshold = 1;
        final StringCorrectionEngine correctionEngine = new StringCorrectionEngine(OPTIONS, distanceThreshold);
        final String uncorrectedInput = "help";

        final String expectedCorrectedItem = "help";
        final CorrectionStatus expectedCorrectionStatus = CorrectionStatus.UNCHANGED;
        final CorrectionResult<String> expectedCorrectionResult = new CorrectionResult<>(
                expectedCorrectionStatus, expectedCorrectedItem);

        CorrectionResult<String> correctionResult = correctionEngine.correct(uncorrectedInput);
        assertEquals(expectedCorrectionResult, correctionResult);
    }

    @Test
    public void correct_differentCase() {
        final int distanceThreshold = 1;
        final StringCorrectionEngine correctionEngine = new StringCorrectionEngine(OPTIONS, distanceThreshold);
        final String uncorrectedInput = "HElP";

        final String expectedCorrectedItem = "help";
        final CorrectionStatus expectedCorrectionStatus = CorrectionStatus.CORRECTED;
        final CorrectionResult<String> expectedCorrectionResult = new CorrectionResult<>(
                expectedCorrectionStatus, expectedCorrectedItem);

        CorrectionResult<String> correctionResult = correctionEngine.correct(uncorrectedInput);
        assertEquals(expectedCorrectionResult, correctionResult);
    }
}


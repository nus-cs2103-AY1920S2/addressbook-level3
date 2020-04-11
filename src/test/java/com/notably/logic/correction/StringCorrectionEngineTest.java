package com.notably.logic.correction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

public class StringCorrectionEngineTest {
    private static final List<String> OPTIONS = List.of("open", "new", "help", "delete");

    @Test
    public void constructor_nullOptions_exceptionThrown() {
        assertThrows(NullPointerException.class, () ->
                new StringCorrectionEngine(null, new CorrectionEngineParameters()));
    }

    @Test
    public void constructor_emptyOptions_exceptionThrown() {
        final List<String> emptyOptions = List.of();

        assertThrows(IllegalArgumentException.class, () ->
                new StringCorrectionEngine(emptyOptions, new CorrectionEngineParameters()));
    }

    @Test
    public void constructor_negativeDistanceThreshold_exceptionThrown() {
        final int negativeDistanceThreshold = -1;

        assertThrows(IllegalArgumentException.class, () -> new StringCorrectionEngine(OPTIONS,
                new CorrectionEngineParameters().setDistanceThreshold(negativeDistanceThreshold)));
    }

    @Test
    public void constructor_negativeForwardMatchingThreshold_exceptionThrown() {
        final int negativeForwardMatchingThreshold = -1;

        assertThrows(IllegalArgumentException.class, () -> new StringCorrectionEngine(OPTIONS,
                new CorrectionEngineParameters().setForwardMatchingThreshold(negativeForwardMatchingThreshold)));
    }

    @Test
    public void correct_noForwardMatchingAndWithinDistanceThreshold_correctionDone() {
        final int distanceThreshold = 2;
        final StringCorrectionEngine correctionEngine = new StringCorrectionEngine(OPTIONS,
                new CorrectionEngineParameters().setForwardMatching(false).setDistanceThreshold(distanceThreshold));
        final String uncorrectedInput = "NRW";

        final List<String> expectedCorrectedItems = List.of("new");
        final CorrectionStatus expectedCorrectedStatus = CorrectionStatus.CORRECTED;
        final CorrectionResult<String> expectedCorrectionResult = new CorrectionResult<>(
                expectedCorrectedStatus, expectedCorrectedItems);

        CorrectionResult<String> correctionResult = correctionEngine.correct(uncorrectedInput);
        assertEquals(expectedCorrectionResult, correctionResult);
    }

    @Test
    public void correct_noForwardMatchingAndExceedDistanceThreshold_correctionFailed() {
        final int distanceThreshold = 1;
        final StringCorrectionEngine correctionEngine = new StringCorrectionEngine(OPTIONS,
                new CorrectionEngineParameters().setForwardMatching(false).setDistanceThreshold(distanceThreshold));
        final String uncorrectedInput = "OPne";

        final CorrectionStatus expectedCorrectedStatus = CorrectionStatus.FAILED;
        final CorrectionResult<String> expectedCorrectionResult = new CorrectionResult<>(expectedCorrectedStatus);

        CorrectionResult<String> correctionResult = correctionEngine.correct(uncorrectedInput);
        assertEquals(expectedCorrectionResult, correctionResult);
    }

    @Test
    public void correct_noForwardMatchingAndExactlyMatchOption_noCorrection() {
        final int distanceThreshold = 1;
        final StringCorrectionEngine correctionEngine = new StringCorrectionEngine(OPTIONS,
                new CorrectionEngineParameters().setForwardMatching(false).setDistanceThreshold(distanceThreshold));
        final String uncorrectedInput = "hELP";

        final List<String> expectedCorrectedItems = List.of("help");
        final CorrectionStatus expectedCorrectionStatus = CorrectionStatus.UNCHANGED;
        final CorrectionResult<String> expectedCorrectionResult = new CorrectionResult<>(
                expectedCorrectionStatus, expectedCorrectedItems);

        CorrectionResult<String> correctionResult = correctionEngine.correct(uncorrectedInput);
        assertEquals(expectedCorrectionResult, correctionResult);
    }

    @Test
    public void correct_withForwardMatchingAndValidPrefixAndBelowForwardMatchingThreshold_forwardMatchingDone() {
        final int distanceThreshold = 2;
        final int forwardMatchingThreshold = 2;
        final StringCorrectionEngine correctionEngine = new StringCorrectionEngine(OPTIONS,
                new CorrectionEngineParameters()
                    .setForwardMatching(true)
                    .setDistanceThreshold(distanceThreshold)
                    .setForwardMatchingThreshold(forwardMatchingThreshold));
        final String uncorrectedInput = "D";

        final List<String> expectedCorrectedItems = List.of("delete");
        final CorrectionStatus expectedCorrectionStatus = CorrectionStatus.CORRECTED;
        final CorrectionResult<String> expectedCorrectionResult = new CorrectionResult<>(
                expectedCorrectionStatus, expectedCorrectedItems);

        CorrectionResult<String> correctionResult = correctionEngine.correct(uncorrectedInput);
        assertEquals(expectedCorrectionResult, correctionResult);
    }

    @Test
    public void correct_withForwardMatchingAndInvalidPrefixAndBelowForwardMatchingThreshold_noForwardMatching() {
        final int distanceThreshold = 2;
        final int forwardMatchingThreshold = 2;
        final StringCorrectionEngine correctionEngine = new StringCorrectionEngine(OPTIONS,
                new CorrectionEngineParameters()
                    .setForwardMatching(true)
                    .setDistanceThreshold(distanceThreshold)
                    .setForwardMatchingThreshold(forwardMatchingThreshold));
        final String uncorrectedInput = "F";

        final CorrectionStatus expectedCorrectionStatus = CorrectionStatus.FAILED;
        final CorrectionResult<String> expectedCorrectionResult = new CorrectionResult<>(
                expectedCorrectionStatus);

        CorrectionResult<String> correctionResult = correctionEngine.correct(uncorrectedInput);
        assertEquals(expectedCorrectionResult, correctionResult);
    }

    @Test
    public void correct_withForwardMatchingAndWithinThresholds_forwardMatchingAndCorrectionDone() {
        final int distanceThreshold = 2;
        final int forwardMatchingThreshold = 2;
        final StringCorrectionEngine correctionEngine = new StringCorrectionEngine(OPTIONS,
                new CorrectionEngineParameters()
                    .setForwardMatching(true)
                    .setDistanceThreshold(distanceThreshold)
                    .setForwardMatchingThreshold(forwardMatchingThreshold));
        final String uncorrectedInput = "DL";

        final List<String> expectedCorrectedItems = List.of("delete");
        final CorrectionStatus expectedCorrectionStatus = CorrectionStatus.CORRECTED;
        final CorrectionResult<String> expectedCorrectionResult = new CorrectionResult<>(
                expectedCorrectionStatus, expectedCorrectedItems);

        CorrectionResult<String> correctionResult = correctionEngine.correct(uncorrectedInput);
        assertEquals(expectedCorrectionResult, correctionResult);
    }

}


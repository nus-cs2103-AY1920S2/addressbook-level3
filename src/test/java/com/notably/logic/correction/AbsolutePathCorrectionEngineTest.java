package com.notably.logic.correction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.notably.commons.core.path.AbsolutePath;
import com.notably.commons.core.path.exceptions.InvalidPathException;

public class AbsolutePathCorrectionEngineTest {
    private static List<AbsolutePath> possiblePaths;

    @BeforeAll
    public static void setup() throws InvalidPathException {
        possiblePaths = List.of(
                AbsolutePath.fromString("/path/to/block"),
                AbsolutePath.fromString("/path/to/another"),
                AbsolutePath.fromString("/path/to/another/block")
        );
    }

    @Test
    public void constructor_constructWithNegativeDistanceThreshold_exceptionThrown() {
        final int negativeDistanceThreshold = -1;

        assertThrows(IllegalArgumentException.class, () ->
                new AbsolutePathCorrectionEngine(possiblePaths, negativeDistanceThreshold));
    }

    @Test
    public void correct_withinDistanceThreshold_correctionDone() throws InvalidPathException {
        final int distanceThreshold = 2;
        final AbsolutePathCorrectionEngine correctionEngine = new AbsolutePathCorrectionEngine(
                possiblePaths, distanceThreshold);
        final AbsolutePath uncorrectedInput = AbsolutePath.fromString("/path/to/blcok");

        final AbsolutePath expectedCorrectedItem = AbsolutePath.fromString("/path/to/block");
        final CorrectionStatus expectedCorrectedStatus = CorrectionStatus.CORRECTED;
        final CorrectionResult<AbsolutePath> expectedCorrectionResult = new CorrectionResult<>(
                expectedCorrectedStatus, expectedCorrectedItem);

        CorrectionResult<AbsolutePath> correctionResult = correctionEngine.correct(uncorrectedInput);
        assertEquals(expectedCorrectionResult, correctionResult);
    }

    @Test
    public void correct_exceedDistanceThreshold_correctionFailed() throws InvalidPathException {
        final int distanceThreshold = 1;
        final AbsolutePathCorrectionEngine correctionEngine = new AbsolutePathCorrectionEngine(
                possiblePaths, distanceThreshold);
        final AbsolutePath uncorrectedInput = AbsolutePath.fromString("/path/to/anoth");

        final CorrectionStatus expectedCorrectedStatus = CorrectionStatus.FAILED;
        final CorrectionResult<AbsolutePath> expectedCorrectionResult = new CorrectionResult<>(expectedCorrectedStatus);

        CorrectionResult<AbsolutePath> correctionResult = correctionEngine.correct(uncorrectedInput);
        assertEquals(expectedCorrectionResult, correctionResult);
    }

    @Test
    public void correct_exactMatch_noCorrection() throws InvalidPathException {
        final int distanceThreshold = 1;
        final AbsolutePathCorrectionEngine correctionEngine = new AbsolutePathCorrectionEngine(
                possiblePaths, distanceThreshold);
        final AbsolutePath uncorrectedInput = AbsolutePath.fromString("/path/to/another/block");

        final AbsolutePath expectedCorrectedItem = AbsolutePath.fromString("/path/to/another/block");
        final CorrectionStatus expectedCorrectionStatus = CorrectionStatus.UNCHANGED;
        final CorrectionResult<AbsolutePath> expectedCorrectionResult = new CorrectionResult<>(
                expectedCorrectionStatus, expectedCorrectedItem);

        CorrectionResult<AbsolutePath> correctionResult = correctionEngine.correct(uncorrectedInput);
        assertEquals(expectedCorrectionResult, correctionResult);
    }
}


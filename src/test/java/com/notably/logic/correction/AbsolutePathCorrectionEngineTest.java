package com.notably.logic.correction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.notably.commons.path.AbsolutePath;
import com.notably.model.Model;
import com.notably.model.ModelManager;
import com.notably.model.block.BlockImpl;
import com.notably.model.block.BlockModel;
import com.notably.model.block.BlockModelImpl;
import com.notably.model.block.Title;
import com.notably.model.suggestion.SuggestionModel;
import com.notably.model.suggestion.SuggestionModelImpl;
import com.notably.model.viewstate.ViewStateModel;
import com.notably.model.viewstate.ViewStateModelImpl;

public class AbsolutePathCorrectionEngineTest {
    private static AbsolutePath toBlock;
    private static AbsolutePath toAnother;
    private static AbsolutePath toAnotherBlock;
    private static Model model;

    @BeforeAll
    public static void setUp() {
        // Set up paths
        toBlock = AbsolutePath.fromString("/block");
        toAnother = AbsolutePath.fromString("/another");
        toAnotherBlock = AbsolutePath.fromString("/another/block");

        // Set up model
        BlockModel blockModel = new BlockModelImpl();
        SuggestionModel suggestionModel = new SuggestionModelImpl();
        ViewStateModel viewStateModel = new ViewStateModelImpl();
        model = new ModelManager(blockModel, suggestionModel, viewStateModel);

        // Populate model with test data
        model.addBlockToCurrentPath(new BlockImpl(new Title("block")));
        model.addBlockToCurrentPath(new BlockImpl(new Title("another")));

        model.setCurrentlyOpenBlock(toAnother);
        model.addBlockToCurrentPath(new BlockImpl(new Title("block")));
    }

    @Test
    public void constructor_constructWithNegativeDistanceThreshold_exceptionThrown() {
        final int negativeDistanceThreshold = -1;

        assertThrows(IllegalArgumentException.class, () ->
                new AbsolutePathCorrectionEngine(model, negativeDistanceThreshold, true));
    }

    @Test
    public void correct_withinDistanceThreshold_correctionDone() {
        final int distanceThreshold = 2;
        final AbsolutePathCorrectionEngine correctionEngine = new AbsolutePathCorrectionEngine(
                model, distanceThreshold, false);
        final AbsolutePath uncorrectedInput = AbsolutePath.fromString("/blcok");

        final AbsolutePath expectedCorrectedItem = toBlock;
        final CorrectionStatus expectedCorrectedStatus = CorrectionStatus.CORRECTED;
        final CorrectionResult<AbsolutePath> expectedCorrectionResult = new CorrectionResult<>(
                expectedCorrectedStatus, expectedCorrectedItem);

        CorrectionResult<AbsolutePath> correctionResult = correctionEngine.correct(uncorrectedInput);
        assertEquals(expectedCorrectionResult, correctionResult);
    }

    @Test
    public void correct_exceedDistanceThreshold_correctionFailed() {
        final int distanceThreshold = 1;
        final AbsolutePathCorrectionEngine correctionEngine = new AbsolutePathCorrectionEngine(
                model, distanceThreshold, false);
        final AbsolutePath uncorrectedInput = AbsolutePath.fromString("/anoth");

        final CorrectionStatus expectedCorrectedStatus = CorrectionStatus.FAILED;
        final CorrectionResult<AbsolutePath> expectedCorrectionResult = new CorrectionResult<>(expectedCorrectedStatus);

        CorrectionResult<AbsolutePath> correctionResult = correctionEngine.correct(uncorrectedInput);
        assertEquals(expectedCorrectionResult, correctionResult);
    }

    @Test
    public void correct_exactMatch_noCorrection() {
        final int distanceThreshold = 1;
        final AbsolutePathCorrectionEngine correctionEngine = new AbsolutePathCorrectionEngine(
                model, distanceThreshold, false);
        final AbsolutePath uncorrectedInput = AbsolutePath.fromString("/another/block");

        final AbsolutePath expectedCorrectedItem = toAnotherBlock;
        final CorrectionStatus expectedCorrectionStatus = CorrectionStatus.UNCHANGED;
        final CorrectionResult<AbsolutePath> expectedCorrectionResult = new CorrectionResult<>(
                expectedCorrectionStatus, expectedCorrectedItem);

        CorrectionResult<AbsolutePath> correctionResult = correctionEngine.correct(uncorrectedInput);
        assertEquals(expectedCorrectionResult, correctionResult);
    }

    @Test
    public void correct_differentCase() {
        final int distanceThreshold = 1;
        final AbsolutePathCorrectionEngine correctionEngine = new AbsolutePathCorrectionEngine(
                model, distanceThreshold, false);
        final AbsolutePath uncorrectedInput = AbsolutePath.fromString("/ANOTHER/BLOCK");

        final AbsolutePath expectedCorrectedItem = toAnotherBlock;
        final CorrectionStatus expectedCorrectionStatus = CorrectionStatus.CORRECTED;
        final CorrectionResult<AbsolutePath> expectedCorrectionResult = new CorrectionResult<>(
                expectedCorrectionStatus, expectedCorrectedItem);

        CorrectionResult<AbsolutePath> correctionResult = correctionEngine.correct(uncorrectedInput);
        assertEquals(expectedCorrectionResult, correctionResult);
    }

    @Test
    public void correct_forwardMatching() {
        final int distanceThreshold = 1;
        final AbsolutePathCorrectionEngine correctionEngine = new AbsolutePathCorrectionEngine(
                model, distanceThreshold, true);
        final AbsolutePath uncorrectedInput = AbsolutePath.fromString("/another/bl");

        final AbsolutePath expectedCorrectedItem = toAnotherBlock;
        final CorrectionStatus expectedCorrectionStatus = CorrectionStatus.CORRECTED;
        final CorrectionResult<AbsolutePath> expectedCorrectionResult = new CorrectionResult<>(
                expectedCorrectionStatus, expectedCorrectedItem);

        CorrectionResult<AbsolutePath> correctionResult = correctionEngine.correct(uncorrectedInput);
        assertEquals(expectedCorrectionResult, correctionResult);
    }

    @Test
    public void correct_forwardMatchingDifferingCases() {
        final int distanceThreshold = 1;
        final AbsolutePathCorrectionEngine correctionEngine = new AbsolutePathCorrectionEngine(
                model, distanceThreshold, true);
        final AbsolutePath uncorrectedInput = AbsolutePath.fromString("/another/BL");

        final AbsolutePath expectedCorrectedItem = toAnotherBlock;
        final CorrectionStatus expectedCorrectionStatus = CorrectionStatus.CORRECTED;
        final CorrectionResult<AbsolutePath> expectedCorrectionResult = new CorrectionResult<>(
                expectedCorrectionStatus, expectedCorrectedItem);

        CorrectionResult<AbsolutePath> correctionResult = correctionEngine.correct(uncorrectedInput);
        assertEquals(expectedCorrectionResult, correctionResult);
    }
}


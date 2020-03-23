package com.notably.logic.correction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.notably.commons.core.path.AbsolutePath;
import com.notably.model.Model;
import com.notably.model.block.BlockImpl;
import com.notably.model.block.BlockTree;
import com.notably.model.block.BlockTreeImpl;
import com.notably.model.block.Title;
import com.notably.testutil.ModelStubBase;

public class AbsolutePathCorrectionEngineTest {
    private static class ModelStub extends ModelStubBase {
        @Override
        public BlockTree getBlockTree() {
            BlockTree stubTree = new BlockTreeImpl();
            stubTree.add(AbsolutePath.fromString("/"), new BlockImpl(new Title("block")));
            stubTree.add(AbsolutePath.fromString("/"), new BlockImpl(new Title("another")));
            stubTree.add(AbsolutePath.fromString("/another"), new BlockImpl(new Title("block")));
            return stubTree;
        }
    }

    private static final Model modelStub = new ModelStub();

    @Test
    public void constructor_constructWithNegativeDistanceThreshold_exceptionThrown() {
        final int negativeDistanceThreshold = -1;

        assertThrows(IllegalArgumentException.class, () ->
                new AbsolutePathCorrectionEngine(modelStub, negativeDistanceThreshold));
    }

    @Test
    public void correct_withinDistanceThreshold_correctionDone() {
        final int distanceThreshold = 2;
        final AbsolutePathCorrectionEngine correctionEngine = new AbsolutePathCorrectionEngine(
                modelStub, distanceThreshold);
        final AbsolutePath uncorrectedInput = AbsolutePath.fromString("/blcok");

        final AbsolutePath expectedCorrectedItem = AbsolutePath.fromString("/block");
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
                modelStub, distanceThreshold);
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
                modelStub, distanceThreshold);
        final AbsolutePath uncorrectedInput = AbsolutePath.fromString("/another/block");

        final AbsolutePath expectedCorrectedItem = AbsolutePath.fromString("/another/block");
        final CorrectionStatus expectedCorrectionStatus = CorrectionStatus.UNCHANGED;
        final CorrectionResult<AbsolutePath> expectedCorrectionResult = new CorrectionResult<>(
                expectedCorrectionStatus, expectedCorrectedItem);

        CorrectionResult<AbsolutePath> correctionResult = correctionEngine.correct(uncorrectedInput);
        assertEquals(expectedCorrectionResult, correctionResult);
    }
}


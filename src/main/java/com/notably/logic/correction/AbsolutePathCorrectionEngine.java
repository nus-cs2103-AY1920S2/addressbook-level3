package com.notably.logic.correction;

import java.util.Objects;

import com.notably.commons.core.path.AbsolutePath;
import com.notably.model.Model;

public class AbsolutePathCorrectionEngine implements CorrectionEngine<AbsolutePath> {
    private final Model model;

    public AbsolutePathCorrectionEngine(Model model) {
        Objects.requireNonNull(model);

        this.model = model;
    }

    @Override
    public CorrectionResult<AbsolutePath> correct(AbsolutePath uncorrected) {
        Objects.requireNonNull(uncorrected);

        return new CorrectionResult<>(CorrectionStatus.FAILED);
    }
}


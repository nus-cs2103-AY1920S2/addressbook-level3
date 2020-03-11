package com.notably.logic.correction;

import java.util.Optional;

public class CorrectionResultStub implements CorrectionResult {
    private Optional<String> correctedInput;
    public CorrectionResultStub(Optional<String> input){
        this.correctedInput = input;
    }

    public CorrectionStatus getCorrectionStatus(){
        return CorrectionStatus.CORRECTED;
    }
    public Optional<String> getCorrectedItem() {
        return this.correctedInput;
    }

}

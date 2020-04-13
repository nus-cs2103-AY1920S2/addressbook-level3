package seedu.address.model.step;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.step.exceptions.DuplicateStepException;
import seedu.address.model.step.exceptions.StepNotFoundException;


public class UniqueStepListTest {

    private static final Step TESTSTEP = new Step("Cook 1 egg."); // for the time being only
    private static final Step TESTSTEP2 = new Step("Boil 2 egg."); // for the time being only
    private final UniqueStepList uniqueStepList = new UniqueStepList();

    @Test
    public void contains_nullStep_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStepList.contains(null));
    }

    @Test
    public void contains_stepNotInList_returnsFalse() {
        assertFalse(uniqueStepList.contains(TESTSTEP));
    }

    @Test
    public void contains_stepInList_returnsTrue() {
        uniqueStepList.add(TESTSTEP);
        assertTrue(uniqueStepList.contains(TESTSTEP));
    }

    @Test
    public void add_nullStep_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStepList.add(null));
    }

    @Test
    public void add_duplicateStep_throwsDuplicateStepException() {
        uniqueStepList.add(TESTSTEP);
        assertThrows(DuplicateStepException.class, () -> uniqueStepList.add(TESTSTEP));
    }

    @Test
    public void setStep_nullTargetStep_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStepList.setStep(null, TESTSTEP));
    }

    @Test
    public void setStep_nullEditedStep_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStepList.setStep(TESTSTEP, null));
    }

    @Test
    public void setStep_targetStepNotInList_throwsStepNotFoundException() {
        assertThrows(StepNotFoundException.class, () -> uniqueStepList.setStep(TESTSTEP, TESTSTEP));
    }

    @Test
    public void setStep_editedStepIsSameStep_success() {
        uniqueStepList.add(TESTSTEP);
        uniqueStepList.setStep(TESTSTEP, TESTSTEP);
        UniqueStepList expectedUniqueStepList = new UniqueStepList();
        expectedUniqueStepList.add(TESTSTEP);
        assertEquals(expectedUniqueStepList, uniqueStepList);
    }

    @Test
    public void setStep_editedStepHasDifferentDescription_success() {
        uniqueStepList.add(TESTSTEP);
        uniqueStepList.setStep(TESTSTEP, TESTSTEP2);
        UniqueStepList expectedUniqueStepList = new UniqueStepList();
        expectedUniqueStepList.add(TESTSTEP2);
        assertEquals(expectedUniqueStepList, uniqueStepList);
    }

    @Test
    public void setStep_editedStepHasNonUniqueDescription_throwsDuplicateStepException() {
        uniqueStepList.add(TESTSTEP);
        uniqueStepList.add(TESTSTEP2);
        assertThrows(DuplicateStepException.class, () -> uniqueStepList.setStep(TESTSTEP, TESTSTEP2));
    }

    @Test
    public void remove_nullStep_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStepList.remove((Step) null));
    }

    @Test
    public void remove_stepDoesNotExist_throwsStepNotFoundException() {
        assertThrows(StepNotFoundException.class, () -> uniqueStepList.remove(TESTSTEP));
    }

    @Test
    public void remove_existingStep_removesStep() {
        uniqueStepList.add(TESTSTEP);
        uniqueStepList.remove(TESTSTEP);
        UniqueStepList expectedUniqueStepList = new UniqueStepList();
        assertEquals(expectedUniqueStepList, uniqueStepList);
    }

    @Test
    public void setSteps_nullUniqueStepList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStepList.setSteps((UniqueStepList) null));
    }

    @Test
    public void setSteps_uniqueStepList_replacesOwnListWithProvidedUniqueStepList() {
        uniqueStepList.add(TESTSTEP);
        UniqueStepList expectedUniqueStepList = new UniqueStepList();
        expectedUniqueStepList.add(TESTSTEP2);
        uniqueStepList.setSteps(expectedUniqueStepList);
        assertEquals(expectedUniqueStepList, uniqueStepList);
    }

    @Test
    public void setSteps_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStepList.setSteps((List<Step>) null));
    }

    @Test
    public void setSteps_list_replacesOwnListWithProvidedList() {
        uniqueStepList.add(TESTSTEP);
        List<Step> stepList = Collections.singletonList(TESTSTEP2);
        uniqueStepList.setSteps(stepList);
        UniqueStepList expectedUniqueStepList = new UniqueStepList();
        expectedUniqueStepList.add(TESTSTEP2);
        assertEquals(expectedUniqueStepList, uniqueStepList);
    }

    @Test
    public void setSteps_listWithDuplicateSteps_throwsDuplicateStepException() {
        List<Step> listWithDuplicateSteps = Arrays.asList(TESTSTEP, TESTSTEP);
        assertThrows(DuplicateStepException.class, () -> uniqueStepList.setSteps(listWithDuplicateSteps));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueStepList.asUnmodifiableObservableList().remove(0));
    }
}

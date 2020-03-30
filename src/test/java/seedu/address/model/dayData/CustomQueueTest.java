package seedu.address.model.dayData;

import org.junit.jupiter.api.Test;
import seedu.address.model.dayData.exceptions.DayDataNotFoundException;
import seedu.address.model.dayData.exceptions.InvalidTableException;
import seedu.address.testutil.DayDataBuilder;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.storage.JsonAdaptedDayDataTest.VALID_POM_DURATION_DATA;
import static seedu.address.storage.JsonAdaptedDayDataTest.VALID_TASKS_DONE_DATA;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalDayDatas.DAYNEW;
import static seedu.address.testutil.TypicalDayDatas.DAYNEW2;

public class CustomQueueTest {

    private CustomQueue customQueue = new CustomQueue();
    private LocalDate TYPICAL_TASKS_LATEST_LOCAL_DATE = LocalDate.parse("2020-03-23");

    @Test
    public void contains_nullDayDatathrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> customQueue.contains(null));
    }

    @Test
    public void contains_DayDataNotInList_returnsFalse() {
        assertFalse(customQueue.contains(DAYNEW));
    }

    @Test
    public void contains_taskInList_returnsTrue() throws InvalidTableException {
        customQueue.init(TYPICAL_TASKS_LATEST_LOCAL_DATE);
        customQueue.pop();

        customQueue.add(DAYNEW);
        assertTrue(customQueue.contains(DAYNEW));
    }

    @Test
    public void contains_dayDataWithSameIdentityFieldsInList_returnsTrue() throws InvalidTableException {
        customQueue.init(TYPICAL_TASKS_LATEST_LOCAL_DATE);
        customQueue.pop();

        customQueue.add(DAYNEW);
        DayData editedDayData =
                new DayDataBuilder(DAYNEW)
                        .withPomDurationData(VALID_POM_DURATION_DATA)
                        .withTasksDoneData(VALID_TASKS_DONE_DATA)
                        .build();
        assertTrue(customQueue.contains(editedDayData));
    }

    @Test
    public void add_nullDayData_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> customQueue.add(null));
    }
    
    @Test
    public void setDayData_nullTargetDayData_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> customQueue.setDayData(null, DAYNEW));
    }

    @Test
    public void setDayData_nullEditedDayDatan_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> customQueue.setDayData(DAYNEW, null));
    }

    @Test
    public void setDayData_targetDayDataNotInList_throwsDayDataNotFoundException() {
        assertThrows(
                DayDataNotFoundException.class, () -> customQueue.setDayData(DAYNEW, DAYNEW));
    }

    @Test
    public void setDayData_editedDayDataIsSameDayData_success() throws InvalidTableException {
        customQueue.init(TYPICAL_TASKS_LATEST_LOCAL_DATE);
        customQueue.pop();

        customQueue.add(DAYNEW);
        customQueue.setDayData(DAYNEW, DAYNEW);

        CustomQueue expectedCustomQueue = new CustomQueue();
        expectedCustomQueue.init(TYPICAL_TASKS_LATEST_LOCAL_DATE);
        expectedCustomQueue.pop();

        expectedCustomQueue.add(DAYNEW);
        assertEquals(expectedCustomQueue, customQueue);
    }

    @Test
    public void setDayData_editedDayDataHasSameIdentity_success() throws InvalidTableException {
        customQueue.init(TYPICAL_TASKS_LATEST_LOCAL_DATE);
        customQueue.pop();

        customQueue.add(DAYNEW);
        DayData editedDayData =
                new DayDataBuilder(DAYNEW)
                        .withPomDurationData(VALID_POM_DURATION_DATA)
                        .withTasksDoneData(VALID_TASKS_DONE_DATA)
                        .build();
        customQueue.setDayData(DAYNEW, editedDayData);

        CustomQueue expectedCustomQueue = new CustomQueue();
        expectedCustomQueue.init(TYPICAL_TASKS_LATEST_LOCAL_DATE);
        expectedCustomQueue.pop();

        expectedCustomQueue.add(editedDayData);
        assertEquals(expectedCustomQueue, customQueue);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() throws InvalidTableException {
        customQueue.init(TYPICAL_TASKS_LATEST_LOCAL_DATE);
        customQueue.pop();

        customQueue.add(DAYNEW);
        customQueue.setDayData(DAYNEW, DAYNEW2);

        CustomQueue expectedCustomQueue = new CustomQueue();
        expectedCustomQueue.init(TYPICAL_TASKS_LATEST_LOCAL_DATE);
        expectedCustomQueue.pop();

        expectedCustomQueue.add(DAYNEW2);
        assertEquals(expectedCustomQueue, customQueue);
    }

    @Test
    public void remove_nullDayData_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> customQueue.remove(null));
    }

    @Test
    public void remove_dayDataDoesNotExist_throwsDayDataNotFoundException() {
        assertThrows(DayDataNotFoundException.class, () -> customQueue.remove(DAYNEW));
    }

    @Test
    public void setDayDatas_nullCustomQueue_throwsNullPointerException() {
        assertThrows(
                NullPointerException.class, () -> customQueue.setDayDatas((CustomQueue)null));
    }

    @Test
    public void setDayDatas_customQueue_replacesOwnListWithProvidedCustomQueue() throws InvalidTableException {
        customQueue.init(TYPICAL_TASKS_LATEST_LOCAL_DATE);
        customQueue.pop();

        customQueue.add(DAYNEW);

        CustomQueue expectedCustomQueue = new CustomQueue();
        expectedCustomQueue.init(TYPICAL_TASKS_LATEST_LOCAL_DATE);
        expectedCustomQueue.pop();

        expectedCustomQueue.add(DAYNEW);
        customQueue.setDayDatas(expectedCustomQueue);
        assertEquals(expectedCustomQueue, customQueue);
    }

    @Test
    public void setDayDatas_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> customQueue.setDayDatas((List<DayData>) null));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(
                UnsupportedOperationException.class,
                () -> customQueue.asUnmodifiableObservableList().remove(0));
    }
}


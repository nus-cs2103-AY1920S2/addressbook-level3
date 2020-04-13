package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.consult.Consult;
import seedu.address.model.event.consult.UniqueConsultList;
import seedu.address.model.event.consult.exceptions.ConsultNotFoundException;
import seedu.address.model.event.consult.exceptions.DuplicateConsultException;
import seedu.address.testutil.ConsultBuilder;

public class UniqueConsultListTest {
    private UniqueConsultList uniqueConsultList = new UniqueConsultList();
    private Consult consult1 = new ConsultBuilder().build();
    private Consult consult2 = new ConsultBuilder().withBeginDateTime("2020-03-04 15:00")
        .withEndDateTime("2020-03-04 17:00").build();


    public UniqueConsultListTest() throws ParseException {
    }

    @Test
    public void contains_emptyConsult_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueConsultList.contains(null));
    }

    @Test
    public void contains_consultNotInList_returnFalse() {
        assertFalse(uniqueConsultList.contains(consult1));
    }

    @Test
    public void contains_consultInList_returnTrue() {
        uniqueConsultList.add(consult1);
        assertTrue(uniqueConsultList.contains(consult1));
    }

    @Test
    public void hasSameDate_emptyConsult_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueConsultList.hasSameDateTime(null));
    }

    @Test
    public void hasSameDateTime_differentDateTime_returnFalse() {
        uniqueConsultList.clearConsults();
        assertFalse(uniqueConsultList.hasSameDateTime(consult1));
    }

    @Test
    public void add_nullConsult_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueConsultList.add(null));
    }

    @Test
    public void add_duplicateConsult_throwsDuplicateConsultException() {
        uniqueConsultList.add(consult1);
        assertThrows(DuplicateConsultException.class, () -> uniqueConsultList.add(consult1));
    }

    @Test
    public void add_validConsult_returnsSuccess() {
        uniqueConsultList.clearConsults();
        uniqueConsultList.add(consult1);
        assertTrue(uniqueConsultList.contains(consult1));
    }

    @Test
    public void setConsult_nullConsultTarget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueConsultList.setConsult(null, consult1));
    }

    @Test
    public void setConsult_nullEditedConsult_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueConsultList.setConsult(consult1, null));
    }

    @Test
    public void setConsult_consultNotInList_throwsConsultNotFoundException() {
        uniqueConsultList.clearConsults();
        assertThrows(ConsultNotFoundException.class, () -> uniqueConsultList.setConsult(consult1, consult1));
    }

    @Test
    public void setConsult_differentConsult_success() {
        uniqueConsultList.clearConsults();
        uniqueConsultList.add(consult1);
        uniqueConsultList.setConsult(consult1, consult2);
        UniqueConsultList expectedList = new UniqueConsultList();
        expectedList.add(consult2);
        assertEquals(expectedList, uniqueConsultList);
    }

    @Test
    public void remove_emptyConsult_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueConsultList.remove(null));
    }

    @Test
    public void remove_consultNotInList_throwsConsultNotFoundException() {
        uniqueConsultList.clearConsults();
        assertThrows(ConsultNotFoundException.class, () -> uniqueConsultList.remove(consult1));
    }

    @Test
    public void remove_consultInList_success() {
        uniqueConsultList.clearConsults();
        uniqueConsultList.add(consult1);
        uniqueConsultList.remove(consult1);
        UniqueConsultList expectedList = new UniqueConsultList();
        assertEquals(expectedList, uniqueConsultList);
    }

    @Test
    public void setConsults_emptyConsult_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueConsultList.setConsults((List<Consult>) null));
    }

    @Test
    public void setConsults_newList_success() {
        uniqueConsultList.clearConsults();
        List<Consult> expectedList = Collections.singletonList(consult1);
        uniqueConsultList.setConsults(expectedList);
        UniqueConsultList expectedConsultList = new UniqueConsultList();
        expectedConsultList.add(consult1);
        assertEquals(expectedConsultList, uniqueConsultList);
    }

}

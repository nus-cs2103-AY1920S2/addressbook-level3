package seedu.expensela.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.expensela.ui.DateLabelMaker.getColouredDateLabel;

import org.junit.jupiter.api.Test;

class DateLabelMakerTest {

    @Test
    public static void equals() {
        assertEquals(getColouredDateLabel("2020-01"), getColouredDateLabel("2020-01"));
        assertNotEquals(getColouredDateLabel("2020-01"), getColouredDateLabel("2020-02"));
        assertNotEquals(getColouredDateLabel("2020-01"), getColouredDateLabel("2021-01"));
        assertNotEquals(getColouredDateLabel("2020-01"), getColouredDateLabel("FOOD"));
    }

}

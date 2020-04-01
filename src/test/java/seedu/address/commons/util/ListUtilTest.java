package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertFalse;

import static seedu.address.model.parcel.parcelattributes.TimeStamp.FORMAT_CHECKER;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class ListUtilTest {

    //---------------- Tests for isBeforeDeadline --------------------------------------
    // Note : the method isToday is not tested because the method isToday depends on
    // the method isBeforeDeadline. If isBeforeDeadline works, the method isToday will work

    @Test
    public void isBeforeDeadlineTest() {

        // EP: Dates Not Today (before current date and time)
        assertFalse(ListUtil.isBeforeDeadline(LocalDateTime.parse("2020-02-31 1200", FORMAT_CHECKER)));
        assertFalse(ListUtil.isBeforeDeadline(LocalDateTime.parse("2020-02-31 1200", FORMAT_CHECKER)));
        assertFalse(ListUtil.isBeforeDeadline(LocalDateTime.parse("1997-07-31 1200", FORMAT_CHECKER)));

        // EP: Dates not Today (after current date and time)
        assertFalse(ListUtil.isBeforeDeadline(LocalDateTime.parse("2022-12-31 1200", FORMAT_CHECKER)));
        assertFalse(ListUtil.isBeforeDeadline(LocalDateTime.parse("2100-07-31 1200", FORMAT_CHECKER)));
    }
}

package seedu.address.testutil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.admin.Admin;
import seedu.address.model.admin.Date;
import seedu.address.model.student.Student;

/**
 * A utility class containing a list of {@code Date} objects to be used in tests.
 */
public class TypicalDates {

    public static final List<Student> STUDENTS = TypicalStudents.getTypicalStudents();
    public static final Date JAN_26_2020 = new DateBuilder().withDate(LocalDate.parse("2020-01-26"))
            .withStudents(STUDENTS).build();

    public static final Date FEB_26_2020 = new DateBuilder().withDate(LocalDate.parse("2020-02-26"))
            .withStudents(STUDENTS).build();

    private TypicalDates() {} // prevents instantiation

    /**
     * Returns an {@code Date} with all the typical dates.
     */
    public static Admin getTypicalAdmin() {
        Admin ad = new Admin();
        for (Date date : getTypicalDates()) {
            ad.addDate(date);
        }
        return ad;
    }

    public static List<Date> getTypicalDates() {
        return new ArrayList<>(Arrays.asList(JAN_26_2020, FEB_26_2020));
    }
}

package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AttendanceTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Temperature(null));
    }

    @Test
    public void constructor_invalidAttendance_throwsIllegalArgumentException() {
        String invalidAttendance = "";
        assertThrows(IllegalArgumentException.class, () -> new Attendance(invalidAttendance));
    }

    @Test
    public void isValidAttendance() {
        // null attendances
        assertThrows(NullPointerException.class, () -> Attendance.isValidAttendance(null));

        // invalid attendances
        assertFalse(Attendance.isValidAttendance("")); // empty string
        assertFalse(Attendance.isValidAttendance(" ")); // spaces only
        assertFalse(Attendance.isValidAttendance("Present1")); // containing numbers
        assertFalse(Attendance.isValidAttendance("temperature")); // does not contain keyword
        assertFalse(Attendance.isValidAttendance("present")); // p is not capital
        assertFalse(Attendance.isValidAttendance("PreSent")); // S is not small capital
        assertFalse(Attendance.isValidAttendance("Pre sent")); // spaces within alphabets

        // valid attendances
        assertTrue(Attendance.isValidAttendance("Present")); // valid attendance
        assertTrue(Attendance.isValidAttendance("Sick")); // valid attendance
        assertTrue(Attendance.isValidAttendance("Late")); // valid attendance
        assertTrue(Attendance.isValidAttendance("Absent")); // valid attendance
    }
}

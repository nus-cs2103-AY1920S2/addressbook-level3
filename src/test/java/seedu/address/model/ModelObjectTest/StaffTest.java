package seedu.address.model.ModelObjectTest;

import org.junit.jupiter.api.Test;
import seedu.address.model.modelObjectTags.*;
import seedu.address.model.modelStaff.Staff;
import seedu.address.model.tag.Tag;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTeacher.*;

public class StaffTest {
    public static Set<Tag> tags = new HashSet<Tag>();

    @Test
    public void isValidStaff() {
        // null staff
        assertThrows(NullPointerException.class, () -> new Staff(null, null,
                null, null, null, null, null, null, null));
        // null name
        assertThrows(NullPointerException.class, () -> new Staff(null, new ID("16100"),
                new Gender("m"), Staff.Level.TEACHER, new Phone("12345678"), new Email("test@gmail.com"),
                new Salary("3000"), new Address("City Hall"), tags));
        // null gender
        assertThrows(NullPointerException.class, () -> new Staff(null, new ID("16100"),
                null, Staff.Level.TEACHER, new Phone("12345678"), new Email("test@gmail.com"),
                new Salary("3000"), new Address("City Hall"), tags));
        // null level
        assertThrows(NullPointerException.class, () -> new Staff(null, new ID("16100"),
                new Gender("m"), null, new Phone("12345678"), new Email("test@gmail.com"),
                new Salary("3000"), new Address("City Hall"), tags));
    }

    @Test
    public void equals_sameStaff_success() {
        assertEquals(DEFAULT_STAFF_1, DEFAULT_STAFF_2);
        assertEquals(TWINS_A1, TWINS_A2);
    }

    @Test
    public void equals_differentStaff_failure() {
        // different gender
        assertNotEquals(TWINS_B1, TWINS_B2);
        // different level
        assertNotEquals(TWINS_C1, TWINS_C2);
        // different id
        assertNotEquals(TWINS_D1, TWINS_D2);
        // different everything
        assertNotEquals(AMY, BOB);
    }
}

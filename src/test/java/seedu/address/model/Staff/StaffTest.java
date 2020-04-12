package seedu.address.model.Staff;

import org.junit.jupiter.api.Test;
import seedu.address.model.modelObjectTags.*;
import seedu.address.model.modelStaff.Staff;
import seedu.address.model.tag.Tag;

import java.util.HashSet;
import java.util.Set;

import static seedu.address.testutil.Assert.assertThrows;

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
}

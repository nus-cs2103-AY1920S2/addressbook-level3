package seedu.address.model.ModelObjectTest;

import org.junit.jupiter.api.Test;
import seedu.address.model.modelObjectTags.Gender;
import seedu.address.model.modelObjectTags.Name;
import seedu.address.model.modelStudent.Student;
import seedu.address.model.tag.Tag;

import java.util.HashSet;
import java.util.Set;

import static seedu.address.testutil.Assert.assertThrows;

public class StudentTest {
    public static Name validName = new Name("Test Name");
    public static Gender validGender = new Gender("m");
    public static Set<Tag> validTags = new HashSet<Tag>();

    @Test
    public void isValidStudent() {
        // null staff
        assertThrows(NullPointerException.class, () -> new Student(null, null, null));
        // null name
        assertThrows(NullPointerException.class, () -> new Student(null, validGender, validTags));
        // null gender
        assertThrows(NullPointerException.class, () -> new Student(validName, null, validTags));
        // null tags
        assertThrows(NullPointerException.class, () -> new Student(validName, validGender, null));
    }
}

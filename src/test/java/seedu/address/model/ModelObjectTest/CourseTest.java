package seedu.address.model.ModelObjectTest;

import org.junit.jupiter.api.Test;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.modelObjectTags.Amount;
import seedu.address.model.modelObjectTags.Name;
import seedu.address.model.tag.Tag;

import java.util.HashSet;
import java.util.Set;

import static seedu.address.testutil.Assert.assertThrows;

public class CourseTest {
    public static Name validName = new Name("Test Name");
    public static Amount validAmount = new Amount("2000");
    public static Set<Tag> validTags = new HashSet<Tag>();

    @Test
    public void isValidCourse() {
        // null staff
        assertThrows(NullPointerException.class, () -> new Course(null, null, null));
        // null name
        assertThrows(NullPointerException.class, () -> new Course(null, validAmount, validTags));
        // null amount
        assertThrows(NullPointerException.class, () -> new Course(validName, null, validTags));
        // null tags
        assertThrows(NullPointerException.class, () -> new Course(validName, validAmount, null));
    }
}

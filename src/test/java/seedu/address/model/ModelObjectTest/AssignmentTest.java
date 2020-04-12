package seedu.address.model.ModelObjectTest;

import org.junit.jupiter.api.Test;
import seedu.address.model.modelAssignment.Assignment;
import seedu.address.model.modelObjectTags.Date;
import seedu.address.model.modelObjectTags.Name;
import seedu.address.model.tag.Tag;

import java.util.HashSet;
import java.util.Set;

import static seedu.address.testutil.Assert.assertThrows;

public class AssignmentTest {
    public static Name validName = new Name("Test Name");
    public static Date validDate = new Date("2020-04-13");
    public static Set<Tag> validTags = new HashSet<Tag>();

    @Test
    public void isValidAssignment() {
        // null assignment
        assertThrows(NullPointerException.class, () -> new Assignment(null, null, null));
        // null name
        assertThrows(NullPointerException.class, () -> new Assignment(null, validDate, validTags));
        // null amount
        assertThrows(NullPointerException.class, () -> new Assignment(validName, null, validTags));
        // null tags
        assertThrows(NullPointerException.class, () -> new Assignment(validName, validDate, null));
    }

}

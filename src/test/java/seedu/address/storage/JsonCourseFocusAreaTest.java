package seedu.address.storage;

import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author chanckben
public class JsonCourseFocusAreaTest {
    private static final List<String> VALID_PRIMARIES = new ArrayList<>();
    private static final List<String> VALID_ELECTIVES = new ArrayList<>();

    @Test
    public void toModelType_nullFocusAreaName_throwsIllegalValueException() {
        JsonCourseFocusArea focusArea = new JsonCourseFocusArea(null, VALID_PRIMARIES, VALID_ELECTIVES);
        String expectedMessage = String.format(JsonCourse.MISSING_FIELD_MESSAGE_FORMAT, "Focus Area");
        assertThrows(IllegalValueException.class, expectedMessage, focusArea::toModelType);
    }
}

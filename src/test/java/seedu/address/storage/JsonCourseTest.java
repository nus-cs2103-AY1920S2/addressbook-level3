package seedu.address.storage;

import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author chanckben
public class JsonCourseTest {
    public static final String VALID_COURSE_NAME = "Computer Science";
    public static final List<JsonCourseRequirement> VALID_REQUIREMENTS = new ArrayList<>();
    public static final List<JsonCourseFocusArea> VALID_FOCUS_AREAS = new ArrayList<>();

    @Test
    public void toModelType_nullCourseName_throwsIllegalValueException() {
        JsonCourse course = new JsonCourse(null, VALID_REQUIREMENTS, VALID_FOCUS_AREAS);
        String expectedMessage = String.format(JsonCourse.MISSING_FIELD_MESSAGE_FORMAT, "Course");
        assertThrows(IllegalValueException.class, expectedMessage, course::toModelType);
    }

    @Test
    public void toModelType_nullRequirements_throwsIllegalValueException() {
        JsonCourse course = new JsonCourse(VALID_COURSE_NAME, null, VALID_FOCUS_AREAS);
        String expectedMessage = String.format(JsonCourse.MISSING_FIELD_MESSAGE_FORMAT, "Requirement");
        assertThrows(IllegalValueException.class, expectedMessage, course::toModelType);
    }
}

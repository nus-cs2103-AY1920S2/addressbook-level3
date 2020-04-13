package seedu.address.storage;

import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author chanckben
public class JsonCourseRequirementTest {
    private static final String VALID_REQUIREMENT_NAME = "requirement";
    private static final List<String> VALID_MODULES = new ArrayList<>();
    private static final String VALID_MODULAR_CREDITS = "4";
    private static final List<String> VALID_REQUIREMENT_INFO = new ArrayList<>();

    @Test
    public void toModelType_nullRequirementName_throwsIllegalValueException() {
        JsonCourseRequirement courseRequirement = new JsonCourseRequirement(null, VALID_MODULES,
                VALID_MODULAR_CREDITS, VALID_REQUIREMENT_INFO);
        String expectedMessage = String.format(JsonCourseRequirement.MISSING_FIELD_MESSAGE_FORMAT, "Requirement Name");
        assertThrows(IllegalValueException.class, expectedMessage, courseRequirement::toModelType);
    }

    @Test
    public void toModelType_nullModules_throwsIllegalValueException() {
        JsonCourseRequirement courseRequirement = new JsonCourseRequirement(VALID_REQUIREMENT_NAME, null,
                VALID_MODULAR_CREDITS, VALID_REQUIREMENT_INFO);
        String expectedMessage = String.format(JsonCourseRequirement.MISSING_FIELD_MESSAGE_FORMAT, "Module");
        assertThrows(IllegalValueException.class, expectedMessage, courseRequirement::toModelType);
    }

    @Test
    public void toModelType_nullModularCredits_throwsIllegalValueException() {
        JsonCourseRequirement courseRequirement = new JsonCourseRequirement(VALID_REQUIREMENT_NAME, VALID_MODULES,
                null, VALID_REQUIREMENT_INFO);
        String expectedMessage = String.format(JsonCourseRequirement.MISSING_FIELD_MESSAGE_FORMAT, "Modular Credits");
        assertThrows(IllegalValueException.class, expectedMessage, courseRequirement::toModelType);
    }
}

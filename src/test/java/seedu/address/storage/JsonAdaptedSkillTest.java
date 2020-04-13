package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import static seedu.address.storage.JsonAdaptedSkill.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalSkill.REACT;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.field.Level;
import seedu.address.model.item.field.Name;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.TypicalSkill;

public class JsonAdaptedSkillTest {

    private static final String VALID_NAME = REACT.getName().toString();
    private static final String VALID_LEVEL = REACT.getLevel().toString();
    private static final String VALID_ID = String.valueOf(REACT.getId());

    private static final List<JsonAdaptedTag> EMPTY_TAGS = new ArrayList<>();
    private static final List<JsonAdaptedTag> VALID_TAGS = REACT.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    private static final String INVALID_NAME = "Re@ct";
    private static final String INVALID_LEVEL = "Professional";
    private static final String INVALID_ID = "2a";
    private static final String INVALID_TAG = "?cool";

    @Test
    public void toModelType_validSkill_returnsSkill() throws IllegalValueException {
        JsonAdaptedSkill skill = new JsonAdaptedSkill(VALID_NAME, VALID_ID, VALID_LEVEL, VALID_TAGS);
        assertEquals(REACT, skill.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() throws IllegalValueException {
        JsonAdaptedSkill skill = new JsonAdaptedSkill(INVALID_NAME, VALID_ID, VALID_LEVEL, VALID_TAGS);
        assertThrows(IllegalValueException.class, Name.MESSAGE_CONSTRAINTS, skill::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() throws IllegalValueException {
        JsonAdaptedSkill skill = new JsonAdaptedSkill(null, VALID_ID, VALID_LEVEL, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, skill::toModelType);
    }

    @Test
    public void toModelType_invalidNotIntegerId_throwsIllegalValueException() throws IllegalValueException {
        JsonAdaptedSkill skill = new JsonAdaptedSkill(VALID_NAME, "a", VALID_LEVEL, VALID_TAGS);
        assertThrows(IllegalValueException.class, "The id field can only be an integer.",
                skill::toModelType);
    }

    @Test
    public void toModelType_invalidNegativeId_throwsIllegalValueException() throws IllegalValueException {
        JsonAdaptedSkill skill = new JsonAdaptedSkill(VALID_NAME, "-2", VALID_LEVEL, VALID_TAGS);
        assertThrows(IllegalValueException.class, "The id field must not be negative.",
                skill::toModelType);
    }

    @Test
    public void toModelType_invalidLevel_throwsIllegalValueException() throws IllegalValueException {
        JsonAdaptedSkill skill = new JsonAdaptedSkill(VALID_NAME, VALID_ID, INVALID_LEVEL, VALID_TAGS);
        assertThrows(IllegalValueException.class, Level.MESSAGE_CONSTRAINTS, skill::toModelType);
    }

    @Test
    public void toModelType_nullLevel_throwsIllegalValueException() throws IllegalValueException {
        JsonAdaptedSkill skill = new JsonAdaptedSkill(VALID_NAME, VALID_ID, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Level.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, skill::toModelType);
    }

    @Test
    public void toModelType_invalidTag_throwsIllegalValueException() throws IllegalValueException {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedSkill skill = new JsonAdaptedSkill(VALID_NAME, VALID_ID, VALID_LEVEL, invalidTags);
        assertThrows(IllegalValueException.class, Tag.MESSAGE_CONSTRAINTS, skill::toModelType);
    }

    @Test
    public void equals() {
        JsonAdaptedSkill skillWithNoTags = new JsonAdaptedSkill(VALID_NAME, VALID_ID, VALID_LEVEL, EMPTY_TAGS);
        JsonAdaptedSkill skillWithTags = new JsonAdaptedSkill(VALID_NAME, VALID_ID, VALID_LEVEL, VALID_TAGS);
        JsonAdaptedSkill skillWithTagsButDifferentName =
                new JsonAdaptedSkill("Angular", VALID_ID, VALID_LEVEL, VALID_TAGS);

        assertEquals(skillWithTags, new JsonAdaptedSkill(TypicalSkill.REACT));

        assertNotEquals(skillWithNoTags, new JsonAdaptedSkill(TypicalSkill.REACT));
        assertNotEquals(skillWithTagsButDifferentName, new JsonAdaptedSkill(TypicalSkill.REACT));
    }
}

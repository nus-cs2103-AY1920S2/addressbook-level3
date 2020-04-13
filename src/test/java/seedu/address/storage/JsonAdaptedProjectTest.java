package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIME;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_WEBSITE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_DUKE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_NAME_DUKE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_JAVA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TECH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEBSITE_DUKE;
import static seedu.address.storage.JsonAdaptedProject.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.field.Description;
import seedu.address.model.item.field.Name;
import seedu.address.model.item.field.Time;
import seedu.address.model.item.field.Website;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ProjectBuilder;
import seedu.address.testutil.TypicalProject;

public class JsonAdaptedProjectTest {
    @Test
    public void toModelType_validProject_returnsProject() throws IllegalValueException {
        // Without tags
        JsonAdaptedProject jsonAdaptedProject = new JsonAdaptedProject(new ProjectBuilder(TypicalProject.DUKE).build());
        assertEquals(new ProjectBuilder(TypicalProject.DUKE).build(), jsonAdaptedProject.toModelType());

        jsonAdaptedProject = new JsonAdaptedProject(VALID_PROJECT_NAME_DUKE, "1", VALID_TIME_2, VALID_WEBSITE_DUKE,
                        VALID_DESCRIPTION_DUKE, new ArrayList<>());
        assertEquals(new ProjectBuilder(TypicalProject.DUKE).withTags().build(), jsonAdaptedProject.toModelType());

        // With tags
        jsonAdaptedProject = new JsonAdaptedProject(
                new ProjectBuilder(TypicalProject.DUKE).withTags(VALID_TAG_TECH, VALID_TAG_JAVA).build());
        assertEquals(
                new ProjectBuilder(TypicalProject.DUKE).build(),
                jsonAdaptedProject.toModelType());

        List<JsonAdaptedTag> tagList = new ArrayList<>();
        tagList.add(new JsonAdaptedTag(VALID_TAG_JAVA));
        tagList.add(new JsonAdaptedTag(VALID_TAG_TECH));
        jsonAdaptedProject = new JsonAdaptedProject(VALID_PROJECT_NAME_DUKE, "1", VALID_TIME_2,
                VALID_WEBSITE_DUKE, VALID_DESCRIPTION_DUKE, tagList);
        assertEquals(new ProjectBuilder(TypicalProject.DUKE).build(), jsonAdaptedProject.toModelType());
    }

    @Test
    public void toModelType_missingId_throwsIllegalValueException() {
        JsonAdaptedProject jsonAdaptedProject = new JsonAdaptedProject(VALID_PROJECT_NAME_DUKE, null,
                VALID_TIME_2, VALID_WEBSITE_DUKE, VALID_DESCRIPTION_DUKE, new ArrayList<>());
        assertThrows(IllegalValueException.class,
                String.format(MISSING_FIELD_MESSAGE_FORMAT, "Id"),
                jsonAdaptedProject::toModelType);
    }

    @Test
    public void toModelType_invalidId_throwsIllegalValueException() {
        // Id must be an integer
        JsonAdaptedProject jsonAdaptedProject = new JsonAdaptedProject(VALID_PROJECT_NAME_DUKE, "a", VALID_TIME_2,
                VALID_WEBSITE_DUKE, VALID_DESCRIPTION_DUKE, new ArrayList<>());
        assertThrows(IllegalValueException.class,
                "The id field can only be an integer.",
                jsonAdaptedProject::toModelType);

        // Id cannot be negative
        jsonAdaptedProject = new JsonAdaptedProject(VALID_PROJECT_NAME_DUKE, "-1", VALID_TIME_2, VALID_WEBSITE_DUKE,
                        VALID_DESCRIPTION_DUKE, new ArrayList<>());
        assertThrows(IllegalValueException.class,
                "The id field must not be negative.",
                jsonAdaptedProject::toModelType);
    }

    @Test
    public void toModelType_missingName_throwsIllegalValueException() {
        JsonAdaptedProject jsonAdaptedProject = new JsonAdaptedProject(null, "1", VALID_TIME_2,
                VALID_WEBSITE_DUKE, VALID_DESCRIPTION_DUKE, new ArrayList<>());
        assertThrows(IllegalValueException.class,
                String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()),
                jsonAdaptedProject::toModelType);
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedProject jsonAdaptedProject = new JsonAdaptedProject(INVALID_NAME, "1", VALID_TIME_2,
                VALID_WEBSITE_DUKE, VALID_DESCRIPTION_DUKE, new ArrayList<>());
        assertThrows(IllegalValueException.class,
                Name.MESSAGE_CONSTRAINTS,
                jsonAdaptedProject::toModelType);
    }

    @Test
    public void toModelType_missingTime_throwsIllegalValueException() {
        JsonAdaptedProject jsonAdaptedProject = new JsonAdaptedProject(VALID_PROJECT_NAME_DUKE, "1", null,
                VALID_WEBSITE_DUKE, VALID_DESCRIPTION_DUKE, new ArrayList<>());
        assertThrows(IllegalValueException.class,
                String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName()),
                jsonAdaptedProject::toModelType);
    }

    @Test
    public void toModelType_invalidTime_throwsIllegalValueException() {
        JsonAdaptedProject jsonAdaptedProject = new JsonAdaptedProject(VALID_PROJECT_NAME_DUKE, "1", INVALID_TIME,
                VALID_WEBSITE_DUKE, VALID_DESCRIPTION_DUKE, new ArrayList<>());
        assertThrows(IllegalValueException.class,
                Time.MESSAGE_CONSTRAINTS,
                jsonAdaptedProject::toModelType);
    }

    @Test
    public void toModelType_missingWebsite_throwsIllegalValueException() {
        JsonAdaptedProject jsonAdaptedProject = new JsonAdaptedProject(VALID_PROJECT_NAME_DUKE, "1", VALID_TIME_2,
                null, VALID_DESCRIPTION_DUKE, new ArrayList<>());
        assertThrows(IllegalValueException.class,
                String.format(MISSING_FIELD_MESSAGE_FORMAT, Website.class.getSimpleName()),
                jsonAdaptedProject::toModelType);
    }

    @Test
    public void toModelType_invalidWebsite_throwsIllegalValueException() {
        JsonAdaptedProject jsonAdaptedProject = new JsonAdaptedProject(VALID_PROJECT_NAME_DUKE, "1", VALID_TIME_2,
                INVALID_WEBSITE, VALID_DESCRIPTION_DUKE, new ArrayList<>());
        assertThrows(IllegalValueException.class,
                Website.MESSAGE_CONSTRAINTS,
                jsonAdaptedProject::toModelType);
    }

    @Test
    public void toModelType_missingDescription_throwsIllegalValueException() {
        JsonAdaptedProject jsonAdaptedProject = new JsonAdaptedProject(VALID_PROJECT_NAME_DUKE, "1", VALID_TIME_2,
                VALID_WEBSITE_DUKE, null, new ArrayList<>());
        assertThrows(IllegalValueException.class,
                String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName()),
                jsonAdaptedProject::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedProject jsonAdaptedProject = new JsonAdaptedProject(VALID_PROJECT_NAME_DUKE, "1", VALID_TIME_2,
                VALID_WEBSITE_DUKE, "", new ArrayList<>());
        assertThrows(IllegalValueException.class,
                Description.MESSAGE_CONSTRAINTS,
                jsonAdaptedProject::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> tags = new ArrayList<>();
        tags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedProject jsonAdaptedProject = new JsonAdaptedProject(VALID_PROJECT_NAME_DUKE, "1", VALID_TIME_2,
                VALID_WEBSITE_DUKE, VALID_DESCRIPTION_DUKE, tags);
        assertThrows(IllegalValueException.class,
                Tag.MESSAGE_CONSTRAINTS,
                jsonAdaptedProject::toModelType);
    }

    @Test
    public void equals() {
        JsonAdaptedProject jsonAdaptedDuke = new JsonAdaptedProject(new ProjectBuilder(TypicalProject.DUKE).build());
        JsonAdaptedProject jsonAdaptedOrbital = new JsonAdaptedProject(
                new ProjectBuilder(TypicalProject.ORBITAL).build());

        List<JsonAdaptedTag> tags = new ArrayList<>();
        tags.add(new JsonAdaptedTag(VALID_TAG_TECH));
        tags.add(new JsonAdaptedTag(VALID_TAG_JAVA));

        // Two constructors gives the same result
        assertEquals(jsonAdaptedDuke,
                new JsonAdaptedProject(VALID_PROJECT_NAME_DUKE, "1", VALID_TIME_2, VALID_WEBSITE_DUKE,
                        VALID_DESCRIPTION_DUKE, tags));

        // Different json adapted projects are not equal
        assertNotEquals(jsonAdaptedDuke, jsonAdaptedOrbital);
    }
}

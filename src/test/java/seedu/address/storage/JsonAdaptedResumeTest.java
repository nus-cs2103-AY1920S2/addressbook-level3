package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RESUME_NAME_ME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_JAVA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TECH;
import static seedu.address.storage.JsonAdaptedResume.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalInternship.NINJA_VAN;
import static seedu.address.testutil.TypicalProject.ORBITAL;
import static seedu.address.testutil.TypicalSkill.REACT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.Resume;
import seedu.address.model.item.field.Name;
import seedu.address.testutil.ResumeBuilder;
import seedu.address.testutil.TypicalResume;

public class JsonAdaptedResumeTest {
    private Resume resumeWithoutTags = new ResumeBuilder(TypicalResume.ME_RESUME)
            .withInternship(NINJA_VAN).withProject(ORBITAL).withSkill(REACT).build();
    private Resume resumeSeWithoutTags = new ResumeBuilder(TypicalResume.SE_RESUME)
            .withInternship(NINJA_VAN).withProject(ORBITAL).withSkill(REACT).build();
    private Resume resumeWithTags = new ResumeBuilder(TypicalResume.ME_RESUME)
            .withInternship(NINJA_VAN).withProject(ORBITAL).withSkill(REACT)
            .withTags(VALID_TAG_TECH, VALID_TAG_JAVA).build();
    private List<JsonAdaptedTag> emptyTags = new ArrayList<>();
    private List<JsonAdaptedTag> tags = Arrays.asList(VALID_TAG_JAVA, VALID_TAG_TECH)
            .stream().map(x -> new JsonAdaptedTag(x)).collect(Collectors.toList());

    private List<String> internshipIds = Arrays.asList(String.valueOf(NINJA_VAN.getId()));
    private List<String> projectIds = Arrays.asList(String.valueOf(ORBITAL.getId()));
    private List<String> skillIds = Arrays.asList(String.valueOf(REACT.getId()));

    @Test
    public void toModelType_validResume_returnsResume() throws IllegalValueException {
        // Without tags
        JsonAdaptedResume jsonAdaptedResume = new JsonAdaptedResume(resumeWithoutTags);
        assertEquals(resumeWithoutTags, jsonAdaptedResume.toModelType());

        jsonAdaptedResume = new JsonAdaptedResume(VALID_RESUME_NAME_ME, "4",
                internshipIds, projectIds, skillIds, emptyTags);
        assertEquals(resumeWithoutTags, jsonAdaptedResume.toModelType());

        // With tags
        jsonAdaptedResume = new JsonAdaptedResume(resumeWithTags);
        assertEquals(resumeWithTags, jsonAdaptedResume.toModelType());

        jsonAdaptedResume = new JsonAdaptedResume(VALID_RESUME_NAME_ME, "4",
                internshipIds, projectIds, skillIds, tags);
        assertEquals(resumeWithTags, jsonAdaptedResume.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedResume jsonAdaptedResume = new JsonAdaptedResume(INVALID_NAME_DESC, "4",
                internshipIds, projectIds, skillIds, tags);
        assertThrows(IllegalValueException.class, Name.MESSAGE_CONSTRAINTS, jsonAdaptedResume::toModelType);
    }

    @Test
    public void toModelType_missingName_throwsIllegalValueException() {
        JsonAdaptedResume jsonAdaptedResume = new JsonAdaptedResume(null, "4",
                internshipIds, projectIds, skillIds, tags);
        assertThrows(IllegalValueException.class,
                String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()),
                jsonAdaptedResume::toModelType);
    }

    @Test
    public void toModelType_invalidId_throwsIllegalValueException() {
        JsonAdaptedResume jsonAdaptedResume = new JsonAdaptedResume(VALID_RESUME_NAME_ME, "abc",
                internshipIds, projectIds, skillIds, tags);
        assertThrows(IllegalValueException.class, "The id field can only be an integer.",
                jsonAdaptedResume::toModelType);

        jsonAdaptedResume = new JsonAdaptedResume(VALID_RESUME_NAME_ME, "-4",
                internshipIds, projectIds, skillIds, tags);
        assertThrows(IllegalValueException.class, "The id field must not be negative.",
                jsonAdaptedResume::toModelType);
    }

    @Test
    public void toModelType_missingId_throwsIllegalValueException() {
        JsonAdaptedResume jsonAdaptedResume = new JsonAdaptedResume(VALID_RESUME_NAME_ME, null,
                internshipIds, projectIds, skillIds, tags);
        assertThrows(IllegalValueException.class,
                String.format(MISSING_FIELD_MESSAGE_FORMAT, "Id"), jsonAdaptedResume::toModelType);
    }

    @Test
    public void toModelType_invalidItemIds_throwsIllegalValueException() {
        List<String> invalidInternshipIds = Arrays.asList("abc");
        List<String> invalidProjectIds = Arrays.asList("-4");

        JsonAdaptedResume jsonAdaptedResume = new JsonAdaptedResume(VALID_RESUME_NAME_ME, "4",
                invalidInternshipIds, projectIds, skillIds, tags);
        assertThrows(IllegalValueException.class, "The id field can only be an integer.",
                jsonAdaptedResume::toModelType);

        jsonAdaptedResume = new JsonAdaptedResume(VALID_RESUME_NAME_ME, "4",
                internshipIds, invalidProjectIds, skillIds, tags);
        assertThrows(IllegalValueException.class, "The id field must not be negative.",
                jsonAdaptedResume::toModelType);
    }

    @Test
    public void equals() {

        JsonAdaptedResume jsonAdaptedResumeMeWithNoTag =
                new JsonAdaptedResume(VALID_RESUME_NAME_ME, "0", internshipIds, projectIds, skillIds, emptyTags);

        JsonAdaptedResume jsonAdaptedResumeMeWithTag =
                new JsonAdaptedResume(VALID_RESUME_NAME_ME, "0", internshipIds, projectIds, skillIds, tags);

        assertEquals(jsonAdaptedResumeMeWithNoTag, new JsonAdaptedResume(resumeWithoutTags));

        // Different names
        assertNotEquals(new JsonAdaptedResume(resumeSeWithoutTags), new JsonAdaptedResume(resumeWithoutTags));

        assertEquals(jsonAdaptedResumeMeWithTag, new JsonAdaptedResume(resumeWithTags));
    }
}

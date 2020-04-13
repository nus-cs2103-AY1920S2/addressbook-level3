package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.storage.JsonSerializableResumeBook.MESSAGE_DUPLICATE_INTERNSHIP;
import static seedu.address.storage.JsonSerializableResumeBook.MESSAGE_DUPLICATE_NOTE;
import static seedu.address.storage.JsonSerializableResumeBook.MESSAGE_DUPLICATE_PROJECT;
import static seedu.address.storage.JsonSerializableResumeBook.MESSAGE_DUPLICATE_RESUME;
import static seedu.address.storage.JsonSerializableResumeBook.MESSAGE_DUPLICATE_SKILL;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ResumeBook;
import seedu.address.model.item.field.Level;
import seedu.address.model.item.field.Name;
import seedu.address.model.item.field.Website;
import seedu.address.testutil.TypicalResumeBook;

public class JsonSerializableResumeBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get(
            "src", "test", "data", "JsonSerializableResumeBookTest");
    private static final Path TYPICAL_RESUME_BOOK_FILE = TEST_DATA_FOLDER.resolve("typicalResumeBook.json");
    private static final Path OTHER_TYPICAL_RESUME_BOOK_FILE = TEST_DATA_FOLDER.resolve("otherTypicalResumeBook.json");

    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonResumeBook.json");

    private static final Path INVALID_RESUME_FILE = TEST_DATA_FOLDER.resolve("invalidResumeResumeBook.json");
    private static final Path DUPLICATE_RESUME_FILE = TEST_DATA_FOLDER.resolve("duplicateResumeResumeBook.json");

    private static final Path INVALID_INTERNSHIP_FILE = TEST_DATA_FOLDER.resolve("invalidInternshipResumeBook.json");
    private static final Path DUPLICATE_INTERNSHIP_FILE =
            TEST_DATA_FOLDER.resolve("duplicateInternshipResumeBook.json");

    private static final Path INVALID_NOTE_FILE = TEST_DATA_FOLDER.resolve("invalidNoteResumeBook.json");
    private static final Path DUPLICATE_NOTE_FILE = TEST_DATA_FOLDER.resolve("duplicateNoteResumeBook.json");

    private static final Path INVALID_PROJECT_FILE = TEST_DATA_FOLDER.resolve("invalidProjectResumeBook.json");
    private static final Path DUPLICATE_PROJECT_FILE = TEST_DATA_FOLDER.resolve("duplicateProjectResumeBook.json");

    private static final Path INVALID_SKILL_FILE = TEST_DATA_FOLDER.resolve("invalidSkillResumeBook.json");
    private static final Path DUPLICATE_SKILL_FILE = TEST_DATA_FOLDER.resolve("duplicateSkillResumeBook.json");

    @Test
    public void toModelType_typicalFile_success() throws Exception {
        JsonSerializableResumeBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_RESUME_BOOK_FILE,
                JsonSerializableResumeBook.class).get();
        ResumeBook resumeBookFromFile = dataFromFile.toModelType();
        ResumeBook typicalResumeBook = TypicalResumeBook.TYPICAL_WITHOUT_GOGGLE;
        assertEquals(resumeBookFromFile, typicalResumeBook);
    }

    @Test
    public void toModelType_invalidResumeFile_throwsIllegalValueException() throws Exception {
        // The file contains an invalid resume name
        JsonSerializableResumeBook dataFromFile = JsonUtil.readJsonFile(INVALID_RESUME_FILE,
                JsonSerializableResumeBook.class).get();
        assertThrows(IllegalValueException.class, Name.MESSAGE_CONSTRAINTS, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateResumeFile_throwsIllegalValueException() throws Exception {
        // The file contains duplicates of resume which have a same name
        JsonSerializableResumeBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_RESUME_FILE,
                JsonSerializableResumeBook.class).get();
        assertThrows(IllegalValueException.class, MESSAGE_DUPLICATE_RESUME, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_invalidSkillFile_throwsIllegalValueException() throws Exception {
        // The file contains an invalid skill with invalid level
        JsonSerializableResumeBook dataFromFile = JsonUtil.readJsonFile(INVALID_SKILL_FILE,
                JsonSerializableResumeBook.class).get();
        assertThrows(IllegalValueException.class, Level.MESSAGE_CONSTRAINTS, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateSkillFile_throwsIllegalValueException() throws Exception {
        // The file contains duplicates of skill which have a same name
        JsonSerializableResumeBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_SKILL_FILE,
                JsonSerializableResumeBook.class).get();
        assertThrows(IllegalValueException.class, MESSAGE_DUPLICATE_SKILL, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_invalidInternshipFile_throwsIllegalValueException() throws Exception {
        // The file contains an invalid internship wherein "from" is after "to"
        JsonSerializableResumeBook dataFromFile = JsonUtil.readJsonFile(INVALID_INTERNSHIP_FILE,
                JsonSerializableResumeBook.class).get();
        assertThrows(
                IllegalValueException.class,
                "The \"to\" field must not precede the \"from\" field.", dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateInternshipFile_throwsIllegalValueException() throws Exception {
        // The file contains duplicates of internship which have a same name, role, and from-to period
        JsonSerializableResumeBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_INTERNSHIP_FILE,
                JsonSerializableResumeBook.class).get();
        assertThrows(IllegalValueException.class, MESSAGE_DUPLICATE_INTERNSHIP, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_invalidNoteFile_throwsIllegalValueException() throws Exception {
        // The file contains an invalid note with "null" for isDone
        JsonSerializableResumeBook dataFromFile = JsonUtil.readJsonFile(INVALID_NOTE_FILE,
                JsonSerializableResumeBook.class).get();
        assertThrows(
                IllegalValueException.class, "A boolean field can only be true or false.", dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateNoteFile_throwsIllegalValueException() throws Exception {
        // The file contains duplicates of note which have a same name, isDone status, and time.
        JsonSerializableResumeBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_NOTE_FILE,
                JsonSerializableResumeBook.class).get();
        assertThrows(IllegalValueException.class, MESSAGE_DUPLICATE_NOTE, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_invalidProjectFile_throwsIllegalValueException() throws Exception {
        // The file contains a project with an invalid website name
        JsonSerializableResumeBook dataFromFile = JsonUtil.readJsonFile(INVALID_PROJECT_FILE,
                JsonSerializableResumeBook.class).get();
        assertThrows(IllegalValueException.class, Website.MESSAGE_CONSTRAINTS, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateProjectFile_throwsIllegalValueException() throws Exception {
        // The file contains duplicates of project which have a same name, website, and time
        JsonSerializableResumeBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PROJECT_FILE,
                JsonSerializableResumeBook.class).get();
        assertThrows(IllegalValueException.class, MESSAGE_DUPLICATE_PROJECT, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        // The file contains an invalid person whose current cap > max cap
        JsonSerializableResumeBook dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableResumeBook.class).get();
        assertThrows(
                IllegalValueException.class,
                "The current cap value must not be greater than the maximum cap value.", dataFromFile::toModelType);
    }

    @Test
    public void equals() throws Exception {
        // This should be congruent with the content of typicalResumeBook.json
        ResumeBook typicalResumeBook = TypicalResumeBook.TYPICAL_WITHOUT_GOGGLE;

        // Equals
        JsonSerializableResumeBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_RESUME_BOOK_FILE,
                JsonSerializableResumeBook.class).get();
        assertEquals(dataFromFile, new JsonSerializableResumeBook(typicalResumeBook));


        // Not equals
        JsonSerializableResumeBook alternateDataFromFile = JsonUtil.readJsonFile(OTHER_TYPICAL_RESUME_BOOK_FILE,
                JsonSerializableResumeBook.class).get();
        assertNotEquals(alternateDataFromFile, new JsonSerializableResumeBook(typicalResumeBook));
    }
}

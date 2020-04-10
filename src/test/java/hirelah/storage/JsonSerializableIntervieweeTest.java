package hirelah.storage;

import hirelah.commons.exceptions.IllegalValueException;
import hirelah.commons.util.JsonUtil;
import hirelah.model.hirelah.IntervieweeList;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static hirelah.commons.util.JsonUtil.readJsonFile;
import static hirelah.testutil.Assert.assertThrows;
import static hirelah.testutil.TypicalAttributes.getTypicalAttributes;
import static hirelah.testutil.TypicalInterviewee.IntervieweeBeforeInterview;

import static hirelah.testutil.TypicalQuestionList.getTypicalQns;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonSerializableIntervieweeTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableIntervieweeTest");
    TranscriptStorage transcriptStorage = new TranscriptStorage(Paths.get("src", "test", "data", "JsonSerializableIntervieweeTest", "transcript"));
    private static final Path TYPICAL_INTERVIEWEE_FILE = TEST_DATA_FOLDER.resolve("ValidInterviewee.json");
    private static final Path INVALID_INTERVIEWEE_FILE = TEST_DATA_FOLDER.resolve("InvalidInterviewee.json");
    private static final Path DUPLICATE_INTERVIEWEE_FILE = TEST_DATA_FOLDER.resolve("DuplicateInterviewee.json");

    @Test
    public void toModelType_typicalIntervieweeFile_success() throws Exception {
        JsonSerializableInterviewee dataFromFile = readJsonFile(TYPICAL_INTERVIEWEE_FILE,
                JsonSerializableInterviewee.class).get();
        IntervieweeList intervieweeListFromFile = dataFromFile.toModelType(getTypicalQns(), getTypicalAttributes(), transcriptStorage, false);
        IntervieweeList typicalInterviewee = IntervieweeBeforeInterview();
        assertEquals(typicalInterviewee, intervieweeListFromFile);
    }

    @Test
    public void toModelType_invalidIntervieweeFile_throwsIllegalValueException() throws Exception {
        JsonSerializableInterviewee dataFromFile = JsonUtil.readJsonFile(INVALID_INTERVIEWEE_FILE,
                JsonSerializableInterviewee.class).get();
        assertThrows(IllegalValueException.class, () -> {
            dataFromFile.toModelType(getTypicalQns(), getTypicalAttributes(), transcriptStorage, false);
        });
    }

    @Test
    public void toModelType_duplicateIntervieweeFile_throwsIllegalValueException() throws Exception {
        JsonSerializableInterviewee dataFromFile = JsonUtil.readJsonFile(DUPLICATE_INTERVIEWEE_FILE,
                JsonSerializableInterviewee.class).get();
        assertThrows(IllegalValueException.class, () -> {
            dataFromFile.toModelType(getTypicalQns(), getTypicalAttributes(), transcriptStorage, false);
        });
    }
}

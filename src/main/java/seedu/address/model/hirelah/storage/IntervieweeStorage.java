package seedu.address.model.hirelah.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.hirelah.AttributeList;
import seedu.address.model.hirelah.IntervieweeList;
import seedu.address.model.hirelah.QuestionList;


/**
 * IntervieweeStorage containing the file path
 * to store the Interviewee objects.
 */
public class IntervieweeStorage {
    private static final Logger logger = LogsCenter.getLogger(IntervieweeStorage.class);
    private final Path path;

    public IntervieweeStorage(Path newPath) {
        this.path = newPath;
    }

    public Path getPath() {
        return this.path;
    }

    /**
     * reads the data from the current Path to
     * retrieve all the information regarding Interviewee.
     * @return OptionalIntervieweeList
     * @throws DataConversionException error when reading the file
     */
    public Optional<IntervieweeList> readInterviewee(Path filePath, QuestionList questionList,
                                                     AttributeList attributeList, Boolean finalised,
                                                     TranscriptStorage storage) throws DataConversionException {
        requireNonNull(filePath);
        Optional<JsonSerializableInterviewee> jsonInterviewee = JsonUtil.readJsonFile(
                filePath, JsonSerializableInterviewee.class);
        if (jsonInterviewee.isEmpty()) {
            return Optional.empty();
        }
        try {
            return Optional.of(jsonInterviewee.get().toModelType(questionList, attributeList, storage, finalised));
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    /**
     * Save the information of the Interviewee
     * @param  source of the data. Cannot be null.
     */
    public void saveInterview(IntervieweeList source) throws IOException {
        requireNonNull(source);
        requireNonNull(path);
        FileUtil.createIfMissing(path);
        JsonUtil.saveJsonFile(new JsonSerializableInterviewee(source), path);
    }
}

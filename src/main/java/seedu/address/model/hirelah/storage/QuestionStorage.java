package seedu.address.model.hirelah.storage;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.hirelah.QuestionList;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

public class QuestionStorage {
    private final Path path;
    private static final Logger logger = LogsCenter.getLogger(QuestionStorage.class);

    public QuestionStorage(Path newPath) {
        this.path = newPath;
    }

    public Path getPath() {
        return this.path;
    }

    /**
     * reads the data from the current Path to
     * retrieve all the information regarding Question.
     * @return Optional<QuestionList>
     * @throws DataConversionException
     */
    public Optional<QuestionList> readQuestion(Path filePath) throws DataConversionException {
        requireNonNull(filePath);
        Optional<JsonSerializableQuestion> jsonQuestion = JsonUtil.readJsonFile(
                filePath, JsonSerializableQuestion.class);
        if (jsonQuestion.isEmpty()) {
            return Optional.empty();
        }
        try {
            return Optional.of(jsonQuestion.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    /**
     * Save the information of the Question
     * @param  source of the data. Cannot be null.
     */
    public void saveQuestions(QuestionList source) throws IOException, IllegalValueException {
        requireNonNull(source);
        requireNonNull(path);
        FileUtil.createIfMissing(path);
        JsonUtil.saveJsonFile(new JsonSerializableQuestion(source), path);
    }
}

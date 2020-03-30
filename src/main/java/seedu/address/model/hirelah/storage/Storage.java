package seedu.address.model.hirelah.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.hirelah.AttributeList;
import seedu.address.model.hirelah.IntervieweeList;
import seedu.address.model.hirelah.MetricList;
import seedu.address.model.hirelah.QuestionList;
import seedu.address.storage.UserPrefsStorage;

/**
 * API of the Storage component
 * I am still allowing the structure of the previous addressbook to
 * prevent any bugs.
 */
public interface Storage extends UserPrefsStorage {
    void saveInterviewee(IntervieweeList source) throws IOException, IllegalValueException;
    Optional<IntervieweeList> readInterviewee() throws DataConversionException;

    void saveAttribute(AttributeList source) throws IOException, IllegalValueException;
    Optional<AttributeList> readAttribute() throws DataConversionException;

    void saveQuestion(QuestionList source) throws IOException, IllegalValueException;
    Optional<QuestionList> readQuestion() throws DataConversionException;

    void saveMetric(MetricList source) throws IOException, IllegalValueException;
    Optional<MetricList> readMetric() throws DataConversionException;
}

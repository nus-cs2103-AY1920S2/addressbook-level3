package hirelah.storage;

import java.io.IOException;
import java.util.Optional;

import hirelah.commons.exceptions.DataConversionException;
import hirelah.commons.exceptions.IllegalValueException;
import hirelah.model.hirelah.AttributeList;
import hirelah.model.hirelah.Interviewee;
import hirelah.model.hirelah.IntervieweeList;
import hirelah.model.hirelah.MetricList;
import hirelah.model.hirelah.QuestionList;

/**
 * API of the Storage component.
 */
public interface Storage extends UserPrefsStorage {
    void saveInterviewee(IntervieweeList source) throws IOException, IllegalValueException;

    Optional<IntervieweeList> readInterviewee(QuestionList questionList,
                                              AttributeList attributeList,
                                              Boolean initialModel) throws DataConversionException;

    void saveAttribute(AttributeList source) throws IOException, IllegalValueException;

    Optional<AttributeList> readAttribute() throws DataConversionException;

    void saveQuestion(QuestionList source) throws IOException, IllegalValueException;

    Optional<QuestionList> readQuestion() throws DataConversionException;

    void saveMetric(MetricList source) throws IOException, IllegalValueException;

    Optional<MetricList> readMetric() throws DataConversionException;

    void saveTranscript(Interviewee source) throws IOException;

    void saveModel(Boolean model) throws IOException;

    Optional<Boolean> readModel() throws DataConversionException;
}

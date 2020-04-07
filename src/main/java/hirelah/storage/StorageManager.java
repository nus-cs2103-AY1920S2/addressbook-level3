package hirelah.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import hirelah.commons.core.LogsCenter;
import hirelah.commons.exceptions.DataConversionException;
import hirelah.model.ReadOnlyUserPrefs;
import hirelah.model.UserPrefs;
import hirelah.model.hirelah.AttributeList;
import hirelah.model.hirelah.Interviewee;
import hirelah.model.hirelah.IntervieweeList;
import hirelah.model.hirelah.MetricList;
import hirelah.model.hirelah.QuestionList;

/**
 * Manages storage of different Sessions data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private UserPrefsStorage userPrefsStorage;
    private IntervieweeStorage intervieweeStorage;
    private AttributeStorage attributeStorage;
    private QuestionStorage questionStorage;
    private MetricStorage metricStorage;
    private TranscriptStorage transcriptStorage;
    private ModelStorage modelStorage;

    public StorageManager(UserPrefsStorage userPrefsStorage, IntervieweeStorage intervieweeStorage,
                          AttributeStorage attributeStorage, QuestionStorage questionStorage,
                          MetricStorage metricStorage, TranscriptStorage transcriptStorage,
                          ModelStorage modelStorage) {
        super();
        this.userPrefsStorage = userPrefsStorage;
        this.intervieweeStorage = intervieweeStorage;
        this.attributeStorage = attributeStorage;
        this.questionStorage = questionStorage;
        this.metricStorage = metricStorage;
        this.transcriptStorage = transcriptStorage;
        this.modelStorage = modelStorage;
    }

    public StorageManager(UserPrefsStorage userPrefsStorage) {
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ InterviewStorage methods ==============================
    /** Save all the IntervieweeList into their Json file*/
    @Override
    public void saveInterviewee(IntervieweeList source) throws IOException {
        logger.fine("Attempting to write to Interviewee data file: " + getIntervieweeDirectory());
        intervieweeStorage.saveInterview(source);
    }

    @Override
    public Optional<IntervieweeList> readInterviewee(QuestionList questionList,
                                                     AttributeList attributeList,
                                                     Boolean initialModel) throws DataConversionException {
        return readInterviewee(intervieweeStorage.getPath(), questionList, attributeList, initialModel);
    }

    /** Reads the Json file and converts them to Interviewee objects*/
    public Optional<IntervieweeList> readInterviewee(Path filepath, QuestionList questionList,
                                                     AttributeList attributeList,
                                                     Boolean initialModel) throws DataConversionException {
        logger.fine("Attempting to read data from Interviewee file: " + filepath);
        return intervieweeStorage
                .readInterviewee(filepath, questionList, attributeList, initialModel, this.transcriptStorage);
    }

    public Path getIntervieweeDirectory() {
        return intervieweeStorage.getPath();
    }

    // ================ AttributeStorage methods ==============================
    /** Save all the AttributeList into their Json file*/
    @Override
    public void saveAttribute(AttributeList source) throws IOException {
        logger.fine("Attempting to write to Attribute data file: " + getAttributeDirectory());
        attributeStorage.saveAttributes(source);
    }

    @Override
    public Optional<AttributeList> readAttribute() throws DataConversionException {
        return readAttribute(attributeStorage.getPath());
    }

    /** Reads the Json file and converts them to Interviewee objects*/
    public Optional<AttributeList> readAttribute(Path filepath) throws DataConversionException {
        logger.fine("Attempting to read data from Attribute file: " + filepath);
        return attributeStorage.readAttribute(filepath);
    }

    public Path getAttributeDirectory() {
        return attributeStorage.getPath();
    }

    // ================ QuestionStorage methods ==============================
    /** Save all the QuestionList into their Json file*/
    @Override
    public void saveQuestion(QuestionList source) throws IOException {
        logger.fine("Attempting to write to Metric data file: " + getQuestionDirectory());
        questionStorage.saveQuestions(source);
    }

    @Override
    public Optional<QuestionList> readQuestion() throws DataConversionException {
        return readQuestion(questionStorage.getPath());
    }

    /** Reads the Json file and converts them to Interviewee objects*/
    public Optional<QuestionList> readQuestion(Path filepath) throws DataConversionException {
        logger.fine("Attempting to read data from Question file: " + filepath);
        return questionStorage.readQuestion(filepath);
    }

    public Path getQuestionDirectory() {
        return questionStorage.getPath();
    }

    // ================ MetricStorage methods ================================
    /** Save all the MetricList into their Json file*/
    @Override
    public void saveMetric(MetricList source) throws IOException {
        logger.fine("Attempting to write to Metric data file: " + getMetricDirectory());
        metricStorage.saveMetrics(source);
    }

    @Override
    public Optional<MetricList> readMetric() throws DataConversionException {
        return readMetric(metricStorage.getPath());
    }

    /** Reads the Json file and converts them to Interviewee objects */
    public Optional<MetricList> readMetric(Path filepath) throws DataConversionException {
        logger.fine("Attempting to read data from Metric file: " + filepath);
        return metricStorage.readMetric(filepath);
    }

    public Path getMetricDirectory() {
        return metricStorage.getPath();
    }

    // ================ TranscriptStorage methods ================================
    /** Save all the MetricList into their Json file */
    @Override
    public void saveTranscript(Interviewee source) throws IOException {
        logger.fine("Attempting to write to Transcript data file: " + getMetricDirectory());
        transcriptStorage.saveTranscript(source);
    }

    public Path getTranscriptDirectory() {
        return transcriptStorage.getPath();
    }

    // ================ ModelStorage methods ================================
    public Path getModelDirectory() {
        return modelStorage.getPath();
    }

    /**
     * Saves the Model state to hard disk.
     *
     * @param model the model state - whether the model is finalised.
     * @throws IOException if an error occurs while writing the file.
     */
    @Override
    public void saveModel(Boolean model) throws IOException {
        logger.fine("Attempting to write to Model data file: " + getModelDirectory());
        modelStorage.saveModel(model);
    }

    @Override
    public Optional<Boolean> readModel() throws DataConversionException {
        return readModel(getModelDirectory());
    }

    /**
     * Reads the Model finalised state from memory.
     */
    public Optional<Boolean> readModel(Path filePath) throws DataConversionException {
        logger.fine("Attempting to read data from Model file: " + filePath);
        return modelStorage.readModel(filePath);
    }

    // ================ UserPrefsStorage methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }
}

package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.UserPrefs;
import seedu.address.model.hirelah.AttributeList;
import seedu.address.model.hirelah.IntervieweeList;
import seedu.address.model.hirelah.MetricList;
import seedu.address.model.hirelah.QuestionList;
import seedu.address.model.hirelah.storage.AttributeStorage;
import seedu.address.model.hirelah.storage.IntervieweeStorage;
import seedu.address.model.hirelah.storage.MetricStorage;
import seedu.address.model.hirelah.storage.ModelStorage;
import seedu.address.model.hirelah.storage.QuestionStorage;
import seedu.address.model.hirelah.storage.StorageManager;
import seedu.address.model.hirelah.storage.TranscriptStorage;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    /**
     * the following is a sample of the
     * AttributeList, Questionlist,
     * MetricList, IntervieweeList and model that will be used
     * for testing of the storage.
     */
    private StorageManager storageManager;
    private IntervieweeList intervieweeList = new IntervieweeList();
    private AttributeList attributeList = new AttributeList();
    private QuestionList questionList = new QuestionList();
    private MetricList metricList = new MetricList();
    private boolean model = false;

    /**
     * Setting up of the respective fields
     * needed for the testing.
     */
    @BeforeEach
    public void setUp() throws IllegalValueException {
        /** populating the different lists with initial values for testing.*/
        this.attributeList.add("Productivity");
        this.intervieweeList.addInterviewee("John Doe");
        this.questionList.add("How can you contribute to the company?");
        List<String> alias = List.of("Produc");
        List<Double> weights = List.of(2.0);
        this.metricList.add("Team player", this.attributeList, alias, weights);

        /**Initialising the different components of the Storage*/
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        IntervieweeStorage intervieweeStorage = new IntervieweeStorage(getTempFilePath("interviewees"));
        AttributeStorage attributeStorage = new AttributeStorage(getTempFilePath("attributes"));
        QuestionStorage questionStorage = new QuestionStorage(getTempFilePath("questions"));
        MetricStorage metricStorage = new MetricStorage(getTempFilePath("metrics"));
        TranscriptStorage transcriptStorage = new TranscriptStorage(getTempFilePath("transcripts"));
        ModelStorage modelStorage = new ModelStorage(getTempFilePath("model"));
        storageManager = new StorageManager(userPrefsStorage, intervieweeStorage, attributeStorage, questionStorage,
                metricStorage, transcriptStorage, modelStorage);
    }

    /** Provide custom path based on input String. */
    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    /** The following tests check if all the different components of Storage can save and retrieve the
     * various fields accurately.*/
    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void intervieweesReadSave() throws Exception {
        storageManager.saveInterviewee(intervieweeList);
        IntervieweeList retrieved = storageManager.readInterviewee(questionList, attributeList, model).get();
        assertEquals(this.intervieweeList, retrieved);
    }

    @Test
    public void attributesReadSave() throws Exception {
        storageManager.saveAttribute(attributeList);
        AttributeList retrieved = storageManager.readAttribute().get();
        assertEquals(attributeList, retrieved);
    }

    @Test
    public void metricsReadSave() throws Exception {
        storageManager.saveMetric(this.metricList);
        MetricList retrieved = storageManager.readMetric().get();
        assertEquals(metricList, retrieved);
    }

    @Test
    public void modelSaveRead() throws Exception {
        storageManager.saveModel(model);
        boolean retrieved = storageManager.readModel().get();
        assertEquals(model, retrieved);
    }

    /** The following tests are to check that StorageManager can return
     * the a valid path for the various components.
     */

    @Test
    public void getModelPath() {
        assertNotNull(storageManager.getModelDirectory());
    }

    @Test
    public void getTranscriptPath() {
        assertNotNull(storageManager.getTranscriptDirectory());
    }

    @Test
    public void getIntervieweePath() {
        assertNotNull(storageManager.getIntervieweeDirectory());
    }

    @Test
    public void getQuestionsPath() {
        assertNotNull(storageManager.getQuestionDirectory());
    }

    @Test
    public void getAttributesPath() {
        assertNotNull(storageManager.getAttributeDirectory());
    }

    @Test
    public void getUserPrefsPath() {
        assertNotNull(storageManager.getUserPrefsFilePath());
    }
}

package hirelah.model;

import static hirelah.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import hirelah.commons.core.GuiSettings;
import hirelah.commons.core.LogsCenter;
import hirelah.model.hirelah.AppPhase;
import hirelah.model.hirelah.Attribute;
import hirelah.model.hirelah.AttributeList;
import hirelah.model.hirelah.Interviewee;
import hirelah.model.hirelah.IntervieweeList;
import hirelah.model.hirelah.IntervieweeToScore;
import hirelah.model.hirelah.Metric;
import hirelah.model.hirelah.MetricList;
import hirelah.model.hirelah.Question;
import hirelah.model.hirelah.QuestionList;
import hirelah.model.hirelah.Transcript;
import hirelah.model.hirelah.exceptions.IllegalActionException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private Path session;
    private boolean finalisedInterviewProperties;
    private AppPhase appPhase;
    private Interviewee currentInterviewee;
    private IntervieweeList intervieweeList;
    private AttributeList attributeList;
    private QuestionList questionList;
    private MetricList metricList;
    private UserPrefs userPrefs;
    private ObservableList<IntervieweeToScore> bestNIntervieweeList;
    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(userPrefs);

        logger.fine("Initializing with user prefs " + userPrefs);

        this.appPhase = AppPhase.PRE_SESSION;

        this.userPrefs = new UserPrefs(userPrefs);
        this.bestNIntervieweeList = FXCollections.observableArrayList();
    }

    public ModelManager() {
        this(new UserPrefs());
    }


    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getSessionsDirectory() {
        return userPrefs.getSessionsDirectory();
    }

    @Override
    public void setSessionsDirectory(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setSessionsDirectory(addressBookFilePath);
    }


    //=========== App state setters/getters ======================================================

    @Override
    public Optional<Path> getCurrentSession() {
        return Optional.ofNullable(session);
    }

    @Override
    public void setCurrentSession(Path session) {
        this.session = session;
    }

    @Override
    public Path closeSession() {
        Path closedSession = this.session;
        setCurrentSession(null);
        setAppPhase(AppPhase.PRE_SESSION);
        finalisedInterviewProperties = false;
        return closedSession;
    }

    @Override
    public void setAppPhase(AppPhase phase) {
        this.appPhase = phase;
    }

    /**
     * Returns the current mode of the App
     */
    @Override
    public AppPhase getAppPhase() {
        return appPhase;
    }

    /**
     * Sets the interviewee currently in focus, either when viewing his/her transcript or
     * when interviewing him/her.
     *
     * @param interviewee the interviewee in focus.
     */
    @Override
    public void setCurrentInterviewee(Interviewee interviewee) {
        this.currentInterviewee = interviewee;
    }

    @Override
    public Interviewee getCurrentInterviewee() {
        return currentInterviewee;
    }

    /**
     * Checks whether there is an interviewee currently in focus
     *
     * @return boolean whether there is an interviewee in focus.
     */
    @Override
    public boolean hasCurrentInterviewee() {
        return !(this.currentInterviewee == null);
    }

    /**
     * A utility to get the Transcript of the current Interviewee (guaranteed to exist).
     * Do not use when there is no interviewee.
     */
    @Override
    public Transcript getCurrentTranscript() {
        return currentInterviewee.getTranscript().get();
    }

    @Override
    public void startInterview(Interviewee interviewee) throws IllegalActionException {
        if (interviewee.isInterviewed()) {
            throw new IllegalActionException("Interviewee has been interviewed already!");
        }
        setCurrentInterviewee(interviewee);
        if (currentInterviewee.getTranscript().isEmpty()) {
            currentInterviewee.setTranscript(new Transcript(questionList, attributeList));
        }
        setAppPhase(AppPhase.INTERVIEW);
    }

    @Override
    public void endInterview() {
        Transcript transcript = getCurrentTranscript();
        currentInterviewee.setTranscript(null);
        currentInterviewee.setTranscript(transcript);
        setCurrentInterviewee(null);
        setAppPhase(AppPhase.NORMAL);
    }

    //=========== Observable accessors =============================================================


    @Override
    public ObservableList<Attribute> getAttributeListView() {
        return attributeList.getObservableList();
    }

    @Override
    public ObservableList<Question> getQuestionListView() {
        return questionList.getObservableList();
    }

    @Override
    public ObservableList<Interviewee> getIntervieweeListView() {
        return intervieweeList.getObservableList();
    }

    @Override
    public ObservableList<Metric> getMetricListView() {
        return metricList.getObservableList();
    }

    //=========== Model component accessors ========================================================

    @Override
    public IntervieweeList getIntervieweeList() {
        return intervieweeList;
    }

    @Override
    public void setIntervieweeList(IntervieweeList intervieweeList) {
        this.intervieweeList = intervieweeList;
    }

    @Override
    public AttributeList getAttributeList() {
        return attributeList;
    }

    @Override
    public void setAttributeList(AttributeList attributeList) {
        this.attributeList = attributeList;
    }

    @Override
    public QuestionList getQuestionList() {
        return questionList;
    }

    @Override
    public void setQuestionList(QuestionList questionList) {
        this.questionList = questionList;
    }

    @Override
    public MetricList getMetricList() {
        return metricList;
    }

    @Override
    public void setMetricList(MetricList metricList) {
        this.metricList = metricList;
    }

    @Override
    public ObservableList<IntervieweeToScore> getBestNInterviewees() {
        return bestNIntervieweeList;
    }

    /**
     * Finalizes the questions and attributes so they do not change between interviews.
     */
    @Override
    public void finaliseInterviewProperties() {
        this.finalisedInterviewProperties = true;
    }

    /** Checks whether the questions and attributes has been finalised */
    @Override
    public boolean isFinalisedInterviewProperties() {
        return this.finalisedInterviewProperties;
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return userPrefs.equals(other.userPrefs)
                && Objects.equals(session, other.session)
                && Objects.equals(appPhase, other.appPhase)
                && finalisedInterviewProperties == other.finalisedInterviewProperties
                && Objects.equals(currentInterviewee, other.currentInterviewee)
                && Objects.equals(intervieweeList, other.intervieweeList)
                && Objects.equals(attributeList, other.attributeList)
                && Objects.equals(questionList, other.questionList)
                && Objects.equals(metricList, other.metricList)
                && bestNIntervieweeList.equals(other.bestNIntervieweeList);
    }
}

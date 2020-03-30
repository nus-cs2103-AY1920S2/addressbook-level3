package seedu.zerotoone.model;

import static java.util.Objects.requireNonNull;
import static seedu.zerotoone.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import org.apache.commons.lang3.time.StopWatch;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.zerotoone.commons.core.GuiSettings;
import seedu.zerotoone.commons.core.LogsCenter;
import seedu.zerotoone.model.exercise.Exercise;
import seedu.zerotoone.model.exercise.ExerciseList;
import seedu.zerotoone.model.exercise.ReadOnlyExerciseList;
import seedu.zerotoone.model.schedule.Schedule;
import seedu.zerotoone.model.schedule.ScheduleList;
import seedu.zerotoone.model.schedule.ScheduledWorkout;
import seedu.zerotoone.model.schedule.Scheduler;
import seedu.zerotoone.model.session.CompletedSession;
import seedu.zerotoone.model.session.Session;
import seedu.zerotoone.model.userprefs.ReadOnlyUserPrefs;
import seedu.zerotoone.model.userprefs.UserPrefs;


/**
 * Represents the in-memory model of the exercise list data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final UserPrefs userPrefs;
    private final ExerciseList exerciseList;
    private final FilteredList<Exercise> filteredExercises;
    private Optional<Session> currentSession;
    private final StopWatch stopwatch;
    private final Scheduler scheduler;

    /**
     * Initializes a ModelManager with the given exerciseList and userPrefs.
     */
    public ModelManager(ReadOnlyUserPrefs userPrefs,
                        ReadOnlyExerciseList exerciseList,
                        ScheduleList scheduleList) {
        super();
        requireAllNonNull(exerciseList,
                userPrefs,
                scheduleList);
        logger.fine("Initializing with user prefs " + userPrefs);

        this.exerciseList = new ExerciseList(exerciseList);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredExercises = new FilteredList<>(this.exerciseList.getExerciseList());
        this.currentSession = Optional.empty();
        this.stopwatch = new StopWatch();
        this.scheduler = new Scheduler(scheduleList); // STEPH_TODO add storage
    }

    public ModelManager() {
        this(new UserPrefs(),
                new ExerciseList(),
                new ScheduleList());
    }

    // -----------------------------------------------------------------------------------------
    // Common - User Preferences
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

    // -----------------------------------------------------------------------------------------
    // Exercise List
    @Override
    public Path getExerciseListFilePath() {
        return userPrefs.getExerciseListFilePath();
    }

    @Override
    public void setExerciseListFilePath(Path exerciseListFilePath) {
        requireNonNull(exerciseListFilePath);
        userPrefs.setExerciseListFilePath(exerciseListFilePath);
    }

    @Override
    public void setExerciseList(ReadOnlyExerciseList exerciseList) {
        this.exerciseList.resetData(exerciseList);
    }

    @Override
    public ReadOnlyExerciseList getExerciseList() {
        return exerciseList;
    }

    @Override
    public boolean hasExercise(Exercise exercise) {
        requireNonNull(exercise);
        return exerciseList.hasExercise(exercise);
    }

    @Override
    public void deleteExercise(Exercise target) {
        exerciseList.removeExercise(target);
    }

    @Override
    public void addExercise(Exercise exercise) {
        exerciseList.addExercise(exercise);
        updateFilteredExerciseList(PREDICATE_SHOW_ALL_EXERCISES);
    }

    @Override
    public void setExercise(Exercise target, Exercise editedExercise) {
        requireAllNonNull(target, editedExercise);
        exerciseList.setExercise(target, editedExercise);
    }

    @Override
    public ObservableList<Exercise> getFilteredExerciseList() {
        return filteredExercises;
    }

    @Override
    public void updateFilteredExerciseList(Predicate<Exercise> predicate) {
        requireNonNull(predicate);
        filteredExercises.setPredicate(predicate);
    }

    @Override
    public boolean isInSession() {
        return this.currentSession.isPresent();
    }

    @Override
    public Session startSession(Exercise exerciseToStart, LocalDateTime currentDateTime) {
        Session session = new Session(exerciseToStart, currentDateTime);
        this.currentSession = Optional.of(session);
        return session;
    }

    @Override
    public void stopSession(LocalDateTime currentDateTime) {
        Session session = this.currentSession.get();
        CompletedSession completedSession = session.finish(currentDateTime);
        // do smth like save completed workout
        this.currentSession = Optional.empty();
    }

    @Override
    public Optional<Session> getCurrentSession() {
        return Optional.ofNullable(this.currentSession.orElse(null));
    }

    // -----------------------------------------------------------------------------------------
    // Schedule
    @Override
    public ScheduleList getScheduleList() {
        return scheduler.getScheduleList();
    }

    @Override
    public boolean hasSchedule(Schedule schedule) {
        return scheduler.hasSchedule(schedule);
    }

    @Override
    public void addSchedule(Schedule schedule) {
        scheduler.addSchedule(schedule);
    }

    @Override
    public void setSchedule(Schedule scheduleToEdit, Schedule editedSchedule) {
        scheduler.setSchedule(scheduleToEdit, editedSchedule);
    }

    @Override
    public void deleteScheduledWorkout(ScheduledWorkout scheduledWorkoutToDelete) {
        scheduler.deleteScheduledWorkout(scheduledWorkoutToDelete);
    }

    @Override
    public ObservableList<ScheduledWorkout> getSortedScheduledWorkoutList() {
        return scheduler.getSortedScheduledWorkoutList();
    }

    // -----------------------------------------------------------------------------------------
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
        return exerciseList.equals(other.exerciseList)
                && userPrefs.equals(other.userPrefs)
                && filteredExercises.equals(other.filteredExercises);
        // && scheduler.equals(other.scheduler);   // STEPH_TODO: implement later
    }
}

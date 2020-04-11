package seedu.zerotoone.model.userprefs;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {
    private Path exerciseListFilePath = Paths.get("data" , "exerciselist.json");
    private Path workoutListFilePath = Paths.get("data" , "workoutlist.json");
    private Path scheduleListFilePath = Paths.get("data" , "schedulelist.json");
    private Path logListFilePath = Paths.get("data" , "loglist.json");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {}

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setExerciseListFilePath(newUserPrefs.getExerciseListFilePath());
        setWorkoutListFilePath(newUserPrefs.getWorkoutListFilePath());
        setScheduleListFilePath(newUserPrefs.getScheduleListFilePath());
        setLogListFilePath(newUserPrefs.getLogListFilePath());
    }

    public Path getExerciseListFilePath() {
        return exerciseListFilePath;
    }

    public void setExerciseListFilePath(Path exerciseListFilePath) {
        requireNonNull(exerciseListFilePath);
        this.exerciseListFilePath = exerciseListFilePath;
    }

    public Path getWorkoutListFilePath() {
        return workoutListFilePath;
    }

    public void setWorkoutListFilePath(Path workoutListFilePath) {
        requireNonNull(workoutListFilePath);
        this.workoutListFilePath = workoutListFilePath;
    }

    public Path getScheduleListFilePath() {
        return scheduleListFilePath;
    }

    public void setScheduleListFilePath(Path scheduleListFilePath) {
        requireNonNull(scheduleListFilePath);
        this.scheduleListFilePath = scheduleListFilePath;
    }

    public Path getLogListFilePath() {
        return logListFilePath;
    }

    public void setLogListFilePath(Path logListFilePath) {
        requireNonNull(logListFilePath);
        this.logListFilePath = logListFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return exerciseListFilePath.equals(o.exerciseListFilePath)
                && workoutListFilePath.equals(o.workoutListFilePath)
                && scheduleListFilePath.equals(o.scheduleListFilePath)
                && logListFilePath.equals(o.logListFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exerciseListFilePath,
                workoutListFilePath, scheduleListFilePath, logListFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nExercise List file location : " + exerciseListFilePath);
        sb.append("\nSession List file location : " + workoutListFilePath);
        sb.append("\nSchedule List file location : " + scheduleListFilePath);
        sb.append("\nLog List file location : " + logListFilePath);
        return sb.toString();
    }

}

package seedu.zerotoone.storage;

import seedu.zerotoone.storage.exercise.ExerciseListStorage;
import seedu.zerotoone.storage.log.LogListStorage;
import seedu.zerotoone.storage.schedule.ScheduleListStorage;
import seedu.zerotoone.storage.userprefs.UserPrefsStorage;
import seedu.zerotoone.storage.workout.WorkoutListStorage;

/**
 * API of the Storage component
 */
public interface Storage extends UserPrefsStorage, ExerciseListStorage,
        WorkoutListStorage, ScheduleListStorage, LogListStorage {}

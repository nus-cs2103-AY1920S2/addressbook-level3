package seedu.zerotoone.model;

import java.util.function.Predicate;

import seedu.zerotoone.model.exercise.Exercise;
import seedu.zerotoone.model.exercise.ExerciseModel;
import seedu.zerotoone.model.log.LogModel;
import seedu.zerotoone.model.schedule.SchedulerModel;
import seedu.zerotoone.model.session.CompletedWorkout;
import seedu.zerotoone.model.session.SessionModel;
import seedu.zerotoone.model.userprefs.UserPrefsModel;
import seedu.zerotoone.model.workout.WorkoutModel;

/**
 * The API of the Model component.
 */
public interface Model extends UserPrefsModel, SessionModel,
        ExerciseModel, WorkoutModel, SchedulerModel, LogModel {

    /** {@code Predicate} that always evaluate to true */
    Predicate<Exercise> PREDICATE_SHOW_ALL_EXERCISES = unused -> true;
    Predicate<CompletedWorkout> PREDICATE_SHOW_ALL_LOGS = unused -> true;
    Predicate<Object> PREDICATE_SHOW_NONE = unused -> false;
}

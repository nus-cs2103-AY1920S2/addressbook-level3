package seedu.zerotoone.storage.workout.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.zerotoone.commons.exceptions.IllegalValueException;
import seedu.zerotoone.model.workout.ReadOnlyWorkoutList;
import seedu.zerotoone.model.workout.Workout;
import seedu.zerotoone.model.workout.WorkoutList;

/**
 * An Immutable WorkoutList that is serializable to JSON format.
 */
@JsonRootName(value = "workoutlist")
public class JacksonWorkoutList {

    public static final String MESSAGE_DUPLICATE_WORKOUT = "Workouts list contains duplicate workout(s).";

    private final List<JacksonWorkout> workouts = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableWorkoutList} with the given workouts.
     */
    @JsonCreator
    public JacksonWorkoutList(@JsonProperty("workouts") List<JacksonWorkout> workouts) {
        this.workouts.addAll(workouts);
    }

    /**
     * Converts a given {@code ReadOnlyWorkoutList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableWorkoutList}.
     */
    public JacksonWorkoutList(ReadOnlyWorkoutList source) {
        workouts.addAll(source.getWorkoutList().stream().map(JacksonWorkout::new).collect(Collectors.toList()));
    }

    /**
     * Converts this workout list into the model's {@code WorkoutList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public WorkoutList toModelType() throws IllegalValueException {
        WorkoutList workoutList = new WorkoutList();
        for (JacksonWorkout jsonAdaptedWorkout : workouts) {
            Workout workout = jsonAdaptedWorkout.toModelType();
            if (workoutList.hasWorkout(workout)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_WORKOUT);
            }
            workoutList.addWorkout(workout);
        }
        return workoutList;
    }

}

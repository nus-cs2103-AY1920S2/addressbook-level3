package seedu.zerotoone.storage.workout.util;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.zerotoone.commons.exceptions.IllegalValueException;
import seedu.zerotoone.model.exercise.Exercise;
import seedu.zerotoone.model.workout.Workout;
import seedu.zerotoone.model.workout.WorkoutName;
import seedu.zerotoone.storage.exercise.util.JacksonExercise;

// import java.util.stream.Collectors;

/**
 * Jackson-friendly version of {@link Workout}.
 */
public class JacksonWorkout {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Workout's %s field is missing!";

    private final String workoutName;
    private final List<JacksonExercise> workoutExercises = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedWorkout} with the given workout details.
     */
    @JsonCreator
    public JacksonWorkout(@JsonProperty("workoutName") String workoutName,
            @JsonProperty("workoutExercises") List<JacksonExercise> workoutExercises) {
        this.workoutName = workoutName;
        if (workoutExercises != null) {
            this.workoutExercises.addAll(workoutExercises);
        }
    }

    /**
     * Converts a given {@code Workout} into this class for Jackson use.
     */
    public JacksonWorkout(Workout source) {
        workoutName = source.getWorkoutName().fullName;
        for (Exercise workoutExercise : source.getWorkoutExercises()) {
            workoutExercises.add(new JacksonExercise(workoutExercise));
        }
    }

    /**
     * Converts this Jackson-friendly adapted workout object into the model's {@code Workout} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted workout.
     */
    public Workout toModelType() throws IllegalValueException {
        if (workoutName == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    WorkoutName.class.getSimpleName()));
        } else if (!WorkoutName.isValidWorkoutName(workoutName)) {
            throw new IllegalValueException(WorkoutName.MESSAGE_CONSTRAINTS);
        }
        final WorkoutName modelWorkoutName = new WorkoutName(workoutName);

        final List<Exercise> modelWorkoutExercises = new ArrayList<>();
        for (JacksonExercise workoutExercise : workoutExercises) {
            modelWorkoutExercises.add(workoutExercise.toModelType());
        }
        return new Workout(modelWorkoutName, modelWorkoutExercises);
    }

}

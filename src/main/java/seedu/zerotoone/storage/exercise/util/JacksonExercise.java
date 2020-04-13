package seedu.zerotoone.storage.exercise.util;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.zerotoone.commons.core.LogsCenter;
import seedu.zerotoone.commons.exceptions.IllegalValueException;
import seedu.zerotoone.model.exercise.Exercise;
import seedu.zerotoone.model.exercise.ExerciseName;
import seedu.zerotoone.model.exercise.ExerciseSet;

// import java.util.stream.Collectors;

/**
 * Jackson-friendly version of {@link Exercise}.
 */
public class JacksonExercise {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Exercise's %s field is missing!";

    private final String exerciseName;
    private final List<JacksonExerciseSet> exerciseSets = new ArrayList<>();
    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Constructs a {@code JsonAdaptedExercise} with the given exercise details.
     */
    @JsonCreator
    public JacksonExercise(@JsonProperty("exerciseName") String exerciseName,
            @JsonProperty("exerciseSets") List<JacksonExerciseSet> exerciseSets) {
        this.exerciseName = exerciseName;
        if (exerciseSets != null) {
            this.exerciseSets.addAll(exerciseSets);
        }
    }

    /**
     * Converts a given {@code Exercise} into this class for Jackson use.
     */
    public JacksonExercise(Exercise source) {
        exerciseName = source.getExerciseName().fullName;
        for (ExerciseSet exerciseSet : source.getExerciseSets()) {
            exerciseSets.add(new JacksonExerciseSet(exerciseSet));
        }
    }

    /**
     * Converts this Jackson-friendly adapted exercise object into the model's {@code Exercise} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted exercise.
     */
    public Exercise toModelType() throws IllegalValueException {
        logger.fine("Converting JSON data to Exercise");

        if (exerciseName == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ExerciseName.class.getSimpleName()));
        } else if (!ExerciseName.isValidExerciseName(exerciseName)) {
            throw new IllegalValueException(ExerciseName.MESSAGE_CONSTRAINTS);
        }
        final ExerciseName modelExerciseName = new ExerciseName(exerciseName);

        final List<ExerciseSet> modelExerciseSets = new ArrayList<>();
        for (JacksonExerciseSet exerciseSet : exerciseSets) {
            modelExerciseSets.add(exerciseSet.toModelType());
        }
        return new Exercise(modelExerciseName, modelExerciseSets);
    }

}

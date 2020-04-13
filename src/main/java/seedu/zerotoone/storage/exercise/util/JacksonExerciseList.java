package seedu.zerotoone.storage.exercise.util;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.zerotoone.commons.core.LogsCenter;
import seedu.zerotoone.commons.exceptions.IllegalValueException;
import seedu.zerotoone.model.exercise.Exercise;
import seedu.zerotoone.model.exercise.ExerciseList;
import seedu.zerotoone.model.exercise.ReadOnlyExerciseList;

/**
 * An Immutable ExerciseList that is serializable to JSON format.
 */
@JsonRootName(value = "exerciselist")
public class JacksonExerciseList {

    public static final String MESSAGE_DUPLICATE_EXERCISE = "Exercises list contains duplicate exercise(s).";

    private final List<JacksonExercise> exercises = new ArrayList<>();
    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Constructs a {@code JsonSerializableExerciseList} with the given exercises.
     */
    @JsonCreator
    public JacksonExerciseList(@JsonProperty("exercises") List<JacksonExercise> exercises) {
        this.exercises.addAll(exercises);
    }

    /**
     * Converts a given {@code ReadOnlyExerciseList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableExerciseList}.
     */
    public JacksonExerciseList(ReadOnlyExerciseList source) {
        exercises.addAll(source.getExerciseList().stream().map(JacksonExercise::new).collect(Collectors.toList()));
    }

    /**
     * Converts this exercise list into the model's {@code ExerciseList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ExerciseList toModelType() throws IllegalValueException {
        logger.fine("Converting JSON data to ExerciseList");

        ExerciseList exerciseList = new ExerciseList();
        for (JacksonExercise jsonAdaptedExercise : exercises) {
            Exercise exercise = jsonAdaptedExercise.toModelType();
            if (exerciseList.hasExercise(exercise)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_EXERCISE);
            }
            exerciseList.addExercise(exercise);
        }
        return exerciseList;
    }

}

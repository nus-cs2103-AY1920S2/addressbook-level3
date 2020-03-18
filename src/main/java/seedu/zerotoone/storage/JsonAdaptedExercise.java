package seedu.zerotoone.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
// import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.zerotoone.commons.exceptions.IllegalValueException;
import seedu.zerotoone.model.exercise.Exercise;
import seedu.zerotoone.model.exercise.ExerciseName;
import seedu.zerotoone.model.exercise.ExerciseSet;

/**
 * Jackson-friendly version of {@link Exercise}.
 */
class JsonAdaptedExercise {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Exercise's %s field is missing!";

    private final String exerciseName;
    // private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedExercise} with the given exercise details.
     */
    @JsonCreator
    public JsonAdaptedExercise(@JsonProperty("exerciseName") String exerciseName, @JsonProperty("phone") String phone,
                               @JsonProperty("email") String email, @JsonProperty("address") String address) {
        this.exerciseName = exerciseName;
    }

    /**
     * Converts a given {@code Exercise} into this class for Jackson use.
     */
    public JsonAdaptedExercise(Exercise source) {
        exerciseName = source.getExerciseName().fullName;
    }

    /**
     * Converts this Jackson-friendly adapted exercise object into the model's {@code Exercise} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted exercise.
     */
    public Exercise toModelType() throws IllegalValueException {
        // final List<Tag> exerciseTags = new ArrayList<>();
        // for (JsonAdaptedTag tag : tagged) {
        //     exerciseTags.add(tag.toModelType());
        // }

        if (exerciseName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, ExerciseName.class.getSimpleName()));
        }
        if (!ExerciseName.isValidExerciseName(exerciseName)) {
            throw new IllegalValueException(ExerciseName.MESSAGE_CONSTRAINTS);
        }
        final ExerciseName modelExerciseName = new ExerciseName(exerciseName);
        final List<ExerciseSet> modelExerciseSets = new ArrayList<>();
       
        return new Exercise(modelExerciseName, modelExerciseSets);
    }

}

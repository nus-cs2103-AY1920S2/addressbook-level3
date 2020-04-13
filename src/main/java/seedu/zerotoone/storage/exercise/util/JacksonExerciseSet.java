package seedu.zerotoone.storage.exercise.util;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.zerotoone.commons.core.LogsCenter;
import seedu.zerotoone.commons.exceptions.IllegalValueException;
import seedu.zerotoone.model.exercise.ExerciseSet;
import seedu.zerotoone.model.exercise.NumReps;
import seedu.zerotoone.model.exercise.Weight;

/**
 * Jackson-friendly version of {@link ExerciseSet}.
 */
class JacksonExerciseSet {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "ExerciseSet's %s field is missing!";

    private final String weight;
    private final String numReps;
    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Constructs a {@code JacksonExerciseSet} with the given {@code exerciseSet}.
     */
    @JsonCreator
    public JacksonExerciseSet(@JsonProperty("weight") String weight,
            @JsonProperty("numReps") String numReps) {
        this.weight = weight;
        this.numReps = numReps;
    }

    /**
     * Converts a given {@code ExerciseSet} into this class for Jackson use.
     */
    public JacksonExerciseSet(ExerciseSet source) {
        weight = source.getWeight().value;
        numReps = source.getNumReps().value;
    }

    /**
     * Converts this Jackson-friendly adapted exerciseSet object into the model's {@code ExerciseSet} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted exerciseSet.
     */
    public ExerciseSet toModelType() throws IllegalValueException {
        logger.fine("Converting JSON data to ExerciseSet");

        if (weight == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Weight.class.getSimpleName()));
        } else if (!Weight.isValidWeight(weight)) {
            throw new IllegalValueException(Weight.MESSAGE_CONSTRAINTS);
        }
        final Weight modelWeight = new Weight(weight);

        if (numReps == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, NumReps.class.getSimpleName()));
        } else if (!NumReps.isValidNumReps(numReps)) {
            throw new IllegalValueException(NumReps.MESSAGE_CONSTRAINTS);
        }
        final NumReps modelNumReps = new NumReps(numReps);

        return new ExerciseSet(modelWeight, modelNumReps);
    }

}

package seedu.zerotoone.storage.session.util;

import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.zerotoone.commons.exceptions.IllegalValueException;
import seedu.zerotoone.model.exercise.NumReps;
import seedu.zerotoone.model.exercise.Weight;
import seedu.zerotoone.model.session.SessionSet;

/**
 * Jackson-friendly version of {@link SessionSet}.
 */
class JacksonSessionSet {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "SessionSet's %s field is missing!";
    public static final String MALFORMED_BOOLEAN_MESSAGE = "SessionSet's isFinished field is incorrect!";
    private Pattern isBoolean = Pattern.compile("true|false", Pattern.CASE_INSENSITIVE);

    private final String weight;
    private final String numReps;
    private final String isFinished;

    /**
     * Constructs a {@code JacksonSessionSet} with the given {@code sessionSet}.
     */
    @JsonCreator
    public JacksonSessionSet(@JsonProperty("weight") String weight,
            @JsonProperty("numReps") String numReps,
            @JsonProperty("isFinished") String isFinished) {
        this.weight = weight;
        this.numReps = numReps;
        this.isFinished = isFinished;
    }

    /**
     * Converts a given {@code SessionSet} into this class for Jackson use.
     */
    public JacksonSessionSet(SessionSet source) {
        weight = source.getWeight().value;
        numReps = source.getNumReps().value;
        isFinished = "" + source.isFinished();
    }

    /**
     * Converts this Jackson-friendly adapted sessionSet object into the model's {@code SessionSet} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted sessionSet.
     */
    public SessionSet toModelType() throws IllegalValueException {
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

        if (isFinished == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "isFinished"));
        } else if (!isBoolean.matcher(isFinished.trim()).matches()) {
            throw new IllegalValueException(MALFORMED_BOOLEAN_MESSAGE);
        }

        return new SessionSet(modelWeight, modelNumReps, Boolean.parseBoolean(isFinished));
    }

}

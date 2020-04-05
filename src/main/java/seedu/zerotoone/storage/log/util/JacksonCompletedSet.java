package seedu.zerotoone.storage.log.util;

import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.zerotoone.commons.exceptions.IllegalValueException;
import seedu.zerotoone.model.exercise.ExerciseName;
import seedu.zerotoone.model.exercise.NumReps;
import seedu.zerotoone.model.exercise.Weight;
import seedu.zerotoone.model.session.CompletedSet;

/**
 * Jackson-friendly version of {@link CompletedSet}.
 */
class JacksonCompletedSet {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "CompletedSets's %s field is missing!";
    public static final String MALFORMED_BOOLEAN_MESSAGE = "CompletedSets's isFinished field is incorrect!";
    public static final String MALFORMED_INDEX_MESSAGE = "CompletedSets's index field is incorrect!";
    public static final String MALFORMED_NAME_MESSAGE = "CompletedSets's exerciseName field is incorrect!";
    private static final Pattern IS_BOOLEAN = Pattern.compile("true|false", Pattern.CASE_INSENSITIVE);

    private final String weight;
    private final String numReps;
    private final String isFinished;
    private final String exerciseName;
    private final String index;

    /**
     * Constructs a {@code JacksonSessionSet} with the given {@code sessionSet}.
     */
    @JsonCreator
    public JacksonCompletedSet(@JsonProperty("weight") String weight,
                               @JsonProperty("numReps") String numReps,
                               @JsonProperty("isFinished") String isFinished,
                               @JsonProperty("isFinished") String exerciseName,
                               @JsonProperty("isFinished") String index) {
        this.weight = weight;
        this.numReps = numReps;
        this.isFinished = isFinished;
        this.exerciseName = exerciseName;
        this.index = index;

    }

    /**
     * Converts a given {@code SessionSet} into this class for Jackson use.
     */
    public JacksonCompletedSet(CompletedSet source) {
        weight = source.getWeight().value;
        numReps = source.getNumReps().value;
        isFinished = "" + source.isFinished();
        exerciseName = source.getExerciseName().fullName;
        index = String.valueOf(source.getIndex());
    }

    /**
     * Converts this Jackson-friendly adapted sessionSet object into the model's {@code SessionSet} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted sessionSet.
     */
    public CompletedSet toModelType() throws IllegalValueException {
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
        } else if (!IS_BOOLEAN.matcher(isFinished.trim()).matches()) {
            throw new IllegalValueException(MALFORMED_BOOLEAN_MESSAGE);
        }

        if (exerciseName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ExerciseName.class.getSimpleName()));
        } else if (!IS_BOOLEAN.matcher(isFinished.trim()).matches()) {
            throw new IllegalValueException(MALFORMED_NAME_MESSAGE);
        }
        final ExerciseName modelExerciseName = new ExerciseName(exerciseName);

        if (index == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "index"));
        } else if (!IS_BOOLEAN.matcher(isFinished.trim()).matches()) {
            throw new IllegalValueException(MALFORMED_INDEX_MESSAGE);
        }
        final int modelIndex = Integer.parseInt(index);

        return new CompletedSet(modelWeight, modelNumReps, Boolean.parseBoolean(isFinished),
                modelExerciseName, modelIndex);
    }

}

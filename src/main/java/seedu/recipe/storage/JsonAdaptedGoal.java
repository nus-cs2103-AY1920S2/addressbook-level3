package seedu.recipe.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.recipe.commons.exceptions.IllegalValueException;
import seedu.recipe.model.goal.Goal;

/**
 * Jackson-friendly version of {@link Goal}.
 */
public class JsonAdaptedGoal {

    private final String goalName;

    /**
     * Constructs a {@code JsonAdaptedGoal} with the given {@code goalName}.
     */
    @JsonCreator
    public JsonAdaptedGoal(String goalName) {
        this.goalName = goalName;
    }

    /**
     * Converts a given {@code Goal} into this class for Jackson use.
     */
    public JsonAdaptedGoal(Goal source) {
        goalName = source.goalName;
    }

    @JsonValue
    public String getGoalName() {
        return goalName;
    }

    /**
     * Converts this Jackson-friendly adapted goal object into the model's {@code Goal} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted goal.
     */
    public Goal toModelType() throws IllegalValueException {
        if (!Goal.isValidGoalName(goalName)) {
            throw new IllegalValueException(Goal.MESSAGE_CONSTRAINTS);
        }
        return new Goal(goalName);
    }

}

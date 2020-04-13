package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.step.Step;

/**
 * Jackson-friendly version of {@link Step}.
 */
class JsonAdaptedStep {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Step's %s field is missing!";

    private final String stepDescription;

    /**
     * Constructs a {@code JsonAdaptedStep} with the given {@code stepDescription}.
     */
    @JsonCreator
    public JsonAdaptedStep(@JsonProperty("stepDescription") String stepDescription) {
        this.stepDescription = stepDescription;
    }

    /**
     * Converts a given {@code Step} into this class for Jackson use.
     */
    public JsonAdaptedStep(Step source) {
        stepDescription = source.getStepDescription();
    }

    /**
     * Converts this Jackson-friendly adapted ingredient object into the model's {@code Ingredient} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted ingredient.
     */
    public Step toModelType() throws IllegalValueException {
        if (stepDescription == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    "step description"));
        } else if (!Step.isValidStep(stepDescription)) {
            throw new IllegalValueException(Step.MESSAGE_CONSTRAINTS);
        }

        return new Step(stepDescription);
    }
}

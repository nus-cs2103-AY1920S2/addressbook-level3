package seedu.address.testutil;

import seedu.address.model.step.Step;

/**
 * A utility class to help with building {@code Step} objects
 */
public class StepBuilder {

    public static final String DEFAULT_STEP_NAME = "Drink some water";

    private String stepName;

    public StepBuilder(String stepName) {
        this.stepName = stepName;
    }

    public StepBuilder() {
        this.stepName = DEFAULT_STEP_NAME;
    }

    /**
     * Sets the {@code stepName} to this step's stepName.
     */
    public StepBuilder editStep(String stepName) {
        this.stepName = stepName;
        return this;
    }

    public Step build() {
        return new Step(stepName);
    }
}

package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.step.Step;

/**
 * A utility class containing a list of {@code Step} objects to be used in tests.
 */
public class TypicalSteps {

    public static final Step CARBONARA_ONE = new Step("Boil the pasta.");
    public static final Step CARBONARA_TWO = new Step("Fry pancetta in oil in a frying pan for a few mins "
            + "until golden and crisp. Add garlic, fry for 1 min.");
    public static final Step CARBONARA_THREE = new Step("Turn off the heat.");
    public static final Step CARBONARA_FOUR = new Step("Briefly whisk egg and yolks with most of the Parmesan "
            + "and some seasoning.");

    private TypicalSteps() {} // prevents instantiation

    public static List<Step> getTypicalSteps() {
        return new ArrayList<>(Arrays.asList(CARBONARA_ONE, CARBONARA_TWO, CARBONARA_THREE, CARBONARA_FOUR));
    }
}

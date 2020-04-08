package seedu.recipe.model.goal;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.commons.util.AppUtil.checkArgument;

import seedu.recipe.model.recipe.ingredient.MainIngredientType;
import seedu.recipe.model.recipe.ingredient.MainTypeMagnitude;

/**
 * Represents a Goal in the recipe book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidGoalName(String)}
 */
public class Goal {

    public static final String MESSAGE_CONSTRAINTS = "Goals names should contain only alphabetical letters or spaces";
    public static final String VALIDATION_REGEX = "^[ A-Za-z]+$+";

    public final String goalName;
    private final MainIngredientType mainIngredientType =  MainIngredientType.GRAIN;

    /**
     * Constructs a {@code Goal} based on main ingredient type that hits the basic nutrition requirement.
     *
     */
    public Goal(String goalName) {
        requireNonNull(goalName);
        this.goalName = goalName;
        //this.goalName = setGoalName();
        checkArgument(isValidGoalName(goalName), MESSAGE_CONSTRAINTS);
    }

    /**
     * Returns true if a given string is a valid goal name.
     */
    public static boolean isValidGoalName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public String setGoalName() {
        String name;
        switch (mainIngredientType) {
        case VEGETABLE:
           name = "Herbivore";
           break;
        case PROTEIN:
            name = "Bulk like the Hulk";
            break;
        case GRAIN:
            name = "Wholesome Wholemeal";
            break;
        case FRUIT:
            name = "Fruity Fiesta";
            break;
        default:
            name = null;
            break;
        }
        return name;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Goal // instanceof handles nulls
                && goalName.toLowerCase().equals(((Goal) other).goalName.toLowerCase())); // state check
    }

    @Override
    public int hashCode() {
        return goalName.toLowerCase().hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + goalName + ']';
    }

}

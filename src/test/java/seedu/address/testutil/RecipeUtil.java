package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_GOAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STEP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditRecipeDescriptor;
import seedu.address.model.goal.Goal;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.recipe.Step;

/**
 * A utility class for Recipe.
 */
public class RecipeUtil {

    /**
     * Returns an add command string for adding the {@code recipe}.
     */
    public static String getAddCommand(Recipe recipe) {
        return AddCommand.COMMAND_WORD + " " + getRecipeDetails(recipe);
    }

    /**
     * Returns the part of command string for the given {@code recipe}'s details.
     */
    public static String getRecipeDetails(Recipe recipe) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME).append(recipe.getName().fullName).append(" ");
        sb.append(PREFIX_TIME).append(recipe.getTime().value).append(" ");
        //sb.append(PREFIX_INGREDIENT_GRAIN).append(recipe.getIngredients())
        // TODO: reconsider saving each ingredient type as a separate recipe variable
        recipe.getSteps().forEach(step -> sb.append(PREFIX_STEP).append(step.value).append(" "));
        recipe.getGoals().forEach(s -> sb.append(PREFIX_GOAL).append(s.goalName).append(" "));
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditRecipeDescriptor}'s details.
     */
    public static String getEditRecipeDescriptorDetails(EditRecipeDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getTime().ifPresent(time -> sb.append(PREFIX_TIME).append(time.value).append(" "));
        if (descriptor.getSteps().isPresent()) {
            List<Step> steps = descriptor.getSteps().get();
            if (steps.isEmpty()) {
                sb.append(PREFIX_STEP);
            } else {
                steps.forEach(step -> sb.append(PREFIX_STEP).append(step.value).append(" "));
            }
        }
        if (descriptor.getGoals().isPresent()) {
            Set<Goal> goals = descriptor.getGoals().get();
            if (goals.isEmpty()) {
                sb.append(PREFIX_GOAL);
            } else {
                goals.forEach(s -> sb.append(PREFIX_GOAL).append(s.goalName).append(" "));
            }
        }
        return sb.toString();
    }
}

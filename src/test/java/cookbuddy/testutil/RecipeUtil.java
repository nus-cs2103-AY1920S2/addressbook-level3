package cookbuddy.testutil;

import static cookbuddy.logic.parser.CliSyntax.PREFIX_CALORIE;
import static cookbuddy.logic.parser.CliSyntax.PREFIX_INGREDIENTS;
import static cookbuddy.logic.parser.CliSyntax.PREFIX_INSTRUCTIONS;
import static cookbuddy.logic.parser.CliSyntax.PREFIX_NAME;
import static cookbuddy.logic.parser.CliSyntax.PREFIX_SERVING;
import static cookbuddy.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import cookbuddy.logic.commands.ModifyCommand.EditRecipeDescriptor;
import cookbuddy.logic.commands.NewCommand;
import cookbuddy.model.recipe.Recipe;
import cookbuddy.model.recipe.attribute.Tag;

/**
 * A utility class for Recipe.
 */
public class RecipeUtil {

    /**
     * Returns an add command string for adding the {@code recipe}.
     */
    public static String getAddCommand(Recipe recipe) {
        return NewCommand.COMMAND_WORD + " " + getRecipeDetails(recipe);
    }

    /**
     * Returns the part of command string for the given {@code recipe}'s details.
     */
    public static String getRecipeDetails(Recipe recipe) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + recipe.getName().name + " ");
        sb.append(PREFIX_INGREDIENTS + recipe.getIngredients().toString() + " ");
        sb.append(PREFIX_INSTRUCTIONS + recipe.getInstructions().toString() + " ");
        sb.append(PREFIX_CALORIE + recipe.getCalorie().toString() + " ");
        sb.append(PREFIX_SERVING + recipe.getServing().toString() + " ");
        recipe.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditRecipeDescriptor}'s details.
     */
    public static String getEditRecipeDescriptorDetails(EditRecipeDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.name).append(" "));
        descriptor.getIngredients().ifPresent(ingredients -> sb.append(PREFIX_INGREDIENTS)
                .append(ingredients.toString()).append(" "));
        descriptor.getInstructions().ifPresent(instructions -> sb.append(PREFIX_INSTRUCTIONS)
                .append(instructions.toString()).append(" "));
        descriptor.getCalorie().ifPresent(calorie -> sb.append(PREFIX_CALORIE).append(calorie.toString()).append(" "));
        descriptor.getServing().ifPresent(serving -> sb.append(PREFIX_SERVING).append(serving.toString()).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}

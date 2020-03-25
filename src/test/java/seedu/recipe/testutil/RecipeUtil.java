package seedu.recipe.testutil;

import static seedu.recipe.logic.parser.CliSyntax.PREFIX_GOAL;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT_FRUIT;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT_GRAIN;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT_OTHER;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT_PROTEIN;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INGREDIENT_VEGE;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_STEP;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.List;
import java.util.Set;

import seedu.recipe.logic.commands.AddCommand;
import seedu.recipe.logic.commands.EditCommand.EditRecipeDescriptor;
import seedu.recipe.model.goal.Goal;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.Step;
import seedu.recipe.model.recipe.ingredient.Fruit;
import seedu.recipe.model.recipe.ingredient.Grain;
import seedu.recipe.model.recipe.ingredient.Other;
import seedu.recipe.model.recipe.ingredient.Protein;
import seedu.recipe.model.recipe.ingredient.Vegetable;

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

        recipe.getGrains().forEach(grain -> sb.append(PREFIX_INGREDIENT_GRAIN)
                .append(grain.getQuantity() + "," + grain.getIngredientName()).append(" "));
        recipe.getVegetables().forEach(vege -> sb.append(PREFIX_INGREDIENT_VEGE)
                .append(vege.getQuantity() + "," + vege.getIngredientName()).append(" "));
        recipe.getProteins().forEach(protein -> sb.append(PREFIX_INGREDIENT_PROTEIN)
                .append(protein.getQuantity() + "," + protein.getIngredientName()).append(" "));
        recipe.getOthers().forEach(other -> sb.append(PREFIX_INGREDIENT_OTHER)
                .append(other.getQuantity() + "," + other.getIngredientName()).append(" "));

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
        if (descriptor.getGrains().isPresent()) {
            Set<Grain> grains = descriptor.getGrains().get();
            if (grains.isEmpty()) {
                sb.append(PREFIX_INGREDIENT_GRAIN).append(" ");
            } else {
                grains.forEach(grain -> sb.append(PREFIX_INGREDIENT_GRAIN).append(grain.getQuantity() + ","
                        + grain.getIngredientName()).append(" "));
            }
        }
        if (descriptor.getVegetables().isPresent()) {
            Set<Vegetable> vegetables = descriptor.getVegetables().get();
            if (vegetables.isEmpty()) {
                sb.append(PREFIX_INGREDIENT_VEGE).append(" ");
            } else {
                vegetables.forEach(vegetable -> sb.append(PREFIX_INGREDIENT_VEGE).append(vegetable.getQuantity() + ","
                        + vegetable.getIngredientName()).append(" "));
            }
        }

        if (descriptor.getProteins().isPresent()) {
            Set<Protein> proteins = descriptor.getProteins().get();
            if (proteins.isEmpty()) {
                sb.append(PREFIX_INGREDIENT_PROTEIN).append(" ");
            } else {
                proteins.forEach(protein -> sb.append(PREFIX_INGREDIENT_PROTEIN).append(protein.getQuantity() + ","
                        + protein.getIngredientName()).append(" "));
            }
        }
        if (descriptor.getFruits().isPresent()) {
            Set<Fruit> fruits = descriptor.getFruits().get();
            if (fruits.isEmpty()) {
                sb.append(PREFIX_INGREDIENT_FRUIT).append(" ");
            } else {
                fruits.forEach(fruit -> sb.append(PREFIX_INGREDIENT_FRUIT).append(fruit.getQuantity() + ","
                    + fruit.getIngredientName()).append(" "));
            }
        }
        if (descriptor.getOthers().isPresent()) {
            Set<Other> others = descriptor.getOthers().get();
            if (others.isEmpty()) {
                sb.append(PREFIX_INGREDIENT_OTHER).append(" ");
            } else {
                others.forEach(other -> sb.append(PREFIX_INGREDIENT_OTHER).append(other.getQuantity() + ","
                        + other.getIngredientName()).append(" "));
            }
        }
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

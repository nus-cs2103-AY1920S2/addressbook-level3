package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.ReadOnlyRecipeBook;
import seedu.address.model.RecipeBook;
import seedu.address.model.goal.Goal;
import seedu.address.model.ingredient.Grain;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.Other;
import seedu.address.model.ingredient.Protein;
import seedu.address.model.ingredient.Vegetable;
import seedu.address.model.recipe.Email;
import seedu.address.model.recipe.Name;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.recipe.Time;

/**
 * Contains utility methods for populating {@code RecipeBook} with sample data.
 */
public class SampleDataUtil {
    public static Recipe[] getSampleRecipes() {
        return new Recipe[] {
            new Recipe(new Name("Caesar Salad"), new Time("30"),
                    getIngredientSet("Tomato, 100, vegetable", "Honeydew, 100, other"),
                    new Email("alexyeoh@example.com"), getGoalSet("Herbivore")),
            new Recipe(new Name("Grilled Sandwich"), new Time("10"),
                    getIngredientSet("Bread, 50, grain", "Cheese, 50, other"),
                    new Email("berniceyu@example.com"), getGoalSet("Wholesome Wholemeal")),
            new Recipe(new Name("Boiled Chicken"), new Time("10"),
                    getIngredientSet("Chicken, 100, protein"),
                    new Email("charlotte@example.com"), getGoalSet("Bulk like the Hulk")),
            new Recipe(new Name("Chocolate Cake"), new Time("120"),
                    getIngredientSet("Sugar, 100, other"),
                    new Email("lidavid@example.com"), getGoalSet()),
            new Recipe(new Name("Omelet"), new Time("15"),
                    getIngredientSet("Egg, 100, protein"),
                    new Email("irfan@example.com"), getGoalSet()),
            new Recipe(new Name("Tuna Bread"), new Time("60"),
                    getIngredientSet("Wholemeal bread, 100, grain", "Tuna, 100, protein"),
                    new Email("royb@example.com"), getGoalSet("Bulk like the Hulk", "Wholesome Wholemeal"))
        };
    }

    public static ReadOnlyRecipeBook getSampleRecipeBook() {
        RecipeBook sampleAb = new RecipeBook();
        for (Recipe sampleRecipe : getSampleRecipes()) {
            sampleAb.addRecipe(sampleRecipe);
        }
        return sampleAb;
    }

    /**
     * Returns a goal set containing the list of strings given.
     */
    public static Set<Goal> getGoalSet(String... strings) {
        return Arrays.stream(strings)
                .map(Goal::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a goal set containing the list of strings given in the format (name, quantity, type).
     */
    public static Set<Ingredient> getIngredientSet(String... ingredients) {
        Set<Ingredient> ingredientSet = new HashSet<Ingredient>();
        for (String ingredient: ingredients) {
            String[] splitDetails = ingredient.split(",");
            String name = splitDetails[0].trim();
            double quantity = Double.parseDouble(splitDetails[1].trim());
            String type = splitDetails[2].trim();
            switch (type) {
                case "vegetable":
                    ingredientSet.add(new Vegetable(name, quantity));
                    break;
                case "grain":
                    ingredientSet.add(new Grain(name, quantity));
                    break;
                case "protein":
                    ingredientSet.add(new Protein(name, quantity));
                    break;
                case "other":
                    ingredientSet.add(new Other(name, quantity));
                    break;
                default:
                    System.out.println("Error: ingredient type does not belong to oen of the defaults");
            }
        }
        return ingredientSet;
    }

}

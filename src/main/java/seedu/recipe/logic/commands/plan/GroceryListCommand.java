package seedu.recipe.logic.commands.plan;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import seedu.recipe.logic.commands.Command;
import seedu.recipe.logic.commands.CommandResult;
import seedu.recipe.logic.commands.exceptions.CommandException;
import seedu.recipe.model.Model;
import seedu.recipe.model.plan.Plan;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.ingredient.Fruit;
import seedu.recipe.model.recipe.ingredient.Grain;
import seedu.recipe.model.recipe.ingredient.Ingredient;
import seedu.recipe.model.recipe.ingredient.MainIngredientType;
import seedu.recipe.model.recipe.ingredient.Other;
import seedu.recipe.model.recipe.ingredient.Protein;
import seedu.recipe.model.recipe.ingredient.Quantity;
import seedu.recipe.model.recipe.ingredient.Vegetable;
import seedu.recipe.model.util.QuantityUtil;
import seedu.recipe.ui.tab.Tab;

/**
 * Collates and lists all the ingredients used in the planned recipes.
 */
public class GroceryListCommand extends Command {

    public static final String COMMAND_WORD = "grocerylist";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all ingredients used in the planned recipes.";
    public static final String MESSAGE_EMPTY_LIST = "There are no planned recipes. Why not plan one today?";
    public static final String MESSAGE_SUCCESS = "All ingredients required have been listed down.";

    private final Tab planTab = Tab.PLANNING;
    private Map<String, Quantity> grainMap = new TreeMap<>();
    private Map<String, Quantity> vegetableMap = new TreeMap<>();
    private Map<String, Quantity> proteinMap = new TreeMap<>();
    private Map<String, Quantity> fruitMap = new TreeMap<>();
    private Map<String, Quantity> otherMap = new TreeMap<>();

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Set<Map.Entry<Recipe, List<Plan>>> allPlans = model.getPlannedMap().getAllEntries();

        if (allPlans.isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_LIST);
        }

        for (Map.Entry<Recipe, List<Plan>> entry : allPlans) {
            Recipe recipe = entry.getKey();
            int numberOfTimes = entry.getValue().size();
            addAllIngredients(recipe, numberOfTimes);
        }
        model.setGroceryList(formatMapsToString());

        return new CommandResult(MESSAGE_SUCCESS, true, false, planTab, false);
    }

    /**
     * Adds ingredients from {@code toAdd} into {@code map}.
     * Adds up the quantity of similar ingredients except for 'Other' ingredients.
     * All duplicate 'Other' Ingredients will be listed instead since the category is too diverse.
     */
    private Map<String, Quantity> collateIngredients(Map<String, Quantity> map, Set<? extends Ingredient> toAdd,
             int multiplier) {
        Iterator<? extends Ingredient> ingredientsIterator = toAdd.iterator();
        while (ingredientsIterator.hasNext()) {
            Ingredient ingredient = ingredientsIterator.next();
            Quantity scaledQuantity = QuantityUtil.scaleQuantity(ingredient.getQuantity(), multiplier);
            String ingredientKey = ingredient.getIngredientName();

            if (map.containsKey(ingredientKey)) { // adds the quantity of duplicate ingredients
                MainIngredientType type = convertIngredient(ingredient);

                if (type != null) {
                    Quantity currentQuantityInMap = map.get(ingredientKey);
                    Quantity totalQuantity = scaledQuantity.addQuantityInGram(currentQuantityInMap, type);
                    map.put(ingredientKey, totalQuantity);

                } else {
                    // all 'Other' ingredient quantities will be listed since conversion of this
                    // diverse group will not be accurate.
                    String repeatName = appendWhitespace(map, ingredientKey);
                    map.put(repeatName, scaledQuantity);
                }

            } else {
                map.put(ingredient.getIngredientName(), scaledQuantity);
            }
        }
        return map;
    }

    /**
     * Returns the same {@code name} with whitespace appended to it.
     * To use in listing 'Other' ingredients.
     */
    private static String appendWhitespace(Map<String, Quantity> map, String name) {
        while (map.containsKey(name)) {
            name = name + " ";
        }
        return name;
    }

    /**
     * Returns the MainIngredientType of {@code ingredient}.
     * Returns null for 'Other' ingredient.
     */
    private static MainIngredientType convertIngredient(Ingredient ingredient) {
        if (ingredient instanceof Grain) {
            return MainIngredientType.GRAIN;

        } else if (ingredient instanceof Vegetable) {
            return MainIngredientType.VEGETABLE;

        } else if (ingredient instanceof Protein) {
            return MainIngredientType.PROTEIN;

        } else if (ingredient instanceof Fruit) {
            return MainIngredientType.FRUIT;

        } else if (ingredient instanceof Other) {
            return null; // Other does not belong to a MainIngredientType.

        } else {
            throw new IllegalStateException("Unexpected value: " + ingredient);
        }
    }

    /**
     * Adds all ingredients from all categories.
     */
    private void addAllIngredients(Recipe recipe, int multiplier) {
        collateIngredients(grainMap, recipe.getGrains(), multiplier);
        collateIngredients(vegetableMap, recipe.getVegetables(), multiplier);
        collateIngredients(proteinMap, recipe.getProteins(), multiplier);
        collateIngredients(fruitMap, recipe.getFruits(), multiplier);
        collateIngredients(otherMap, recipe.getOthers(), multiplier);
    }

    /**
     * Formats the string of all maps to be displayed in the window.
     */
    private String formatMapsToString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Grains: \n").append(formatMapToString(grainMap));
        sb.append("\nVegetables: \n").append(formatMapToString(vegetableMap));
        sb.append("\nProteins: \n").append(formatMapToString(proteinMap));
        sb.append("\nFruits: \n").append(formatMapToString(fruitMap));
        sb.append("\nOthers: \n").append(formatMapToString(otherMap));
        return sb.toString();
    }

    /**
     * Formats the string of one map to be displayed in the window.
     */
    private String formatMapToString(Map<String, Quantity> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Quantity> entry : map.entrySet()) {
            sb.append(entry.getValue())
                .append(" ")
                .append(entry.getKey())
                .append("\n");
        }
        return sb.toString();
    }
}

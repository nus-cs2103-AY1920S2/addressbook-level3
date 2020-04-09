package seedu.recipe.logic.commands.plan;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.model.Model.PREDICATE_SHOW_ALL_PLANNED_RECIPES;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import seedu.recipe.logic.commands.Command;
import seedu.recipe.logic.commands.CommandResult;
import seedu.recipe.logic.commands.exceptions.CommandException;
import seedu.recipe.model.Model;
import seedu.recipe.model.plan.PlannedDate;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.ingredient.SortByIngredientName;
import seedu.recipe.model.recipe.ingredient.Fruit;
import seedu.recipe.model.recipe.ingredient.Grain;
import seedu.recipe.model.recipe.ingredient.Ingredient;
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
    private SortByIngredientName sortByIngredientName = new SortByIngredientName();
    // is it auto sorted by alphabetical order? todo
    private Map<String, Quantity> grainMap = new TreeMap<>();
    private Map<String, Quantity> vegetableMap = new TreeMap<>();
    private Map<String, Quantity> proteinMap = new TreeMap<>();
    private Map<String, Quantity> fruitMap = new TreeMap<>();
    private Map<String, Quantity> otherMap = new TreeMap<>();
    private static final String WINDOW_TITLE = "Grocery list:";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Map<Recipe, List<PlannedDate>> allPlans = model.getPlannedMap().getInternalMap();

        if (allPlans.isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_LIST);
        }

        for (Map.Entry<Recipe, List<PlannedDate>> entry : allPlans.entrySet()) {
            Recipe recipe = entry.getKey();
            int numberOfTimes = entry.getValue().size();
            addAllIngredients(recipe, numberOfTimes);
        }
        model.setGroceryList(formatMapsToString());

        return new CommandResult(MESSAGE_SUCCESS, true, false, planTab, false);
    }

    /**
     * Adds ingredients from {@code toAdd} into {@code map}.
     * Adds up the quantity of similar ingredients.
     */
    private Map<String, Quantity> collateIngredients(Map<String, Quantity> map, Set<? extends Ingredient> toAdd,
             int multiplier) {
        Iterator<? extends Ingredient> ingredientsIterator = toAdd.iterator();
        while (ingredientsIterator.hasNext()) {
            Ingredient ingredient = ingredientsIterator.next();
            Quantity scaledQuantity = QuantityUtil.scaleQuantity(ingredient.getQuantity(), multiplier);
            map.put(ingredient.getIngredientName(), scaledQuantity);
            // todo check for duplicate and add them up
        }
        System.out.println("MAP: " + map); //todo remove ltr
        return map;
    }

    private void addAllIngredients(Recipe recipe, int multiplier) {
        collateIngredients(grainMap, recipe.getGrains(), multiplier);
        collateIngredients(vegetableMap, recipe.getVegetables(), multiplier);
        collateIngredients(proteinMap, recipe.getProteins(), multiplier);
        collateIngredients(fruitMap, recipe.getFruits(), multiplier);
        collateIngredients(otherMap, recipe.getOthers(), multiplier);
    }

    private String formatMapsToString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nGrains: \n").append(formatMapToString(grainMap));
        sb.append("\nVegetables: \n").append(formatMapToString(vegetableMap));
        sb.append("\nProteins: \n").append(formatMapToString(proteinMap));
        sb.append("\nFruits: \n").append(formatMapToString(fruitMap));
        sb.append("\nOthers: \n").append(formatMapToString(otherMap));
        return sb.toString();
    }

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

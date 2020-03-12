package seedu.address.model.recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Recipe's ingredients in the recipe book.
 */
public class IngredientList {

    public List<Ingredient> ingredients = new ArrayList<>();

    /**
     * Constructs an {@code IngredientList}.
     */
    public IngredientList() {
    }

    /**
     * Adds an ingredient to the ingredients list.
     *
     * @param ingredient, the ingredient to be added.
     */
    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    /**
     * Deletes an ingredient from the ingredients list.
     *
     * @param ingredient, the ingredient to be deleted.
     */
    public void deleteIngredient(Ingredient ingredient) {
        ingredients.remove(ingredient);
    }

    /**
     * Prints out the ingredients list.
     */
    public void print() {
        for (int i = 0; i < ingredients.size(); i++) {
            System.out.print(ingredients.get(i));
        }
    }
}

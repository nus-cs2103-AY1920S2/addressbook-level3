package seedu.address.model.recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Recipe's ingredients in the recipe book.
 */
public class IngredientList {

    private List<Ingredient> ingredients = new ArrayList<>();

    /**
     * Constructs for IngredientList.
     */
    public IngredientList() {
    }

    /**
     * Adds an ingredient to the ingredients list.
     *
     * @param ingredient the ingredient to be added.
     */
    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    /**
     * Deletes an ingredient from the ingredients list.
     *
     * @param ingredient the ingredient to be deleted.
     */
    public void deleteIngredient(Ingredient ingredient) {
        ingredients.remove(ingredient);
    }

    //must have accessors to avoid checkstyle failure
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
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

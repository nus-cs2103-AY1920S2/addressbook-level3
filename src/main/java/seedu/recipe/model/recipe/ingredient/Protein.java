package seedu.recipe.model.recipe.ingredient;

/**
 * Represents a Protein Ingredient in the recipe book.
 */
public class Protein extends Ingredient {

    public Protein(String name, Quantity quantity) {
        super(name, quantity);
    }

    /**
     * Overloaded Protein constructor for the purpose of filtering by protein name.
     */
    public Protein(String name) {
        super(name);
    }

}

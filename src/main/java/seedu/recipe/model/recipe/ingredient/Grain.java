
package seedu.recipe.model.recipe.ingredient;

/**
 * Represents a Grain Ingredient in the recipe book.
 */
public class Grain extends Ingredient {

    private boolean isWholemeal;

    public Grain(String name, Quantity quantity) {
        super(name, quantity);
        isWholemeal = false;
    }

    /**
     * Overloaded Grain constructor for the purpose of filtering by grain name.
     */
    public Grain(String name) {
        super(name);
    }

    public boolean isWholemeal() {
        return isWholemeal;
    }

    public void setWholemeal(boolean wholemeal) {
        isWholemeal = wholemeal;
    }
}

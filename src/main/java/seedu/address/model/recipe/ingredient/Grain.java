
package seedu.address.model.recipe.ingredient;

/**
 * Represents a Grain Ingredient in the recipe book.
 */
public class Grain extends Ingredient {

    private boolean isWholemeal;

    public Grain(String name, Quantity quantity) {
        super(name, quantity);
        isWholemeal = false;
    }

    public boolean isWholemeal() {
        return isWholemeal;
    }

    public void setWholemeal(boolean wholemeal) {
        isWholemeal = wholemeal;
    }
}

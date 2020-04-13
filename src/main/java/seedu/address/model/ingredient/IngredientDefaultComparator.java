package seedu.address.model.ingredient;

import java.util.Comparator;

/**
 * The default comparator for {@code Ingredient}, which compares ingredients by their names using lexicographical order.
 */
public class IngredientDefaultComparator implements Comparator<Ingredient> {

    /**
     * Compares ingredients by their names using lexicographical order.
     */
    @Override
    public int compare(Ingredient o1, Ingredient o2) {
        return o1.getName().toString().compareToIgnoreCase(o2.getName().toString());
    }
}

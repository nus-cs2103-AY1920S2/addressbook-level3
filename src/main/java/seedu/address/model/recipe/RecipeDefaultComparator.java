package seedu.address.model.recipe;

import java.util.Comparator;

/**
 * The default comparator for {@code Recipe}, which compares recipes by their names using lexicographical order.
 */
public class RecipeDefaultComparator implements Comparator<Recipe> {

    /**
     * Compares recipes by their names using lexicographical order.
     */
    @Override
    public int compare(Recipe o1, Recipe o2) {
        return o1.getName().toString().compareToIgnoreCase(o2.getName().toString());
    }
}

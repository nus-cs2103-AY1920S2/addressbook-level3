package cookbuddy.model.recipe;

import java.util.function.Predicate;

/**
 * Predicate to test for.
 */
public interface ContainsKeywordsPredicate extends Predicate<Recipe> {
    boolean test(Recipe recipe);
}

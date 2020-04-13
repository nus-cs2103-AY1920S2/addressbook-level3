package seedu.address.model.recipe;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Recipe}'s {@code Tag} matches any of the keywords given.
 */
public class RecipeContainsTagsPredicate implements Predicate<Recipe> {
    private final List<String> tags;

    public RecipeContainsTagsPredicate(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public boolean test(Recipe recipe) {
        return tags.stream()
            .anyMatch(tag -> StringUtil.containsWordIgnoreCase(recipe.getTagsString(), tag));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof RecipeContainsTagsPredicate // instanceof handles nulls
            && tags.equals(((RecipeContainsTagsPredicate) other).tags)); // state check
    }
}

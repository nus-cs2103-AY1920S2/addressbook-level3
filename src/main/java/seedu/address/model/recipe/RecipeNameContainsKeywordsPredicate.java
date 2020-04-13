package seedu.address.model.recipe;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Recipe}'s {@code RecipeName} matches any of the keywords given.
 */
public class RecipeNameContainsKeywordsPredicate implements Predicate<Recipe> {
    private final List<String> keywords;

    public RecipeNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Recipe recipe) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(recipe.getName().fullRecipeName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecipeNameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((RecipeNameContainsKeywordsPredicate) other).keywords)); // state check
    }
}

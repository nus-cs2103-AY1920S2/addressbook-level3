package seedu.recipe.model.recipe;

import java.util.Arrays;
import java.util.function.Predicate;

import seedu.recipe.commons.util.StringUtil;

/**
 * Tests that a {@code Recipe}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Recipe> {
    private final String keywords;
    private final boolean isStrict;

    public NameContainsKeywordsPredicate(boolean isStrict, String keywords) {
        this.isStrict = isStrict;
        this.keywords = keywords;
    }

    @Override
    public boolean test(Recipe recipe) {
        if (isStrict) {
            String[] nameKeywords = keywords.split("\\s+");
            return Arrays.stream(nameKeywords)
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(recipe.getName().fullName, keyword));
        } else {
            return recipe.getName().fullName.toLowerCase().contains(keywords);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && isStrict == ((NameContainsKeywordsPredicate) other).isStrict
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}

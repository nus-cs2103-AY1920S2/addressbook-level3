package cookbuddy.model.recipe;

import java.util.List;
import java.util.stream.Collectors;

import cookbuddy.commons.util.StringUtil;

/**
 * Tests that a {@code Recipe}'s {@code IngredientList} matches any of the keywords given.
 */
public class IngredientContainsKeywordsPredicate implements ContainsKeywordsPredicate {
    private final List<String> keywords;

    public IngredientContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Recipe recipe) {
        List<String> ingredients = recipe.getIngredients().ingredientData.stream()
                .map(x -> x.name)
                .collect(Collectors.toList());

        return keywords.stream().anyMatch(keyword -> ingredients.stream()
                .anyMatch(ingredient -> StringUtil.containsWordIgnoreCase(ingredient, keyword)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
               || (other instanceof IngredientContainsKeywordsPredicate // instanceof handles nulls
                   && keywords.equals(((IngredientContainsKeywordsPredicate) other).keywords)); // state check
    }

}

package cookbuddy.model.recipe;

import java.util.ArrayList;
import java.util.List;

import cookbuddy.commons.util.StringUtil;
import cookbuddy.model.recipe.attribute.Ingredient;

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
        List<Ingredient> ingredients = recipe.getIngredients().ingredientData;

        List<String> ingredientNames = new ArrayList<String>();
        for (Ingredient i : ingredients) {
            ingredientNames.add(i.name);
        }

        return keywords.stream().anyMatch(keyword -> ingredientNames.stream()
                .anyMatch(ingredient -> StringUtil.containsWordIgnoreCase(ingredient, keyword)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IngredientContainsKeywordsPredicate // instanceof handles nulls
                    && keywords.equals(((IngredientContainsKeywordsPredicate) other).keywords)); // state check
    }

}

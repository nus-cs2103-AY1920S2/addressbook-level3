package cookbuddy.model.recipe;

import java.util.List;
import java.util.stream.Collectors;

import cookbuddy.commons.util.StringUtil;

/**
 * Tests that a {@code Recipe}'s {@code IngredientList} matches any of the keywords given.
 */
public class InstructionContainsKeywordsPredicate implements ContainsKeywordsPredicate {
    private final List<String> keywords;

    public InstructionContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Recipe recipe) {
        List<String> instructions = recipe.getInstructions().instructionData.stream()
                .map(x -> x.instructionString)
                .collect(Collectors.toList());

        return keywords.stream().anyMatch(keyword -> instructions.stream()
                .anyMatch(instruction -> StringUtil.containsWordIgnoreCase(instruction, keyword)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InstructionContainsKeywordsPredicate // instanceof handles nulls
                    && keywords.equals(((InstructionContainsKeywordsPredicate) other).keywords)); // state check
    }

}

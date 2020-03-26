package cookbuddy.model.recipe;

import java.util.ArrayList;
import java.util.List;

import cookbuddy.commons.util.StringUtil;
import cookbuddy.model.recipe.attribute.Instruction;

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
        List<Instruction> instructions = recipe.getInstructions().instructionData;

        List<String> instructionValues = new ArrayList<String>();
        for (Instruction i : instructions) {
            instructionValues.add(i.instructionString);
        }

        return keywords.stream().anyMatch(keyword -> instructionValues.stream()
                .anyMatch(ingredient -> StringUtil.containsWordIgnoreCase(ingredient, keyword)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InstructionContainsKeywordsPredicate // instanceof handles nulls
                    && keywords.equals(((InstructionContainsKeywordsPredicate) other).keywords)); // state check
    }

}

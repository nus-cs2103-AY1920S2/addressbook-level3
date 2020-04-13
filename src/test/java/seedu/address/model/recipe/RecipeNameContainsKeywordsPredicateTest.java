package seedu.address.model.recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalRecipes.AGLIO_OLIO;
import static seedu.address.testutil.TypicalRecipes.CARBONARA;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class RecipeNameContainsKeywordsPredicateTest {
    @Test
    public void constructor_validInput() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Carbonara");
        RecipeNameContainsKeywordsPredicate c = new RecipeNameContainsKeywordsPredicate(list);
        assertEquals(c, new RecipeNameContainsKeywordsPredicate(list));
    }

    @Test
    public void test_validInput() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Carbonara");
        RecipeNameContainsKeywordsPredicate c = new RecipeNameContainsKeywordsPredicate(list);
        assertTrue(c.test(CARBONARA));

        RecipeNameContainsKeywordsPredicate d = new RecipeNameContainsKeywordsPredicate(list);
        assertFalse(c.test(AGLIO_OLIO));
    }

    @Test
    public void equalsMethod() {
        ArrayList<String> list = new ArrayList<>();
        assertEquals(new RecipeNameContainsKeywordsPredicate(list),
            new RecipeNameContainsKeywordsPredicate(list));

        list.add("Carbonara");
        assertEquals(new RecipeNameContainsKeywordsPredicate(list),
            new RecipeNameContainsKeywordsPredicate(list));

        ArrayList<String> otherList = new ArrayList<>();
        assertNotEquals(new RecipeNameContainsKeywordsPredicate(list),
            new RecipeNameContainsKeywordsPredicate(otherList));
    }
}

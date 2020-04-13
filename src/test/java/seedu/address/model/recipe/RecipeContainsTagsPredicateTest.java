package seedu.address.model.recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalRecipes.getValidRecipe;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class RecipeContainsTagsPredicateTest {
    private static final List<String> VALID_TAGS = Arrays.asList("tag");
    private static final Recipe VALID_RECIPE = getValidRecipe();

    @Test
    public void constructor_validInput() {
        RecipeContainsTagsPredicate c =
            new RecipeContainsTagsPredicate(VALID_TAGS);
        assertEquals(c, new RecipeContainsTagsPredicate(VALID_TAGS));
    }

    @Test
    public void test_validInput() {
        Model model = new ModelManager();
        model.addCookbookRecipe(VALID_RECIPE);
        RecipeContainsTagsPredicate c =
            new RecipeContainsTagsPredicate(VALID_TAGS);
        assertTrue(c.test(VALID_RECIPE));
    }

    @Test
    public void equalsMethod() {
        Model model = new ModelManager();
        model.addCookbookRecipe(VALID_RECIPE);
        RecipeContainsTagsPredicate c =
            new RecipeContainsTagsPredicate(VALID_TAGS);
        assertEquals(c, new RecipeContainsTagsPredicate(VALID_TAGS));
        assertNotEquals(c, null);
    }
}

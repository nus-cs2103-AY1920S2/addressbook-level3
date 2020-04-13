package seedu.address.logic.commands.cookbook;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_RECIPES_LISTED_OVERVIEW;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.recipe.RecipeDescription;
import seedu.address.model.recipe.RecipeName;
import seedu.address.model.recipe.RecipeNameContainsKeywordsPredicate;

public class CookbookSearchByKeywordCommandTest {
    private static final List<String> VALID_KEYWORDS_ONE = Arrays.asList("key", "words");
    private static final List<String> VALID_KEYWORDS_TWO = Collections.singletonList("keywords");
    private static final Recipe VALID_RECIPE = new Recipe(new RecipeName("key words"),
        new RecipeDescription("Description"));
    private static final RecipeNameContainsKeywordsPredicate VALID_PREDICATE_ONE =
        new RecipeNameContainsKeywordsPredicate(VALID_KEYWORDS_ONE);
    private static final RecipeNameContainsKeywordsPredicate VALID_PREDICATE_TWO =
        new RecipeNameContainsKeywordsPredicate(VALID_KEYWORDS_TWO);

    @Test
    public void execute_validInput() {
        CookbookSearchByKeywordCommand c = new CookbookSearchByKeywordCommand(VALID_PREDICATE_ONE);
        Model model = new ModelManager();
        model.addCookbookRecipe(VALID_RECIPE);
        assertEquals(c.execute(model), new CommandResult(String.format(MESSAGE_RECIPES_LISTED_OVERVIEW,
                model.getFilteredCookbookRecipeList().size())));
        assertTrue(!model.getFilteredCookbookRecipeList().isEmpty());

        CookbookSearchByKeywordCommand d = new CookbookSearchByKeywordCommand(VALID_PREDICATE_TWO);
        assertEquals(d.execute(model), new CommandResult(String.format(MESSAGE_RECIPES_LISTED_OVERVIEW,
                model.getFilteredCookbookRecipeList().size())));
        assertTrue(model.getFilteredCookbookRecipeList().isEmpty());
    }

    @Test
    public void execute_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            new CookbookSearchByKeywordCommand(VALID_PREDICATE_ONE).execute(null));
    }

    @Test
    public void equalsMethod() {
        CookbookSearchByKeywordCommand c = new CookbookSearchByKeywordCommand(VALID_PREDICATE_ONE);
        assertEquals(c, c);
        assertNotEquals(c, new CookbookSearchByKeywordCommand(VALID_PREDICATE_TWO));
        assertNotEquals(c, null);
    }
}

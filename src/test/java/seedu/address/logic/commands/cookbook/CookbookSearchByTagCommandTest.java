package seedu.address.logic.commands.cookbook;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.commons.core.Messages.MESSAGE_RECIPES_LISTED_OVERVIEW;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.recipe.RecipeContainsTagsPredicate;
import seedu.address.model.recipe.RecipeDescription;
import seedu.address.model.recipe.RecipeName;

public class CookbookSearchByTagCommandTest {
    private static final List<String> VALID_TAGS_ONE = Arrays.asList("easy", "difficult");
    private static final List<String> VALID_TAGS_TWO = Collections.singletonList("easydifficult");
    private static final Recipe VALID_RECIPE = new Recipe(new RecipeName("key words"),
            new RecipeDescription("Description"));
    private static final RecipeContainsTagsPredicate VALID_PREDICATE_ONE =
            new RecipeContainsTagsPredicate(VALID_TAGS_ONE);
    private static final RecipeContainsTagsPredicate VALID_PREDICATE_TWO =
            new RecipeContainsTagsPredicate(VALID_TAGS_TWO);
    @Test
    public void execute_validInput() {
        CookbookSearchByTagCommand c = new CookbookSearchByTagCommand(VALID_PREDICATE_ONE);
        Model model = new ModelManager();
        model.addCookbookRecipe(VALID_RECIPE);
        assertEquals(c.execute(model), new CommandResult(String.format(MESSAGE_RECIPES_LISTED_OVERVIEW,
                model.getFilteredCookbookRecipeList().size())));
    }

    @Test
    public void execute_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new CookbookSearchByTagCommand(VALID_PREDICATE_ONE).execute(null));
    }
}

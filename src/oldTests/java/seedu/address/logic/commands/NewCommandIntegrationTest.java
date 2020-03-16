package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.recipe.Recipe;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code NewCommand}.
 */
public class NewCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Recipe validRecipe = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getRecipeBook(), new UserPrefs());
        expectedModel.addRecipe(validRecipe);

        assertCommandSuccess(new NewCommand(validRecipe), model,
                String.format(NewCommand.MESSAGE_SUCCESS, validRecipe), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Recipe recipeInList = model.getRecipeBook().getRecipeList().get(0);
        assertCommandFailure(new NewCommand(recipeInList), model, NewCommand.MESSAGE_DUPLICATE_PERSON);
    }

}

package seedu.address.logic.commands.product;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PRODUCTS_LISTED_OVERVIEW;
import static seedu.address.testutil.product.TypicalProducts.getTypicalInventorySystem;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.product.DescriptionContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindProductCommand}.
 */
public class FindProductCommandTest {
    private Model model = new ModelManager(getTypicalInventorySystem(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalInventorySystem(), new UserPrefs());

    @Test
    public void equals() {
        DescriptionContainsKeywordsPredicate firstPredicate =
                new DescriptionContainsKeywordsPredicate(Collections.singletonList("first"));
        DescriptionContainsKeywordsPredicate secondPredicate =
                new DescriptionContainsKeywordsPredicate(Collections.singletonList("second"));

        FindProductCommand findFirstCommand = new FindProductCommand(firstPredicate);
        FindProductCommand findSecondCommand = new FindProductCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindProductCommand findFirstCommandCopy = new FindProductCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different product -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noProductFound() {
        String expectedMessage = "Please enter at least one keyword!";
        DescriptionContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindProductCommand command = new FindProductCommand(predicate);
        expectedModel.updateFilteredProductList(predicate);
        // assertCommandSuccess(command, model, expectedMessage, expectedModel);
        // assertEquals(Collections.emptyList(), model.getFilteredProductList());
    }

    @Test
    public void execute_multipleKeywords_multipleProductsFound() {
        String expectedMessage = String.format(MESSAGE_PRODUCTS_LISTED_OVERVIEW, 3);
        DescriptionContainsKeywordsPredicate predicate = preparePredicate("bag book");
        FindProductCommand command = new FindProductCommand(predicate);
        expectedModel.updateFilteredProductList(predicate);
        // assertCommandSuccess(command, model, expectedMessage, expectedModel);
        // assertEquals(Arrays.asList(BAG, BOOK), model.getFilteredProductList());
    }

    /**
     * Parses {@code userInput} into a {@code DescriptionContainsKeywordsPredicate}.
     */
    private DescriptionContainsKeywordsPredicate preparePredicate(String userInput) {
        return new DescriptionContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}

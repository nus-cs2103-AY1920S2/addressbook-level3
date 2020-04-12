package seedu.eylah.expensesplitter.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eylah.expensesplitter.logic.parser.SplitterCliSyntax.PREFIX_ITEM;
import static seedu.eylah.expensesplitter.logic.parser.SplitterCliSyntax.PREFIX_NAME;
import static seedu.eylah.expensesplitter.logic.parser.SplitterCliSyntax.PREFIX_PRICE;
import static seedu.eylah.expensesplitter.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.eylah.commons.core.index.Index;
import seedu.eylah.commons.logic.command.Command;
import seedu.eylah.commons.logic.command.CommandResult;
import seedu.eylah.commons.logic.command.exception.CommandException;
import seedu.eylah.expensesplitter.model.PersonAmountBook;
import seedu.eylah.expensesplitter.model.SplitterModel;
import seedu.eylah.expensesplitter.model.person.NameContainsKeywordsPredicate;
import seedu.eylah.expensesplitter.model.person.Person;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_NAME_ANNA = "anna";
    public static final String VALID_NAME_BRANDON = "brandon";
    public static final String VALID_NAME_CHARLIE = "charlie";
    public static final String VALID_ITEMNAME_CHICKENRICE = "Chicken Rice";
    public static final String VALID_ITEMNAME_PIZZA = "pizza";
    public static final String VALID_ITEMPRICE_CHICKENRICE = "3.50";
    public static final String VALID_ITEMPRICE_PIZZA = "25";

    public static final String ITEMNAME_DESC_CHICKENRICE = " " + PREFIX_ITEM + " " + VALID_ITEMNAME_CHICKENRICE;
    public static final String ITEMNAME_DESC_PIZZA = " " + PREFIX_ITEM + " " + VALID_ITEMNAME_PIZZA;
    public static final String ITEMPRICE_DESC_CHICKENRICE = " " + PREFIX_PRICE + " " + VALID_ITEMPRICE_CHICKENRICE;
    public static final String ITEMPRICE_DESC_PIZZA = " " + PREFIX_PRICE + " " + VALID_ITEMPRICE_PIZZA;
    public static final String NAME_DESC_ANNA = " " + PREFIX_NAME + " " + VALID_NAME_ANNA;
    public static final String NAME_DESC_BRANDON = " " + PREFIX_NAME + " " + VALID_NAME_BRANDON;
    public static final String NAME_DESC_CHARLIE = " " + PREFIX_NAME + " " + VALID_NAME_CHARLIE;

    public static final String INVALID_ITEMNAME_DESC = " " + PREFIX_ITEM
            + " " + "Chicken&Rice"; // '&' not allowed in item names
    public static final String INVALID_ITEMPRICE_DESC = " " + PREFIX_PRICE
            + " " + "3.P0"; // only numbers are allowed
    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME
            + " " + "James&"; // '&' not allowed in names

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command<SplitterModel> command, SplitterModel actualSplitterModel,
                                            CommandResult expectedCommandResult, SplitterModel expectedSplitterModel) {
        try {

            CommandResult result = command.execute(actualSplitterModel);
            System.out.println(result.getFeedbackToUser());
            System.out.println(expectedCommandResult.getFeedbackToUser());
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedSplitterModel, actualSplitterModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }

    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, SplitterModel, CommandResult, SplitterModel)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, SplitterModel actualSplitterModel, String expectedMessage,
                                            SplitterModel expectedSplitterModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualSplitterModel, expectedCommandResult, expectedSplitterModel);
    }



    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the person amount book, filtered person list and selected person in {@code actualModel} remain unchanged
     */

    public static void assertCommandFailure(Command<SplitterModel> command, SplitterModel actualSplitterModel,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        PersonAmountBook expectedPersonAmountBook = new PersonAmountBook(actualSplitterModel.getPersonAmountBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualSplitterModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualSplitterModel));
        assertEquals(expectedPersonAmountBook, actualSplitterModel.getPersonAmountBook());
        assertEquals(expectedFilteredList, actualSplitterModel.getFilteredPersonList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s person amount book.
     */
    public static void showPersonAtIndex(SplitterModel splitterModel, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < splitterModel.getFilteredPersonList().size());

        Person person = splitterModel.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        splitterModel.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, splitterModel.getFilteredPersonList().size());
    }





}


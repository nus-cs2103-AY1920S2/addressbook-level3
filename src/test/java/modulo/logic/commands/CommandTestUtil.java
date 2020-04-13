package modulo.logic.commands;

import static modulo.logic.parser.CliSyntax.PREFIX_ACADEMIC_YEAR;
import static modulo.logic.parser.CliSyntax.PREFIX_END_DATETIME;
import static modulo.logic.parser.CliSyntax.PREFIX_EVENT;
import static modulo.logic.parser.CliSyntax.PREFIX_FREQUENCY;
import static modulo.logic.parser.CliSyntax.PREFIX_MODULE;
import static modulo.logic.parser.CliSyntax.PREFIX_NAME;
import static modulo.logic.parser.CliSyntax.PREFIX_REPEAT;
import static modulo.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static modulo.logic.parser.CliSyntax.PREFIX_START_DATETIME;
import static modulo.logic.parser.CliSyntax.PREFIX_STOP_REPEAT;
import static modulo.logic.parser.CliSyntax.PREFIX_VENUE;
import static modulo.testutil.Assert.assertThrows;
import static modulo.testutil.TypicalIndexesUtils.INDEX_FIRST_ITEM;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import modulo.commons.core.index.Index;
import modulo.logic.commands.exceptions.CommandException;
import modulo.logic.predicate.NameContainsKeywordsPredicate;
import modulo.model.Model;
import modulo.model.Modulo;
import modulo.model.displayable.Displayable;
import modulo.model.displayable.DisplayableType;
import modulo.model.module.Module;


/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {
    // --------- Input String for Module --------- //
    public static final String VALID_CODE_CS2103 = "CS2103";
    public static final String VALID_CODE_CS2103_LOWER_CASE = "cs2103";
    public static final String VALID_CODE_CS2105 = "CS2105";
    public static final String VALID_CODE_CS1231S = "CS1231S";
    public static final String VALID_NAME_CS2103 = "Software Engineering";
    public static final String VALID_NAME_CS2105 = "Introduction to Computer Networking";
    public static final int VALID_ACADEMIC_START_YEAR_CS2103 = 2019;
    public static final int VALID_ACADEMIC_END_YEAR_CS2103 = 2020;
    public static final int VALID_SEMESTER_CS2103 = 2;
    public static final int VALID_ACADEMIC_START_YEAR_CS2105 = 2019;
    public static final int VALID_ACADEMIC_END_YEAR_CS2105 = 2020;
    public static final int VALID_SEMESTER_CS2105 = 2;
    public static final String VALID_DESCRIPTION_CS2103 = "This module introduces the necessary conceptual and "
            + "analytical tools for systematic and rigorous development of software systems";
    public static final String VALID_DESCRIPTION_CS2105 = "This module aims to provide a broad introduction to "
            + "computer networks and network application programming.";
    public static final String CODE_DESC_CS1231S = " " + PREFIX_MODULE + VALID_CODE_CS1231S;
    public static final String CODE_DESC_CS2105 = " " + PREFIX_MODULE + VALID_CODE_CS2105;
    public static final String CODE_DESC_CS2103 = " " + PREFIX_MODULE + VALID_CODE_CS2103;
    public static final String NAME_DESC_CS2103 = " " + PREFIX_NAME + VALID_NAME_CS2103;
    public static final String NAME_DESC_CS2105 = " " + PREFIX_NAME + VALID_NAME_CS2105;
    public static final String SEMESTER_DESC_CS2103 = " " + PREFIX_SEMESTER + VALID_SEMESTER_CS2103;
    public static final String INVALID_MODULE_CODE_CS2000 = "cs2000";
    public static final String INVALID_CODE_DESC = " " + PREFIX_MODULE + "CS210&";
    public static final String ACADEMIC_YEAR_DESC_CS2103 = " " + PREFIX_ACADEMIC_YEAR + VALID_ACADEMIC_START_YEAR_CS2103
            + "/" + VALID_ACADEMIC_END_YEAR_CS2103;
    public static final String ACADEMIC_YEAR_DESC_CS2105 = " " + PREFIX_ACADEMIC_YEAR + VALID_ACADEMIC_START_YEAR_CS2105
            + "/" + VALID_ACADEMIC_END_YEAR_CS2105;
    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    // --------- End of Input String for Module --------- //

    // --------- Input String for Deadline --------- //
    public static final String VALID_NAME_EVENT_TUTORIAL_1 = "Tutorial 1";
    public static final String VALID_NAME_EVENT_TUTORIAL_10 = "Tutorial 10";
    public static final String VALID_NAME_DEADLINE_TUTORIAL = "Finish up tutorial homework";
    public static final String VALID_NAME_DEADLINE_LECTURE = "Prepare for lecture by reading slides";
    public static final String VALID_NAME_DEADLINE_LECTURE_STANDARD = "Prepare for lecture in advance";
    public static final String VALID_DEADLINE_ONE = "Complete tutorial Questions";
    public static final String VALID_REPEAT = "YES";
    public static final String VALID_NO_REPEAT = "NO";
    public static final boolean VALID_REPEAT_BOOL = true;
    public static final boolean VALID_NO_REPEAT_BOOL = false;
    public static final String REPEAT = " " + PREFIX_REPEAT + VALID_REPEAT;
    public static final String NO_REPEAT = " " + PREFIX_REPEAT + VALID_NO_REPEAT;
    public static final String EVENT_DESC_TUTORIAL_1 = " " + PREFIX_EVENT + VALID_NAME_EVENT_TUTORIAL_1;
    public static final String DEADLINE_DESC_ONE = " " + PREFIX_NAME + VALID_DEADLINE_ONE;
    // --------- End of Input String for Deadline --------- //

    // ------------ Input String for adding Event ------------ //
    public static final String VALID_EVENT_TUTORIAL_1 = "Tutorial 1";
    public static final String VALID_EVENT_TUTORIAL_2 = "Tutorial 2";
    public static final String INVALID_EVENT_NAME = "Tutorial %%%";
    public static final String VALID_STARTDATE_TUTORIAL = "2020-01-15 09:00";
    public static final String VALID_ENDDATE_TUTORIAL = "2020-01-15 10:00";
    public static final String VALID_VENUE_TUTORIAL = "COM1-B103";
    public static final String VALID_REPEAT_TUTORIAL = "YES";
    public static final String VALID_FREQUENCY_TUTORIAL = "2";
    public static final String VALID_STOP_REPEAT_TUTORIAL = "2020-05-08";
    public static final String INVALID_DESC_EVENT_NAME = " " + PREFIX_NAME + INVALID_EVENT_NAME;
    public static final String NAME_DESC_TUTORIAL = " " + PREFIX_NAME + VALID_EVENT_TUTORIAL_1;
    public static final String NAME_DESC_TUTORIAL_2 = " " + PREFIX_NAME + VALID_EVENT_TUTORIAL_2;
    public static final String STARTDATE_DESC_TUTORIAL = " " + PREFIX_START_DATETIME + VALID_STARTDATE_TUTORIAL;
    public static final String ENDDATE_DESC_TUTORIAL = " " + PREFIX_END_DATETIME + VALID_ENDDATE_TUTORIAL;
    public static final String VENUE_DESC_TUTORIAL = " " + PREFIX_VENUE + VALID_VENUE_TUTORIAL;
    public static final String REPEAT_DESC_TUTORIAL = " " + PREFIX_REPEAT + VALID_REPEAT_TUTORIAL;
    public static final String FREQUENCY_DESC_TUTORIAL = " " + PREFIX_FREQUENCY + VALID_FREQUENCY_TUTORIAL;
    public static final String STOP_REPEAT_DESC_TUTORIAL = " " + PREFIX_STOP_REPEAT + VALID_STOP_REPEAT_TUTORIAL;
    // ------------ END of Input String for Add Event ------------ //

    public static final NameContainsKeywordsPredicate VALID_PREDICATE_CS2103 =
            new NameContainsKeywordsPredicate("cs2103");

    /**
     * Executes the given {@code command}, confirms that <br> - the returned {@link CommandResult} matches {@code
     * expectedCommandResult} <br> - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br> - a {@code CommandException} is thrown <br> - the
     * CommandException message matches {@code expectedMessage} <br> - the address book, filtered person list and
     * selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        Modulo expectedModulo = new Modulo(actualModel.getModulo());
        List<Module> expectedFilteredList = new ArrayList<>(actualModel.getFilteredModuleList());
        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedModulo, actualModel.getModulo());
        assertEquals(expectedFilteredList, actualModel.getFilteredModuleList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the module at the given {@code targetIndex} in the {@code
     * model}'s modulo.
     * <p>
     */
    public static void showModuleAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredModuleList().size());
        model.setFilteredFocusedList(DisplayableType.MODULE);
        ObservableList<? extends Displayable> lastShownList = model.getFilteredFocusedList();
        Displayable itemToView = lastShownList.get(INDEX_FIRST_ITEM.getZeroBased());
        model.setFocusedDisplayable(itemToView);
        assertEquals(itemToView, model.getFocusedDisplayable());
    }


    /**
     * Updates {@code model}'s filtered list to show only the event at the given {@code targetIndex} in the {@code
     * model}'s modulo.
     * <p>
     */
    public static void showEventAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredEventList().size());
        model.setFilteredFocusedList(DisplayableType.EVENT);
        ObservableList<? extends Displayable> lastShownList = model.getFilteredFocusedList();
        Displayable itemToView = lastShownList.get(INDEX_FIRST_ITEM.getZeroBased());
        model.setFocusedDisplayable(itemToView);
        assertEquals(itemToView, model.getFocusedDisplayable());
    }


}

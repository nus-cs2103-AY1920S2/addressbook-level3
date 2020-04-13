package nasa.logic.commands;

import static nasa.logic.parser.CliSyntax.PREFIX_ACTIVITY_NAME;
import static nasa.logic.parser.CliSyntax.PREFIX_DATE;
import static nasa.logic.parser.CliSyntax.PREFIX_END_DATE;
import static nasa.logic.parser.CliSyntax.PREFIX_MODULE;
import static nasa.logic.parser.CliSyntax.PREFIX_MODULE_NAME;
import static nasa.logic.parser.CliSyntax.PREFIX_NOTE;
import static nasa.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static nasa.logic.parser.CliSyntax.PREFIX_START_DATE;
import static nasa.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import nasa.logic.commands.exceptions.CommandException;
import nasa.logic.commands.module.EditModuleCommand;
import nasa.model.Model;
import nasa.model.NasaBook;
import nasa.model.module.Module;
import nasa.testutil.EditModuleDescriptorBuilder;

/**
 * Test util.
 */
public class CommandTestUtil {

    // valid module names and codes
    public static final String VALID_MODULE_CODE_CS2030 = "CS2030";
    public static final String VALID_MODULE_CODE_CS1231 = "CS1231";
    public static final String VALID_MODULE_NAME_CS2030 = "Programming Methodology II";
    public static final String VALID_MODULE_NAME_CS1231 = "Discrete Structures";

    // valid activity names and their parameters
    public static final String VALID_ACTIVITY_NAME_HWK = "Homework 3";
    public static final String VALID_ACTIVITY_NAME_TUTORIAL = "Weekly Tutorial";
    public static final String VALID_ACTIVITY_NAME_EXAM = "Final Paper";
    public static final String VALID_NOTES_TEST = "This is merely a testing of the notes";
    public static final String VALID_NOTES_TEST_2 = "Notes test two";
    public static final String VALID_PRIORITY_HIGH = "1";
    public static final String VALID_PRIORITY_LOW = "5";
    public static final String VALID_DATE_TEST = "12-12-2020 23:59";
    public static final String VALID_DATE_TEST_2 = "14-12-2020 23:59";

    // parameters with their prefixes
    public static final String MODULE_CODE_DESC_CS2030 = " " + PREFIX_MODULE + VALID_MODULE_CODE_CS2030;
    public static final String MODULE_CODE_DESC_CS1231 = " " + PREFIX_MODULE + VALID_MODULE_CODE_CS1231;
    public static final String MODULE_NAME_DESC_CS2030 = " " + PREFIX_MODULE_NAME + VALID_MODULE_NAME_CS2030;
    public static final String MODULE_NAME_DESC_CS1231 = " " + PREFIX_MODULE_NAME + VALID_MODULE_NAME_CS1231;
    public static final String ACTIVITY_NAME_DESC_HWK = " " + PREFIX_ACTIVITY_NAME + VALID_ACTIVITY_NAME_HWK;
    public static final String ACTIVITY_NAME_DESC_TUTORIAL = " " + PREFIX_ACTIVITY_NAME + VALID_ACTIVITY_NAME_TUTORIAL;
    public static final String ACTIVITY_NAME_DESC_EXAM = " " + PREFIX_ACTIVITY_NAME + VALID_ACTIVITY_NAME_EXAM;
    public static final String NOTES_DESC_TEST = " " + PREFIX_NOTE + VALID_NOTES_TEST;
    public static final String NOTES_DESC_TEST_2 = " " + PREFIX_NOTE + VALID_NOTES_TEST_2;
    public static final String PRIORITY_DESC_HIGH = " " + PREFIX_PRIORITY + VALID_PRIORITY_HIGH;
    public static final String PRIORITY_DESC_LOW = " " + PREFIX_PRIORITY + VALID_PRIORITY_LOW;
    public static final String DATE_DESC_TEST = " " + PREFIX_DATE + VALID_DATE_TEST;
    public static final String DATE_DESC_TEST_2 = " " + PREFIX_DATE + VALID_DATE_TEST_2;
    public static final String DATE_DESC_TEST_FROM = " " + PREFIX_START_DATE + VALID_DATE_TEST;
    public static final String DATE_DESC_TEST_TO = " " + PREFIX_END_DATE + VALID_DATE_TEST_2;

    // invalid parameters with their prefixes
    public static final String INVALID_MODULE_DESC = " " + PREFIX_MODULE + "@31_+"; //only alphanumeric char
    public static final String INVALID_MODULE_NAME_DESC = " " + PREFIX_MODULE_NAME + "\t"; //only whitespaces
    public static final String INVALID_ACTIVITY_NAME_DESC = " " + PREFIX_ACTIVITY_NAME
        + "\t\t"; // only whitespaces
    public static final String INVALID_NOTES_DESC = " " + PREFIX_NOTE
        + "        "; //only whitespaces
    public static final String INVALID_PRIORITY_DESC = " " + PREFIX_PRIORITY + "r"; //not an integer
    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "2020-12-31 11:00"; //not DD-MM-YYYY HH:MM format
    public static final String INVALID_DATE_FROM_DESC = " " + PREFIX_START_DATE
        + "2020-12-31 11:00"; // not DD-MM-YYYY HH:MM format
    public static final String INVALID_DATE_TO_DESC = " " + PREFIX_END_DATE
        + "2020-12-31 11:00"; // not DD-MM-YYYY HH:MM format

    //extra test strings
    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditModuleCommand.EditModuleDescriptor DESC_CS2030;
    public static final EditModuleCommand.EditModuleDescriptor DESC_CS1231;

    static {
        DESC_CS2030 = new EditModuleDescriptorBuilder().withModuleCode(VALID_MODULE_CODE_CS2030)
                .withModuleName(VALID_MODULE_NAME_CS2030).build();
        DESC_CS1231 = new EditModuleDescriptorBuilder().withModuleCode(VALID_MODULE_CODE_CS1231)
                .withModuleName(VALID_MODULE_NAME_CS1231).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
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
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        NasaBook expectedAddressBook = new NasaBook(actualModel.getNasaBook());
        List<Module> expectedFilteredList = new ArrayList<>(actualModel.getFilteredModuleList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getNasaBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredModuleList());
    }
}

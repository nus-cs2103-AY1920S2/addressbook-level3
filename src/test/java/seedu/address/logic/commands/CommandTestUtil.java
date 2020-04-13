package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OFFICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.facilitator.FacilEditCommand;
import seedu.address.logic.commands.module.ModuleEditCommand;
import seedu.address.model.ModManager;
import seedu.address.model.Model;
import seedu.address.model.facilitator.Facilitator;
import seedu.address.model.facilitator.NameContainsKeywordsPredicate;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.module.Module;
import seedu.address.model.task.Task;
import seedu.address.testutil.EditFacilitatorDescriptorBuilder;
import seedu.address.testutil.EditModuleDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_MODULE_CODE_CS2103T = "CS2103T";
    public static final String VALID_MODULE_CODE_CS2101 = "CS2101";
    public static final String VALID_MODULE_CODE_CS1101S = "CS1101S";
    public static final String VALID_MODULE_CODE_CS3230 = "CS3230";
    public static final String VALID_MODULE_CODE_CS4215 = "CS4215";
    public static final String VALID_MODULE_CODE_GEQ1000 = "GEQ1000";
    public static final String DOES_NOT_EXIST_MODULE_GGG1234P = "GGG1234P";
    public static final String VALID_MODULE_CODES_CHAIN = "CS2101 CS2103T CS3230 CS4215";

    public static final String TASK_DATE_1 = "05/05/2020";
    public static final String TASK_DATE_2 = "06/06/2020";
    public static final String TASK_TIME_1 = "23:59";
    public static final String TASK_TIME_2 = "16:00";
    public static final String TASK_DAY_25 = "25";
    public static final String TASK_DAY_26 = "26";
    public static final String TASK_MONTH_03 = "3";
    public static final String TASK_MONTH_06 = "6";
    public static final String TASK_YEAR_2020 = "2020";
    public static final String VALID_TASK_ID_FIRST = "619";
    public static final String VALID_TASK_ID_SECOND = "737";
    public static final String VALID_TASK_ID_THIRD = "123";
    public static final String VALID_TASK_ID_FOURTH = "345";
    public static final String INVALID_TASK_ID = "1103";

    public static final String VALID_DESCRIPTION_CS2103T = "Software Engineering";
    public static final String VALID_DESCRIPTION_CS2101 = "Effective Communication for Computing Professionals";
    public static final String VALID_DESCRIPTION_CS1101S = "Programming Methodology";
    public static final String VALID_TASK_DESCRIPTION_PROGRAMMING = "Programming Lab 2";
    public static final String VALID_TASK_DESCRIPTION_ASSIGNMENT = "written assignment 1 ";
    public static final String VALID_TASK_DESCRIPTION_HOMEWORK = "homework 3";

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_OFFICE_AMY = "COM3-04-04";
    public static final String VALID_OFFICE_BOB = "COM3-03-03";

    public static final String VALID_FIND_WORDS_PROGRAMMING = "Programming";
    public static final String VALID_FIND_WORDS_ASSIGNMENT = "assignment";
    public static final String VALID_FIND_WORDS_HOMEWORK = "homEWorK";
    public static final String VALID_BROKEN_FIND_WORDS_PROG = "pROg";
    public static final String VALID_BROKEN_FIND_WORDS_AS = "aSSiGN";
    public static final String VALID_BROKEN_FIND_WORDS_HOME = "hOMe";
    public static final String VALID_BROKEN_FIND_WORDS_SLEEP = "slEe";
    public static final String VALID_BROKEN_FIND_WORDS_ONLINE = "lIne";
    public static final String VALID_BROKEN_FIND_WORDS_CONQUER = "cOnQ";

    public static final String TASK_UNKNOWN_COMMAND = "task unknownCommand";

    public static final String INVALID_TASK_DAY_32 = "32";
    public static final String INVALID_TASK_MONTH_13 = "13";
    public static final String INVALID_TASK_YEAR_NEGATIVE = "-1";

    public static final String MODULE_CODE_DESC_CS2103T = " " + PREFIX_MODULE_CODE + " " + VALID_MODULE_CODE_CS2103T;
    public static final String MODULE_CODE_DESC_CS2101 = " " + PREFIX_MODULE_CODE + " " + VALID_MODULE_CODE_CS2101;
    public static final String MODULE_CODE_DESC_CS1101S = " " + PREFIX_MODULE_CODE + " " + VALID_MODULE_CODE_CS1101S;
    public static final String MODULE_CODE_CHAIN = " " + PREFIX_MODULE_CODE + " " + VALID_MODULE_CODES_CHAIN;
    public static final String DESCRIPTION_DESC_CS2103T = " " + PREFIX_DESCRIPTION + " " + VALID_DESCRIPTION_CS2103T;
    public static final String DESCRIPTION_DESC_CS2101 = " " + PREFIX_DESCRIPTION + " " + VALID_DESCRIPTION_CS2101;
    public static final String DESCRIPTION_DESC_CS1101S = " " + PREFIX_DESCRIPTION + " " + VALID_DESCRIPTION_CS1101S;

    public static final String TASK_DESCRIPTION_DESC_PROGRAMMING = " " + PREFIX_DESCRIPTION
            + " " + VALID_TASK_DESCRIPTION_PROGRAMMING;
    public static final String TASK_DESCRIPTION_DESC_ASSIGNMENT = " " + PREFIX_DESCRIPTION
            + " " + VALID_TASK_DESCRIPTION_ASSIGNMENT;
    public static final String TASK_DESCRIPTION_DESC_HOMEWORK = " " + PREFIX_DESCRIPTION
            + " " + VALID_TASK_DESCRIPTION_HOMEWORK;
    public static final String TASK_DATE_DESC_1 = " " + PREFIX_ON + " " + TASK_DATE_1;
    public static final String TASK_DATE_DESC_2 = " " + PREFIX_ON + " " + TASK_DATE_2;
    public static final String TASK_TIME_DESC_1 = " " + PREFIX_AT + " " + TASK_TIME_1;
    public static final String TASK_TIME_DESC_2 = " " + PREFIX_AT + " " + TASK_TIME_2;

    public static final String TASK_CMD_DAY_26 = " " + PREFIX_DATE + " " + TASK_DAY_26;
    public static final String TASK_CMD_MONTH_03 = " " + PREFIX_MONTH + " " + TASK_MONTH_03;
    public static final String TASK_CMD_YEAR_2020 = " " + PREFIX_YEAR + " " + TASK_YEAR_2020;
    public static final String TASK_ID_DESC_VALID_ID = " " + PREFIX_TASK_ID + " " + VALID_TASK_ID_FIRST;

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + " " + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + " " + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + " " + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + " " + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + " " + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + " " + VALID_EMAIL_BOB;
    public static final String OFFICE_DESC_AMY = " " + PREFIX_OFFICE + " " + VALID_OFFICE_AMY;
    public static final String OFFICE_DESC_BOB = " " + PREFIX_OFFICE + " " + VALID_OFFICE_BOB;

    public static final String INVALID_MODULE_CODE_DESC = " " + PREFIX_MODULE_CODE
            + " cs2103*"; // '*' not allowed in module codes
    public static final String INVALID_DESCRIPTION_DESC = " " + PREFIX_DESCRIPTION + " very very very very"
            + " very very very very very very very very very long description"; // string more than 64 characters

    public static final String INVALID_DATE_DESC = " " + PREFIX_ON + " 20/13/2020";
    public static final String INVALID_TIME_DESC = " " + PREFIX_AT + " 16:66";

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + " James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + " 911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + " bob!yahoo"; // missing '@' symbol
    public static final String INVALID_OFFICE_DESC = " " + PREFIX_OFFICE; // empty string not allowed for offices

    public static final String INVALID_TASK_DAY_STRING =
            " " + PREFIX_DATE + " " + "monday"; // strings not allowed for day
    public static final String INVALID_TASK_MONTH_STRING =
            " " + PREFIX_MONTH + " " + "facebook"; // strings not allowed for month
    public static final String INVALID_TASK_YEAR_STRING =
            " " + PREFIX_YEAR + " " + "instagram"; // strings not allowed for year
    public static final String INVALID_TASK_DAY_OUT_OF_BOUNDS =
            " " + PREFIX_DATE + " " + INVALID_TASK_DAY_32; // strings not allowed for day
    public static final String INVALID_TASK_MONTH_OUT_OF_BOUNDS =
            " " + PREFIX_MONTH + " " + INVALID_TASK_MONTH_13; // strings not allowed for month
    public static final String INVALID_TASK_YEAR_OUT_OF_BOUNDS =
            " " + PREFIX_YEAR + " " + INVALID_TASK_YEAR_NEGATIVE; // strings not allowed for year

    public static final String INVALID_INDEX_WITH_STRING = " 1 some random string ";
    public static final String INVALID_INDEX_WITH_PREFIX = " 1 /some random string ";

    public static final String INVALID_MODULE_CODE_CS9000 = "CS9000";
    public static final String INVALID_MODULE_CODE_CODE123 = "code123";

    public static final String VALID_LESSON_TYPE_LEC = "LEC";
    public static final String VALID_LESSON_TYPE_TUT = "TUT";
    public static final String VALID_LESSON_TYPE_REC = "REC";
    public static final String VALID_LESSON_TYPE_SEC = "SEC";
    public static final String INVALID_LESSON_TYPE_FREE = "FREE";

    public static final String VALID_DAY_AND_TIME_SUNDAY = "SUNDAY 01:00 02:00";
    public static final String VALID_DAY_AND_TIME_MONDAY = "MONDAY 19:00 20:00";
    public static final String VALID_DAY_FRIDAY = "FRIDAY";
    public static final String INVALID_DAY_SUNNYDAY = "SUNNYDAY 19:00 20:00";
    public static final String INVALID_TIME = "SUNDAY 19:00 2:00";

    public static final String VALID_VENUE_HOME = "Home";
    public static final String VALID_VENUE_PARK = "Park";

    public static final String INVALID_INDEX_A = "a";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";


    public static final String EMPTY_ARGUMENTS = "";
    public static final String EMPTY_SPACES = "     ";
    public static final int TASK_LIST_VALID_CONSTANT = 3;
    public static final int AN_INT_VALUE = 10;

    public static final FacilEditCommand.EditFacilitatorDescriptor DESC_AMY;
    public static final FacilEditCommand.EditFacilitatorDescriptor DESC_BOB;
    public static final ModuleEditCommand.EditModuleDescriptor DESC_CS2101;
    public static final ModuleEditCommand.EditModuleDescriptor DESC_CS1101S;

    static {
        DESC_CS2101 = new EditModuleDescriptorBuilder().withModuleCode(VALID_MODULE_CODE_CS2101)
                .withDescription(VALID_DESCRIPTION_CS2101).build();
        DESC_CS1101S = new EditModuleDescriptorBuilder().withModuleCode(VALID_MODULE_CODE_CS1101S)
                .withDescription(VALID_DESCRIPTION_CS1101S).build();
        DESC_AMY = new EditFacilitatorDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withOffice(VALID_OFFICE_AMY)
                .withModuleCodes(VALID_MODULE_CODE_CS2101).build();
        DESC_BOB = new EditFacilitatorDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withOffice(VALID_OFFICE_BOB)
                .withModuleCodes(VALID_MODULE_CODE_CS2103T, VALID_MODULE_CODE_CS2101).build();
    }

    private static final Logger logger = LogsCenter.getLogger(CommandTestUtil.class);

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
            logger.severe(ce.getMessage());
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                CommandType type, Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, type);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered module list, filtered facilitator list, filtered task list and lesson list
     * in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        ModManager expectedModManager = new ModManager(actualModel.getModManager());
        List<Module> expectedModuleList = new ArrayList<>(actualModel.getFilteredModuleList());
        List<Facilitator> expectedFacilitatorList = new ArrayList<>(actualModel.getFilteredFacilitatorList());
        List<Task> expectedTaskList = new ArrayList<>(actualModel.getFilteredTaskList());
        List<Lesson> expectedLessonList = new ArrayList<>(actualModel.getLessons());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedModManager, actualModel.getModManager());
        assertEquals(expectedModuleList, actualModel.getFilteredModuleList());
        assertEquals(expectedFacilitatorList, actualModel.getFilteredFacilitatorList());
        assertEquals(expectedTaskList, actualModel.getFilteredTaskList());
        assertEquals(expectedLessonList, actualModel.getLessons());
    }

    /**
     * Updates {@code model}'s filtered list to show only the facilitator at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showFacilitatorAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredFacilitatorList().size());

        Facilitator facilitator = model.getFilteredFacilitatorList().get(targetIndex.getZeroBased());
        final String[] splitName = facilitator.getName().fullName.split("\\s+");
        model.updateFilteredFacilitatorList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredFacilitatorList().size());
    }

}

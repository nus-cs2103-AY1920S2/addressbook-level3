package team.easytravel.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_ACCOMMODATION_LOCATION;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_ACTIVITY_DURATION;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_ACTIVITY_LOCATION;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_ACTIVITY_TAG;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_TRIP_TITLE;
import static team.easytravel.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import team.easytravel.commons.core.index.Index;
import team.easytravel.logic.commands.activity.EditActivityCommand;
import team.easytravel.logic.commands.exceptions.CommandException;
import team.easytravel.model.Model;
import team.easytravel.model.listmanagers.ActivityManager;
import team.easytravel.model.listmanagers.activity.Activity;
import team.easytravel.model.listmanagers.activity.ActivityContainKeywordPredicate;
import team.easytravel.testutil.activity.EditActivityDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    //--- COMMONS ----
    public static final String VALID_LOCATION = "KL";
    public static final String VALID_TITLE = "this is a pretty good title";
    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    //---- TRIP----
    public static final String VALID_TRIP_TITLE = "Graduation Trip";
    public static final String VALID_DATE_START = "28-09-2020";
    public static final String VALID_DATE_END = "5-10-2020";
    public static final Integer VALID_TRIP_BUDGET = 1000;
    public static final Double VALID_TRIP_EXCHANGE_RATE = 1.30;


    //-----ACTIVITY-----
    public static final String VALID_ACTIVITY_TITLE_JAPAN = "Shopping street";
    public static final String VALID_ACTIVITY_TITLE_FINLAND = "Somewhere Cold";
    public static final Integer VALID_ACTIVITY_DURATION_JAPAN = 1;
    public static final Integer VALID_ACTIVITY_DURATION_FINLAND = 2;
    public static final String VALID_LOCATION_JAPAN = "Japan";
    public static final String VALID_LOCATION_FINLAND = "Finland";
    public static final String VALID_TAG_EXPENSIVE = "expensive";
    public static final String VALID_TAG_SIGHTSEEING = "sightseeing";

    public static final String ACTIVITY_TITLE_DESC_JAPAN = " " + PREFIX_TRIP_TITLE + VALID_ACTIVITY_TITLE_JAPAN;
    public static final String ACTIVITY_TITLE_DESC_FINLAND = " " + PREFIX_TRIP_TITLE + VALID_ACTIVITY_TITLE_FINLAND;
    public static final String ACTIVITY_DURATION_DESC_JAPAN = " " + PREFIX_ACTIVITY_DURATION
            + VALID_ACTIVITY_DURATION_JAPAN;
    public static final String ACTIVITY_LOCATION_DESC_FINLAND = " " + PREFIX_ACTIVITY_LOCATION
            + VALID_ACTIVITY_DURATION_FINLAND;
    public static final String ACTIVITY_LOCATION_DESC_JAPAN = " " + PREFIX_ACTIVITY_LOCATION
            + VALID_ACTIVITY_DURATION_JAPAN;
    public static final String TAG_DESC_EXPENSIVE = " " + PREFIX_ACTIVITY_TAG + VALID_TAG_EXPENSIVE;
    public static final String TAG_DESC_SIGHTSEEING = " " + PREFIX_ACTIVITY_TAG + VALID_TAG_SIGHTSEEING;


    public static final String INVALID_NAME_DESC = " " + PREFIX_TRIP_TITLE
            + "Cheese land&"; // '&' not allowed in title
    public static final String INVALID_PHONE_DESC = " "
            + PREFIX_ACTIVITY_DURATION + "911a"; // 'a' not allowed in duraation
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_ACTIVITY_LOCATION
            + "The moon&"; // missing '@' symbol
    public static final String INVALID_TAG_DESC = " " + PREFIX_ACTIVITY_TAG + "stupid*"; // '*' not allowed in tags

    //----ACCOMMODATION--
    public static final String VALID_ACCOMMODATION_TITLE = "JW Marriott Hotel";
    public static final String VALID_ACCOMMODATION_START_DAY = "1";
    public static final String VALID_ACCOMMODATION_END_DAY = "2";

    public static final String ACCOMMODATION_LOCATION_DESC = " " + PREFIX_ACCOMMODATION_LOCATION
            + VALID_LOCATION;

    //---TRANSPORT---

    public static final String VALID_ACCOMMODATION_MODE = "plane";

    //---- Packing list ---
    public static final String VALID_ITEM_NAME = "Underwear";
    public static final Integer VALID_QUANTITY = 7;
    public static final String VALID_ITEM_CATEGORY = "Clothes";


    //---- FIXED EXPENSES --
    public static final String VALID_AMOUNT = "10";



    public static final EditActivityCommand.EditActivityDescriptor DESC_JAPAN;
    public static final EditActivityCommand.EditActivityDescriptor DESC_FINLAND;

    static {
        DESC_JAPAN = new EditActivityDescriptorBuilder().withTitle(VALID_ACTIVITY_TITLE_JAPAN)
                .withDuration(VALID_ACTIVITY_DURATION_JAPAN).withLocation(VALID_LOCATION_JAPAN)
                .withTags(VALID_TAG_EXPENSIVE).build();
        DESC_FINLAND = new EditActivityDescriptorBuilder().withTitle(VALID_ACTIVITY_TITLE_FINLAND)
                .withDuration(VALID_ACTIVITY_DURATION_FINLAND).withLocation(VALID_LOCATION_FINLAND)
                .withTags(VALID_TAG_SIGHTSEEING).build();
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
            //assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertActivityCommandSuccess(Command command, Model actualModel,
                                                    String expectedMessage, Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage,
                CommandResult.Action.SWITCH_TAB_ACTIVITY);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertPackingListItemCommandSuccess(Command command, Model actualModel,
                                                    String expectedMessage, Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage,
                CommandResult.Action.SWITCH_TAB_PACKING_LIST);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the activityManager, filtered activity list and selected activity in {@code actualModel} remain unchanged
     */
    public static void assertActivityCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        ActivityManager expectedActivityManager = new ActivityManager(actualModel.getActivityManager());
        List<Activity> expectedFilteredList = new ArrayList<>(actualModel.getFilteredActivityList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedActivityManager, actualModel.getActivityManager());
        assertEquals(expectedFilteredList, actualModel.getFilteredActivityList());

    }


    /**
     * Updates {@code model}'s filtered list to show only the activity at the given {@code targetIndex} in the
     * {@code model}'s manager.
     */
    public static void showActivityAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredActivityList().size());

        Activity activity = model.getFilteredActivityList().get(targetIndex.getZeroBased());
        final String[] splitName = activity.getTitle().value.split("\\s+");
        model.updateFilteredActivityList(new ActivityContainKeywordPredicate(Arrays.asList(splitName[0])));
        assertEquals(1, model.getFilteredActivityList().size());
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertAccommodationBookingCommandSuccess(Command command, Model actualModel,
                                                                String expectedMessage, Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage,
                CommandResult.Action.SWITCH_TAB_ACCOMMODATION);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

}

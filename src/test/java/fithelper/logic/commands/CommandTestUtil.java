package fithelper.logic.commands;

import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_CALORIE;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_LOCATION;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_NAME;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_TIME;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_TYPE;
import static fithelper.testutil.AssertUtil.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fithelper.commons.core.index.Index;
import fithelper.commons.exceptions.IllegalValueException;
import fithelper.logic.exceptions.CommandException;
import fithelper.model.FitHelper;
import fithelper.model.Model;
import fithelper.model.entry.Entry;
import fithelper.model.entry.NameContainsKeywordsPredicate;
import fithelper.testutil.EditEntryDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_FOOD = "chicken rice";
    public static final String VALID_NAME_SPORTS = "running";
    public static final String VALID_NAME_CAKE = "Cake";
    public static final String VALID_NAME_BURGER = "Burger";

    public static final String VALID_CALORIE_FOOD = "300";
    public static final String VALID_CALORIE_SPORTS = "200";
    public static final String VALID_CALORIE_CAKE = "500";
    public static final String VALID_CALORIE_BURGER = "550";

    public static final String VALID_DURATION_SPORTS = "1.5";

    public static final String VALID_TIME_SPORTS = "2020-05-11-11:00";
    public static final String VALID_TIME_FOOD = "2020-05-10-12:00";
    public static final String VALID_TIME_CAKE = "2020-03-31-06:15";
    public static final String VALID_TIME_BURGER = "2019-04-05-06:50";

    public static final String VALID_LOCATION_SPORTS = "TRACK";
    public static final String VALID_LOCATION_FOOD = "Fine food, Utown";
    public static final String VALID_LOCATION_CAKE = "home";
    public static final String VALID_LOCATION_BURGER = "super snack";

    public static final String VALID_TYPE_SPORTS = "sports";
    public static final String VALID_TYPE_FOOD = "food";

    public static final String NAME_DESC_CAKE = " " + PREFIX_NAME + VALID_NAME_CAKE;
    public static final String NAME_DESC_BURGER = " " + PREFIX_NAME + VALID_NAME_BURGER;
    public static final String TIME_DESC_CAKE = " " + PREFIX_TIME + VALID_TIME_CAKE;
    public static final String TIME_DESC_BURGER = " " + PREFIX_TIME + VALID_TIME_BURGER;
    public static final String LOCATION_DESC_CAKE = " " + PREFIX_LOCATION + VALID_LOCATION_CAKE;
    public static final String LOCATION_DESC_BURGER = " " + PREFIX_LOCATION + VALID_LOCATION_BURGER;
    public static final String CALORIE_DESC_CAKE = " " + PREFIX_CALORIE + VALID_CALORIE_CAKE;
    public static final String CALORIE_DESC_BURGER = " " + PREFIX_CALORIE + VALID_CALORIE_BURGER;


    public static final String NAME_DESC_FOOD = " " + PREFIX_NAME + VALID_NAME_FOOD;
    public static final String NAME_DESC_SPORTS = " " + PREFIX_NAME + VALID_NAME_SPORTS;
    public static final String CALORIE_DESC_FOOD = " " + PREFIX_CALORIE + VALID_CALORIE_FOOD;
    public static final String CALORIE_DESC_SPORTS = " " + PREFIX_CALORIE + VALID_CALORIE_SPORTS;
    public static final String TIME_DESC_FOOD = " " + PREFIX_TIME + VALID_TIME_FOOD;
    public static final String TIME_DESC_SPROTS = " " + PREFIX_TIME + VALID_TIME_SPORTS;
    public static final String LOCATION_DESC_FOOD = " " + PREFIX_LOCATION + VALID_LOCATION_FOOD;
    public static final String LOCATION_DESC_SPORTS = " " + PREFIX_LOCATION + VALID_LOCATION_SPORTS;
    public static final String TYPE_DESC_FOOD = " " + PREFIX_TYPE + VALID_TYPE_FOOD;
    public static final String TYPE_DESC_SPORTS = " " + PREFIX_TYPE + VALID_TYPE_SPORTS;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + ""; // 'name cannot be empty
    public static final String INVALID_LOCATION_DESC = " " + PREFIX_LOCATION + "  "; // location cannot be empty
    public static final String INVALID_TIME_DESC = " " + PREFIX_TIME + "12:00"; // missing date
    public static final String INVALID_TYPE_DESC = " " + PREFIX_TYPE + "x"; // type can only be sports or food
    public static final String INVALID_CALORIE_DESC = " " + PREFIX_CALORIE + "-200"; // calorie can only be positive

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditEntryDescriptor DESC_FOOD;
    public static final EditCommand.EditEntryDescriptor DESC_SPORTS;

    public static final String WHITE_SPACE = " ";
    public static final String INVALID = "invalid";
    public static final String TIME = "time";
    public static final String CALORIE = "cal";
    public static final String NAME = "name";
    public static final String TIME_ACRONYM = "t";
    public static final String CAL_ACRONYM = "c";
    public static final String NAME_ACRONYM = "n";
    public static final String SPORTS = "sports";
    public static final String SPORTS_ACRONYM = "s";
    public static final String FOOD = "food";
    public static final String FOOD_ACRONYM = "f";
    public static final String DESCENDING = "d";
    public static final String ASCENDING = "a";
    public static final String APPLE = "apple";
    public static final String BANANA = "banana";
    public static final String SWIM = "swimming";

    static {
        DESC_FOOD = new EditEntryDescriptorBuilder().withName(VALID_NAME_FOOD)
                .withCalorie(VALID_CALORIE_FOOD).withLocation(VALID_LOCATION_FOOD).withTime(VALID_TIME_FOOD)
                .withType(VALID_TYPE_FOOD).build();
        DESC_SPORTS = new EditEntryDescriptorBuilder().withName(VALID_NAME_SPORTS)
                .withCalorie(VALID_CALORIE_SPORTS).withLocation(VALID_LOCATION_SPORTS).withTime(VALID_TIME_SPORTS)
                .withType(VALID_TYPE_SPORTS).build();
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
        } catch (IllegalValueException e) {
            e.printStackTrace();
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
     * - the address book, filtered entry list and selected entry in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        FitHelper expectedAddressBook = new FitHelper(actualModel.getFitHelper());
        List<Entry> expectedFilteredList = new ArrayList<>(actualModel.getFilteredFoodEntryList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getFitHelper());
        assertEquals(expectedFilteredList, actualModel.getFilteredFoodEntryList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the entry at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showEntryAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredFoodEntryList().size());

        Entry entry = model.getFilteredFoodEntryList().get(targetIndex.getZeroBased());
        final String[] splitName = entry.getName().toString().split("\\s+");
        model.updateFilteredFoodEntryList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredFoodEntryList().size());
    }

}

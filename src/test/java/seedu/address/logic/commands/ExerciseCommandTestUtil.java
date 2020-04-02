package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXERCISE_WEIGHT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REPS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SETS;

/**
 * Contains helper methods for testing exerise commands.
 */
public class ExerciseCommandTestUtil {

    public static final String VALID_EXERCISE_NAME_PUSHUP = "pushup";
    public static final String VALID_EXERCISE_NAME_BENCH = "bench press";
    public static final String VALID_EXERCISE_REPS_PUSHUP = "30";
    public static final String VALID_EXERCISE_REPS_BENCH = "2";
    public static final String VALID_EXERCISE_SETS_PUSHUP = "5";
    public static final String VALID_EXERCISE_SETS_BENCH = "4";
    public static final String VALID_EXERCISE_WEIGHT_PUSHUP = "";
    public static final String VALID_EXERCISE_WEIGHT_BENCH = "100";
    public static final String VALID_EXERCISE_DATE_PUSHUP = "12-12-2012";
    public static final String VALID_EXERCISE_DATE_BENCH = "13-01-2014";

    public static final String EXERCISE_NAME_DESC_PUSHUP =
        " " + PREFIX_NAME + VALID_EXERCISE_NAME_PUSHUP;
    public static final String EXERCISE_NAME_DESC_BENCH =
        " " + PREFIX_NAME + VALID_EXERCISE_NAME_BENCH;
    public static final String EXERCISE_REPS_DESC_PUSHUP =
        " " + PREFIX_REPS + VALID_EXERCISE_REPS_PUSHUP;
    public static final String EXERCISE_REPS_DESC_BENCH =
        " " + PREFIX_REPS + VALID_EXERCISE_REPS_BENCH;
    public static final String EXERCISE_SETS_DESC_PUSHUP =
        " " + PREFIX_SETS + VALID_EXERCISE_SETS_PUSHUP;
    public static final String EXERCISE_SETS_DESC_BENCH =
        " " + PREFIX_SETS + VALID_EXERCISE_SETS_BENCH;
    public static final String EXERCISE_WEIGHT_DESC_PUSHUP =
        " " + PREFIX_EXERCISE_WEIGHT + VALID_EXERCISE_WEIGHT_PUSHUP;
    public static final String EXERCISE_WEIGHT_DESC_BENCH =
        " " + PREFIX_EXERCISE_WEIGHT + VALID_EXERCISE_WEIGHT_BENCH;
    public static final String EXERCISE_DATE_DESC_PUSHUP =
        " " + PREFIX_DATE + VALID_EXERCISE_DATE_PUSHUP;
    public static final String EXERCISE_DATE_DESC_BENCH =
        " " + PREFIX_DATE + VALID_EXERCISE_DATE_BENCH;

    public static final String INVALID_EXERCISE_NAME_DESC =
        " " + PREFIX_NAME + "Pushup&"; // '&' not allowed in names
    public static final String INVALID_EXERCISE_REPS_DESC =
        " " + PREFIX_REPS + "91a"; // 'a' not allowed in reps
    public static final String INVALID_EXERCISE_SETS_DESC =
        " " + PREFIX_SETS + "21a9"; // 'a' not allowed in sets
    public static final String INVALID_EXERCISE_WEIGHT_DESC =
        " " + PREFIX_EXERCISE_WEIGHT + "1o21"; // 'o' not allowed in weight
    public static final String INVALID_EXERCISE_DATE_DESC = " " + PREFIX_DATE + ""; // empty string not allowed for date

    // TODO: if there is edit exercise
    // public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    // public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    // public static final EditCommand.EditClientDescriptor DESC_AMY;
    // public static final EditCommand.EditClientDescriptor DESC_BOB;

    // static {
    //     DESC_AMY = new EditClientDescriptorBuilder().withName(VALID_NAME_PUSHUP).withPhone(VALID_PHONE_AMY)
    //             .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    //     DESC_BOB = new EditClientDescriptorBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
    //             .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
    //             .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
    //             .build();
    // }
    //
    // /**
    //  * Executes the given {@code command}, confirms that <br>
    //  * - the returned {@link CommandResult} matches {@code expectedCommandResult}
    //  * <br>
    //  * - the {@code actualModel} matches {@code expectedModel}
    //  */
    // public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
    //         Model expectedModel) {
    //     try {
    //         CommandResult result = command.execute(actualModel);
    //         assertEquals(expectedCommandResult, result);
    //         assertEquals(expectedModel, actualModel);
    //     } catch (CommandException ce) {
    //         throw new AssertionError("Execution of command should not fail.", ce);
    //     }
    // }

    // /**
    //  * Convenience wrapper to
    //  * {@link #assertCommandSuccess(Command, Model, CommandResult, Model)} that
    //  * takes a string {@code expectedMessage}.
    //  */
    // public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
    //         Model expectedModel) {
    //     CommandResult expectedCommandResult = new CommandResult(expectedMessage);
    //     assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    // }

    // /**
    //  * Executes the given {@code command}, confirms that <br>
    //  * - a {@code CommandException} is thrown <br>
    //  * - the CommandException message matches {@code expectedMessage} <br>
    //  * - the address book, filtered client list and selected client in
    //  * {@code actualModel} remain unchanged
    //  */
    // public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
    //     // we are unable to defensively copy the model for comparison later, so we can
    //     // only do so by copying its components.
    //     AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
    //     List<Client> expectedFilteredList = new ArrayList<>(actualModel.getFilteredClientList());

    //     assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
    //     assertEquals(expectedAddressBook, actualModel.getAddressBook());
    //     assertEquals(expectedFilteredList, actualModel.getFilteredClientList());
    // }

    // /**
    //  * Updates {@code model}'s filtered list to show only the client at the given
    //  * {@code targetIndex} in the {@code model}'s address book.
    //  */
    // public static void showClientAtIndex(Model model, Index targetIndex) {
    //     assertTrue(targetIndex.getZeroBased() < model.getFilteredClientList().size());

    //     Client client = model.getFilteredClientList().get(targetIndex.getZeroBased());
    //     final String[] splitName = client.getName().fullName.split("\\s+");
    //     model.updateFilteredClientList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

    //     assertEquals(1, model.getFilteredClientList().size());
    // }

}

package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.BIRTHDAY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_CS2103;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CUISINE_AMEENS;
import static seedu.address.logic.commands.CommandTestUtil.DESC_HOURS_AMEENS;
import static seedu.address.logic.commands.CommandTestUtil.DESC_LOCATION_AMEENS;
import static seedu.address.logic.commands.CommandTestUtil.DESC_NAME_AMEENS;
import static seedu.address.logic.commands.CommandTestUtil.DESC_PRICE_AMEENS;
import static seedu.address.logic.commands.CommandTestUtil.DESC_VISITED_AMEENS;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_CS2103;
import static seedu.address.logic.commands.CommandTestUtil.WORKLOAD_DESC_CS2103;

import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAssignments.CS2103_TP;
import static seedu.address.testutil.TypicalPersons.AMY;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.commands.AddAssignmentCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddRestaurantCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.EventSchedule;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyRestaurantBook;
import seedu.address.model.ReadOnlySchoolworkTracker;
import seedu.address.model.RestaurantBook;
import seedu.address.model.SchoolworkTracker;
import seedu.address.model.UserPrefs;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.person.Person;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonEventScheduleStorage;
import seedu.address.storage.JsonRestaurantBookStorage;
import seedu.address.storage.JsonSchoolworkTrackerStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;

import seedu.address.testutil.AssignmentBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.RestaurantBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json"));
        JsonRestaurantBookStorage restaurantBookStorage =
                new JsonRestaurantBookStorage(temporaryFolder.resolve("restaurantBook.json"));
        JsonSchoolworkTrackerStorage schedulerStorage =
                new JsonSchoolworkTrackerStorage(temporaryFolder.resolve("assignments.json"));
        JsonEventScheduleStorage eventScheduleStorage =
                new JsonEventScheduleStorage(temporaryFolder.resolve("events.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage,
                restaurantBookStorage,
                schedulerStorage,
                eventScheduleStorage,
                userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "(ab)delete 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonAddressBookIoExceptionThrowingStub
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionAddressBook.json"));
        JsonRestaurantBookStorage restaurantBookStorage =
                new JsonRestaurantBookStorage(temporaryFolder.resolve("ioExceptionRestaurantBook.json"));
        JsonSchoolworkTrackerStorage assignmentScheduleStorage =
                new JsonSchoolworkTrackerStorage(temporaryFolder.resolve("ioExceptionAssignments.json"));
        JsonEventScheduleStorage eventScheduleStorage =
                new JsonEventScheduleStorage(temporaryFolder.resolve("ioExceptionEvents.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage,
                restaurantBookStorage,
                assignmentScheduleStorage,
                eventScheduleStorage,
                userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + BIRTHDAY_DESC_AMY + ADDRESS_DESC_AMY;
        Person expectedPerson = new PersonBuilder(AMY).withTags().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addPerson(expectedPerson);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);

        // Setup LogicManager with JsonAssignmentScheduleIoExceptionThrowingStub
        addressBookStorage = new JsonAddressBookStorage(temporaryFolder.resolve("ioExceptionAddressBook.json"));
        assignmentScheduleStorage =
            new JsonSchoolworkTrackerIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionAssignments.json"));

        storage = new StorageManager(addressBookStorage,
            restaurantBookStorage,
            assignmentScheduleStorage,
            eventScheduleStorage,
            userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        addCommand = AddAssignmentCommand.COMMAND_WORD + TITLE_DESC_CS2103 + DEADLINE_DESC_CS2103
            + WORKLOAD_DESC_CS2103;
        Assignment expectedAssignment = new AssignmentBuilder(CS2103_TP).build();
        expectedModel = new ModelManager();
        expectedModel.addAssignment(expectedAssignment);
        expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);

        // Setup LogicManager with JsonRestaurantBookIoExceptionThrowingStub
        assignmentScheduleStorage =
            new JsonSchoolworkTrackerStorage(temporaryFolder.resolve("ioExceptionAssignments.json"));
        restaurantBookStorage =
            new JsonRestaurantBookIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionRestaurantBook.json"));

        storage = new StorageManager(addressBookStorage,
            restaurantBookStorage,
            assignmentScheduleStorage,
            eventScheduleStorage,
            userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        addCommand = AddRestaurantCommand.COMMAND_WORD + DESC_NAME_AMEENS + DESC_LOCATION_AMEENS + DESC_HOURS_AMEENS
            + DESC_CUISINE_AMEENS + DESC_PRICE_AMEENS + DESC_VISITED_AMEENS;
        Restaurant expectedRestaurant = new RestaurantBuilder().withRecommended("").withBad("").withGood("").build();
        expectedModel = new ModelManager();
        expectedModel.addRestaurant(expectedRestaurant);
        expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredPersonList().remove(0));
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
            Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage) {
        Model expectedModel = new ModelManager(model.getAddressBook(),
                new RestaurantBook(),
                new SchoolworkTracker(),
                new EventSchedule(),
                new UserPrefs());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage, Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        //assertEquals(expectedModel, model);
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonAddressBookIoExceptionThrowingStub extends JsonAddressBookStorage {
        private JsonAddressBookIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonRestaurantBookIoExceptionThrowingStub extends JsonRestaurantBookStorage {
        private JsonRestaurantBookIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveRestaurantBook(ReadOnlyRestaurantBook restaurantBook, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonSchoolworkTrackerIoExceptionThrowingStub extends JsonSchoolworkTrackerStorage {
        private JsonSchoolworkTrackerIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveSchoolworkTracker(ReadOnlySchoolworkTracker schoolworkTracker, Path filePath)
            throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}

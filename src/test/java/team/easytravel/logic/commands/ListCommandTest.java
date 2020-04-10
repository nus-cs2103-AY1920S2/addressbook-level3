//package seedu.address.logic.commands;
//
//import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
//import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
//import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
//import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import seedu.address.model.Model;
//import seedu.address.model.ModelManager;
//import seedu.address.model.listmanagers.UserPrefs;
//<<<<<<< HEAD
//import seedu.address.model.listmanager.ActivityManager;
//=======
//import seedu.address.model.listmanager.AccommodationBookingManager;
//>>>>>>> Improve Code Quality
//import seedu.address.model.listmanager.FixedExpenseManager;
//import seedu.address.model.listmanager.PackingListManager;
//import seedu.address.model.listmanager.TransportBookingManager;
//
///**
// * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
// */
//public class ListCommandTest {
//
//    private Model model;
//    private Model expectedModel;
//
//    @BeforeEach
//    public void setUp() {
//        model = new ModelManager(getTypicalAddressBook(),
//                new TransportBookingManager(), new FixedExpenseManager(), new PackingListManager(),
//<<<<<<< HEAD
//                new ActivityManager(),
//                new UserPrefs());
//        expectedModel = new ModelManager(getTypicalAddressBook(),
//                new TransportBookingManager(), new FixedExpenseManager(), new PackingListManager(),
//                new ActivityManager(),
//                new UserPrefs());
//=======
//                new AccommodationBookingManager(), new UserPrefs());
//        expectedModel = new ModelManager(getTypicalAddressBook(),
//                new TransportBookingManager(), new FixedExpenseManager(), new PackingListManager(),
//                new AccommodationBookingManager(), new UserPrefs());
//>>>>>>> Improve Code Quality
//    }
//
//    @Test
//    public void execute_listIsNotFiltered_showsSameList() {
//        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
//    }
//
//    @Test
//    public void execute_listIsFiltered_showsEverything() {
//        showPersonAtIndex(model, INDEX_FIRST_PERSON);
//        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
//    }
//}

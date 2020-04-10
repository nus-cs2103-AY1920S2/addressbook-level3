//package seedu.address.logic.commands;
//
//import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
//import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
//
//import org.junit.jupiter.api.Test;
//
//import seedu.address.model.AddressBook;
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
//public class ClearCommandTest {
//
//    @Test
//    public void execute_emptyAddressBook_success() {
//        Model model = new ModelManager();
//        Model expectedModel = new ModelManager();
//
//        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
//    }
//
//    @Test
//    public void execute_nonEmptyAddressBook_success() {
//        Model model = new ModelManager(getTypicalAddressBook(), new TransportBookingManager(),
//<<<<<<< HEAD
//                new FixedExpenseManager(), new PackingListManager(), new ActivityManager(), new UserPrefs());
//        Model expectedModel = new ModelManager(getTypicalAddressBook(), new TransportBookingManager(),
//                new FixedExpenseManager(), new PackingListManager(), new ActivityManager(), new UserPrefs());
//=======
//                new FixedExpenseManager(), new PackingListManager(), new AccommodationBookingManager(),
//                new UserPrefs());
//        Model expectedModel = new ModelManager(getTypicalAddressBook(), new TransportBookingManager(),
//                new FixedExpenseManager(), new PackingListManager(), new AccommodationBookingManager(),
//                new UserPrefs());
//>>>>>>> Improve Code Quality
//        expectedModel.setAddressBook(new AddressBook());
//
//        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
//    }
//
//}

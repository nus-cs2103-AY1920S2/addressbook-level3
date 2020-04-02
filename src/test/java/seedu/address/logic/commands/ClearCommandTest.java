package seedu.address.logic.commands;

import static seedu.address.logic.commands.ClearCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.EventSchedule;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.RestaurantBook;
import seedu.address.model.Scheduler;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        CommandResult expectedCommandResult = new CommandResult(String.format(MESSAGE_SUCCESS),
                false, false, false,
                false, false, true, false, false);

        assertCommandSuccess(new ClearCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(),
                new RestaurantBook(),
                new Scheduler(),
                new EventSchedule(),
                new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(),
                new RestaurantBook(),
                new Scheduler(),
                new EventSchedule(),
                new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());

        CommandResult expectedCommandResult = new CommandResult(String.format(MESSAGE_SUCCESS),
                false, false, false,
                false, false, true, false, false);

        assertCommandSuccess(new ClearCommand(), model, expectedCommandResult, expectedModel);
    }

}

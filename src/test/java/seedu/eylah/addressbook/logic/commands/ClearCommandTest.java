package seedu.eylah.addressbook.logic.commands;

import static seedu.eylah.addressbook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.eylah.addressbook.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.eylah.addressbook.model.AddressBook;
import seedu.eylah.addressbook.model.Model;
import seedu.eylah.addressbook.model.ModelManager;
import seedu.eylah.addressbook.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}

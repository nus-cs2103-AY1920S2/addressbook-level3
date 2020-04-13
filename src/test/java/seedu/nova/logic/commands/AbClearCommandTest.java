package seedu.nova.logic.commands;

import static seedu.nova.logic.commands.CommandTestUtil.assertCommandSuccess;

import static seedu.nova.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.nova.logic.commands.abcommands.AbClearCommand;
import seedu.nova.model.AddressBook;
import seedu.nova.model.Model;
import seedu.nova.model.ModelManager;
import seedu.nova.model.Nova;
import seedu.nova.model.UserPrefs;
import seedu.nova.model.VersionedAddressBook;


public class AbClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Nova nova = new Nova();
        nova.setAddressBookNova(new VersionedAddressBook(new AddressBook()));
        Model model = new ModelManager(nova, new UserPrefs());
        Model expectedModel = new ModelManager(nova, new UserPrefs());

        assertCommandSuccess(new AbClearCommand(), model, AbClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Nova nova = new Nova();
        nova.setAddressBookNova(new VersionedAddressBook(getTypicalAddressBook()));
        Model model = new ModelManager(nova, new UserPrefs());
        Model expectedModel = new ModelManager(nova, new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new AbClearCommand(), model, AbClearCommand.MESSAGE_SUCCESS, expectedModel);
    }


}

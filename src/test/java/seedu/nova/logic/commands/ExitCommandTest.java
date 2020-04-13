package seedu.nova.logic.commands;

import static seedu.nova.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.nova.logic.commands.commoncommands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;
import static seedu.nova.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.nova.logic.commands.commoncommands.ExitCommand;
import seedu.nova.model.Model;
import seedu.nova.model.ModelManager;
import seedu.nova.model.Nova;
import seedu.nova.model.UserPrefs;
import seedu.nova.model.VersionedAddressBook;

public class ExitCommandTest {
    private final Nova nova = new Nova();
    private final VersionedAddressBook ab = new VersionedAddressBook(getTypicalAddressBook());

    @Test
    public void execute_exit_success() {
        nova.setAddressBookNova(ab);
        Model model = new ModelManager(nova, new UserPrefs());
        Model expectedModel = new ModelManager(nova, new UserPrefs());

        CommandResult expectedCommandResult = new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
        assertCommandSuccess(new ExitCommand(), model, expectedCommandResult, expectedModel);
    }
}

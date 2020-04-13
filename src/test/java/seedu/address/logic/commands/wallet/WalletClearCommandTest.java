package seedu.address.logic.commands.wallet;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalWallet.getTypicalWallet;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.Wallet;

public class WalletClearCommandTest {
    @Test
    public void execute_emptyWallet_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new WalletClearCommand(), model, WalletClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyWallet_success() {
        Model model = new ModelManager(getTypicalAddressBook(), getTypicalWallet(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalWallet(), new UserPrefs());
        expectedModel.setWallet(new Wallet());

        assertCommandSuccess(new WalletClearCommand(), model, WalletClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}

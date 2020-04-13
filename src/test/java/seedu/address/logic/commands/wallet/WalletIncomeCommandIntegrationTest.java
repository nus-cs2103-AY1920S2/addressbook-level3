package seedu.address.logic.commands.wallet;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalWallet.TA_JOB;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code WalletIncomeCommand}.
 */
public class WalletIncomeCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newIncome_success() {

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addIncome(TA_JOB);

        assertCommandSuccess(new WalletIncomeCommand(TA_JOB), model,
                String.format(WalletIncomeCommand.MESSAGE_SUCCESS, TA_JOB), expectedModel);
    }

}

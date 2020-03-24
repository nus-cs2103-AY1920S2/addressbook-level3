package seedu.address.logic.commands;

import static seedu.address.logic.commands.ClearCommand.MESSAGE_ENQUIRY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CliSyntax.FLAG_FORCE_CLEAR;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;
import static seedu.address.testutil.TypicalReturnOrders.getTypicalReturnOrderBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.OrderBook;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void executeForceClear_emptyOrderBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(FLAG_FORCE_CLEAR.toString()), model, ClearCommand.MESSAGE_SUCCESS,
                expectedModel);
    }

    @Test
    public void executeWithoutForceClear_replyEnquiryMessage_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(null), model, new CommandResult(MESSAGE_ENQUIRY, false, false, true),
                expectedModel);
    }

    @Test
    public void execute_nonEmptyOrderBook_success() {
        Model model = new ModelManager(getTypicalOrderBook(), getTypicalReturnOrderBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalOrderBook(), getTypicalReturnOrderBook(), new UserPrefs());
        expectedModel.setOrderBook(new OrderBook());

        assertCommandSuccess(new ClearCommand(FLAG_FORCE_CLEAR.toString()), model, ClearCommand.MESSAGE_SUCCESS,
                expectedModel);
    }
}

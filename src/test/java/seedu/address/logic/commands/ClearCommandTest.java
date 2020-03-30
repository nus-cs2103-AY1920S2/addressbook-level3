package seedu.address.logic.commands;

import static seedu.address.logic.commands.ClearCommand.MESSAGE_ENQUIRY_BOTH_BOOK;
import static seedu.address.logic.commands.ClearCommand.MESSAGE_ENQUIRY_ORDER_BOOK;
import static seedu.address.logic.commands.ClearCommand.MESSAGE_ENQUIRY_RETURN_BOOK;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CliSyntax.FLAG_FORCE_CLEAR;
import static seedu.address.logic.parser.CliSyntax.FLAG_ORDER_BOOK;
import static seedu.address.logic.parser.CliSyntax.FLAG_RETURN_BOOK;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;
import static seedu.address.testutil.TypicalReturnOrders.getTypicalReturnOrderBook;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.OrderBook;
import seedu.address.model.ReturnOrderBook;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {
    private HashSet<String> flags;

    @BeforeEach
    public void setup() {
        flags = new HashSet<>();
    }

    @Test
    public void executeWithEmptyBooks_forceClear_emptyBothBooks() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        flags.add(FLAG_FORCE_CLEAR.toString());

        assertCommandSuccess(new ClearCommand(flags), model, ClearCommand.MESSAGE_SUCCESS_BOTH_BOOK,
                expectedModel);
    }

    @Test
    public void executeWithEmptyBooks_forceClearWithOrderFlag_emptyOrderBook() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        flags.add(FLAG_FORCE_CLEAR.toString());
        flags.add(FLAG_ORDER_BOOK.toString());

        assertCommandSuccess(new ClearCommand(flags), model, ClearCommand.MESSAGE_SUCCESS_ORDER_BOOK,
                expectedModel);
    }

    @Test
    public void executeWithEmptyBooks_forceClearWithReturnFlag_emptyReturnOrderBook() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        flags.add(FLAG_FORCE_CLEAR.toString());
        flags.add(FLAG_RETURN_BOOK.toString());

        assertCommandSuccess(new ClearCommand(flags), model, ClearCommand.MESSAGE_SUCCESS_RETURN_BOOK,
                expectedModel);
    }

    @Test
    public void executeWithEmptyBooks_withoutForceClear_replyCorrectEnquiryMessage() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(null), model,
                new CommandResult(MESSAGE_ENQUIRY_BOTH_BOOK, false, false, true, false),
                expectedModel);
    }

    @Test
    public void executeWithEmptyBooks_orderFlagOnly_replyCorrectEnquiryMessage() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        flags.add(FLAG_ORDER_BOOK.toString());

        assertCommandSuccess(new ClearCommand(flags), model,
                new CommandResult(MESSAGE_ENQUIRY_ORDER_BOOK, false, false, true, false),
                expectedModel);
    }

    @Test
    public void executeWithEmptyBooks_returnFlagOnly_replyCorrectEnquiryMessage() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        flags.add(FLAG_RETURN_BOOK.toString());

        assertCommandSuccess(new ClearCommand(flags), model,
                new CommandResult(MESSAGE_ENQUIRY_RETURN_BOOK, false, false, true, false),
                expectedModel);
    }

    @Test
    public void execute_forceClear_emptyBothBooks() {
        Model model = new ModelManager(getTypicalOrderBook(), getTypicalReturnOrderBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalOrderBook(), getTypicalReturnOrderBook(), new UserPrefs());
        expectedModel.setOrderBook(new OrderBook());
        expectedModel.setReturnOrderBook(new ReturnOrderBook());

        flags.add(FLAG_FORCE_CLEAR.toString());

        assertCommandSuccess(new ClearCommand(flags), model, ClearCommand.MESSAGE_SUCCESS_BOTH_BOOK,
                expectedModel);
    }

    @Test
    public void execute_forceClearWithOrderFlag_emptyOrderBook() {
        Model model = new ModelManager(getTypicalOrderBook(), getTypicalReturnOrderBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalOrderBook(), getTypicalReturnOrderBook(), new UserPrefs());
        expectedModel.setOrderBook(new OrderBook());

        flags.add(FLAG_FORCE_CLEAR.toString());
        flags.add(FLAG_ORDER_BOOK.toString());

        assertCommandSuccess(new ClearCommand(flags), model, ClearCommand.MESSAGE_SUCCESS_ORDER_BOOK,
                expectedModel);
    }

    @Test
    public void execute_forceClearWithReturnFlag_emptyReturnOrderBook() {
        Model model = new ModelManager(getTypicalOrderBook(), getTypicalReturnOrderBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalOrderBook(), getTypicalReturnOrderBook(), new UserPrefs());
        expectedModel.setReturnOrderBook(new ReturnOrderBook());

        flags.add(FLAG_FORCE_CLEAR.toString());
        flags.add(FLAG_RETURN_BOOK.toString());

        assertCommandSuccess(new ClearCommand(flags), model, ClearCommand.MESSAGE_SUCCESS_RETURN_BOOK,
                expectedModel);
    }

    @Test
    public void execute_withoutForceClear_replyCorrectEnquiryMessage() {
        Model model = new ModelManager(getTypicalOrderBook(), getTypicalReturnOrderBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalOrderBook(), getTypicalReturnOrderBook(), new UserPrefs());

        assertCommandSuccess(new ClearCommand(null), model,
                new CommandResult(MESSAGE_ENQUIRY_BOTH_BOOK, false, false, true, false),
                expectedModel);
    }

    @Test
    public void execute_orderFlagOnly_replyCorrectEnquiryMessage() {
        Model model = new ModelManager(getTypicalOrderBook(), getTypicalReturnOrderBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalOrderBook(), getTypicalReturnOrderBook(), new UserPrefs());
        flags.add(FLAG_ORDER_BOOK.toString());

        assertCommandSuccess(new ClearCommand(flags), model,
                new CommandResult(MESSAGE_ENQUIRY_ORDER_BOOK, false, false, true, false),
                expectedModel);
    }

    @Test
    public void execute_returnFlagOnly_replyCorrectEnquiryMessage() {
        Model model = new ModelManager(getTypicalOrderBook(), getTypicalReturnOrderBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalOrderBook(), getTypicalReturnOrderBook(), new UserPrefs());
        flags.add(FLAG_RETURN_BOOK.toString());

        assertCommandSuccess(new ClearCommand(flags), model,
                new CommandResult(MESSAGE_ENQUIRY_RETURN_BOOK, false, false, true, false),
                expectedModel);
    }
}

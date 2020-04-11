package seedu.expensela.logic.commands;

import static seedu.expensela.logic.commands.CommandTestUtil.assertCommandFailure;

import org.junit.jupiter.api.Test;

import seedu.expensela.commons.core.Messages;
import seedu.expensela.model.ExpenseLa;
import seedu.expensela.model.GlobalData;
import seedu.expensela.model.Model;
import seedu.expensela.model.ModelManager;
import seedu.expensela.model.UserPrefs;

public class ExportCommandTest {

    private Model model;

    @Test
    public void execute_emptyTransactionList_throwsCommandException() {
        model = new ModelManager(new ExpenseLa(), new UserPrefs(), new GlobalData());
        ExportCommand exportCommand = new ExportCommand();

        assertCommandFailure(exportCommand, model, Messages.MESSAGE_EMPTY_TRANSACTION_LIST);
    }

}

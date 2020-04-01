package seedu.expensela.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.expensela.testutil.TypicalTransactions.getTypicalExpenseLa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;

import seedu.expensela.model.Model;
import seedu.expensela.model.ModelManager;
import seedu.expensela.model.UserPrefs;
import seedu.expensela.model.transaction.Transaction;

public class ResetBalanceCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalExpenseLa(), new UserPrefs());
        UserPrefs up = new UserPrefs();
        ObservableList<Transaction> transactionList = model.getExpenseLa().getTransactionList();
        double balance = 0;
        for (int i = 0; i < transactionList.size(); i++) {
            if (transactionList.get(i).getAmount().positive) {
                balance += transactionList.get(i).getAmount().transactionAmount;
            } else {
                balance -= transactionList.get(i).getAmount().transactionAmount;
            }
        }
        up.setTotalBalance(balance);
        expectedModel = new ModelManager(model.getExpenseLa(), up);
    }

    @Test
    public void execute_resetBalance() throws Exception {
        ObservableList<Transaction> transactionList = model.getExpenseLa().getTransactionList();
        double balance = 0;
        for (int i = 0; i < transactionList.size(); i++) {
            if (transactionList.get(i).getAmount().positive) {
                balance += transactionList.get(i).getAmount().transactionAmount;
            } else {
                balance -= transactionList.get(i).getAmount().transactionAmount;
            }
        }
        CommandResult result = new ResetBalanceCommand().execute(model);
        assertEquals(String.format(ResetBalanceCommand.MESSAGE_SUCCESS, balance), result.getFeedbackToUser());
    }
}

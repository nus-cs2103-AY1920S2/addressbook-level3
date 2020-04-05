package seedu.expensela.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.expensela.testutil.TypicalTransactions.getTypicalExpenseLa;

import java.text.DecimalFormat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;

import seedu.expensela.model.Balance;
import seedu.expensela.model.GlobalData;
import seedu.expensela.model.Model;
import seedu.expensela.model.ModelManager;
import seedu.expensela.model.UserPrefs;
import seedu.expensela.model.transaction.Transaction;

public class ResetBalanceCommandTest {

    private static DecimalFormat df2 = new DecimalFormat("#.##");

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalExpenseLa(), new UserPrefs(), new GlobalData());
        GlobalData gd = new GlobalData();
        ObservableList<Transaction> transactionList = model.getExpenseLa().getTransactionList();
        double balance = 0;
        for (int i = 0; i < transactionList.size(); i++) {
            if (transactionList.get(i).getAmount().positive) {
                balance += transactionList.get(i).getAmount().transactionAmount;
            } else {
                balance -= transactionList.get(i).getAmount().transactionAmount;
            }
        }
        gd.setTotalBalance(new Balance(df2.format(balance)));
        expectedModel = new ModelManager(model.getExpenseLa(), new UserPrefs(), gd);
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

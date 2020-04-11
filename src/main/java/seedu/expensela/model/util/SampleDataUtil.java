package seedu.expensela.model.util;

import java.time.LocalDate;

import seedu.expensela.model.ExpenseLa;
import seedu.expensela.model.ReadOnlyExpenseLa;
import seedu.expensela.model.monthlydata.Budget;
import seedu.expensela.model.monthlydata.Expense;
import seedu.expensela.model.monthlydata.Income;
import seedu.expensela.model.monthlydata.MonthlyData;
import seedu.expensela.model.transaction.Amount;
import seedu.expensela.model.transaction.Category;
import seedu.expensela.model.transaction.Date;
import seedu.expensela.model.transaction.Name;
import seedu.expensela.model.transaction.Remark;
import seedu.expensela.model.transaction.Transaction;


/**
 * Contains utility methods for populating {@code ExpenseLa} with sample data.
 */
public class SampleDataUtil {
    public static Transaction[] getSampleTransactions() {
        return new Transaction[] {
            new Transaction(new Name("Laptop"), new Amount("434", false),
                    new Date(LocalDate.now().toString()), new Remark("New Laptop"), new Category("SHOPPING")),
            new Transaction(new Name("Masks"), new Amount("6.00", false),
                new Date(LocalDate.now().toString()), new Remark("Face mask for family"), new Category("UTILITIES")),
            new Transaction(new Name("Breakfast"), new Amount("10.00", false),
                new Date(LocalDate.now().toString()), new Remark("Breakfast at Macs"), new Category("FOOD")),
            new Transaction(new Name("Pocket Money"), new Amount("450", true),
                new Date(LocalDate.now().toString()), new Remark("Monthly pocket money"), new Category("INCOME"))
        };
    }

    public static MonthlyData getSampleMonthlyData() {
        return new MonthlyData("1", new Budget("450"), new Expense("450"), new Income("450"));
    }

    public static ReadOnlyExpenseLa getSampleExpenseLa() {
        ExpenseLa sampleEl = new ExpenseLa();
        for (Transaction sampleTransaction : getSampleTransactions()) {
            sampleEl.addTransaction(sampleTransaction);
        }
        sampleEl.setMonthlyData(getSampleMonthlyData());
        return sampleEl;
    }

}

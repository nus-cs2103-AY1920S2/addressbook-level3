package seedu.expensela.model.util;

import seedu.expensela.model.ExpenseLa;
import seedu.expensela.model.ReadOnlyExpenseLa;
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
            new Transaction(new Name("Apple AirPods"), new Amount("188", true),
                new Date("2020-02-14"), new Remark("On discount for valentine's"), new Category("SHOPPING")),
            new Transaction(new Name("Sushi"), new Amount("6", true),
                new Date("2020-02-06"), new Remark("Snack after lunch"), new Category("FOOD")),
            new Transaction(new Name("Dominoes Pizza"), new Amount("34", true),
                new Date("2020-02-02"), new Remark("Party with friends"), new Category("FOOD"))
        };
    }

    public static ReadOnlyExpenseLa getSampleExpenseLa() {
        ExpenseLa sampleEl = new ExpenseLa();
        for (Transaction sampleTransaction : getSampleTransactions()) {
            sampleEl.addTransaction(sampleTransaction);
        }
        return sampleEl;
    }

}

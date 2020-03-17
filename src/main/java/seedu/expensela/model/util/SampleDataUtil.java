package seedu.expensela.model.util;

import seedu.expensela.model.ExpenseLa;
import seedu.expensela.model.ReadOnlyExpenseLa;
import seedu.expensela.model.transaction.Amount;
import seedu.expensela.model.transaction.Date;
import seedu.expensela.model.transaction.Name;
import seedu.expensela.model.transaction.Transaction;
<<<<<<< HEAD
import seedu.expensela.model.transaction.Type;
import seedu.expensela.model.transaction.Remark;
=======
import seedu.expensela.model.transaction.*;
>>>>>>> 27d97e7fec796f2b553eef875187febf3a2528e7

/**
 * Contains utility methods for populating {@code ExpenseLa} with sample data.
 */
public class SampleDataUtil {
    public static Transaction[] getSampleTransactions() {
        return new Transaction[] {
<<<<<<< HEAD
            new Transaction(new TransactionID("12345678"), new Name("Domino's Pizza"),
                    new Amount("25", false), new Date("2019-12-10"),
                    new Type("Food"), new Remark("Treat friends")),
            new Transaction(new TransactionID("12345679"), new Name("Grab to work"),
                    new Amount("15", false), new Date("2019-12-23"),
                    new Type("Transport"), new Remark("Split with Mark")),
            new Transaction(new TransactionID("12345680"), new Name("Angpao Money"),
                new Amount("200", true), new Date("2020-01-25"),
                new Type("Others"), new Remark("GONG XI FA CAI")),
            new Transaction(new TransactionID("12345681"), new Name("CS1231 Textbook"),
                    new Amount("56", false), new Date("2020-01-30"),
                    new Type("Others"), new Remark(null)),
            new Transaction(new TransactionID("12345682"), new Name("BlueSG GF back"),
                new Amount("12", false), new Date("2020-02-12"),
                new Type("Transport"), new Remark("Claim coupon")),
            new Transaction(new TransactionID("12345683"), new Name("Bedok 88 BCM"),
                    new Amount("4.50", false), new Date("2020-02-25",
                    new Type("Food"), new Remark("BEST SOUP BCM")))
        };
    }

    public static ReadOnlyExpenseLa getSampleExpenseLa() {
        ExpenseLa sampleEL = new ExpenseLa();
        for (Transaction sampleTransaction : getSampleTransactions()) {
            sampleEL.addTransaction(sampleTransaction);
=======
            new Transaction(new Name("Apple AirPods"), new Amount("188", true),
                new Date("2020-02-14"), new Remark("On discount for valentine's"), new Category("SHOPPING")),
            new Transaction(new Name("Sushi"), new Amount("6", true),
                new Date("2020-02-06"), new Remark("Snack after lunch"), new Category("FOOD")),
            new Transaction(new Name("Dominoes Pizza"), new Amount("34", true),
                new Date("2020-02-02"), new Remark("Party with friends"), new Category("FOOD"))
        };
    }

    public static ReadOnlyExpenseLa getSampleAddressBook() {
        ExpenseLa sampleAb = new ExpenseLa();
        for (Transaction sampleTransaction : getSamplePersons()) {
            sampleAb.addPerson(sampleTransaction);
>>>>>>> 27d97e7fec796f2b553eef875187febf3a2528e7
        }
        return sampleEL;
    }

}

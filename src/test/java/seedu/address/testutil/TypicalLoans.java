package seedu.address.testutil;

import seedu.address.model.transaction.Loan;

/**
 * A utility class containing a list of {@code Loan} objects to be used in tests.
 */
public class TypicalLoans {
    public static final Loan BREAKFAST = new TransactionBuilder()
            .withDescription("Breakfast")
            .withAmount("5")
            .withDate("08/08/2018")
            .buildLoan();
    public static final Loan LUNCH = new TransactionBuilder()
            .withDescription("Lunch")
            .withAmount("12")
            .withDate("09/09/2019")
            .buildLoan();
    public static final Loan DINNER = new TransactionBuilder()
            .withDescription("Dinner")
            .withAmount("22")
            .withDate("02/02/2020")
            .buildLoan();
    public static final Loan SUPPER = new TransactionBuilder()
            .withDescription("Supper")
            .withAmount("8")
            .withDate("18/08/2018")
            .buildLoan();
    public static final Loan DESSERT = new TransactionBuilder()
            .withDescription("Dessert")
            .withAmount("6")
            .withDate("19/09/2019")
            .buildLoan();
    public static final Loan SHOPPING = new TransactionBuilder()
            .withDescription("Shopping")
            .withAmount("50")
            .withDate("20/02/2020")
            .buildLoan();
    public static final Loan TRAVEL = new TransactionBuilder()
            .withDescription("Travel")
            .withAmount("120")
            .withDate("22/02/2022")
            .buildLoan();
}

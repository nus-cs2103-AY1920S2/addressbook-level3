package seedu.expensela.testutil;

import static seedu.expensela.logic.commands.CommandTestUtil.VALID_AMOUNT_AIRPODS;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_AMOUNT_INVESTMENT;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_AMOUNT_INVESTMENT_1;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_AMOUNT_PIZZA;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_CATEGORY_FOOD;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_CATEGORY_INVESTMENT;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_CATEGORY_SHOPPING;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_DATE_AIRPODS;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_DATE_INVESTMENT;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_DATE_PIZZA;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_NAME_AIRPODS;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_NAME_INVESTMENT;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_NAME_PIZZA;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_REMARK_AIRPODS;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_REMARK_INVESTMENT;
import static seedu.expensela.logic.commands.CommandTestUtil.VALID_REMARK_PIZZA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.expensela.model.ExpenseLa;
import seedu.expensela.model.Filter;
import seedu.expensela.model.monthlydata.Budget;
import seedu.expensela.model.monthlydata.Expense;
import seedu.expensela.model.monthlydata.Income;
import seedu.expensela.model.monthlydata.MonthlyData;
import seedu.expensela.model.transaction.CategoryEqualsKeywordPredicate;
import seedu.expensela.model.transaction.DateEqualsKeywordPredicate;
import seedu.expensela.model.transaction.Transaction;

/**
 * A utility class containing a list of {@code Transaction} objects to be used in tests.
 */
public class TypicalTransactions {

    public static final Transaction APPLES = new TransactionBuilder().withName("Fuji Apples from Cold Storage")
            .withAmount("10.00", false)
            .withDate("2020-02-26")
            .withRemark("Give my mate some")
            .withCategory("GROCERIES")
            .build();
    public static final Transaction BONUS = new TransactionBuilder().withName("Bonus")
            .withAmount("1000.00", true)
            .withDate("2020-02-27")
            .withRemark("End of year bonus")
            .withCategory("MISC")
            .build();
    public static final Transaction CAR_GAS = new TransactionBuilder().withName("Car Gas")
            .withAmount("50.49", false)
            .withDate("2020-02-28")
            .withRemark("for my car")
            .withCategory("UTILITIES")
            .build();
    public static final Transaction DOMINOS = new TransactionBuilder().withName("Domino's Pizza")
            .withAmount("36.00", false)
            .withDate("2020-03-01")
            .withRemark("Treat Friends")
            .withCategory("FOOD")
            .build();
    public static final Transaction ELECTRICITY = new TransactionBuilder().withName("Electricity bill")
            .withAmount("250.00", false)
            .withDate("2020-03-02")
            .withRemark("expensive")
            .withCategory("UTILITIES")
            .build();
    public static final Transaction FLOWERS = new TransactionBuilder().withName("Flowers")
            .withAmount("12.00", false)
            .withDate("2020-03-03")
            .withRemark("Babe likes pink")
            .withCategory("SHOPPING")
            .build();
    public static final Transaction GRAB = new TransactionBuilder().withName("Grab to work")
            .withAmount("100.00", false)
            .withDate("2020-03-04")
            .withRemark("Claim from boss")
            .withCategory("TRANSPORT")
            .build();

    // Manually added
    public static final Transaction HEALTH = new TransactionBuilder().withName("Health Checkup")
            .withAmount("100.00", false)
            .withDate("2020-03-05")
            .withRemark("Annual health checkup")
            .withCategory("HEALTH")
            .build();
    public static final Transaction ICE_CREAM = new TransactionBuilder().withName("Ice Cream from Udders")
            .withAmount("6.00", false)
            .withDate("2020-03-06")
            .withRemark("Rum ice cream is the best")
            .withCategory("FOOD")
            .build();

    // Manually added - Transaction's details found in {@code CommandTestUtil}
    public static final Transaction PIZZA = new TransactionBuilder().withName(VALID_NAME_PIZZA)
            .withAmount(VALID_AMOUNT_PIZZA, false)
            .withDate(VALID_DATE_PIZZA)
            .withRemark(VALID_REMARK_PIZZA)
            .withCategory(VALID_CATEGORY_FOOD)
            .build();
    public static final Transaction AIRPODS = new TransactionBuilder().withName(VALID_NAME_AIRPODS)
            .withAmount(VALID_AMOUNT_AIRPODS, false)
            .withDate(VALID_DATE_AIRPODS)
            .withRemark(VALID_REMARK_AIRPODS)
            .withCategory(VALID_CATEGORY_SHOPPING)
            .build();
    public static final Transaction MAX_INVESTMENT = new TransactionBuilder().withName(VALID_NAME_INVESTMENT)
            .withAmount(VALID_AMOUNT_INVESTMENT, false)
            .withDate(VALID_DATE_INVESTMENT)
            .withRemark(VALID_REMARK_INVESTMENT)
            .withCategory(VALID_CATEGORY_INVESTMENT)
            .build();
    public static final Transaction MIN_INVESTMENT = new TransactionBuilder().withName(VALID_NAME_INVESTMENT)
            .withAmount(VALID_AMOUNT_INVESTMENT_1, false)
            .withDate(VALID_DATE_INVESTMENT)
            .withRemark(VALID_REMARK_INVESTMENT)
            .withCategory(VALID_CATEGORY_INVESTMENT)
            .build();

    public static final String KEYWORD_MATCHING_TREAT = "Treat"; // A keyword that matches TREAT

    private TypicalTransactions() {} // prevents instantiation

    /**
     * Returns an {@code ExpenseLa} with all the typical transactions.
     */
    public static ExpenseLa getTypicalExpenseLa() {
        ExpenseLa el = new ExpenseLa();
        MonthlyData monthlyData = new MonthlyData("1", new Budget("1000"), new Expense("500"), new Income("2000"));
        for (Transaction transaction : getTypicalTransactions()) {
            el.addTransaction(transaction);
        }
        el.setFilter(new Filter(new CategoryEqualsKeywordPredicate(Arrays.asList("ALL")),
                new DateEqualsKeywordPredicate(Arrays.asList("ALL"))));
        el.setMonthlyData(monthlyData);
        return el;
    }

    public static List<Transaction> getTypicalTransactions() {
        return new ArrayList<>(Arrays.asList(APPLES, BONUS, CAR_GAS, DOMINOS, ELECTRICITY, FLOWERS, GRAB));
    }
}

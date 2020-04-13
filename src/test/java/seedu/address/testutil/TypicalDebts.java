// @@author cheyannesim
package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.transaction.Debt;

/**
 * A utility class containing a list of {@code Debt} objects to be used in tests.
 */
public class TypicalDebts {

    public static final Debt SUPPER = new TransactionBuilder()
            .withDescription("Supper")
            .withAmount("10")
            .withDate("23/01/2020")
            .buildDebt();
    public static final Debt TEXTBOOK = new TransactionBuilder()
            .withDescription("Textbook")
            .withAmount("100")
            .withDate("10/08/2019")
            .buildDebt();
    public static final Debt MILKTEA = new TransactionBuilder()
            .withDescription("Milk Tea")
            .withAmount("3")
            .withDate("21/02/2020")
            .buildDebt();
    public static final Debt MOVIE = new TransactionBuilder()
            .withDescription("Movie")
            .withAmount("13")
            .withDate("26/12/2019")
            .buildDebt();
    public static final Debt IPHONE = new TransactionBuilder()
            .withDescription("iPhone 13")
            .withAmount("1000")
            .withDate("03/10/2022")
            .buildDebt();
    public static final Debt TAOBAO = new TransactionBuilder()
            .withDescription("TaoBao")
            .withAmount("11")
            .withDate("11/11/2019")
            .buildDebt();
    public static final Debt MCGRIDDLE = new TransactionBuilder()
            .withDescription("McGriddle")
            .withAmount("5.40")
            .withDate("19/02/2020")
            .buildDebt();

    private TypicalDebts() {
    } // prevents instantiation


    public static List<Debt> getTypicalDebts() {
        return new ArrayList<>(Arrays.asList(SUPPER, TEXTBOOK, MILKTEA, MOVIE, IPHONE, TAOBAO, MCGRIDDLE));
    }
}

package csdev.couponstash.logic.commands;

import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_CONDITION;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_EXPIRY_DATE;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_LIMIT;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_NAME;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_PROMO_CODE;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_SAVINGS;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_START_DATE;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_TAG;
import static csdev.couponstash.logic.parser.CliSyntax.PREFIX_USAGE;
import static csdev.couponstash.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import csdev.couponstash.commons.core.index.Index;
import csdev.couponstash.commons.moneysymbol.MoneySymbol;
import csdev.couponstash.logic.commands.exceptions.CommandException;
import csdev.couponstash.model.CouponStash;
import csdev.couponstash.model.Model;
import csdev.couponstash.model.coupon.Coupon;
import csdev.couponstash.model.coupon.NameContainsKeywordsPredicate;
import csdev.couponstash.model.coupon.savings.MonetaryAmount;
import csdev.couponstash.model.coupon.savings.Saveable;
import csdev.couponstash.model.coupon.savings.Savings;
import csdev.couponstash.testutil.EditCouponDescriptorBuilder;
import csdev.couponstash.testutil.MoneySymbolStub;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PROMO_CODE_AMY = "ILOVESTASH";
    public static final String VALID_PROMO_CODE_BOB = "STASH4LYFE";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_EXPIRY_DATE_AMY = "30-12-2020";
    public static final String VALID_EXPIRY_DATE_BOB = "31-12-2020";
    public static final String VALID_START_DATE_AMY = "01-12-2020";
    public static final String VALID_START_DATE_BOB = "02-12-2020";
    public static final String VALID_REMIND_DATE_AMY = "27-12-2020";
    public static final String VALID_REMIND_DATE_BOB = "28-12-2020";
    public static final String VALID_SAVEABLE_CAKE = "Cake";
    public static final String VALID_SAVEABLE_CROISSANT = "Croissant";
    public static final Savings VALID_SAVINGS_AMY = new Savings(
            Arrays.asList(new Saveable(VALID_SAVEABLE_CAKE), new Saveable(VALID_SAVEABLE_CROISSANT))
    );
    public static final int VALID_MONETARY_INTEGER_AMOUNT_ONE = 1;
    public static final int VALID_MONETARY_DECIMAL_AMOUNT_FIFTY = 50;
    public static final int VALID_MONETARY_INTEGER_AMOUNT_TWO = 2;
    public static final int VALID_MONETARY_DECIMAL_AMOUNT_TWENTY = 20;
    public static final String VALID_MONETARY_AMOUNT_ONE_FIFTY = VALID_MONETARY_INTEGER_AMOUNT_ONE
            + "." + VALID_MONETARY_DECIMAL_AMOUNT_FIFTY;
    public static final String VALID_MONETARY_AMOUNT_TWO_TWENTY = VALID_MONETARY_INTEGER_AMOUNT_TWO
            + "." + VALID_MONETARY_DECIMAL_AMOUNT_TWENTY;

    public static final String VALID_SAVEABLE_COFFEE = "Coffee";
    public static final String VALID_SAVEABLE_TEA = "Tea";
    public static final Savings VALID_SAVINGS_BOB = new Savings(
            new MonetaryAmount(VALID_MONETARY_INTEGER_AMOUNT_TWO, VALID_MONETARY_DECIMAL_AMOUNT_TWENTY),
            Arrays.asList(new Saveable(VALID_SAVEABLE_COFFEE), new Saveable(VALID_SAVEABLE_TEA))
    );
    public static final MoneySymbol VALID_MONEY_SYMBOL = new MoneySymbolStub("RM");
    public static final int VALID_USAGE_AMY = 0;
    public static final int VALID_USAGE_BOB = 0;
    public static final int VALID_LIMIT_AMY = 1;
    public static final int VALID_LIMIT_BOB = 10;

    public static final String VALID_CONDITION_AMY = "While Stocks Last";
    public static final String VALID_CONDITION_BOB = "While Stocks Last";
    public static final String CONDITION_DESC_AMY = " " + PREFIX_CONDITION + VALID_CONDITION_AMY;
    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PROMO_CODE_DESC_AMY = " " + PREFIX_PROMO_CODE + VALID_PROMO_CODE_AMY;
    public static final String PROMO_CODE_DESC_BOB = " " + PREFIX_PROMO_CODE + VALID_PROMO_CODE_BOB;
    public static final String SAVINGS_DESC_AMY = " " + PREFIX_SAVINGS + VALID_SAVEABLE_CAKE
            + " " + PREFIX_SAVINGS + VALID_SAVEABLE_CROISSANT;
    public static final String SAVINGS_DESC_BOB = " " + PREFIX_SAVINGS + VALID_SAVEABLE_COFFEE
            + " " + PREFIX_SAVINGS + VALID_MONEY_SYMBOL + VALID_MONETARY_AMOUNT_TWO_TWENTY
            + " " + PREFIX_SAVINGS + VALID_SAVEABLE_TEA;
    public static final String SAVINGS_DESC_BOB_TWO_MONETARY_AMOUNT = " " + PREFIX_SAVINGS + VALID_SAVEABLE_COFFEE
            + " " + PREFIX_SAVINGS + VALID_MONEY_SYMBOL + VALID_MONETARY_AMOUNT_ONE_FIFTY
            + " " + PREFIX_SAVINGS + VALID_SAVEABLE_TEA
            + " " + PREFIX_SAVINGS + VALID_MONEY_SYMBOL + VALID_MONETARY_AMOUNT_TWO_TWENTY;
    public static final String EXPIRY_DATE_DESC_AMY = " " + PREFIX_EXPIRY_DATE + VALID_EXPIRY_DATE_AMY;
    public static final String EXPIRY_DATE_DESC_BOB = " " + PREFIX_EXPIRY_DATE + VALID_EXPIRY_DATE_BOB;
    public static final String START_DATE_DESC_AMY = " " + PREFIX_START_DATE + VALID_START_DATE_AMY;
    public static final String START_DATE_DESC_BOB = " " + PREFIX_START_DATE + VALID_START_DATE_BOB;
    public static final String USAGE_DESC_AMY = " " + PREFIX_USAGE + VALID_USAGE_AMY;
    public static final String USAGE_DESC_BOB = " " + PREFIX_USAGE + VALID_USAGE_BOB;
    public static final String LIMIT_DESC_AMY = " " + PREFIX_LIMIT + VALID_LIMIT_AMY;
    public static final String LIMIT_DESC_BOB = " " + PREFIX_LIMIT + VALID_LIMIT_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_SAVINGS_DESC = " " + PREFIX_SAVINGS; // cannot have blank savings
    public static final String INVALID_EXPIRY_DATE_DESC = " " + PREFIX_EXPIRY_DATE + "2-2-22"; // year should be in yyyy
    public static final String INVALID_START_DATE_DESC = " " + PREFIX_START_DATE + "2-2-22"; // year should be in yyyy
    public static final String INVALID_USAGE_DESC = " " + PREFIX_USAGE + "-10"; // '*' not allowed in tags
    public static final String INVALID_LIMIT_DESC = " " + PREFIX_LIMIT + "3c"; // '*' not allowed in tags
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditCouponDescriptor DESC_AMY;
    public static final EditCommand.EditCouponDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditCouponDescriptorBuilder()
                .withName(VALID_NAME_AMY)
                .withPromoCode(VALID_PROMO_CODE_AMY)
                .withSavings(VALID_SAVINGS_AMY)
                .withExpiryDate(VALID_EXPIRY_DATE_AMY)
                .withStartDate(VALID_START_DATE_AMY)
                .withUsage(VALID_USAGE_AMY)
                .withLimit(VALID_LIMIT_AMY)
                .withTags(VALID_TAG_FRIEND)
                .withRemindDate(VALID_REMIND_DATE_AMY)
                .withCondition(VALID_CONDITION_AMY)
                .build();
        DESC_BOB = new EditCouponDescriptorBuilder()
                .withName(VALID_NAME_BOB)
                .withPromoCode(VALID_PROMO_CODE_BOB)
                .withSavings(VALID_SAVINGS_BOB)
                .withExpiryDate(VALID_EXPIRY_DATE_BOB)
                .withStartDate(VALID_START_DATE_BOB)
                .withUsage(VALID_USAGE_BOB)
                .withLimit(VALID_LIMIT_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
                .withRemindDate(VALID_REMIND_DATE_BOB)
                .withCondition(VALID_CONDITION_BOB)
                .build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandResult, result);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the CouponStash, filtered coupon list and selected coupon in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        CouponStash expectedCouponStash = new CouponStash(actualModel.getCouponStash());
        List<Coupon> expectedFilteredList = new ArrayList<>(actualModel.getFilteredCouponList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedCouponStash, actualModel.getCouponStash());
        assertEquals(expectedFilteredList, actualModel.getFilteredCouponList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the coupon at the given {@code targetIndex} in the
     * {@code model}'s CouponStash.
     */
    public static void showCouponAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredCouponList().size());

        Coupon coupon = model.getFilteredCouponList().get(targetIndex.getZeroBased());
        final String[] splitName = coupon.getName().fullName.split("\\s+");
        model.updateFilteredCouponList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredCouponList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show the archived coupon,
     * ensuring that there is at least one archived coupon in the updated filtered list.
     */
    public static void showArchivedCoupon(Model model) {
        Coupon coupon = model.getFilteredCouponList().get(0);
        Coupon archivedCoupon = coupon.archive();
        model.setCoupon(coupon, archivedCoupon, "");
        model.updateFilteredCouponList(Model.PREDICATE_SHOW_ALL_ARCHIVED_COUPONS);

        assertTrue(model.getFilteredCouponList().get(0).getArchived().state);
        assertTrue(model.getFilteredCouponList().size() >= 1);
    }

}

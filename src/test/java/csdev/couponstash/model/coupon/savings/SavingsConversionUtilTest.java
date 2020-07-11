package csdev.couponstash.model.coupon.savings;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for the methods in SavingsConversionUtil.
 * Also acts as integration tests between Savings and
 * PureMonetarySavings.
 */
public class SavingsConversionUtilTest {

    @Test
    public void convertToPure_savingsWithMonetaryAmount_isConvertedSuccessfully() {
        MonetaryAmount ma = new MonetaryAmount(9, 0);
        List<Saveable> saveables = Arrays.asList(
                new Saveable("Red Wine"), new Saveable("Vodka"), new Saveable("Rum", 5)
        );
        Savings originalSavings = new Savings(ma, saveables);
        PureMonetarySavings expectedResult = new PureMonetarySavings(ma, saveables);
        assertEquals(SavingsConversionUtil.convertToPure(originalSavings), expectedResult);
    }

    @Test
    public void convertToPure_originalAmountGivenWithoutPercentageAmount_originalAmountIgnored() {
        MonetaryAmount ma = new MonetaryAmount(9, 0);
        List<Saveable> saveables = Arrays.asList(
                new Saveable("White Wine"), new Saveable("Beer", 4), new Saveable("Champagne")
        );
        MonetaryAmount originalAmount = new MonetaryAmount(1345, 97);
        Savings originalSavings = new Savings(ma, saveables);
        PureMonetarySavings expectedResult = new PureMonetarySavings(ma, saveables);
        assertEquals(SavingsConversionUtil.convertToPure(originalSavings, originalAmount), expectedResult);
    }

    @Test
    public void convertToPure_savingsWithPercentageAmount_isConvertedSuccessfully() {
        PercentageAmount pc = new PercentageAmount(13);
        List<Saveable> saveables = Arrays.asList(
                new Saveable("Whiskey", 6), new Saveable("Tequila", 3), new Saveable("Brandy")
        );
        MonetaryAmount originalAmount = new MonetaryAmount(1345, 0);
        // expected result is 174.85 which is 13% of 1345.0
        MonetaryAmount expectedAmount = new MonetaryAmount(174, 85);
        Savings originalSavings = new Savings(pc, saveables);
        PureMonetarySavings expectedResult = new PureMonetarySavings(expectedAmount, saveables);
        assertEquals(SavingsConversionUtil.convertToPure(originalSavings, originalAmount), expectedResult);
    }

    @Test
    public void convertToPure_originalAmountNotGivenForPercentageAmount_monetaryAmountSetToZero() {
        PercentageAmount pc = new PercentageAmount(42);
        List<Saveable> saveables = Arrays.asList(
                new Saveable("Gin", 3), new Saveable("Cider", 16)
        );
        Savings originalSavings = new Savings(pc, saveables);
        PureMonetarySavings expectedResult = new PureMonetarySavings(saveables);
        assertEquals(SavingsConversionUtil.convertToPure(originalSavings), expectedResult);
    }
}

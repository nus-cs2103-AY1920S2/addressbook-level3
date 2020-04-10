package csdev.couponstash.commons.moneysymbol;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for MoneySymbol.
 */
public class MoneySymbolTest {

    @BeforeEach
    public void setUp() {
        MoneySymbolTest.clearMoneySymbol();
    }

    @Test
    public void getMoneySymbol_calledTwice_onlyOneMoneySymbolExists() {
        MoneySymbol firstMoneySymbol = MoneySymbol.getMoneySymbol("TEST_123");
        MoneySymbol secondMoneySymbol = MoneySymbol.getMoneySymbol("DIFFERENT_1");
        assertSame(firstMoneySymbol, secondMoneySymbol);
    }

    @Test
    public void getMoneySymbol_calledTwice_secondValueIgnored() {
        String testString = "TEST_456";
        MoneySymbol.getMoneySymbol(testString);
        MoneySymbol secondMoneySymbol = MoneySymbol.getMoneySymbol("DIFFERENT_2");
        assertEquals(secondMoneySymbol.getString(), testString);
    }

    @Test
    public void setString_validString_returnsOriginalString() {
        String testString = "TEST_789";
        MoneySymbol moneySymbol = MoneySymbol.getMoneySymbol(testString);
        String returnedString = moneySymbol.setString("DIFFERENT_3");
        assertEquals(returnedString, testString);
    }

    @Test
    public void setString_validString_setsSuccessfully() {
        String firstString = "TEST_101112";
        String secondString = "DIFFERENT_4";
        MoneySymbol moneySymbol = MoneySymbol.getMoneySymbol(firstString);
        // check if symbol is correct before setting
        assertEquals(firstString, moneySymbol.getString());
        moneySymbol.setString(secondString);
        assertEquals(secondString, moneySymbol.getString());
    }

    /**
     * Deletes the current instance of MoneySymbol,
     * to facilitate testing.
     */
    private static void clearMoneySymbol() {
        MoneySymbolImpl.theOne = null;
    }
}

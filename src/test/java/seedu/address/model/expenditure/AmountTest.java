package seedu.address.model.expenditure;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AmountTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Amount(null));
    }

    @Test
    public void constructor_invalidAmount_throwsIllegalArgumentException() {
        String invalidAmount = "";
        assertThrows(IllegalArgumentException.class, () -> new Amount(invalidAmount));
    }

    @Test
    public void isValidAmount() {
        // null amount
        // assertThrows(NullPointerException.class, () -> Amount.isValidAmount(null));

        // blank amount
        assertFalse(Amount.isValidAmount("")); // empty string
        assertFalse(Amount.isValidAmount(" ")); // spaces only

        // missing parts
        // assertFalse(Amount.isValidAmount("@example.com")); // missing local part
        // assertFalse(Amount.isValidAmount("peterjackexample.com")); // missing '@' symbol
        // assertFalse(Amount.isValidAmount("peterjack@")); // missing domain name

        // // invalid parts
        // assertFalse(Amount.isValidAmount("peterjack@-")); // invalid domain name
        // assertFalse(Amount.isValidAmount("peterjack@exam_ple.com")); // underscore in domain name
        // assertFalse(Amount.isValidAmount("peter jack@example.com")); // spaces in local part
        // assertFalse(Amount.isValidAmount("peterjack@exam ple.com")); // spaces in domain name
        // assertFalse(Amount.isValidAmount(" peterjack@example.com")); // leading space
        // assertFalse(Amount.isValidAmount("peterjack@example.com ")); // trailing space
        // assertFalse(Amount.isValidAmount("peterjack@@example.com")); // double '@' symbol
        // assertFalse(Amount.isValidAmount("peter@jack@example.com")); // '@' symbol in local part
        // assertFalse(Amount.isValidAmount("peterjack@example@com")); // '@' symbol in domain name
        // assertFalse(Amount.isValidAmount("peterjack@.example.com")); // domain name starts with a period
        // assertFalse(Amount.isValidAmount("peterjack@example.com.")); // domain name ends with a period
        // assertFalse(Amount.isValidAmount("peterjack@-example.com")); // domain name starts with a hyphen
        // assertFalse(Amount.isValidAmount("peterjack@example.com-")); // domain name ends with a hyphen
        assertFalse(Amount.isValidAmount("fjksjd"));
        assertFalse(Amount.isValidAmount("3.2.1"));
        assertFalse(Amount.isValidAmount("-2"));
        assertFalse(Amount.isValidAmount(-3));

        // // valid amount
        // assertTrue(Amount.isValidAmount("PeterJack_1190@example.com"));
        // assertTrue(Amount.isValidAmount("a@bc")); // minimal
        // assertTrue(Amount.isValidAmount("test@localhost")); // alphabets only
        // assertTrue(Amount.isValidAmount("!#$%&'*+/=?`{|}~^.-@example.org")); // special characters local part
        // assertTrue(Amount.isValidAmount("123@145")); // numeric local part and domain name
        // assertTrue(Amount.isValidAmount("a1+be!@example1.com")); // mixture of alphanumeric and special characters
        // assertTrue(Amount.isValidAmount("peter_jack@very-very-very-long-example.com")); // long domain name
        // assertTrue(Amount.isValidAmount("if.you.dream.it_you.can.do.it@example.com")); // long local part
        assertTrue(Amount.isValidAmount(123.3));
        assertTrue(Amount.isValidAmount("123.1"));
        assertTrue(Amount.isValidAmount("0"));
    }
}

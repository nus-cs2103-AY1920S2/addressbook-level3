package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedIncome.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalWallet.ALLOWANCE;
import static seedu.address.testutil.TypicalWallet.INVALID_AMOUNT;
import static seedu.address.testutil.TypicalWallet.INVALID_DATE;
import static seedu.address.testutil.TypicalWallet.INVALID_DESC;
import static seedu.address.testutil.TypicalWallet.INVALID_TAG;
import static seedu.address.testutil.TypicalWallet.VALID_AMOUNT_ALLOWANCE;
import static seedu.address.testutil.TypicalWallet.VALID_DATE_ALLOWANCE;
import static seedu.address.testutil.TypicalWallet.VALID_DESC_ALLOWANCE;
import static seedu.address.testutil.TypicalWallet.VALID_TAG_ALLOWANCE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Date;
import seedu.address.model.transaction.Description;

class JsonAdaptedIncomeTest {

    @Test
    public void toModelType_validIncomeDetails_returnsIncome() throws Exception {
        JsonAdaptedIncome income = new JsonAdaptedIncome(ALLOWANCE);
        assertEquals(ALLOWANCE, income.toModelType());
    }

    @Test
    public void toModelType_invalidDesc_throwsIllegalValueException() {
        JsonAdaptedIncome income =
                new JsonAdaptedIncome(INVALID_DESC, VALID_AMOUNT_ALLOWANCE, VALID_DATE_ALLOWANCE, VALID_TAG_ALLOWANCE);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, income::toModelType);
    }

    @Test
    public void toModelType_nullDesc_throwsIllegalValueException() {
        JsonAdaptedIncome income =
                new JsonAdaptedIncome(null, VALID_AMOUNT_ALLOWANCE, VALID_DATE_ALLOWANCE, VALID_TAG_ALLOWANCE);
        String expectedMessage =
                String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, income::toModelType);
    }

    @Test
    public void toModelType_invalidAmount_throwsIllegalValueException() {
        JsonAdaptedIncome income =
                new JsonAdaptedIncome(VALID_DESC_ALLOWANCE, INVALID_AMOUNT, VALID_DATE_ALLOWANCE, VALID_TAG_ALLOWANCE);
        String expectedMessage = Amount.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, income::toModelType);
    }

    @Test
    public void toModelType_nullAmount_throwsIllegalValueException() {
        JsonAdaptedIncome income =
                new JsonAdaptedIncome(VALID_DESC_ALLOWANCE, null, VALID_DATE_ALLOWANCE, VALID_TAG_ALLOWANCE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, income::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedIncome income =
                new JsonAdaptedIncome(VALID_DESC_ALLOWANCE, VALID_AMOUNT_ALLOWANCE, INVALID_DATE, VALID_TAG_ALLOWANCE);
        String expectedMessage = Date.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, income::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedIncome income =
                new JsonAdaptedIncome(VALID_DESC_ALLOWANCE, VALID_AMOUNT_ALLOWANCE, null, VALID_TAG_ALLOWANCE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, income::toModelType);
    }

    @Test
    public void toModelType_invalidTag_throwsIllegalValueException() {
        JsonAdaptedIncome income =
                new JsonAdaptedIncome(VALID_DESC_ALLOWANCE, VALID_AMOUNT_ALLOWANCE, VALID_DATE_ALLOWANCE, INVALID_TAG);
        String expectedMessage = Tag.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, income::toModelType);
    }

    @Test
    public void toModelType_nullTag_throwsIllegalValueException() {
        JsonAdaptedIncome income =
                new JsonAdaptedIncome(VALID_DESC_ALLOWANCE, VALID_AMOUNT_ALLOWANCE, VALID_DATE_ALLOWANCE, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Tag.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, income::toModelType);
    }
}

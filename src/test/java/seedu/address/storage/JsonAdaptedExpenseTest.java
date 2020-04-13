package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedExpense.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalWallet.DUCK_RICE;
import static seedu.address.testutil.TypicalWallet.INVALID_AMOUNT;
import static seedu.address.testutil.TypicalWallet.INVALID_DATE;
import static seedu.address.testutil.TypicalWallet.INVALID_DESC;
import static seedu.address.testutil.TypicalWallet.INVALID_TAG;
import static seedu.address.testutil.TypicalWallet.VALID_AMOUNT_DUCK;
import static seedu.address.testutil.TypicalWallet.VALID_DATE_DUCK;
import static seedu.address.testutil.TypicalWallet.VALID_DESC_DUCK;
import static seedu.address.testutil.TypicalWallet.VALID_TAG_DUCK;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Date;
import seedu.address.model.transaction.Description;

class JsonAdaptedExpenseTest {

    @Test
    public void toModelType_validExpenseDetails_returnsExpense() throws Exception {
        JsonAdaptedExpense expense = new JsonAdaptedExpense(DUCK_RICE);
        assertEquals(DUCK_RICE, expense.toModelType());
    }

    @Test
    public void toModelType_invalidDesc_throwsIllegalValueException() {
        JsonAdaptedExpense expense =
                new JsonAdaptedExpense(INVALID_DESC, VALID_AMOUNT_DUCK, VALID_DATE_DUCK, VALID_TAG_DUCK);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);
    }

    @Test
    public void toModelType_nullDesc_throwsIllegalValueException() {
        JsonAdaptedExpense expense =
                new JsonAdaptedExpense(null, VALID_AMOUNT_DUCK, VALID_DATE_DUCK, VALID_TAG_DUCK);
        String expectedMessage =
                String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);
    }

    @Test
    public void toModelType_invalidAmount_throwsIllegalValueException() {
        JsonAdaptedExpense expense =
                new JsonAdaptedExpense(VALID_DESC_DUCK, INVALID_AMOUNT, VALID_DATE_DUCK, VALID_TAG_DUCK);
        String expectedMessage = Amount.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);
    }

    @Test
    public void toModelType_nullAmount_throwsIllegalValueException() {
        JsonAdaptedExpense expense =
                new JsonAdaptedExpense(VALID_DESC_DUCK, null, VALID_DATE_DUCK, VALID_TAG_DUCK);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedExpense expense =
                new JsonAdaptedExpense(VALID_DESC_DUCK, VALID_AMOUNT_DUCK, INVALID_DATE, VALID_TAG_DUCK);
        String expectedMessage = Date.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedExpense expense =
                new JsonAdaptedExpense(VALID_DESC_DUCK, VALID_AMOUNT_DUCK, null, VALID_TAG_DUCK);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);
    }

    @Test
    public void toModelType_invalidTag_throwsIllegalValueException() {
        JsonAdaptedExpense expense =
                new JsonAdaptedExpense(VALID_DESC_DUCK, VALID_AMOUNT_DUCK, VALID_DATE_DUCK, INVALID_TAG);
        String expectedMessage = Tag.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);
    }

    @Test
    public void toModelType_nullTag_throwsIllegalValueException() {
        JsonAdaptedExpense expense =
                new JsonAdaptedExpense(VALID_DESC_DUCK, VALID_AMOUNT_DUCK, VALID_DATE_DUCK, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Tag.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);
    }
}

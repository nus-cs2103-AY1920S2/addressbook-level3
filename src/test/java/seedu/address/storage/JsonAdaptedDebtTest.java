// @@author cheyannesim
package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedDebt.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalDebts.SUPPER;
import static seedu.address.testutil.TypicalWallet.INVALID_AMOUNT;
import static seedu.address.testutil.TypicalWallet.INVALID_DATE;
import static seedu.address.testutil.TypicalWallet.INVALID_DESC;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Date;
import seedu.address.model.transaction.Description;

class JsonAdaptedDebtTest {

    private static final String VALID_DESC = SUPPER.getDescription().toString();
    private static final String VALID_AMOUNT = SUPPER.getAmount().inDollars();
    private static final String VALID_DATE = SUPPER.getDate().toString();

    @Test
    public void toModelType_validDebtDetails_returnsDebt() throws Exception {
        JsonAdaptedDebt debt = new JsonAdaptedDebt(SUPPER);
        assertEquals(SUPPER, debt.toModelType());
    }

    @Test
    public void toModelType_invalidDesc_throwsIllegalValueException() {
        JsonAdaptedDebt debt =
                new JsonAdaptedDebt(INVALID_DESC, VALID_AMOUNT, VALID_DATE);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, debt::toModelType);
    }

    @Test
    public void toModelType_nullDesc_throwsIllegalValueException() {
        JsonAdaptedDebt debt =
                new JsonAdaptedDebt(null, VALID_AMOUNT, VALID_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, debt::toModelType);
    }

    @Test
    public void toModelType_invalidAmount_throwsIllegalValueException() {
        JsonAdaptedDebt debt =
                new JsonAdaptedDebt(VALID_DESC, INVALID_AMOUNT, VALID_DATE);
        String expectedMessage = Amount.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, debt::toModelType);
    }

    @Test
    public void toModelType_nullAmount_throwsIllegalValueException() {
        JsonAdaptedDebt debt =
                new JsonAdaptedDebt(VALID_DESC, null, VALID_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, debt::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedDebt debt =
                new JsonAdaptedDebt(VALID_DESC, VALID_AMOUNT, INVALID_DATE);
        String expectedMessage = Date.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, debt::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedDebt debt =
                new JsonAdaptedDebt(VALID_DESC, VALID_AMOUNT, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, debt::toModelType);
    }

}

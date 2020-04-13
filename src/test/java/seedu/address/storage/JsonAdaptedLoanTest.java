package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedLoan.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLoans.BREAKFAST;
import static seedu.address.testutil.TypicalWallet.INVALID_AMOUNT;
import static seedu.address.testutil.TypicalWallet.INVALID_DATE;
import static seedu.address.testutil.TypicalWallet.INVALID_DESC;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Date;
import seedu.address.model.transaction.Description;

class JsonAdaptedLoanTest {

    private static final String VALID_DESC = BREAKFAST.getDescription().toString();
    private static final String VALID_AMOUNT = BREAKFAST.getAmount().inDollars();
    private static final String VALID_DATE = BREAKFAST.getDate().toString();

    @Test
    public void toModelType_validLoanDetails_returnsLoan() throws Exception {
        JsonAdaptedLoan loan = new JsonAdaptedLoan(BREAKFAST);
        assertEquals(BREAKFAST, loan.toModelType());
    }

    @Test
    public void toModelType_invalidDesc_throwsIllegalValueException() {
        JsonAdaptedLoan loan =
                new JsonAdaptedLoan(INVALID_DESC, VALID_AMOUNT, VALID_DATE);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, loan::toModelType);
    }

    @Test
    public void toModelType_nullDesc_throwsIllegalValueException() {
        JsonAdaptedLoan loan =
                new JsonAdaptedLoan(null, VALID_AMOUNT, VALID_DATE);
        String expectedMessage =
                String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, loan::toModelType);
    }

    @Test
    public void toModelType_invalidAmount_throwsIllegalValueException() {
        JsonAdaptedLoan loan =
                new JsonAdaptedLoan(VALID_DESC, INVALID_AMOUNT, VALID_DATE);
        String expectedMessage = Amount.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, loan::toModelType);
    }

    @Test
    public void toModelType_nullAmount_throwsIllegalValueException() {
        JsonAdaptedLoan loan =
                new JsonAdaptedLoan(VALID_DESC, null, VALID_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, loan::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedLoan loan =
                new JsonAdaptedLoan(VALID_DESC, VALID_AMOUNT, INVALID_DATE);
        String expectedMessage = Date.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, loan::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedLoan loan =
                new JsonAdaptedLoan(VALID_DESC, VALID_AMOUNT, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, loan::toModelType);
    }

}

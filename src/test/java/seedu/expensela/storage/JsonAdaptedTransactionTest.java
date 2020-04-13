package seedu.expensela.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.expensela.storage.JsonAdaptedTransaction.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.expensela.testutil.Assert.assertThrows;
import static seedu.expensela.testutil.TypicalTransactions.GRAB;

import org.junit.jupiter.api.Test;

import seedu.expensela.commons.exceptions.IllegalValueException;
import seedu.expensela.model.transaction.Amount;
import seedu.expensela.model.transaction.Category;
import seedu.expensela.model.transaction.Date;
import seedu.expensela.model.transaction.Name;
import seedu.expensela.model.transaction.Remark;

public class JsonAdaptedTransactionTest {
    private static final String INVALID_NAME = " ";
    private static final String INVALID_AMOUNT = ".20";
    private static final String INVALID_DATE = "20 Jan 2019";
    private static final String INVALID_REMARK = "@@@@@";
    private static final String INVALID_CATEGORY = "PARTY*";

    private static final String VALID_NAME = GRAB.getName().toString();
    private static final String VALID_AMOUNT = GRAB.getAmount().toString().substring(3);
    private static final String VALID_DATE = GRAB.getDate().toString();
    private static final String VALID_REMARK = GRAB.getRemark().toString();
    private static final String VALID_CATEGORY = GRAB.getCategory().toString();

    @Test
    public void toModelType_validTransactionDetails_returnsTransaction() throws Exception {
        JsonAdaptedTransaction transaction = new JsonAdaptedTransaction(GRAB);
        assertEquals(GRAB, transaction.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction =
                new JsonAdaptedTransaction(INVALID_NAME, VALID_AMOUNT, "false",
                        VALID_DATE, VALID_REMARK, VALID_CATEGORY);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction = new JsonAdaptedTransaction(null, VALID_AMOUNT, "false",
                VALID_DATE, VALID_REMARK, VALID_CATEGORY);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void toModelType_invalidAmount_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction = new JsonAdaptedTransaction(VALID_NAME, INVALID_AMOUNT, "false",
                VALID_DATE, VALID_REMARK, VALID_CATEGORY);
        String expectedMessage = Amount.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void toModelType_nullAmount_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction = new JsonAdaptedTransaction(VALID_NAME, null, "false",
                VALID_DATE, VALID_REMARK, VALID_CATEGORY);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction = new JsonAdaptedTransaction(VALID_NAME, VALID_AMOUNT, "false",
                INVALID_DATE, VALID_REMARK, VALID_CATEGORY);
        String expectedMessage = Date.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction = new JsonAdaptedTransaction(VALID_NAME, VALID_AMOUNT, "false",
                null, VALID_REMARK, VALID_CATEGORY);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void toModelType_nullRemark_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction = new JsonAdaptedTransaction(VALID_NAME, VALID_AMOUNT, "false",
                VALID_DATE, null, VALID_CATEGORY);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Remark.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void toModelType_invalidCategory_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction = new JsonAdaptedTransaction(VALID_NAME, VALID_AMOUNT, "false",
                VALID_DATE, VALID_REMARK, INVALID_CATEGORY);
        String expectedMessage = Category.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void toModelType_nullCategory_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction = new JsonAdaptedTransaction(VALID_NAME, VALID_AMOUNT, "false",
                VALID_DATE, VALID_REMARK, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Category.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }
}

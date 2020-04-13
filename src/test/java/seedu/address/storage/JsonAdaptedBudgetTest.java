package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.storage.JsonAdaptedBudget.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalWallet.BUDGET_APRIL_2020;
import static seedu.address.testutil.TypicalWallet.INVALID_AMOUNT;
import static seedu.address.testutil.TypicalWallet.VALID_BUDGET_AMOUNT_JAN_2010;
import static seedu.address.testutil.TypicalWallet.VALID_BUDGET_ISDEFAULT_JAN_2010;
import static seedu.address.testutil.TypicalWallet.VALID_BUDGET_MONTH_JAN_2010;
import static seedu.address.testutil.TypicalWallet.VALID_BUDGET_YEAR_JAN_2010;

import java.time.Month;
import java.time.Year;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Date;

class JsonAdaptedBudgetTest {

    @Test
    public void toModelType_validBudgetDetails_returnsBudget() throws Exception {
        JsonAdaptedBudget budget = new JsonAdaptedBudget(BUDGET_APRIL_2020);
        assertEquals(BUDGET_APRIL_2020, budget.toModelType());
    }

    @Test
    public void toModelType_invalidAmount_throwsIllegalValueException() {
        JsonAdaptedBudget budget = new JsonAdaptedBudget(INVALID_AMOUNT, VALID_BUDGET_MONTH_JAN_2010,
                VALID_BUDGET_YEAR_JAN_2010, VALID_BUDGET_ISDEFAULT_JAN_2010);
        String expectedMessage = Amount.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, budget::toModelType);
    }

    @Test
    public void toModelType_nullAmount_throwsIllegalValueException() {
        JsonAdaptedBudget budget = new JsonAdaptedBudget(null, VALID_BUDGET_MONTH_JAN_2010, VALID_BUDGET_YEAR_JAN_2010,
                VALID_BUDGET_ISDEFAULT_JAN_2010);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, budget::toModelType);
    }

    @Test
    public void toModelType_invalidMonth_throwsIllegalValueException() {
        JsonAdaptedBudget budget = new JsonAdaptedBudget(VALID_BUDGET_AMOUNT_JAN_2010, "February",
                VALID_BUDGET_YEAR_JAN_2010, VALID_BUDGET_ISDEFAULT_JAN_2010);
        String expectedMessage = Date.MONTH_MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, budget::toModelType);
    }

    @Test
    public void toModelType_nullMonth_throwsIllegalValueException() {
        JsonAdaptedBudget budget = new JsonAdaptedBudget(VALID_BUDGET_AMOUNT_JAN_2010, null,
                VALID_BUDGET_YEAR_JAN_2010, VALID_BUDGET_ISDEFAULT_JAN_2010);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Month.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, budget::toModelType);
    }

    @Test
    public void toModelType_invalidYear_throwsIllegalValueException() {
        JsonAdaptedBudget budget = new JsonAdaptedBudget(VALID_BUDGET_AMOUNT_JAN_2010, VALID_BUDGET_MONTH_JAN_2010,
                "-1", VALID_BUDGET_ISDEFAULT_JAN_2010);
        String expectedMessage = Date.YEAR_MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, budget::toModelType);
    }

    @Test
    public void toModelType_nullYear_throwsIllegalValueException() {
        JsonAdaptedBudget budget = new JsonAdaptedBudget(VALID_BUDGET_AMOUNT_JAN_2010, VALID_BUDGET_MONTH_JAN_2010,
                null , VALID_BUDGET_ISDEFAULT_JAN_2010);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Year.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, budget::toModelType);
    }

    @Test
    public void toModelType_nullIsDefault_throwsIllegalValueException() {
        JsonAdaptedBudget budget = new JsonAdaptedBudget(VALID_BUDGET_AMOUNT_JAN_2010, VALID_BUDGET_MONTH_JAN_2010,
                VALID_BUDGET_YEAR_JAN_2010 , null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Boolean.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, budget::toModelType);
    }

    @Test
    public void toModelType_defaultBudget_returnsDefaultBudget() throws IllegalValueException {
        JsonAdaptedBudget budget = new JsonAdaptedBudget(VALID_BUDGET_AMOUNT_JAN_2010, VALID_BUDGET_MONTH_JAN_2010,
                VALID_BUDGET_YEAR_JAN_2010, "true");
        assertTrue(budget.toModelType().isDefault());
    }
}

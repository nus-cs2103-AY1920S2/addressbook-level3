package seedu.expensela.storage;

import static seedu.expensela.storage.JsonAdaptedMonthlyData.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.expensela.testutil.Assert.assertThrows;
import static seedu.expensela.testutil.TypicalTransactions.GRAB;

import org.junit.jupiter.api.Test;

import seedu.expensela.commons.exceptions.IllegalValueException;
import seedu.expensela.model.monthlydata.Budget;
import seedu.expensela.model.monthlydata.Expense;
import seedu.expensela.model.monthlydata.Income;

public class JsonAdaptedMonthlyDataTest {

    private static final String INVALID_AMOUNT = ".20";

    private static final String VALID_AMOUNT = GRAB.getAmount().toString().substring(3);

    @Test
    public void toModelType_invalidBudget_throwsIllegalValueException() {
        JsonAdaptedMonthlyData monthlyData = new JsonAdaptedMonthlyData(INVALID_AMOUNT, VALID_AMOUNT, VALID_AMOUNT);
        String expectedMessage = Budget.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, monthlyData::toModelType);
    }

    @Test
    public void toModelType_nullBudget_throwsIllegalValueException() {
        JsonAdaptedMonthlyData monthlyData = new JsonAdaptedMonthlyData(null, VALID_AMOUNT, VALID_AMOUNT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Budget.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, monthlyData::toModelType);
    }

    @Test
    public void toModelType_invalidExpense_throwsIllegalValueException() {
        JsonAdaptedMonthlyData monthlyData = new JsonAdaptedMonthlyData(VALID_AMOUNT, INVALID_AMOUNT, VALID_AMOUNT);
        String expectedMessage = Expense.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, monthlyData::toModelType);
    }

    @Test
    public void toModelType_nullExpense_throwsIllegalValueException() {
        JsonAdaptedMonthlyData monthlyData = new JsonAdaptedMonthlyData(VALID_AMOUNT, null, VALID_AMOUNT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Expense.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, monthlyData::toModelType);
    }

    @Test
    public void toModelType_invalidIncome_throwsIllegalValueException() {
        JsonAdaptedMonthlyData monthlyData = new JsonAdaptedMonthlyData(VALID_AMOUNT, VALID_AMOUNT, INVALID_AMOUNT);
        String expectedMessage = Income.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, monthlyData::toModelType);
    }

    @Test
    public void toModelType_nullIncome_throwsIllegalValueException() {
        JsonAdaptedMonthlyData monthlyData = new JsonAdaptedMonthlyData(VALID_AMOUNT, VALID_AMOUNT, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Income.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, monthlyData::toModelType);
    }
}

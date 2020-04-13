package seedu.address.storage;

import java.time.Month;
import java.time.Year;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Budget;
import seedu.address.model.transaction.Date;

/**
 * Jackson-friendly version of {@link Budget}
 */
public class JsonAdaptedBudget {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Budget's %s field is missing!";

    private final String amount;
    private final String month;
    private final String year;
    private final String isDefault;

    /**
     * Constructs a {@code JsonAdaptedBudget} with the given budget details.
     */
    @JsonCreator
    public JsonAdaptedBudget(@JsonProperty("amount") String amount,
                             @JsonProperty("month") String month,
                             @JsonProperty("year") String year,
                             @JsonProperty("isDefault") String isDefault) {
        this.amount = amount;
        this.month = month;
        this.year = year;
        this.isDefault = isDefault;
    }

    /**
     * Converts a given {@code Budget} into this class for Jackson use.
     */
    public JsonAdaptedBudget(Budget budget) {
        amount = budget.getAmount().inDollars();
        month = String.valueOf(budget.getMonth().getValue());
        year = String.valueOf(budget.getYear().getValue());
        isDefault = String.valueOf(budget.isDefault());
    }

    /**
     * Converts this Jackson-friendly adapted budget object into the model's Budget class.
     */
    public Budget toModelType() throws IllegalValueException {
        if (amount == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Amount.class.getSimpleName()));
        }
        if (!Amount.isValidAmount(amount)) {
            throw new IllegalValueException(Amount.MESSAGE_CONSTRAINTS);
        }
        final Amount modelAmount = new Amount(Double.parseDouble(amount));

        // Early exit if it is a default budget (don't care about month and year)
        final boolean modelIsDefault = Boolean.parseBoolean(isDefault);
        if (modelIsDefault) {
            return new Budget(modelAmount);
        }

        if (month == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Month.class.getSimpleName()));
        }
        if (!Date.isValidMonth(month)) {
            throw new IllegalValueException(Date.MONTH_MESSAGE_CONSTRAINTS);
        }
        final Month modelMonth = Month.of(Integer.parseInt(month));

        if (year == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Year.class.getSimpleName()));
        }
        if (!Date.isValidYear(year)) {
            throw new IllegalValueException(Date.YEAR_MESSAGE_CONSTRAINTS);
        }
        final Year modelYear = Year.of(Integer.parseInt(year));

        if (isDefault == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Boolean.class.getSimpleName()));
        }

        return new Budget(modelAmount, modelMonth, modelYear);
    }
}

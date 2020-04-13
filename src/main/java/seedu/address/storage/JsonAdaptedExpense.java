package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.transaction.Expense;

/**
 * Jackson-friendly version of {@link Expense}
 */
public class JsonAdaptedExpense extends JsonAdaptedTransaction {

    /**
     * Constructs a {@code JsonAdaptedExpense} with the given expense details.
     */
    @JsonCreator
    public JsonAdaptedExpense(@JsonProperty("description") String description,
                             @JsonProperty("amount") String amount,
                             @JsonProperty("date") String date,
                             @JsonProperty("tag") String tag) {
        super(description, amount, date, tag);
    }

    /**
     * Converts a given {@code Expense} into this class for Jackson use.
     */
    public JsonAdaptedExpense(Expense expense) {
        super(expense);
    }

    /**
     * Converts this Jackson-friendly adapted expense object into the model's Expense.
     */
    public Expense toModelType() throws IllegalValueException {
        TransactionDescriptor descriptor = toDescriptor();
        return new Expense(descriptor.description, descriptor.amount, descriptor.date, descriptor.tag);
    }
}

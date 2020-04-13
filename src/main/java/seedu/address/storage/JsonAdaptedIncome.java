package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.transaction.Income;

/**
 * Jackson-friendly version of {@link Income}
 */
public class JsonAdaptedIncome extends JsonAdaptedTransaction {

    /**
     * Constructs a {@code JsonAdaptedIncome} with the given income details.
     */
    @JsonCreator
    public JsonAdaptedIncome(@JsonProperty("description") String description,
                             @JsonProperty("amount") String amount,
                             @JsonProperty("date") String date,
                             @JsonProperty("tag") String tag) {
        super(description, amount, date, tag);
    }

    /**
     * Converts a given {@code Income} into this class for Jackson use.
     */
    public JsonAdaptedIncome(Income income) {
        super(income);
    }

    /**
     * Converts this Jackson-friendly adapted income object into the model's Income.
     */
    public Income toModelType() throws IllegalValueException {
        TransactionDescriptor descriptor = toDescriptor();
        return new Income(descriptor.description, descriptor.amount, descriptor.date, descriptor.tag);
    }
}

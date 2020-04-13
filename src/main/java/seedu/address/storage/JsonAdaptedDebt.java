// @@author cheyannesim
package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.transaction.Debt;

/**
 * Jackson-friendly version of {@link Debt}
 */
public class JsonAdaptedDebt extends JsonAdaptedTransaction {

    /**
     * Constructs a {@code JsonAdaptedDebt} with the given debt details.
     */
    @JsonCreator
    public JsonAdaptedDebt(@JsonProperty("description") String description,
                             @JsonProperty("amount") String amount,
                             @JsonProperty("date") String date) {
        super(description, amount, date, "Debt");
    }

    /**
     * Converts a given {@code Debt} into this class for Jackson use.
     */
    public JsonAdaptedDebt(Debt debt) {
        super(debt);
    }

    /**
     * Converts this Jackson-friendly adapted debt object into the model's Debt.
     */
    public Debt toModelType() throws IllegalValueException {
        TransactionDescriptor descriptor = toDescriptor();
        return new Debt(descriptor.description, descriptor.amount, descriptor.date);
    }
}

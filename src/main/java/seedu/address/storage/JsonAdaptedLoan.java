package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.transaction.Loan;

/**
 * Jackson-friendly version of {@link Loan}
 */
public class JsonAdaptedLoan extends JsonAdaptedTransaction {

    /**
     * Constructs a {@code JsonAdaptedLoan} with the given loan details.
     */
    @JsonCreator
    public JsonAdaptedLoan(@JsonProperty("description") String description,
                             @JsonProperty("amount") String amount,
                             @JsonProperty("date") String date) {
        super(description, amount, date, "Loan");
    }

    /**
     * Converts a given {@code Loan} into this class for Jackson use.
     */
    public JsonAdaptedLoan(Loan loan) {
        super(loan);
    }

    /**
     * Converts this Jackson-friendly adapted loan object into the model's Loan.
     */
    public Loan toModelType() throws IllegalValueException {
        TransactionDescriptor descriptor = toDescriptor();
        return new Loan(descriptor.description, descriptor.amount, descriptor.date);
    }
}

package team.easytravel.storage.fixedexpense;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import team.easytravel.commons.exceptions.IllegalValueException;
import team.easytravel.model.listmanagers.fixedexpense.Amount;
import team.easytravel.model.listmanagers.fixedexpense.Description;
import team.easytravel.model.listmanagers.fixedexpense.FixedExpense;
import team.easytravel.model.listmanagers.fixedexpense.FixedExpenseCategory;

/**
 * Jackson-friendly version of {@link FixedExpense}.
 */
class JsonAdaptedFixedExpense {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "FixedExpense's %s field is missing!";

    private final String amount;
    private final String description;
    private final String category;

    /**
     * Constructs a {@code JsonAdaptedTransportBooking} with the given details.
     */
    @JsonCreator
    public JsonAdaptedFixedExpense(@JsonProperty("amount") String amount,
                                   @JsonProperty("description") String description,
                                   @JsonProperty("category") String category) {
        this.amount = amount;
        this.description = description;
        this.category = category;
    }

    /**
     * Converts a given {@code FixedExpense} into this class for Jackson use.
     */
    public JsonAdaptedFixedExpense(FixedExpense source) {
        amount = source.getAmount().value;
        description = source.getDescription().value;
        category = source.getFixedExpenseCategory().value;
    }

    /**
     * Converts this Jackson-friendly adapted fixed expense object into the model's {@code FixedExpense} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted fixed expense.
     */
    public FixedExpense toModelType() throws IllegalValueException {
        if (amount == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName()));
        }
        if (!Amount.isValidAmount(amount)) {
            throw new IllegalValueException(Amount.MESSAGE_CONSTRAINTS);
        }
        final Amount modelAmount = new Amount(amount);

        if (description == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        if (category == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, FixedExpenseCategory.class.getSimpleName()));
        }
        if (!FixedExpenseCategory.isValidCategory(category)) {
            throw new IllegalValueException(FixedExpenseCategory.MESSAGE_CONSTRAINTS);
        }
        final FixedExpenseCategory modelFixedExpenseCategory = new FixedExpenseCategory(category);

        return new FixedExpense(modelAmount, modelDescription, modelFixedExpenseCategory);
    }

}

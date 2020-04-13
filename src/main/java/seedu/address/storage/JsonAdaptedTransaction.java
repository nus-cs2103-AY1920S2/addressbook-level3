package seedu.address.storage;

import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Date;
import seedu.address.model.transaction.Description;
import seedu.address.model.transaction.Transaction;

/**
 * Jackson-friendly version of {@link Transaction}
 * Subclasses (Expense, Income, etc) should implement toModelType() with their own constructor.
 */
public abstract class JsonAdaptedTransaction {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Transaction's %s field is missing!";

    private final String description;
    private final String amount;
    private final String date;
    private final String tag;

    /**
     * Constructs a {@code JsonAdaptedTransaction} with the given transaction details.
     */
    @JsonCreator
    public JsonAdaptedTransaction(@JsonProperty("description") String description,
                             @JsonProperty("amount") String amount,
                             @JsonProperty("date") String date,
                             @JsonProperty("tag") String tag) {
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.tag = tag;
    }

    /**
     * Converts a given {@code Transaction} into this class for Jackson use.
     */
    public JsonAdaptedTransaction(Transaction transaction) {
        description = transaction.getDescription().toString();
        amount = transaction.getAmount().inDollars();
        date = transaction.getDate().getInputFormat();
        tag = transaction.getTag().tagName;
    }

    /**
     * Converts this Jackson-friendly adapted transaction object into the model's Transaction subclasses.
     * Subclasses should implement this method with the relevant constructor (Expense, Income, etc).
     */
    public abstract Transaction toModelType() throws IllegalValueException;

    /**
     * Converts this Jackson-friendly adapted transaction object into a TransactionDescriptor.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted transaction.
     */
    public TransactionDescriptor toDescriptor() throws IllegalValueException {

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        if (amount == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Amount.class.getSimpleName()));
        }
        if (!Amount.isValidAmount(amount)) {
            throw new IllegalValueException(Amount.MESSAGE_CONSTRAINTS);
        }
        final Amount modelAmount = new Amount(Double.parseDouble(amount));

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Date.class.getSimpleName()));
        }
        final Date modelDate;
        try {
            modelDate = ParserUtil.parseDate(date);
        } catch (DateTimeParseException | ParseException e) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }

        if (tag == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Tag.class.getSimpleName()));
        }
        if (!Tag.isValidTagName(tag)) {
            throw new IllegalValueException(Tag.MESSAGE_CONSTRAINTS);
        }
        final Tag modelTag = new Tag(tag);

        return new TransactionDescriptor(modelDescription, modelAmount, modelDate, modelTag);
    }

}

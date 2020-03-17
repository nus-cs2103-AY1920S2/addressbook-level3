package seedu.expensela.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.expensela.commons.exceptions.IllegalValueException;
import seedu.expensela.model.transaction.Amount;
import seedu.expensela.model.transaction.Date;
import seedu.expensela.model.transaction.Name;
import seedu.expensela.model.transaction.Type;
import seedu.expensela.model.transaction.Remark;
import seedu.expensela.model.transaction.TransactionID;
import seedu.expensela.model.transaction.Transaction;

/**
 * Jackson-friendly version of {@link Transaction}.
 */
class JsonAdaptedTransaction {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Transaction's %s field is missing!";

    private final String name;
    private final String amount;
    private final String date;
    private final String type;
    private final String remark;
    private final String transactionID;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedTransaction(@JsonProperty("name") String name, @JsonProperty("amount") String amount,
                                  @JsonProperty("date") String date, @JsonProperty("type") String type,
                                  @JsonProperty("remark") String remark, @JsonProperty("transactionID") String transactionID) {
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.type = type;
        this.remark = remark;
        this.transactionID = transactionID;
    }

    /**
     * Converts a given {@code Transaction} into this class for Jackson use.
     */
    public JsonAdaptedTransaction(Transaction source) {
        name = source.getName().transactionName;
        amount = source.getAmount().toString().substring(3);
        date = source.getDate().value;
        type = source.getType().transactionType;
        remark = source.getRemark().transactionRemark;
        transactionID = source.getTransactionID().Id;
    }

    /**
     * Converts this Jackson-friendly adapted transaction object into the model's {@code Transaction} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted transaction.
     */
    public Transaction toModelType() throws IllegalValueException {

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (amount == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName()));
        }
        if (!Amount.isValidAmount(amount)) {
            throw new IllegalValueException(Amount.MESSAGE_CONSTRAINTS);
        }
        final Amount modelAmount = new Amount(amount, true);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelDate = new Date(date);

        if (type == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Type.class.getSimpleName()));
        }
        if (!Type.isValidType(type)) {
            throw new IllegalValueException(Type.MESSAGE_CONSTRAINTS);
        }
        final Type modelType = new Type(type);

        if (Remark == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Remark.class.getSimpleName()));
        }
        if (!Remark.isValidRemark(remark)) {
            throw new IllegalValueException(Remark.MESSAGE_CONSTRAINTS);
        }
        final Remark modelRemark = new Remark(remark);

        if (TransactionID == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TransactionID.class.getSimpleName()));
        }
        if (!TransactionID.isValidDate(transactionID)) {
            throw new IllegalValueException(TransactionID.MESSAGE_CONSTRAINTS);
        }
        final TransactionID modelTransactionID = new TransactionID(transactionID);

        return new Transaction(modelName, modelAmount, modelDate, modelType, modelRemark, modelTransactionID);
    }

}

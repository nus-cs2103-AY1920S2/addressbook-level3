package seedu.eylah.expensesplitter.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.eylah.commons.exceptions.IllegalValueException;
import seedu.eylah.expensesplitter.model.ReadOnlyReceiptBook;
import seedu.eylah.expensesplitter.model.ReceiptBook;
import seedu.eylah.expensesplitter.model.receipt.Receipt;

/**
 * An Immutable ReceiptBook that is serializable to JSON format.
 */
@JsonRootName(value = "receiptbook")

public class JsonSerializableReceiptBook {

    public static final String MESSAGE_DUPLICATE_RECEIPT = "Receipt list contains duplicate receipt(s).";

    private final List<JsonAdaptedReceipt> receipts = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableReceiptBook} with the given receipt.
     */
    @JsonCreator
    public JsonSerializableReceiptBook(@JsonProperty("receipts") List<JsonAdaptedReceipt> receipts) {
        this.receipts.addAll(receipts);
    }

    /**
     * Converts a given {@code ReadOnlyReceiptBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableReceiptBook(ReadOnlyReceiptBook source) {
        receipts.addAll(source.getReceiptList().stream().map(JsonAdaptedReceipt::new).collect(Collectors.toList()));
    }

    /**
     * Converts this receipt book into the model's {@code Receipt} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ReceiptBook toModelType() throws IllegalValueException {
        ReceiptBook receiptBook = new ReceiptBook();
        for (JsonAdaptedReceipt jsonAdaptedReceipt : receipts) {
            Receipt receipt = jsonAdaptedReceipt.toModelType();
            if (receiptBook.hasReceipt(receipt)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_RECEIPT);
            }
            receiptBook.addReceipt(receipt);

        }
        return receiptBook;
    }

}

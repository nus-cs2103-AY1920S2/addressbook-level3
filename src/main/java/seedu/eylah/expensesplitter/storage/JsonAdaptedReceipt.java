package seedu.eylah.expensesplitter.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.eylah.commons.exceptions.IllegalValueException;
import seedu.eylah.expensesplitter.model.receipt.Entry;
import seedu.eylah.expensesplitter.model.receipt.Receipt;

/**
 * Jackson-friendly version of {@link Receipt}.
 */
public class JsonAdaptedReceipt {

    private final List<JsonAdaptedEntry> entries = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedReceipt} with the given entries details.
     */
    @JsonCreator
    public JsonAdaptedReceipt(@JsonProperty("itemPersons") List<JsonAdaptedEntry> entries) {
        if (entries != null) {
            this.entries.addAll(entries);
        }
    }

    /**
     * Converts a given {@code Receipt} into this class for Jackson use.
     */
    public JsonAdaptedReceipt(Receipt source) {
        entries.addAll(source.getReceipt().stream()
                .map(JsonAdaptedEntry::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted receipt object into the model's {@code Receipt} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Receipt toModelType() throws IllegalValueException {
        final ArrayList<Entry> modelEntries = new ArrayList<>();
        for (JsonAdaptedEntry entry : entries) {
            modelEntries.add(entry.toModelType());
        }

        return new Receipt(modelEntries);
    }
}

package seedu.eylah.expensesplitter.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.eylah.addressbook.model.person.Person;
import seedu.eylah.expensesplitter.model.receipt.Receipt;

/**
 * Jackson-friendly version of {@link Receipt}.
 */
public class JsonAdaptedReceipt {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Receipt's %s field is missing!";

    private final List<JsonAdaptedPerson> itemPersons = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedReceipt(@JsonProperty("itemPersons") List<JsonAdaptedPerson> itemPersons) {
        if (itemPersons != null) {
            this.itemPersons.addAll(itemPersons);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedReceipt(Receipt source) {
        itemPersons.addAll(source.getReceipt().stream()
                .map(JsonAdaptedPerson::new)
                .collect(Collectors.toList()));
    }
}

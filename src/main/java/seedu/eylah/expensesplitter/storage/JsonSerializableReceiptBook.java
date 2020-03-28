package seedu.eylah.expensesplitter.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * An Immutable ReceiptBook that is serializable to JSON format.
 */
@JsonRootName(value = "receiptbook")

public class JsonSerializableReceiptBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<> persons = new ArrayList<>();
}

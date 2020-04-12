package seedu.address.storage.storageProgress;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.modelGeneric.ReadOnlyAddressBookGeneric;
import seedu.address.model.modelProgress.Progress;
import seedu.address.model.modelProgress.ProgressAddressBook;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "progressaddressbook")
class JsonProgressSerializableAddressBook {
    private static final Logger logger = LogsCenter.getLogger(JsonProgressSerializableAddressBook.class);

    public static final String MESSAGE_DUPLICATE_PROGRESS = "Assignment list contains duplicate assignment(s).";

    private final List<JsonAdaptedProgress> progresses = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given courses.
     */
    @JsonCreator
    public JsonProgressSerializableAddressBook(
            @JsonProperty("progresses") List<JsonAdaptedProgress> progresses) {
        this.progresses.addAll(progresses);
    }

    /**
     * Converts a given {@code ReadOnlyAssignmentsAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code
     *               JsonProgressSerializableAddressBook}.
     */
    public JsonProgressSerializableAddressBook(ReadOnlyAddressBookGeneric<Progress> source) {
        progresses.addAll(source.getList().stream().map(
                JsonAdaptedProgress::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ProgressAddressBook toModelType() throws IllegalValueException, CommandException {
        ProgressAddressBook progressAddressBook = new ProgressAddressBook();
        logger.info("Number of Progresses:" + progresses.size());
        for (JsonAdaptedProgress jsonAdaptedProgress : progresses) {
            Progress progress = jsonAdaptedProgress.toModelType();

            if (progressAddressBook.has(progress)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PROGRESS);
            }

            progressAddressBook.add(progress);
        }
        return progressAddressBook;
    }

}

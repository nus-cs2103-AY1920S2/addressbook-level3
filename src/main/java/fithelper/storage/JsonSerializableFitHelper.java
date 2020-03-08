package fithelper.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import fithelper.commons.exceptions.IllegalValueException;
import fithelper.model.FitHelper;
import fithelper.model.ReadOnlyFitHelper;
import fithelper.model.entry.Entry;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * An Immutable FitHelper that is serializable to JSON format.
 */
@JsonRootName(value = "fithelper")
class JsonSerializableFitHelper {

    public static final String MESSAGE_DUPLICATE_ENTRY = "Entries list contains duplicate entry(s).";

    private final List<JsonAdaptedEntry> entries = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableFitHelper} with the given entries.
     */
    @JsonCreator
    public JsonSerializableFitHelper(@JsonProperty("entries") List<JsonAdaptedEntry> entries) {
        this.entries.addAll(entries);
    }

    /**
     * Converts a given {@code ReadOnlyFitHelper} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableFitHelper}.
     */
    public JsonSerializableFitHelper(ReadOnlyFitHelper source) {
        entries.addAll(source.getFoodList().stream().map(JsonAdaptedEntry::new).collect(Collectors.toList()));
        entries.addAll(source.getSportsList().stream().map(JsonAdaptedEntry::new).collect(Collectors.toList()));
    }

    /**
     * Converts this fitHelper into the model's {@code FitHelper} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public FitHelper toModelType() throws IllegalValueException {
        FitHelper fitHelper = new FitHelper();
        for (JsonAdaptedEntry jsonAdaptedEntry : entries) {
            Entry entry = jsonAdaptedEntry.toModelType();
            if (fitHelper.hasEntry(entry)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ENTRY);
            }
            fitHelper.addEntry(entry);
        }
        return fitHelper;
    }

}

package nasa.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import nasa.commons.exceptions.IllegalValueException;
import nasa.model.HistoryBook;
import nasa.model.ReadOnlyHistory;
import nasa.model.history.ModuleListHistory;

/**
 * Storage to store data for Ui history.
 */
public class JsonAdaptedUiHistory {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Deadline's %s field is missing!";

    private List<String> name = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedDeadline} with the given activity details.
     */
    @JsonCreator
    public JsonAdaptedUiHistory(@JsonProperty("name") List<String> name) {
        this.name = name;
    }

    public JsonAdaptedUiHistory(ReadOnlyHistory<String> uiHistory) {
        name.addAll(uiHistory.getModuleListHistory());
    }

    /**
     * Return HistoryBook using json file.
     * @return HistoryBook
     * @throws IllegalValueException
     */
    public HistoryBook<String> toModelType() throws IllegalValueException {
        final ModuleListHistory<String> temp = new ModuleListHistory<>();
        temp.setStack(name);
        HistoryBook<String> historyBook = new HistoryBook<>();
        historyBook.setModuleListHistory(temp);
        return historyBook;
    }
}

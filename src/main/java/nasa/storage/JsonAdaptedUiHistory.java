package nasa.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import nasa.commons.exceptions.IllegalValueException;
import nasa.model.HistoryBook;
import nasa.model.ReadOnlyHistory;
import nasa.model.history.ModuleListHistory;

/**
 * Storage to store data for Ui history.
 */
@JsonRootName(value = "uiHistoryBook")
public class JsonAdaptedUiHistory {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Deadline's %s field is missing!";

    private List<JsonAdaptedFilterProperty> name = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedDeadline} with the given activity details.
     */
    @JsonCreator
    public JsonAdaptedUiHistory(@JsonProperty("name") List<JsonAdaptedFilterProperty> name) {
        if (name != null) {
            this.name = name;
        }
    }

    public JsonAdaptedUiHistory(ReadOnlyHistory<String> uiHistory) {
        name.addAll(uiHistory.getModuleListHistory().stream().map(JsonAdaptedFilterProperty::new)
                .collect(Collectors.toList()));
    }

    /**
     * Return HistoryBook using json file.
     * @return HistoryBook
     * @throws IllegalValueException
     */
    public HistoryBook<String> toModelType() throws IllegalValueException {
        final List<String> input = new ArrayList<>();
        for (JsonAdaptedFilterProperty list : name) {
            input.add(list.toModelType());
        }
        final ModuleListHistory<String> temp = new ModuleListHistory<>();
        temp.setStack(input);
        HistoryBook<String> historyBook = new HistoryBook<>();
        historyBook.setModuleListHistory(temp);
        return historyBook;
    }
}

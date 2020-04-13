package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.calender.Task;

/**
 * An Immutable calendarbook that is serializable to JSON format.
 */
@JsonRootName(value = "calendarbook")
class JsonSerializableCalenderBook {

    private final List<JsonAdaptedCalendar> calendar = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableCalenderBook (@JsonProperty("calendar") List<JsonAdaptedCalendar> calendar) {
        this.calendar.addAll(calendar);

    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableCalenderBook(ObservableList<Task> source) {
        calendar.addAll(source.stream().map(JsonAdaptedCalendar::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ObservableList<Task> toModelType() throws IllegalValueException {
        ObservableList<Task> taskList = FXCollections.observableList(new ArrayList<>());
        for (JsonAdaptedCalendar jsonAdaptedCalendar : calendar) {
            Task calendarTask = jsonAdaptedCalendar.toModelType();
            taskList.add(calendarTask);
            Task.addTaskPerDate(calendarTask.getDate(), calendarTask);
        }

        return taskList;
    }

}

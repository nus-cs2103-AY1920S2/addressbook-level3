package seedu.address.storage.event;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventHistory;
import seedu.address.model.event.ReadOnlyEvents;



/**
 * An Immutable eventHistory that is serializable to JSON format.
 */
@JsonRootName(value = "events")
class JsonSerializableEvents {

    private final List<JsonAdaptedEvent> events = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableEvents} with the given students.
     */
    @JsonCreator
    public JsonSerializableEvents(@JsonProperty("events") List<JsonAdaptedEvent> events) {
        this.events.addAll(events);
    }

    /**
     * Converts a given {@code ReadOnlyEvents} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableEvents}.
     */
    public JsonSerializableEvents(ReadOnlyEvents source) {
        events.addAll(source.getEvents().stream().map(JsonAdaptedEvent::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public EventHistory toModelType() throws IllegalValueException {
        List<Event> eventArrayList = new ArrayList<>();
        for (JsonAdaptedEvent jsonAdaptedEvent : events) {
            Event event = jsonAdaptedEvent.toModelType();
            eventArrayList.add(event);
        }
        return new EventHistory(eventArrayList);
    }

}

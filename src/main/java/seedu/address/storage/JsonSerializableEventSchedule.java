package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.EventSchedule;
import seedu.address.model.ReadOnlyEventSchedule;
import seedu.address.model.event.Event;

/**
 * An unmodifiable events schedule that is serializable in JSON format.
 */
@JsonRootName(value = "eventSchedule")
public class JsonSerializableEventSchedule {
    public static final String MESSAGE_DUPLICATE_EVENT = "Event list contains duplicate events.";

    private final List<JsonAdaptedEvent> events = new ArrayList<>();

    /** Constructs a {@code JsonSerializableEventSchedule} with the given Events. */
    @JsonCreator
    public JsonSerializableEventSchedule(@JsonProperty("events") List<JsonAdaptedEvent> events) {
        this.events.addAll(events);
    }

    /**
     * Converts a given {@code ReadOnlyEventSchedule} into this class for Jackson use.
     * @param source future changes to this will not affect the created {@code JsonSerializableEventSchedule}
     */
    public JsonSerializableEventSchedule(ReadOnlyEventSchedule source) {
        events.addAll(source.getEventsList().stream().map(JsonAdaptedEvent::new).collect(Collectors.toList()));
    }

    /**
     * Converts this Event Schedule into the model's {@code Event Schedule} object.
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public EventSchedule toModelType() throws IllegalValueException {
        EventSchedule eventSchedule = new EventSchedule();
        for (JsonAdaptedEvent jsonAdaptedEvent : events) {
            Event event = jsonAdaptedEvent.toModelType();

            if (eventSchedule.hasEvent(event)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_EVENT);
            }

            eventSchedule.addEvent(event);
        }
        return eventSchedule;
    }

}

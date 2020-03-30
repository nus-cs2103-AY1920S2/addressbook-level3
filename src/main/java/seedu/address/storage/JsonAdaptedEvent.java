package seedu.address.storage;

import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Duration;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventTitle;
import seedu.address.model.event.Place;

/**
 * Jackson-friendly version of {@Link Event}
 */
public class JsonAdaptedEvent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";

    private final String eventTitle;
    private final String eventDate;
    private final String duration;
    private final String place;

    /**
     * Constructs a {@code JsonAdaptedEvent} with the given Event details.
     */
    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("eventTitle") String eventTitle, @JsonProperty("eventDate") String eventDate,
                            @JsonProperty("duration") String duration, @JsonProperty("place") String place) {
        this.eventTitle = eventTitle;
        this.eventDate = eventDate;
        this.duration = duration;
        this.place = place;
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {
        eventTitle = source.getEventTitle().eventTitle;
        eventDate = source.getEventDate().dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        duration = source.getDuration().duration;
        place = source.getPlace().place;
    }

    /**
     * Converts this Jackson-friendly adapted Event object into the model's {@code Event} object.
     * @throws IllegalValueException if there were any data constraints violated in the adapted event.
     */
    public Event toModelType() throws IllegalValueException {
        if (eventTitle == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EventTitle.class.getSimpleName()));
        }
        if (!EventTitle.isValidEventTitle(eventTitle)) {
            throw new IllegalValueException(EventTitle.MESSAGE_CONSTRAINTS); // until here
        }
        final EventTitle modelEventTitle = new EventTitle(eventTitle);

        if (eventDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EventDate.class.getSimpleName()));
        }
        if (!EventDate.isValidEventDate(eventDate)) {
            throw new IllegalValueException(EventDate.MESSAGE_CONSTRAINTS);
        }
        final EventDate modelEventDate = new EventDate(eventDate);

        if (duration == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Duration.class.getSimpleName()));
        }
        if (!Duration.isValidDuration(duration)) {
            throw new IllegalValueException(Duration.MESSAGE_CONSTRAINTS);
        }
        final Duration modelDuration = new Duration(duration);

        if (place == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Place.class.getSimpleName()));
        }
        final Place modelPlace = new Place(place); // place has no invalid input

        return new Event(modelEventTitle, modelEventDate, modelDuration, modelPlace);
    }


}

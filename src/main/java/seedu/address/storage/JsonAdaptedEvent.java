package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.event.Event;
import seedu.address.model.person.Time;

/**
 * Jackson-friendly version of {@link Event}
 */
public class JsonAdaptedEvent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Events's %s field is missing!";

    private final String eventId;
    private final String activity;
    private final String place;
    private final String withPerson;
    private final String withGroup;
    private final String time;

    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("eventId") String eventId, @JsonProperty("activity") String activity,
                            @JsonProperty("place") String place,
                            @JsonProperty("withPerson") String withPerson,
                            @JsonProperty("withGroup") String withGroup,
                            @JsonProperty("time") String time) {
        this.activity = activity;
        this.place = place;
        this.time = time;
        this.eventId = eventId;

        if (withGroup != null) {
            this.withGroup = withGroup;
        } else {
            this.withGroup = "-1";
        }

        if (withPerson != null) {
            this.withPerson = withGroup;
        } else {
            this.withPerson = "-1";
        }

    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {

        activity = source.getActivity();
        place = source.getPlace();
        time = source.getTime().toString();
        eventId = Integer.toString(source.getEventId());

        if (source.getWithGroup().isPresent()) {
            withGroup = source.getWithGroup().get().toString();
        } else {
            withGroup = "-1";
        }

        if (source.getWithPerson().isPresent()) {
            withPerson = source.getWithPerson().get().toString();
        } else {
            withPerson = "-1";
        }
    }

    /**
     * Converts this Jackson-friendly adapted event object into the model's {@code Event} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     *                               event.
     */

    public Event toModelType() throws IllegalValueException {

        if (activity == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "activity"));
        }
        if (place == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "place"));
        }

        if (time == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "time"));
        }

        try {
            Time timeSpent = ParserUtil.parseTime(time);
            Event event = new Event(activity, place, timeSpent);
            return event;
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(e.getMessage());
        }
    }

}

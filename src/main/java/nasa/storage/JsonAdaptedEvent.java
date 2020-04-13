package nasa.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import nasa.commons.exceptions.IllegalValueException;
import nasa.model.activity.Date;
import nasa.model.activity.Event;
import nasa.model.activity.Name;
import nasa.model.activity.Note;
import nasa.model.activity.Schedule;

/**
 * Jackson-friendly version of {@link Event}.
 */
class JsonAdaptedEvent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";
    private final String name;
    private final String date;
    private final String note;
    private final String startDate;
    private final String endDate;
    private final String schedule;


    /**
     * Constructs a {@code JsonAdaptedEvent} with the given activity details.
     */
    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("name") String name, @JsonProperty("date") String date,
                            @JsonProperty("note") String note, @JsonProperty("startDate") String startDate,
                            @JsonProperty("endDate") String endDate,
                            @JsonProperty("schedule") String schedule) {
        this.name = name;
        this.date = date;
        this.note = note;
        this.startDate = startDate;
        this.endDate = endDate;
        this.schedule = schedule;
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {
        name = source.getName().name;
        date = source.getDateCreated().toString();
        note = source.getNote().toString();
        startDate = source.getStartDate().toString();
        endDate = source.getEndDate().toString();
        schedule = source.getSchedule().toString();
    }

    /**
     * Converts this Jackson-friendly adapted activity object into the model's {@code Event} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted activity.
     */
    public Event toModelType() throws IllegalValueException {

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelDate = new Date(date);

        if (note == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Note.class.getSimpleName()));
        }
        if (!Note.isValidNote(note)) {
            throw new IllegalValueException(Note.MESSAGE_CONSTRAINTS);
        }

        final Note modelNote = new Note(note);

        if (startDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(startDate)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date eventStartDate = new Date(startDate);

        if (endDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(endDate)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelEndDate = new Date(endDate);
        final Schedule modelSchedule = new Schedule(schedule);

        Event event = new Event(modelName, modelDate, modelNote, eventStartDate, modelEndDate);
        event.setSchedule(modelSchedule);
        return event;
    }
}

package seedu.nova.storage.event;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.nova.commons.exceptions.IllegalValueException;
import seedu.nova.logic.parser.ParserUtil;
import seedu.nova.model.plan.Task;
import seedu.nova.model.schedule.event.Consultation;
import seedu.nova.model.schedule.event.Event;
import seedu.nova.model.schedule.event.Lesson;
import seedu.nova.model.schedule.event.Meeting;
import seedu.nova.model.schedule.event.StudySession;
import seedu.nova.model.schedule.event.WeakEvent;
import seedu.nova.model.util.time.duration.DateTimeDuration;
import seedu.nova.storage.JsonAdaptedPlannerTask;

/**
 * Jackson-friendly version of {@link Event}.
 */
public class JsonAdaptedEvent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";

    private final String eventType;
    private final String desc;
    private final String venue;
    private final String date;
    private final String startTime;
    private final String endTime;
    private final String note;
    private final JsonAdaptedPlannerTask task;


    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("eventType") String eventType,
                            @JsonProperty("desc") String desc,
                            @JsonProperty("venue") String venue,
                            @JsonProperty("date") String date,
                            @JsonProperty("startTime") String startTime,
                            @JsonProperty("endTime") String endTime,
                            @JsonProperty("note") String note,
                            @JsonProperty("task") JsonAdaptedPlannerTask task) {
        this.eventType = eventType;
        this.desc = desc;
        this.venue = venue;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.note = note;
        this.task = task;
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {
        eventType = source.getEventType();
        desc = source.getDesc();
        venue = source.getVenue();
        date = source.getDate().toString();
        startTime = source.getStartTime().toString();
        endTime = source.getEndTime().toString();
        note = source.getNote();
        if (source instanceof WeakEvent) {
            task = new JsonAdaptedPlannerTask(((WeakEvent) source).getOriginTask());
        } else {
            task = null;
        }
    }

    /**
     * Converts this Jackson-friendly adapted event object into the model's {@code Event} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event.
     */
    public Event toModelType() throws IllegalValueException {
        switch (eventType) {
        case "meeting":
            return toMeeting();

        case "consultation":
            return toConsultation();

        case "study":
            return toStudy();

        case "lesson":
            return toLesson();

        case "weak":
            return toWeak();

        default:
            throw new IllegalValueException("Unrecognised event type.");
        }

    }

    /**
     * Converts this Jackson-friendly adapted event object into the model's {@code Meeting} object.
     *
     * @return an Event
     * @throws IllegalValueException if any of the fields are missing
     */
    public Event toMeeting() throws IllegalValueException {
        checkFields();

        LocalDate localDate = ParserUtil.parseDate(date);
        LocalTime start = ParserUtil.parseTime(startTime);
        LocalTime end = ParserUtil.parseTime(endTime);

        Event meeting = new Meeting(desc, venue, start, end, localDate, note);

        return meeting;
    }

    /**
     * Converts this Jackson-friendly adapted event object into the model's {@code Consultation} object.
     *
     * @return an Event
     * @throws IllegalValueException if any of the fields are missing
     */
    public Event toConsultation() throws IllegalValueException {
        checkFields();

        LocalDate localDate = ParserUtil.parseDate(date);
        LocalTime start = ParserUtil.parseTime(startTime);
        LocalTime end = ParserUtil.parseTime(endTime);

        Event consultation = new Consultation(desc, venue, start, end, localDate, note);

        return consultation;
    }

    /**
     * Converts this Jackson-friendly adapted event object into the model's {@code StudySession} object.
     *
     * @return an Event
     * @throws IllegalValueException if any of the fields are missing
     */
    public Event toStudy() throws IllegalValueException {
        checkFields();

        LocalDate localDate = ParserUtil.parseDate(date);
        LocalTime start = ParserUtil.parseTime(startTime);
        LocalTime end = ParserUtil.parseTime(endTime);

        Event study = new StudySession(desc, venue, start, end, localDate, note);

        return study;
    }

    /**
     * Converts this Jackson-friendly adapted event object into the model's {@code Lesson} object.
     *
     * @return an Event
     * @throws IllegalValueException if any of the fields are missing
     */
    public Event toLesson() throws IllegalValueException {
        checkFields();

        LocalDate localDate = ParserUtil.parseDate(date);

        LocalTime start = ParserUtil.parseTime(startTime);
        LocalTime end = ParserUtil.parseTime(endTime);

        Event lesson = new Lesson(desc, venue, start, end, localDate.getDayOfWeek(), localDate, note);

        return lesson;

    }

    /**
     * Converts this Jackson-friendly adapted event object into the model's {@code WeakEvent} object.
     *
     * @return an Event
     * @throws IllegalValueException if any of the fields are missing
     */
    public Event toWeak() throws IllegalValueException {
        checkFields();

        LocalDate localDate = ParserUtil.parseDate(date);

        LocalTime start = ParserUtil.parseTime(startTime);
        LocalTime end = ParserUtil.parseTime(endTime);

        Task t = task.toTask();

        return new WeakEvent(desc, new DateTimeDuration(localDate, start, end), t);
    }

    /**
     * Checks for any missing fields.
     *
     * @throws IllegalValueException if any of the fields are missing
     */
    public void checkFields() throws IllegalValueException {
        if (desc == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "description"));
        }

        if (venue == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "venue"));
        }

        if (startTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "start time"));
        }

        if (endTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "end time"));
        }

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "date"));
        }

        if (note == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "note"));
        }
    }

}

package nasa.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import nasa.commons.exceptions.IllegalValueException;
import nasa.model.activity.Activity;
import nasa.model.activity.Date;
import nasa.model.activity.Deadline;
import nasa.model.activity.Event;
import nasa.model.activity.Lesson;
import nasa.model.activity.Name;
import nasa.model.activity.Note;
import nasa.model.activity.Priority;
import nasa.model.activity.Status;

/**
 * Jackson-friendly version of {@link Activity}.
 */
class JsonAdaptedActivity {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Activity's %s field is missing!";

    private final String type;
    private final String name;
    private final String date;
    private final String note;
    private final String status;
    private final String priority;
    private final String dueDate;
    private final String startDate;
    private final String endDate;


    /**
     * Constructs a {@code JsonAdaptedActivity} with the given activity details.
     */
    @JsonCreator
    public JsonAdaptedActivity(@JsonProperty("type") String type, @JsonProperty("name") String name,
                               @JsonProperty("date") String date, @JsonProperty("note") String note,
                               @JsonProperty("status") String status,
                               @JsonProperty("priority") String priority,
                               @JsonProperty("dueDate") String dueDate,
                               @JsonProperty("startDate") String startDate,
                               @JsonProperty("endDate") String endDate) {
        this.type = type;
        this.name = name;
        this.date = date;
        this.note = note;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
        this.startDate = startDate;
        this.endDate = endDate;

    }

    /**
     * Converts a given {@code Activity} into this class for Jackson use.
     */
    public JsonAdaptedActivity(Activity source) {
        name = source.getName().name;
        date = source.getDate().toString();
        note = source.getNote().toString();
        status = Boolean.toString(source.isDone());
        priority = source.getPriority().toString();

        if (source instanceof Deadline) {
            type = "deadline";
            Deadline temp = (Deadline) source;
            dueDate = temp.getDateline().toString();
            startDate = "";
            endDate = "";
        } else if (source instanceof Event) {
            type = "event";
            Event temp = (Event) source;
            dueDate = "";
            startDate = temp.getDateFrom().toString();
            endDate = temp.getDateTo().toString();
        } else {
            type = "lesson";
            Lesson temp = (Lesson) source;
            dueDate = "";
            startDate = temp.getDateFrom().toString();
            endDate = temp.getDateTo().toString();
        }
    }

    /**
     * Converts this Jackson-friendly adapted activity object into the model's {@code Activity} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted activity.
     */
    public Activity toModelType() throws IllegalValueException {

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(seedu.address.model.person.Name.MESSAGE_CONSTRAINTS);
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

        if (priority == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Priority.class.getSimpleName()));
        }
        if (!Priority.isValidPriorityValue(priority)) {
            throw new IllegalValueException("");
        }
        final Priority modelPriority = new Priority(priority);

        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Status.class.getSimpleName()));
        }

        if (dueDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(dueDate)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelDueDate = new Date(dueDate);

        if (startDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(startDate)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelStartDate = new Date(startDate);

        if (endDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(endDate)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelEndDate = new Date(endDate);

        //TODO check validity
        /*
        if (!Status.(priority)) {
            throw new IllegalValueException("");
        }

         */
        final Status modelStatus = Status.valueOf(status);

        Activity activity = null;
        switch (type) {
        case "deadline":
            activity = new Deadline(modelName, modelDate, modelNote, modelStatus, modelPriority, modelDueDate);
            break;
        case "event":
            activity = new Event(modelName, modelDate, modelNote, modelStatus, modelPriority, modelStartDate, modelEndDate);
            break;
        case "lesson":
            activity = new Lesson(modelName, modelDate, modelNote, modelStatus, modelPriority, modelStartDate, modelEndDate);
            break;
        default:
            throw new IllegalValueException("");
        }
        return activity;
    }
}

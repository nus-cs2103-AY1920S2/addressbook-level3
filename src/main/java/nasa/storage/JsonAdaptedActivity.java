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
import nasa.model.activity.Schedule;
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
    private final String schedule;


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
                               @JsonProperty("endDate") String endDate,
                               @JsonProperty("schedule") String schedule) {
        this.type = type;
        this.name = name;
        this.date = date;
        this.note = note;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.schedule = schedule;

    }

    /**
     * Converts a given {@code Activity} into this class for Jackson use.
     */
    public JsonAdaptedActivity(Activity source) {
        name = source.getName().name;
        date = source.getDate().toString();
        note = source.getNote().toString();
        status = source.getStatus().toString();
        priority = source.getPriority().toString();
        schedule = source.getSchedule().toString();

        if (source instanceof Deadline) {
            type = "deadline";
            Deadline temp = (Deadline) source;
            dueDate = temp.getDueDate().toString();
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


        //TODO check validity
        /*
        if (!Status.(priority)) {
            throw new IllegalValueException("");
        }

         */
        final Status modelStatus = Status.valueOf(status);

        if (schedule == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Schedule.class.getSimpleName()));
        }

        Schedule modelSchedule = new Schedule(schedule);

        Activity activity = null;
        switch (type) {
        case "deadline":

            if (dueDate == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                        Date.class.getSimpleName()));
            }
            if (!Date.isValidDate(dueDate)) {
                throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
            }
            final Date modelDueDate = new Date(dueDate);


            activity = new Deadline(modelName, modelDate, modelNote, modelStatus, modelPriority, modelDueDate);
            activity.setSchedule(modelSchedule);
            break;
        case "event":
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
            activity = new Event(modelName, modelDate, modelNote, modelStatus, modelPriority, eventStartDate,
                    modelEndDate);
            activity.setSchedule(modelSchedule);
            break;
        case "lesson":
            if (startDate == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                        Date.class.getSimpleName()));
            }
            if (!Date.isValidDate(startDate)) {
                throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
            }
            final Date modelStartDate = new Date(startDate);

            if (endDate == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                        Date.class.getSimpleName()));
            }
            if (!Date.isValidDate(endDate)) {
                throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
            }
            final Date lessonEndDate = new Date(endDate);
            activity = new Lesson(modelName, modelDate, modelNote, modelStatus, modelPriority, modelStartDate,
                    lessonEndDate);
            activity.setSchedule(modelSchedule);
            break;
        default:
            throw new IllegalValueException("");
        }
        return activity;
    }
}

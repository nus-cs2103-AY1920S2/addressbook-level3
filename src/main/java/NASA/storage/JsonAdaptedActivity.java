package NASA.storage;


import NASA.model.activity.Activity;
import NASA.model.activity.Date;
import NASA.model.activity.Note;
import NASA.model.activity.Priority;
import NASA.model.activity.Status;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Activity.Activity;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

import static NASA.storage.JsonAdaptedModule.MISSING_FIELD_MESSAGE_FORMAT;

/**
 * Jackson-friendly version of {@link Activity}.
 */
class JsonAdaptedActivity {

    private final String name;
    private final String date;
    private final String note;
    private final String isDone;
    private final String status;
    private final String priority;


    /**
     * Constructs a {@code JsonAdaptedActivity} with the given {@code ActivityName}.
     */
    @JsonCreator
    public JsonAdaptedActivity(String name, String date, String note, String isDone,  String status, String priority) {
        this.name = name;
        this.date = date;
        this.note = note;
        this.isDone = isDone;
        this.status = status;
        this.priority = priority;
    }

    /**
     * Converts a given {@code Activity} into this class for Jackson use.
     */
    public JsonAdaptedActivity(Activity source) {
        ActivityName = source.ActivityName;
    }

    @JsonValue
    public String getActivityName() {
        return ActivityName;
    }

    /**
     * Converts this Jackson-friendly adapted Activity object into the model's {@code Activity} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Activity.
     */
    public Activity toModelType() throws IllegalValueException {

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, seedu.address.model.person.Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(seedu.address.model.person.Name.MESSAGE_CONSTRAINTS);
        }
        final seedu.address.model.person.Name modelName = new Name(name);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Date modelPhone = new Date(date);

        if (isDone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Status.isValidStatus(status)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Status modelEmail = new Status(status);

        if (priority == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Priority.class.getSimpleName()));
        }
        if (!Priority.isValidPriorityValue(priority)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Priority modelPriority = new Priority(priority;

        if (!Activity.isValidActivityName(ActivityName)) {
            throw new IllegalValueException(Activity.MESSAGE_CONSTRAINTS);
        }


        return new Activity(name, date, note);
    }

}

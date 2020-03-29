package seedu.address.storage;

import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.Deadline;
import seedu.address.model.assignment.Status;
import seedu.address.model.assignment.Title;
import seedu.address.model.assignment.Workload;

/**
 * Jackson-friendly version of {@Link Assignment}
 */
public class JsonAdaptedAssignment {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Assignment's %s field is missing!";

    private final String deadline;
    private final String title;
    private final String estHours;
    private final String status;

    /**
     * Constructs a {@code JsonAdaptedAssignment} with the given assignment details.
     */
    @JsonCreator
    public JsonAdaptedAssignment(@JsonProperty("title") String title, @JsonProperty("deadline") String deadline,
                             @JsonProperty("workload") String estHours, @JsonProperty("status") String status) {
        this.deadline = deadline;
        this.title = title;
        this.estHours = estHours;
        this.status = status;
    }

    /**
     * Converts a given {@code Assignment} into this class for Jackson use.
     */
    public JsonAdaptedAssignment(Assignment source) {
        deadline = source.getDeadline().dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        title = source.getTitle().title;
        estHours = source.getWorkload().estHours;
        status = source.getStatus().status;
    }

    /**
     * Converts this Jackson-friendly adapted assignment object into the model's {@code Assignment} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted assignment.
     */
    public Assignment toModelType() throws IllegalValueException {
        if (deadline == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Deadline.class.getSimpleName()));
        }
        if (!Deadline.isValidDeadline(deadline)) {
            throw new IllegalValueException(Deadline.MESSAGE_CONSTRAINTS);
        }
        final Deadline modelDeadline = new Deadline(deadline);

        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
        }
        if (!Title.isValidTitle(title)) {
            throw new IllegalValueException(Title.MESSAGE_CONSTRAINTS);
        }
        final Title modelTitle = new Title(title);

        if (estHours == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Workload.class.getSimpleName()));
        }
        if (!Workload.isValidWorkload(estHours)) {
            throw new IllegalValueException(Workload.MESSAGE_CONSTRAINTS);
        }
        final Workload modelEstHours = new Workload(estHours);

        final Status modelStatus = new Status(status);

        return new Assignment(modelTitle, modelDeadline, modelEstHours, modelStatus);
    }
}

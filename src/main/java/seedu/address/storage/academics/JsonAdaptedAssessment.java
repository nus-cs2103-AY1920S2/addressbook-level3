package seedu.address.storage.academics;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.academics.Assessment;
import seedu.address.model.academics.Exam;
import seedu.address.model.academics.Homework;
import seedu.address.model.academics.Submission;

/**
 * Jackson-friendly version of {@link Assessment}.
 */
class JsonAdaptedAssessment {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Assessments %s field is missing!";

    private final String description;
    private final String type;
    private String date = null;
    private final List<JsonAdaptedSubmission> submissionTracker = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedAssessment} with the given assessment details.
     */
    @JsonCreator
    public JsonAdaptedAssessment(@JsonProperty("description") String description,
                                 @JsonProperty("type") String type,
                                 @JsonProperty("date") String date,
                                 @JsonProperty("submissionTracker") List<JsonAdaptedSubmission> submissionTracker) {
        this.description = description;
        this.type = type;
        this.date = date;
        if (submissionTracker != null) {
            this.submissionTracker.addAll(submissionTracker);
        }
    }

    /**
     * Converts a given {@code Assessment} into this class for Jackson use.
     */
    public JsonAdaptedAssessment(Assessment source) {
        description = source.getDescription();
        if (source instanceof Homework) {
            type = "homework";
            date = ((Homework) source).getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
        } else if (source instanceof Exam) {
            type = "exam";
            date = ((Exam) source).getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
        } else {
            type = null;
        }
        List<Submission> submissions = source.getSubmissionTracker();
        for (Submission submission: submissions) {
            submissionTracker.add(new JsonAdaptedSubmission(submission));
        }
    }

    /**
     * Converts this Jackson-friendly adapted assessment object into the model's {@code Assessment} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted assessment.
     */
    public Assessment toModelType() throws IllegalValueException {
        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "DESCRIPTION"));
        }
        final String modelDescription = description;
        if (type == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "TYPE"));
        }
        final String modelType = type;

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "DATE"));
        }
        if (submissionTracker == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "SUBMISSION TRACKER"));
        }
        final List<Submission> modelSubmission = new ArrayList<>();
        for (JsonAdaptedSubmission jsonAdaptedSubmission: submissionTracker) {
            modelSubmission.add(jsonAdaptedSubmission.toModelType());
        }
        String modelDate = convertDateFormat(date);
        if (modelType.equals("homework")) {
            Homework modelHomework = new Homework(description, modelDate);
            modelHomework.setSubmissionTracker(modelSubmission);
            return modelHomework;
        } else if (modelType.equals("exam")) {
            Exam modelExam = new Exam(description, modelDate);
            modelExam.setSubmissionTracker(modelSubmission);
            return modelExam;
        } else {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "TYPE"));
        }
    }

    /**
     * Converts string indicating month to its corresponding integer as a string.
     * @param month string of month.
     * @return integer of month formatted as a string.
     */
    public String convertMonthToInt(String month) {
        switch (month) {
        case "Jan":
            return "01";
        case "Feb":
            return "02";
        case "Mar":
            return "03";
        case "Apr":
            return "04";
        case "May":
            return "05";
        case "Jun":
            return "06";
        case "Jul":
            return "07";
        case "Aug":
            return "08";
        case "Sep":
            return "09";
        case "Oct":
            return "10";
        case "Nov":
            return "11";
        default:
            return "12";
        }
    }

    /**
     * Converts format of date that can be parsed by java.time.LocalDateTime.
     *
     * @param date string of date.
     * @return formatted string that can be parsed by java.time.LocalDateTime.
     */
    public String convertDateFormat(String date) {
        String[] parts = date.split(" ");
        String year = parts[2];
        String month = convertMonthToInt(parts[1]);
        String day = String.format("%02d", Integer.parseInt(parts[0]));
        return year + "-" + month + "-" + day;
    }
}

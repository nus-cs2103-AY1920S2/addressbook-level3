package seedu.address.testutil;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.academics.Assessment;
import seedu.address.model.academics.Exam;
import seedu.address.model.academics.Homework;
import seedu.address.model.academics.Submission;

/**
 * A utility class to help with building Student objects.
 */
public class AssessmentBuilder {

    public static final String DEFAULT_DESCRIPTION = "CS2103 Assignment";
    public static final String DEFAULT_TYPE = "homework";
    public static final String DEFAULT_DATE = "2020-03-04";

    private String description;
    private String type;
    private String date;
    private List<Submission> submissionTracker;

    public AssessmentBuilder() {
        description = DEFAULT_DESCRIPTION;
        type = DEFAULT_TYPE;
        date = DEFAULT_DATE;
        submissionTracker = new ArrayList<>();
    }

    /**
     * Initializes the StudentBuilder with the data of {@code studentToCopy}.
     */
    public AssessmentBuilder(Assessment assessmentToCopy) {
        description = assessmentToCopy.getDescription();
        if (assessmentToCopy instanceof Homework) {
            type = "homework";
        } else {
            type = "exam";
        }
        date = assessmentToCopy.getDate().toString();
        for (Submission submission: submissionTracker) {
            submissionTracker.add(submission);
        }
    }

    /**
     * Sets the {@code Description} of the {@code Assessment} that we are building.
     */
    public AssessmentBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Parses the {@code submissions} into a {@code List<Submission>} and set it to the
     * {@code Assessment} that we are building.
     */
    public AssessmentBuilder withSubmissions(Submission ... submissions) {
        for (Submission submission: submissions) {
            submissionTracker.add(submission);
        }
        return this;
    }

    /**
     * Sets the {@code type} of the {@code Assessment} that we are building.
     */
    public AssessmentBuilder withType(String type) {
        this.type = type;
        return this;
    }

    /**
     * Sets the {@code date} of the {@code Assessment} that we are building.
     */
    public AssessmentBuilder withDate(String date) {
        this.date = date;
        return this;
    }

    /**
     * Builds an assessment.
     */
    public Assessment build() {
        if (type.equals("homework")) {
            Homework homework = new Homework(description, date);
            homework.setSubmissionTracker(submissionTracker);
            return homework;
        } else {
            Exam exam = new Exam(description, date);
            exam.setSubmissionTracker(submissionTracker);
            return exam;
        }
    }
}

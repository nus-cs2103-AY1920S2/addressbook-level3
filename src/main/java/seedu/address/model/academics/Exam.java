package seedu.address.model.academics;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import seedu.address.model.student.Student;

/**
 * Represents an Exam assigned to the class.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Exam extends Assessment {

    /* EXAM ASSESSMENT PROPERTIES */
    private String description;
    private LocalDate date;

    /* TRACKING SUBMISSIONS */
    private List<Submission> submissionTracker = new ArrayList<>();

    /**
     * Every entry field must be present and not null.
     * @param description description of exam.
     * @param date date of examination.
     */
    public Exam(String description, String date) {
        super(description, date);
        this.description = description;
        this.date = LocalDate.parse(date);
    }

    /**
     * Every entry field must be present and not null.
     * @param description description of exam.
     * @param date date of examination.
     */
    public Exam(String description, LocalDate date) {
        super(description, date.toString());
        this.description = description;
        this.date = date;
    }

    /* ASSESS METHODS */
    /**
     * Returns the type of assessment.
     * @return String assessment type
     */
    public String getType() {
        return "exam";
    }

    /**
     * Returns the deadline of homework as a string.
     * @return String deadline of homework.
     */
    public LocalDate getDate() {
        return date;
    }

    /* SET METHODS */
    /**
     * Set the submission of each student to not submitted and unmarked.
     * @param students list of students assigned with the assessment.
     */
    public void setSubmissions(List<Student> students) {
        setAssessmentSubmissions(students);
        for (Student student: students) {
            submissionTracker.add(new Submission(student.getName().fullName));
        }
    }

    /**
     * Sets the submission tracker to the new submission tracker.
     * @param newSubmissionTracker new submission tracker.
     */
    public void setSubmissionTracker(List<Submission> newSubmissionTracker) {
        setAssessmentSubmissionTracker(newSubmissionTracker);
        for (Submission submission: newSubmissionTracker) {
            submissionTracker.add(submission);
        }
    }

    /**
     * Updates the student name in the submission tracker of assessment.
     */
    public void updateNewStudentName(String prevName, String newName) {
        for (Submission submission: submissionTracker) {
            if (submission.getStudentName().equals(prevName)) {
                submission.setStudentName(newName);
                break;
            }
        }
        updateAssessmentNewStudentName(prevName, newName);
    }

    /* STUDENT-LEVEL METHODS */
    /**
     * Adds new student to the submission tracker of all assessments.
     */
    public void addStudent(String toAdd) {
        submissionTracker.add(new Submission(toAdd));
        addAssessmentStudent(toAdd);
    }

    /**
     * Removes student to the submission tracker of all assessments.
     */
    public void removeStudent(String toRemove) {
        for (Submission submission: submissionTracker) {
            if (submission.getStudentName().equals(toRemove)) {
                submissionTracker.remove(submission);
                break;
            }
        }
        removeAssessmentStudent(toRemove);
    }

    /* ASSESSMENT-LEVEL METHODS */
    /**
     * Returns the number of students who have yet to submit their assessment.
     */
    public int noOfUnsubmittedStudents() {
        int unsubmitted = 0;
        for (Submission submission: submissionTracker) {
            if (!submission.hasSubmitted()) {
                unsubmitted++;
            }
        }
        return unsubmitted;
    }

    /**
     * Returns the number of students who have submitted their assessment.
     */
    public int noOfSubmittedStudents() {
        int submitted = 0;
        for (Submission submission: submissionTracker) {
            if (submission.hasSubmitted()) {
                submitted++;
            }
        }
        return submitted;
    }

    /* SETTING SAMPLE SUBMISSIONS */
    /**
     * Set the submission of each student to not submitted and unmarked.
     * @param students list of students assigned with the assessment.
     */
    public void setSampleSubmissions(List<Student> students, String assessmentDescription) {
        setAssessmentSampleSubmissions(students, assessmentDescription);
        switch (assessmentDescription) {
        case "Math Differentiation Homework" :
            setSampleMathSubmissions(students);
            break;
        case "Science Plant and Species Scrapbook" :
            setSampleScienceSubmissions(students);
            break;
        case "Science Experiment" :
            setSampleExperimentSubmissions(students);
            break;
        case "English Spelling Test" :
            setSampleEnglishSubmissions(students);
            break;
        case "Chinese Final Exam" :
            setSampleChineseSubmissions(students);
            break;
        default:
            break;
        }
    }

    /**
     * Sets the sample submissions.
     * @param students list of students assigned with the assessment.
     */
    public void setSampleMathSubmissions(List<Student> students) {
        submissionTracker.add(new Submission(students.get(0).getName().fullName,
                true, true, 70));
        submissionTracker.add(new Submission(students.get(1).getName().fullName,
                true, true, 78));
        submissionTracker.add(new Submission(students.get(2).getName().fullName,
                true, true, 37));
        submissionTracker.add(new Submission(students.get(3).getName().fullName,
                true, true, 92));
        submissionTracker.add(new Submission(students.get(4).getName().fullName,
                true, true, 56));
    }

    /**
     * Sets the sample submissions.
     * @param students list of students assigned with the assessment.
     */
    public void setSampleScienceSubmissions(List<Student> students) {
        submissionTracker.add(new Submission(students.get(0).getName().fullName,
                true, true, 70));
        submissionTracker.add(new Submission(students.get(1).getName().fullName,
                true, false, 0));
        submissionTracker.add(new Submission(students.get(2).getName().fullName,
                true, true, 37));
        submissionTracker.add(new Submission(students.get(3).getName().fullName,
                true, false, 0));
        submissionTracker.add(new Submission(students.get(4).getName().fullName,
                true, false, 0));
    }

    /**
     * Sets the sample submissions.
     * @param students list of students assigned with the assessment.
     */
    public void setSampleExperimentSubmissions(List<Student> students) {
        submissionTracker.add(new Submission(students.get(0).getName().fullName,
                false, false, 0));
        submissionTracker.add(new Submission(students.get(1).getName().fullName,
                false, false, 0));
        submissionTracker.add(new Submission(students.get(2).getName().fullName,
                true, false, 0));
        submissionTracker.add(new Submission(students.get(3).getName().fullName,
                true, false, 0));
        submissionTracker.add(new Submission(students.get(4).getName().fullName,
                false, false, 0));
    }

    /**
     * Sets the sample submissions.
     * @param students list of students assigned with the assessment.
     */
    public void setSampleEnglishSubmissions(List<Student> students) {
        submissionTracker.add(new Submission(students.get(0).getName().fullName,
                true, false, 0));
        submissionTracker.add(new Submission(students.get(1).getName().fullName,
                true, false, 0));
        submissionTracker.add(new Submission(students.get(2).getName().fullName,
                true, false, 0));
        submissionTracker.add(new Submission(students.get(3).getName().fullName,
                true, false, 0));
        submissionTracker.add(new Submission(students.get(4).getName().fullName,
                true, false, 0));
    }

    /**
     * Sets the sample submissions.
     * @param students list of students assigned with the assessment.
     */
    public void setSampleChineseSubmissions(List<Student> students) {
        submissionTracker.add(new Submission(students.get(0).getName().fullName,
                false, false, 0));
        submissionTracker.add(new Submission(students.get(1).getName().fullName,
                false, false, 0));
        submissionTracker.add(new Submission(students.get(2).getName().fullName,
                false, false, 0));
        submissionTracker.add(new Submission(students.get(3).getName().fullName,
                false, false, 0));
        submissionTracker.add(new Submission(students.get(4).getName().fullName,
                false, false, 0));
    }

    @Override
    public String toString() {
        return "Exam: " + this.description + "\n"
                + "At: " + this.date + "\n";
    }

}

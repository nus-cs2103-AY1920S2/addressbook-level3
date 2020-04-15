package seedu.address.model.academics;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;

import seedu.address.model.student.Student;

/**
 * Represents an Assessment given to the class to complete.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Assessment {

    /* ASSESSMENT PROPERTIES */
    private String description;
    private String type;
    private LocalDate date;
    private int submitted = 0;
    private int marked = 0;

    /* TRACKING SUBMISSIONS */
    private List<Submission> submissionTracker = new ArrayList<>();

    /**
     * Every entry field must be present and not null.
     * @param description description of assessment.
     * @param type type of assessment.
     * @param date date of assessment.
     */
    public Assessment(String description, String type, String date) {
        this.description = description;
        this.type = type.toLowerCase().trim();
        this.date = LocalDate.parse(date);
    }

    /**
     * Every entry field must be present and not null.
     * @param description description of assessment.
     * @param type type of assessment.
     * @param date date of assessment.
     */
    public Assessment(String description, String type, LocalDate date) {
        this.description = description;
        this.type = type;
        this.date = date;
    }

    /* ASSESS METHODS */
    /**
     * Returns the description of the assessment.
     * @return description of assessment.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the type of the assessment.
     * @return type of assessment.
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the date of the assessment.
     * @return date of assessment.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Returns the submission tracker of the assessment.
     * @return submission tracker of assessment.
     */
    public List<Submission> getSubmissionTracker() {
        Collections.sort(submissionTracker);
        return submissionTracker;
    }

    /**
     * Checks the format of the date string given.
     */
    public static boolean checkValidDate(String date) {
        String[] split = date.trim().split("-");
        if (split.length < 3 || split[0].length() < 4 || split[1].length() < 2 || split[2].length() < 2) {
            return false;
        }
        int month = Integer.parseInt(split[1]);
        int day = Integer.parseInt(split[2]);
        switch (month) {
        case 1:
        case 3:
        case 5:
        case 7:
        case 8:
        case 10:
        case 12:
            if (day < 0 || day > 31) {
                return false;
            }
            break;
        case 4:
        case 6:
        case 9:
        case 11:
            if (day < 0 || day > 30) {
                return false;
            }
            break;
        case 2:
            if (day < 0 || day > 28) {
                return false;
            }
            break;
        default:
            return false;
        }
        return true;
    }

    /* SET METHODS */
    /**
     * Set the submission of each student to not submitted and unmarked.
     * @param students list of students assigned with the assessment.
     */
    public void setSubmissions(List<Student> students) {
        students.stream().forEach(student -> submissionTracker.add(new Submission(student.getName().fullName)));
        Collections.sort(submissionTracker);
    }

    /**
     * Sets the submission tracker to the new submission tracker.
     * @param newSubmissionTracker new submission tracker.
     */
    public void setSubmissionTracker(List<Submission> newSubmissionTracker) {
        int hasSubmitted = 0;
        int hasMarked = 0;
        if (newSubmissionTracker != null) {
            for (Submission submission: newSubmissionTracker) {
                submissionTracker.add(submission);
                hasSubmitted = submission.hasSubmitted() ? hasSubmitted + 1 : hasSubmitted;
                hasMarked = submission.isMarked() ? hasMarked + 1 : hasMarked;
            }
        }
        Collections.sort(submissionTracker);
        submitted = hasSubmitted;
        marked = hasMarked;
    }

    /**
     * Updates the student name in the submission tracker of assessment.
     */
    public void updateNewStudentName(String prevName, String newName) {
        submissionTracker
                .stream().filter(submission -> submission.getStudentName().equals(prevName))
                .forEach(submission -> submission.setStudentName(newName));
        Collections.sort(submissionTracker);
    }

    /* STUDENT-LEVEL METHODS */
    /**
     * Adds new student to the submission tracker of all assessments.
     */
    public void addStudent(String toAdd) {
        submissionTracker.add(new Submission(toAdd));
        Collections.sort(submissionTracker);
    }

    /**
     * Removes student to the submission tracker of all assessments.
     */
    public void removeStudent(String toRemove) {
        Optional<Submission> studentSubmission = submissionTracker
                .stream().filter(submission -> submission.getStudentName().equals(toRemove)).findFirst();
        if (studentSubmission.isPresent()) {
            submissionTracker.remove(studentSubmission.get());
            submitted = studentSubmission.get().hasSubmitted() ? submitted - 1 : submitted;
            marked = studentSubmission.get().isMarked() ? marked - 1 : marked;
        }
    }

    /* ASSESSMENT-LEVEL METHODS */
    /**
     * Returns true if the student has submitted their work for the given assessment.
     * record.
     */
    public boolean hasStudentSubmitted(String student) {
        requireNonNull(student);
        Optional<Submission> studentSubmission = submissionTracker
                .stream().filter(submission -> submission.getStudentName().equals(student)).findFirst();
        return studentSubmission.isEmpty() ? false : studentSubmission.get().hasSubmitted();
    }

    /**
     * Submits students' assessments.
     * @param studentList list of students who have completed their assessment.
     */
    public void setSubmitted(List<String> studentList) {
        for (String studentName: studentList) {
            Optional<Submission> studentSubmission = submissionTracker.stream()
                    .filter(submission -> submission.getStudentName().equals(studentName)).findFirst();
            if (studentSubmission.isPresent()) {
                submitted = !studentSubmission.get().hasSubmitted() ? submitted + 1 : submitted;
                studentSubmission.get().markAsSubmitted();
            }
        }
    }

    /**
     * Marks students' submissions to the assessment in {@code Academics}.
     * {@code target} must exist in the assessment list.
     */
    public void markAssessment(HashMap<String, Integer> submissions) {
        submissions.entrySet().stream().forEach(entry -> mark(entry.getKey(), entry.getValue()));
    }

    /**
     * Marks student's submission and assigns a score to the student's submission.
     * @param student student submitting his/her assessment.
     * @param score score given to the student's submission.
     */
    public void mark(String student, int score) {
        Optional<Submission> studentSubmission = submissionTracker
                .stream().filter(submission -> submission.getStudentName().equals(student)).findFirst();
        if (studentSubmission.isPresent()) {
            marked = !studentSubmission.get().isMarked() ? marked + 1 : marked;
            studentSubmission.get().markAssessment(score);
        }
    }

    /**
     * Returns the number of students who have submitted their assessment.
     */
    public int noOfSubmittedStudents() {
        return submitted;
    }

    /**
     * Returns the number of students whose submissions have not been marked.
     */
    public int noOfMarkedSubmissions() {
        return marked;
    }

    /**
     * Returns true if both assessments have the same description.
     */
    public boolean isSameAssessment(Assessment otherAssessment) {
        if (otherAssessment == this) {
            return true;
        }
        return otherAssessment != null && otherAssessment.getDescription().equals(getDescription());
    }

    /* STATISTICS METHODS */
    /**
     * Returns the average score scored by the class for this assessment.
     */
    public double averageScore() {
        OptionalDouble totalScore = submissionTracker.stream().mapToDouble(submission -> submission.getScore())
                .reduce((s1, s2) -> s1 + s2);
        int count = (int) submissionTracker.stream().filter(submission -> submission.getScore() != 0).count();

        return count == 0 ? 0 : totalScore.getAsDouble() / count;
    }

    /**
     * Returns the median score scored by the class for this assessment.
     */
    public double medianScore() {
        ArrayList<Integer> scores = new ArrayList<>();
        submissionTracker.stream()
                .filter(submission -> submission.getScore() != 0)
                .forEach(submission -> scores.add(submission.getScore()));
        Collections.sort(scores);
        int noOfStudents = scores.size();

        if (noOfStudents == 0) {
            return 0;
        } else if (noOfStudents == 1) {
            return scores.get(0);
        } else if (noOfStudents == 2) {
            return (scores.get(0) + scores.get(1)) / 2;
        }

        if (noOfStudents % 2 == 0) {
            int medianIndex = noOfStudents / 2;
            return (double) (scores.get(medianIndex - 1) + scores.get(medianIndex)) / 2;
        } else {
            return scores.get((noOfStudents - 1) / 2);
        }
    }

    @Override
    public String toString() {
        return "Assessment: " + this.description + "\n"
                + "Type: " + this.type + "\n"
                + "Date: " + this.date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Assessment assessment = (Assessment) o;
        return Objects.equals(description, assessment.description)
                && Objects.equals(type, assessment.type);
    }
}

/**
 * Comparator class for assessments.
 */
class AssessmentComparator implements Comparator<Assessment> {
    @Override
    public int compare(Assessment a1, Assessment a2) {
        return a1.getDate()
                .compareTo(a2.getDate());
    }
}

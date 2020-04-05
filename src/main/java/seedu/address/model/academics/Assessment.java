package seedu.address.model.academics;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import seedu.address.model.student.Student;

/**
 * Represents an Assessment given to the class to complete.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public abstract class Assessment {

    /* ASSESSMENT PROPERTIES */
    private String description;
    private LocalDate date;
    private int submitted = 0;
    private int marked = 0;

    /* TRACKING SUBMISSIONS */
    private List<Submission> submissionTracker = new ArrayList<>();

    /**
     * Every entry field must be present and not null.
     * @param description description of assessment.
     * @param date date of assessment.
     */
    public Assessment(String description, String date) {
        this.description = description;
        this.date = LocalDate.parse(date);
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
        return submissionTracker;
    }

    /* SET METHODS */
    /**
     * Set the submission of each student to not submitted and unmarked.
     * @param students list of students assigned with the assessment.
     */
    public abstract void setSubmissions(List<Student> students);

    /**
     * Set the submission of each student to not submitted and unmarked.
     * @param students list of students assigned with the assessment.
     */
    public void setAssessmentSubmissions(List<Student> students) {
        for (Student student: students) {
            submissionTracker.add(new Submission(student.getName().fullName));
        }
    }

    /**
     * Sets the submission tracker to the new submission tracker.
     * @param newSubmissionTracker new submission tracker.
     */
    public abstract void setSubmissionTracker(List<Submission> newSubmissionTracker);

    /**
     * Sets the submission tracker to the new submission tracker.
     * @param newSubmissionTracker new submission tracker.
     */
    public void setAssessmentSubmissionTracker(List<Submission> newSubmissionTracker) {
        int hasSubmitted = 0;
        int hasMarked = 0;
        for (Submission submission: newSubmissionTracker) {
            submissionTracker.add(submission);
            hasSubmitted = submission.hasSubmitted() ? hasSubmitted + 1 : hasSubmitted;
            hasMarked = submission.isMarked() ? hasMarked + 1 : hasMarked;
        }
        submitted = hasSubmitted;
        marked = hasMarked;
    }

    public abstract void updateNewStudentName(String prevName, String newName);

    /**
     * Updates the student name in the submission tracker of assessment.
     */
    public void updateAssessmentNewStudentName(String prevName, String newName) {
        for (Submission submission: submissionTracker) {
            if (submission.getStudentName().equals(prevName)) {
                submission.setStudentName(newName);
                break;
            }
        }
    }

    /* STUDENT-LEVEL METHODS */
    /**
     * Adds new student to the submission tracker of all assessments.
     */
    public abstract void addStudent(String toAdd);

    /**
     * Adds new student to the submission tracker of all assessments.
     */
    public void addAssessmentStudent(String toAdd) {
        submissionTracker.add(new Submission(toAdd));
    }

    /**
     * Removes student to the submission tracker of all assessments.
     */
    public abstract void removeStudent(String toRemove);

    /**
     * Removes student to the submission tracker of all assessments.
     */
    public void removeAssessmentStudent(String toRemove) {
        for (Submission submission: submissionTracker) {
            if (submission.getStudentName().equals(toRemove)) {
                submissionTracker.remove(submission);
                submitted = submission.hasSubmitted() ? submitted - 1 : submitted;
                marked = submission.isMarked() ? marked - 1 : marked;
                break;
            }
        }
    }

    /* ASSESSMENT-LEVEL METHODS */
    /**
     * Returns true if the student has submitted their work for the given assessment.
     * record.
     */
    public boolean hasStudentSubmitted(String student) {
        requireNonNull(student);
        for (Submission submission: submissionTracker) {
            if (submission.getStudentName().equals(student)) {
                return submission.hasSubmitted();
            }
        }
        return false;
    }

    /**
     * Submits students' assessments.
     * @param studentList list of students who have completed their assessment.
     */
    public void setSubmitted(List<String> studentList) {
        for (String studentName: studentList) {
            for (Submission submission: submissionTracker) {
                if (submission.getStudentName().equals(studentName)) {
                    submitted = !submission.hasSubmitted() ? submitted + 1 : submitted;
                    submission.markAsSubmitted();
                }
            }
        }
    }

    /**
     * Marks students' submissions to the assessment in {@code Academics}.
     * {@code target} must exist in the assessment list.
     */
    public void markAssessment(HashMap<String, Integer> submissions) {
        Iterator<Map.Entry<String, Integer>> iterator = submissions.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            mark(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Marks student's submission and assigns a score to the student's submission.
     * @param student student submitting his/her assessment.
     * @param score score given to the student's submission.
     */
    public void mark(String student, int score) {
        for (Submission submission: submissionTracker) {
            if (submission.getStudentName().equals(student)) {
                marked = !submission.isMarked() ? marked + 1 : marked;
                submission.markAssessment(score);
            }
        }
    }

    /**
     * Returns the number of students who have yet to submit their assessment.
     */
    public int noOfUnsubmittedStudents() {
        return submissionTracker.size() - submitted;
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
        return otherAssessment.getDescription().equals(getDescription());
    }

    /* STATISTICS METHODS */
    /**
     * Returns the average score scored by the class for this assessment.
     */
    public int averageScore() {
        int totalScore = 0;
        int count = 0;
        for (Submission submission: submissionTracker) {
            if (submission.getScore() != 0) {
                totalScore += submission.getScore();
                count++;
            }
        }
        if (count == 0) {
            return 0;
        } else {
            return totalScore / count;
        }
    }

    /**
     * Returns the median score scored by the class for this assessment.
     */
    public int medianScore() {
        ArrayList<Integer> scores = new ArrayList<>();
        for (Submission submission: submissionTracker) {
            if (submission.getScore() != 0) {
                scores.add(submission.getScore());
            }
        }
        Collections.sort(scores);
        if (scores.size() == 0) {
            return 0;
        } else if (scores.size() == 1) {
            return scores.get(0);
        } else if (scores.size() == 2) {
            int sum = scores.get(0) + scores.get(1);
            return sum / 2;
        } else if (scores.size() % 2 == 0) {
            int medianIndex = scores.size() / 2;
            int sum = scores.get(medianIndex - 1) + scores.get(medianIndex);
            return sum / 2;
        } else {
            int medianIndex = (scores.size() - 1) / 2;
            return scores.get(medianIndex);
        }
    }

    /* SETTING SAMPLE SUBMISSIONS */
    /**
     * Set the submission of each student to not submitted and unmarked.
     * @param students list of students assigned with the assessment.
     */
    public abstract void setSampleSubmissions(List<Student> students, String assessmentDescription);

    /**
     * Set the submission of each student to not submitted and unmarked.
     * @param students list of students assigned with the assessment.
     */
    public void setAssessmentSampleSubmissions(List<Student> students, String assessmentDescription) {
        switch (assessmentDescription) {
        case "Math Differentiation Homework" :
            setAssessmentSampleMathSubmissions(students);
            break;
        case "Science Plant and Species Scrapbook" :
            setAssessmentSampleScienceSubmissions(students);
            break;
        case "Science Experiment" :
            setAssessmentSampleExperimentSubmissions(students);
            break;
        case "English Spelling Test" :
            setAssessmentSampleEnglishSubmissions(students);
            break;
        case "Chinese Final Exam" :
            setAssessmentSampleChineseSubmissions(students);
            break;
        default:
            break;
        }
    }

    /**
     * Sets the sample submissions.
     * @param students list of students assigned with the assessment.
     */
    public void setAssessmentSampleMathSubmissions(List<Student> students) {
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
    public void setAssessmentSampleScienceSubmissions(List<Student> students) {
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
    public void setAssessmentSampleExperimentSubmissions(List<Student> students) {
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
    public void setAssessmentSampleEnglishSubmissions(List<Student> students) {
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
    public void setAssessmentSampleChineseSubmissions(List<Student> students) {
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
        return "Assessment: " + this.description + "\n"
                + "Submitted: " + noOfSubmittedStudents()
                + "Unsubmitted: " + noOfUnsubmittedStudents();
    }
}

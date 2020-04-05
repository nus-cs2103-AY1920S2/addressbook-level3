package seedu.address.model.academics;

/**
 * Represents a students submission for an assessment.
 */
public class Submission implements Comparable<Submission> {
    /* SUBMISSION PROPERTIES */
    private String studentName;
    private boolean submitted;
    private boolean marked;
    private int score;

    public Submission(String studentName) {
        this.studentName = studentName;
        this.submitted = false;
        this.marked = false;
        this.score = 0;
    }

    public Submission(String studentName, boolean submitted, boolean marked, int score) {
        this.studentName = studentName;
        this.submitted = submitted;
        this.marked = marked;
        this.score = score;
    }

    /* ASSESS METHODS */
    /**
     * Returns student name.
     * @return String student name.
     */
    public String getStudentName() {
        return studentName;
    }

    public int getScore() {
        return this.score;
    }

    /**
     * Returns a boolean that indicates if assessment has been submitted.
     * @return boolean showing whether assessment is submitted by the student.
     */
    public boolean hasSubmitted() {
        return this.submitted;
    }

    /**
     * Sets student of submission to the given student name.
     * @param newName name of new student.
     */
    public void setStudentName(String newName) {
        this.studentName = newName;
    }

    /* SUBMISSION-LEVEL METHODS */
    /**
     * Returns a boolean that indicates if assessment is marked.
     * @return boolean showing whether assessment is marked.
     */
    public boolean isMarked() {
        return this.marked;
    }

    /**
     * Submits current assessment.
     */
    public void markAsSubmitted() {
        this.submitted = true;
    }

    /**
     * Assigns a mark to the the assessment.
     */
    public void markAssessment(int score) {
        this.marked = true;
        this.score = score;
    }

    @Override
    public int compareTo(Submission s) {
        return 0;
    }
}

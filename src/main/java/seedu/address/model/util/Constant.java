package seedu.address.model.util;

public enum Constant {
    COURSE_REMOVE_SIGNAL("Course Remove Signal"),
    STUDENT_REMOVE_SIGNAL("Student Remove Signal");

    private final String text;

    /**
     * @param text
     */
    Constant(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}

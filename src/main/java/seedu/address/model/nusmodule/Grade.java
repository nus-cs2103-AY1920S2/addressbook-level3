package seedu.address.model.nusmodule;

/**
 * Represents the grades obtained by a NUS student for any specific module.
 */
public enum Grade {
    APLUS(5.0, "A+", true, true),
    A(5.0, "A", true, true),
    AMINUS(4.5, "A-", true, true),
    BPLUS(4.0, "B+", true, true),
    B(3.5, "B", true, true),
    BMINUS(3.0, "B-", true, true),
    CPLUS(2.5, "C+", true, true),
    C(2.0, "C", true, true),
    DPLUS(1.5, "D+", true, false),
    D(1.0, "D", true, false),
    F(0.0, "F", false, false),
    S(0.0, "S", true, true),
    U(0.0, "U", false, false);

    public static final String MESSAGE_CONSTRAINTS = "Please enter valid grade.";

    private final double point;
    private final String text;
    private final boolean isPassed;
    private final boolean isSatisfactory;

    Grade(double point, String text, boolean isPassed, boolean isSatisfactory) {
        this.point = point;
        this.text = text;
        this.isPassed = isPassed;
        this.isSatisfactory = isSatisfactory;
    }

    public static Grade getGrade(String text) {
        Grade grade = null;
        for (Grade g : values()) {
            if (g.text.equals(text)) {
                grade = g;
            }
        }
        return grade;
    }

    public static Grade getGradeAfterSu(String text) {
        for (Grade g : values()) {
            if (g.text.equals(text) && g.isSatisfactory) {
                return S;
            }
        }
        return U;
    }

    /**
     * checks whether the Grade object represents a grade that has been SUed or not.
     * @return true if the Grade object represents a grade that has been SUed or false if otherwise.
     */
    public boolean isSued() {
        if (this.equals(S) || this.equals(U)) {
            return true;
        }
        return false;
    }

    public double getPoint() {
        return this.point;
    }

    public String getText() {
        return this.text;
    }

    /**
     * Returns if a given string is a valid grade.
     */
    public static boolean isValidGrade(String test) {
        for (Grade g : values()) {
            if (g.text.equals(test)) {
                return true;
            }
        }
        return false;
    }
}

package seedu.address.model.nusmodule;

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

    private final double point;
    private final String text;
    private final boolean isPassed;
    private final boolean isSatisfactory;

    private Grade(double point, String text, boolean isPassed, boolean isSatisfactory) {
        this.point = point;
        this.text = text;
        this.isPassed = isPassed;
        this.isSatisfactory = isSatisfactory;
    }

    public static Grade getGrade(String text) {
        Grade grade = F;
        for(Grade g : values()) {
            if(g.text.equals(text)) {
                grade = g;
            }
        }
        return grade;
    }

    public static Grade getGradeAfterSu(String text) {
        for (Grade g : values()) {
            if (g.text.equals(text) &&g.isSatisfactory) {
                return S;
            }
        }
        return U;
    }

    public double getPoint() {
        return this.point;
    }

}

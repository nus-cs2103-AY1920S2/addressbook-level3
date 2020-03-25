package tatracker.model.session;

/**
 * Represents a session type. Session types follows the same specifications as the TSS.
 * Example session types include: Tutorial, Grading, Consultation, etc.
 */
public enum SessionType {
    TUTORIAL ("Tutorial"),
    LAB ("Lab"),
    CONSULTATION ("Consultation"),
    GRADING ("Grading"),
    PREPARATION ("Preparation"),
    OTHER ("Other");

    private String typeName;
    private SessionType(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return typeName;
    }
}

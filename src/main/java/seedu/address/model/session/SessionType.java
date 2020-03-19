package seedu.address.model.session;

/**
 * Represents a session type. Session types follows the same specifications as the TSS.
 * Example session types include: Tutorial, Grading, Consultation, etc.
 */
public enum SessionType {
    SESSION_TYPE_TUTORIAL ("Tutorial"),
    SESSION_TYPE_LAB ("Lab"),
    SESSION_TYPE_CONSULTATION ("Consultation"),
    SESSION_TYPE_GRADING ("Grading"),
    SESSION_TYPE_PREPARATION ("Preparation"),
    SESSION_TYPE_OTHER ("Other");

    private String typeName;
    private SessionType(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return typeName;
    }
}

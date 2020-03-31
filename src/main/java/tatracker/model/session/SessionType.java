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

    public static final int NUM_SESSION_TYPES = 6;

    private String typeName;

    public static SessionType getSessionTypeById(int id) {
        switch (id) {
        case 0:
            return SessionType.TUTORIAL;
        case 1:
            return SessionType.LAB;
        case 2:
            return SessionType.CONSULTATION;
        case 3:
            return SessionType.GRADING;
        case 4:
            return SessionType.PREPARATION;
        case 5:
            return SessionType.OTHER;
        default:
            throw new IllegalArgumentException("The requested SessionType ID is invalid!");
        }
    }

    SessionType(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return typeName;
    }
}

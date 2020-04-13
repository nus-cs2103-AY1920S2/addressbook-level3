//@@author fatin99

package tatracker.model.session;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents a session type. Session types follows the same specifications as the TSS.
 * Example session types include: Tutorial, Grading, Consultation, etc.
 */
public enum SessionType {
    TUTORIAL,
    LAB,
    CONSULTATION,
    GRADING,
    PREPARATION,
    OTHER;

    // TODO: Add Recitations to report
    public static final int NUM_SESSION_TYPES = values().length;

    public static final String MESSAGE_CONSTRAINTS =
            "These are the only session types: lab, tutorial, consultation, grading, preparation, other";

    //@@author potatocombat

    private static final Map<String, SessionType> SESSION_TYPES = Arrays.stream(values())
            .collect(Collectors.toUnmodifiableMap(type -> type.name().toLowerCase(), type -> type));

    private static final Map<Integer, SessionType> SESSION_TYPE_IDS = Arrays.stream(values())
            .collect(Collectors.toUnmodifiableMap(Enum::ordinal, type -> type));


    public static boolean isValidSessionType(String test) {
        return SESSION_TYPES.containsKey(test.toLowerCase());
    }

    public static SessionType getSessionType(String sessionType) {
        requireNonNull(sessionType);
        return SESSION_TYPES.get(sessionType.toLowerCase());
    }

    //@@author Eclmist

    public static SessionType getSessionTypeById(int id) {
        if (id < 0 || id >= NUM_SESSION_TYPES) {
            throw new IllegalArgumentException("The requested SessionType ID is invalid!");
        }
        return SESSION_TYPE_IDS.get(id);
    }

    //@@author fatin99

    @Override
    public String toString() {
        String name = name();
        if (name.length() > 0) {
            // Capitalise first letter
            name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        }
        return name;
    }
}

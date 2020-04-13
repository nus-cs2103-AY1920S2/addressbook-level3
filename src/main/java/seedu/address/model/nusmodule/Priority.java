package seedu.address.model.nusmodule;

/**
 * Represents the priority of certain module tasks.
 */
public enum Priority {
    VERYHIGH("Very high", 5),
    HIGH("High", 4),
    MEDIAN("Medium", 3),
    LOW("Low", 2),
    VERYLOW("Very low", 1);

    public static final String MESSAGE_CONSTRAINTS = "Please enter valid priority (an integer from 1 to 5)";

    private final String text;
    private final int levelOfSignificance;

    Priority(String text, int levelOfSignificance) {
        this.text = text;
        this.levelOfSignificance = levelOfSignificance;
    }

    /**
     * Returns if a given string is a valid priority.
     */
    public static boolean isValidPriority(String test) {
        try {
            Integer.parseInt(test);
        } catch (NumberFormatException e) {
            return false;
        }

        for (Priority p : values()) {
            if (p.levelOfSignificance == Integer.parseInt(test)) {
                return true;
            }
        }
        return false;
    }

    public static Priority getPriority(String text) {
        Priority priority = null;
        for (Priority p : values()) {
            if (p.levelOfSignificance == Integer.parseInt(text)) {
                priority = p;
            }
        }
        return priority;
    }

    public int getLevelOfSignificance() {
        return this.levelOfSignificance;
    }

    @Override
    public String toString() {
        return this.text;
    }
}

package seedu.address.model.nusmodule;

public enum Priority {
    VERYHIGH("very high", 5),
    HIGH("high",4),
    MEDIAN("median", 3),
    LOW("low", 2),
    VERYLOW("very low", 1);

    public static final String MESSAGE_CONSTRAINTS = "Please enter valid priority (an integer from 1 to 5)";

    private final String text;
    private final int levelOfSignificance;

    Priority(String text, int levelOfSignificance) {
        this.text = text;
        this.levelOfSignificance = levelOfSignificance;
    }

    public static boolean isValidPriority(String test) {
        for (Priority p : values()) {
            if (p.levelOfSignificance == Integer.parseInt(test)) {
                return true;
            }
        }
        return false;
    }

    public static Priority getPriority(String text) {
        Priority priority = null;
        for (Priority p  : values()) {
            if (p.levelOfSignificance == Integer.parseInt(text)) {
                priority = p;
            }
        }
        return priority;
    }

    @Override
    public String toString() {
        return this.text;
    }
}

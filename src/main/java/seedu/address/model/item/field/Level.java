package seedu.address.model.item.field;

/**
 *  Represents a Skill proficiency level.
 */
public enum Level {
    BASIC(1),
    INTERMEDIATE(2),
    ADVANCED(3);

    public static final String MESSAGE_CONSTRAINTS = "A skill level can only be basic, intermediate, or advanced.";

    private final int levelCode;

    Level(int levelCode) {
        this.levelCode = levelCode;
    }

    public int getLevelCode() {
        return this.levelCode;
    }
}

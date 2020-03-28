package tatracker.model.group;

/**
 * Represents a group type.
 * Can be a lab or a tutorial.
 */
public enum GroupType {
    LAB("Lab"),
    TUTORIAL("Tutorial"),
    RECITATION("Recitation"),
    OTHER("Other");

    private String typeName;
    GroupType(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return typeName;
    }
}

package seedu.address.model.graph;

/**
 * The type of axis that user can plot
 */
public enum AxisType {

    REPS, WEIGHT, NA;

    public static AxisType getAxisType (String value) {
        switch (value) {
        case "reps":
            return REPS;
        case "weight":
            return WEIGHT;
        default:
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String toString() {
        switch(this) {
        case REPS:
            return "reps";
        case WEIGHT:
            return "weight";
        default:
            throw new IllegalArgumentException();
        }
    }
}

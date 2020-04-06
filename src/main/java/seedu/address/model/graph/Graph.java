package seedu.address.model.graph;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.exercise.ExerciseName;

/**
 * Represents a graph of an exercise done by a client. Guarantees: details are present and
 * not null, field values are validated, immutable.
 */
public class Graph {

    private final ExerciseName exerciseName;
    private final Axis axis;
    private final StartDate startDate;
    private final EndDate endDate;

    public Graph(ExerciseName exerciseName, Axis axis, StartDate startDate, EndDate endDate) {
        requireAllNonNull(exerciseName, axis, startDate, endDate);
        this.exerciseName = exerciseName;
        this.axis = axis;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public ExerciseName getExerciseName() {
        return exerciseName;
    }

    public Axis getAxis() {
        return axis;
    }

    public StartDate getStartDate() {
        return startDate;
    }

    public EndDate getEndDate() {
        return endDate;
    }

    /**
     * Returns true if both graph of the same name, axis, startDate and endDate.
     * This defines a weaker notion of equality between two graphs.
     */
    public boolean isSameGraph(Graph otherGraph) {
        if (otherGraph == this) {
            return true;
        }

        return otherGraph != null
            && otherGraph.getExerciseName().equals(getExerciseName())
            && otherGraph.getAxis().equals(getAxis())
            && otherGraph.getStartDate().equals(getStartDate())
            && otherGraph.getEndDate().equals(getEndDate());
    }

}

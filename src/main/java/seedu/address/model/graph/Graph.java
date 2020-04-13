package seedu.address.model.graph;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.model.client.Client;
import seedu.address.model.exercise.Exercise;
import seedu.address.model.exercise.ExerciseName;

/**
 * Represents a graph of an exercise done by a client. Guarantees: details are
 * present and not null, field values are validated, immutable.
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
     * Generates a list of exercises to be displayed in the graph.
     *
     * @param client The client in view
     * @return The list of filtered exercises
     */
    public List<Exercise> generateGraphList(Client client) {
        List<Exercise> exerciseList = client.getExerciseList().asUnmodifiableObservableList();
        Comparator<Exercise> exerciseComparator = (e1, e2)
            -> e1.getExerciseDate().value.compareTo(e2.getExerciseDate().value);

        Stream<Exercise> graphList = exerciseList.stream()
                .filter(exercise -> exercise.getExerciseName().equals(exerciseName))
                .filter(exercise -> (exercise.getExerciseDate().value.compareTo(startDate.value) >= 0))
                .filter(exercise -> (exercise.getExerciseDate().value.compareTo(endDate.value) <= 0))
                .sorted(exerciseComparator);

        switch (axis.getAxisType()) {
        case REPS:
            graphList = graphList.filter(exercise -> !exercise.getExerciseReps().value.isEmpty());
            break;
        case WEIGHT:
            graphList = graphList.filter(exercise -> !exercise.getExerciseWeight().value.isEmpty());
            break;
        default:
        }
        return graphList.collect(Collectors.toList());
    }

    /**
     * Returns true if both graph of the same name, axis, startDate and endDate.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Graph)) {
            return false;
        }

        Graph otherGraph = (Graph) other;

        return otherGraph != null
                && otherGraph.getExerciseName().equals(getExerciseName())
                && otherGraph.getAxis().equals(getAxis())
                && otherGraph.getStartDate().equals(getStartDate())
                && otherGraph.getEndDate().equals(getEndDate());
    }

}

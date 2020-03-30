package seedu.zerotoone.model.session;

import static seedu.zerotoone.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import seedu.zerotoone.model.exercise.ExerciseName;


/**
 * Represents an immutable Session once a session is completed.
 */
public class CompletedSession {

    // Identity fields
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final ExerciseName exerciseName;
    private final List<SessionSet> sets = new LinkedList<>();

    /**
     * Every field must be present and not null.
     */
    public CompletedSession(ExerciseName name, List<SessionSet> sets, LocalDateTime start, LocalDateTime end) {
        requireAllNonNull(name, sets, start, end);
        this.exerciseName = name;
        this.startTime = start;
        this.endTime = end;
        this.sets.addAll(sets);
    }

    public ExerciseName getExerciseName() {
        return this.exerciseName;
    }

    public List<SessionSet> getSets() {
        return Collections.unmodifiableList(sets);
    }

    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    public LocalDateTime getEndTime() {
        return this.endTime;
    }
}

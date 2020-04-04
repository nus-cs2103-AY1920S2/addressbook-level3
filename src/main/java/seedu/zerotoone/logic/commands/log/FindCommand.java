package seedu.zerotoone.logic.commands.log;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.zerotoone.logic.commands.CommandResult;
import seedu.zerotoone.model.Model;
import seedu.zerotoone.model.exercise.ExerciseName;
import seedu.zerotoone.model.log.PredicateFilterLogExerciseName;
import seedu.zerotoone.model.session.CompletedExercise;
import seedu.zerotoone.model.session.CompletedWorkout;
import seedu.zerotoone.model.workout.WorkoutName;

/**
 * Finds and lists all exercises in exercise list whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends LogCommand {
    public static final String COMMAND_WORD = "find";
    public static final String MESSAGE_USAGE =
        "Usage: log find [e/exercise_name] [st/start_time] [et/end_time] [w/workout_name]";
    public static final String MESSAGE_SESSIONS_LISTED_OVERVIEW = "%1$d sessions found!";

    private final Optional<LocalDateTime> startTimeOptional;
    private final Optional<LocalDateTime> endTimeOptional;
    private final Optional<ExerciseName> exerciseNameOptional;
    private final Optional<WorkoutName> workoutNameOptional;

    public FindCommand(Optional<LocalDateTime> startTimeOptional,
        Optional<LocalDateTime> endTimeOptional,
        Optional<ExerciseName> exerciseNameOptional,
        Optional<WorkoutName> workoutNameOptional) {
        this.startTimeOptional = startTimeOptional;
        this.endTimeOptional = endTimeOptional;
        this.exerciseNameOptional = exerciseNameOptional;
        this.workoutNameOptional = workoutNameOptional;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        Predicate<CompletedWorkout> predicate = session -> true;


        if (exerciseNameOptional.isPresent()) {
            predicate = predicate.and(new PredicateFilterLogExerciseName(exerciseNameOptional.get().fullName));
        }

        if (startTimeOptional.isPresent()) {
            predicate = predicate.and(session -> session.getStartTime().equals(startTimeOptional.get()));
        }

        if (endTimeOptional.isPresent()) {
            predicate = predicate.and(session -> session.getEndTime().equals(endTimeOptional.get()));
        }

        // todo implement workout

        model.updateFilteredLogList(predicate);

        String outputMessage = String.format(MESSAGE_SESSIONS_LISTED_OVERVIEW,
                model.getFilteredLogList().size());
        return new CommandResult(outputMessage);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof FindCommand // instanceof handles nulls
            && exerciseNameOptional.equals(((FindCommand) other).exerciseNameOptional)); // state check
    }
}

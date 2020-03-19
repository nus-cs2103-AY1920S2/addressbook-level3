package seedu.zerotoone.logic.commands.exercise;

import static java.util.Objects.requireNonNull;

import seedu.zerotoone.commons.core.Messages;
import seedu.zerotoone.model.Model;
import seedu.zerotoone.model.exercise.PredicateFilterExerciseName;
import seedu.zerotoone.logic.commands.CommandResult;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends ExerciseCommand {
    public static final String COMMAND_WORD = "find";
    public static final String MESSAGE_USAGE = "Usage: exercise find e/<exercise_name>";

    private final PredicateFilterExerciseName predicate;
    public FindCommand(PredicateFilterExerciseName predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredExerciseList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_EXERCISES_LISTED_OVERVIEW, model.getFilteredExerciseList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}

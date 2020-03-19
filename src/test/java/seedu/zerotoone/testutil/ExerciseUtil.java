package seedu.zerotoone.testutil;

import static seedu.zerotoone.logic.parser.CliSyntax.PREFIX_EXERCISE_NAME;

import seedu.zerotoone.logic.commands.exercise.CreateCommand;
import seedu.zerotoone.logic.commands.exercise.EditCommand;
import seedu.zerotoone.model.exercise.Exercise;

/**
 * A utility class for Exercise.
 */
public class ExerciseUtil {

    /**
     * Returns a create command string for creating the {@code exercise}.
     */
    public static String getCreateCommand(Exercise exercise) {
        return CreateCommand.COMMAND_WORD + " " + getExerciseDetails(exercise);
    }

    /**
     * Returns the part of command string for the given {@code exercise}'s details.
     */
    public static String getExerciseDetails(Exercise exercise) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_EXERCISE_NAME + exercise.getExerciseName().fullName + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditExerciseDescriptor}'s details.
     */
    public static String getEditExerciseDescriptorDetails(EditCommand.EditExerciseDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getExerciseName().ifPresent(name -> sb.append(PREFIX_EXERCISE_NAME).append(name.fullName).append(" "));
        return sb.toString();
    }
}

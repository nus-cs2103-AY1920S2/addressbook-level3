package seedu.zerotoone.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import seedu.zerotoone.logic.commands.exceptions.CommandException;
import seedu.zerotoone.model.Model;
import seedu.zerotoone.model.session.CompletedSet;

/**
 * Completes the next up exerciseQueue in the session.
 */
public class DoneCommand extends Command {
    public static final String COMMAND_WORD = "done";
    public static final String MESSAGE_USAGE = "Usage: done";
    public static final String MESSAGE_SUCCESS = "Completed set: %1$s";
    public static final String MESSAGE_NONE_LEFT = "You have finished the last set, "
            + "your workout session is done and saved!";
    public static final FormatStyle FORMAT_STYLE = FormatStyle.MEDIUM;

    public DoneCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.isInSession()) {
            throw new CommandException(MESSAGE_SESSION_NOT_STARTED);
        }

        LocalDateTime currentDateTime = LocalDateTime.now();

        CompletedSet set = model.done();

        String outputMessage = String.format(MESSAGE_SUCCESS, set.toString());

        if (!model.hasExerciseLeft()) {
            model.stopSession(currentDateTime);
            outputMessage = outputMessage + "\n" + MESSAGE_NONE_LEFT;
        }

        String formatted = currentDateTime.format(DateTimeFormatter.ofLocalizedDateTime(this.FORMAT_STYLE));

        return new CommandResult(outputMessage);
    }

    @Override
    public boolean equals(Object other) {
        return other == this; // short circuit if same object
    }
}

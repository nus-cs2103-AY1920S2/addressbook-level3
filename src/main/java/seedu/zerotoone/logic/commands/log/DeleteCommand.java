package seedu.zerotoone.logic.commands.log;

import static java.util.Objects.requireNonNull;
import static seedu.zerotoone.commons.util.DateUtil.getPrettyDateTimeString;

import java.util.List;

import seedu.zerotoone.commons.core.Messages;
import seedu.zerotoone.commons.core.index.Index;
import seedu.zerotoone.logic.commands.CommandResult;
import seedu.zerotoone.logic.commands.exceptions.CommandException;
import seedu.zerotoone.model.Model;
import seedu.zerotoone.model.session.Session;

/**
 * Deletes a session identified using it's displayed index from the session list.
 */
public class DeleteCommand extends LogCommand {
    public static final String COMMAND_WORD = "delete";
    public static final String MESSAGE_USAGE = "Usage: log delete LOG_ID";
    public static final String MESSAGE_DELETE_SESSION_SUCCESS = "Deleted Session: %1$s on %2$s";
    private final Index sessionId;

    public DeleteCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.sessionId = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Session> lastShownList = model.getFilteredSessionList();

        if (sessionId.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INDEX);
        }

        Session sessionToDelete = lastShownList.get(sessionId.getZeroBased());
        model.deleteSession(sessionId.getZeroBased());

        String outputMessage = String.format(MESSAGE_DELETE_SESSION_SUCCESS,
            sessionToDelete.getExerciseName().toString(),
            getPrettyDateTimeString(sessionToDelete.getStartTime()));
        return new CommandResult(outputMessage);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && sessionId.equals(((DeleteCommand) other).sessionId)); // state check
    }
}

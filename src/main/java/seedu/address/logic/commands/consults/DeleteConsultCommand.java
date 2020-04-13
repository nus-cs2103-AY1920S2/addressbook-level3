
package seedu.address.logic.commands.consults;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.consult.Consult;

/**
 * Deletes a consult identified using it's displayed index from the Consult TAble.
 */
public class DeleteConsultCommand extends Command {

    public static final String COMMAND_WORD = "deleteConsult";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the consult identified by the index number used in the displayed Consult TAble.\n"
            + "Parameters: " + PREFIX_INDEX + "\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_CONSULT_SUCCESS = "Deleted Consult: %1$s";

    private final Index targetIndex;

    public DeleteConsultCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Consult> lastShownList = model.getFilteredConsultList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CONSULT_DISPLAYED_INDEX);
        }

        Consult consultToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteConsult(consultToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_CONSULT_SUCCESS, consultToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteConsultCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteConsultCommand) other).targetIndex)); // state check
    }
}

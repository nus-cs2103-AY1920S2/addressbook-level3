package seedu.address.logic.commands.academics;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.academics.Assessment;

/**
 * Deletes a student identified using it's displayed index from the address book.
 */
public class AcademicsDeleteCommand extends AcademicsCommand {

    public static final String MESSAGE_USAGE = "This command deletes the assessment identified by the index number"
            + "used in the displayed academics list. \n"
            + "Format: " + COMMAND_WORD + " "
            + PREFIX_DELETE + " INDEX (must be a positive integer)\n"
            + "Example: academics delete 1\n";

    public static final String MESSAGE_DELETE_ASSESSMENT_SUCCESS = "Deleted Assessment: %1$s";

    private final Index targetIndex;

    public AcademicsDeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Assessment> lastShownList = model.getFilteredAcademicsList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ASSESSMENT_DISPLAYED_INDEX);
        }
        Assessment assessmentToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteAssessment(assessmentToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_ASSESSMENT_SUCCESS, assessmentToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AcademicsDeleteCommand // instanceof handles nulls
                && targetIndex.equals(((AcademicsDeleteCommand) other).targetIndex)); // state check
    }
}

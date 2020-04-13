package seedu.address.logic.commands.tutorials;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_INDEX;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.tutorial.Tutorial;

/**
 * Copies the emails of students in tutorial into the user's OS clipboard, delimited by a semicolon
 */
public class CopyTutorialEmailsCommand extends Command {
    public static final String COMMAND_WORD = "copyTutorialEmails";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Copies tutorial students' emails to clipboard\n"
            + "Parameters: "
            + PREFIX_TUTORIAL_INDEX + "TUTORIAL_INDEX (must be a positive integer) "
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_TUTORIAL_INDEX + "1";

    public static final String MESSAGE_SUCCESS = COMMAND_WORD + ": Copied students' emails from tutorial %1$s %2$s"
            + " to clipboard.";

    private final Index tutorialIndex;

    /**
     * Creates a CopyTutorialEmailsCommand to copy emails of student in {@code tutorial} to clipboard.
     */
    public CopyTutorialEmailsCommand(Index tutorialIndex) {
        this.tutorialIndex = tutorialIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Tutorial> lastShownList = model.getFilteredTutorialList();

        if (tutorialIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TUTORIAL_DISPLAYED_INDEX);
        }

        Tutorial target = lastShownList.get(tutorialIndex.getZeroBased());
        target.copyTutorialEmails();

        return new CommandResult(String.format(MESSAGE_SUCCESS, target.getModCode(),
                target.getTutorialName()));
    }
}

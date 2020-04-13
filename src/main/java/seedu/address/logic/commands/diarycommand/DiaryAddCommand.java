package seedu.address.logic.commands.diarycommand;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENTRY_CONTENT;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.diary.DiaryEntry;

/**
 * Represents the command that adds a diary entry.
 */
public class DiaryAddCommand extends Command {

    /**
     * Dummy java docs.
     */
    public static final String COMMAND_WORD = "diaryAdd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Allows for adding diary entries "
            + "Parameters: "
            + PREFIX_ENTRY_CONTENT + "ENTRY CONTENT"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ENTRY_CONTENT + "I failed my midterm today :( ";

    public static final String MESSAGE_SUCCESS = "Diary entry added:";

    private final DiaryEntry diaryEntry;

    public DiaryAddCommand(DiaryEntry diaryEntry) {
        requireNonNull(diaryEntry);
        this.diaryEntry = diaryEntry;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.isEmptyDiaryEntry(diaryEntry)) {
            throw new CommandException("The diary entry is empty!");
        }

        model.addDiaryEntry(diaryEntry);
        return new CommandResult(String.format(MESSAGE_SUCCESS + diaryEntry.getHeading()));
    }
}

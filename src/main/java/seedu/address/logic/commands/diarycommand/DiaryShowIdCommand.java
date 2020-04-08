package seedu.address.logic.commands.diarycommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.diary.DiaryEntry;

/**
 * Dummy java docs.
 */
public class DiaryShowIdCommand extends DiaryShowCommand {
    public static final String MESSAGE_SUCCESS = "Diary entry shown: ";

    private final int entryId;

    public DiaryShowIdCommand(int entryId) {
        this.entryId = entryId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.isValidEntryId(entryId)) {
            throw new CommandException("The diary entry ID is not in range!");
        }

        DiaryEntry entry = model.getDiaryEntryById(entryId);

        return new CommandResult(MESSAGE_SUCCESS + " " + entry);
    }
}

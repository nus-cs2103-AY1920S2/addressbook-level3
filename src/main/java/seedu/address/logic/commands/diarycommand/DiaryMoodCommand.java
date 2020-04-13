package seedu.address.logic.commands.diarycommand;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENTRY_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MOOD;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.diary.mood.Mood;

/**
 * Represents the command that tags a diary entry with mood.
 */
public class DiaryMoodCommand extends Command {

    public static final String COMMAND_WORD = "diaryMood";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Allows for tagging a diary entry with a specific mood "
            + "Parameters: "
            + PREFIX_ENTRY_ID + "ENTRY ID"
            + PREFIX_MOOD + "WEATHER"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ENTRY_ID + "1"
            + PREFIX_MOOD + "stressed";

    public static final String MESSAGE_SUCCESS = "mood recorded";

    private final int entryId;
    private final Mood mood;

    public DiaryMoodCommand(int entryId, Mood mood) {
        this.entryId = entryId;
        this.mood = mood;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.isValidEntryId(entryId)) {
            throw new CommandException("The diary entry ID is not in range!");
        }

        model.tagMood(entryId, mood);
        String messageResult = "Mood recorded.";
        return new CommandResult(messageResult);
    }
}

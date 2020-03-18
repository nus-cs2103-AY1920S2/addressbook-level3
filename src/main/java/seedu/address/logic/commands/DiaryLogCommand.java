package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.diary.DiaryEntry;

public class DiaryLogCommand extends Command {

    public static final String COMMAND_WORD = "diaryLog";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": shows the log of added diary entries";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        String diaryLogs = model.showDiaryLog();
        return new CommandResult(diaryLogs);
    }
}
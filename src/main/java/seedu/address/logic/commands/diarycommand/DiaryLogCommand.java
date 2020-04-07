package seedu.address.logic.commands.diarycommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Dummy java docs
 */
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

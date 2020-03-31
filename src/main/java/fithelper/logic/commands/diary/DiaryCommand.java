package fithelper.logic.commands.diary;

import static fithelper.logic.commands.CommandResult.DisplayedPage.DIARY;
import static java.util.Objects.requireNonNull;

import fithelper.commons.core.LogsCenter;
import fithelper.logic.commands.Command;
import fithelper.logic.commands.CommandResult;
import fithelper.model.Model;

import java.util.logging.Logger;

/**
 * Adds a entry to FitHelper.
 */
public class DiaryCommand extends Command {

    public static final String COMMAND_WORD = "diary";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays diary page. ";

    public static final String MESSAGE_SUCCESS = "Now you are at Diary Page ~";

    private static final String MESSAGE_COMMIT = "View my diary";

    private static final Logger logger = LogsCenter.getLogger(DiaryCommand.class);

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.commit(MESSAGE_COMMIT);
        logger.info("Switched to Diary Page");

        return new CommandResult(String.format(MESSAGE_SUCCESS), DIARY, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DiaryCommand);
    }
}

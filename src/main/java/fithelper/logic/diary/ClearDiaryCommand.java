package fithelper.logic.diary;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import fithelper.commons.core.LogsCenter;
import fithelper.logic.commands.Command;
import fithelper.logic.commands.CommandResult;
import fithelper.model.Model;

/**
 * Clears the diary in this FitHelper.
 */
public class ClearDiaryCommand extends Command {

    public static final String COMMAND_WORD = "clearDiary";
    public static final String MESSAGE_SUCCESS = "Your diary in FitHelper has been cleared!";

    private static final String MESSAGE_COMMIT = "Clear FitHelper diaries";

    private static final Logger logger = LogsCenter.getLogger(ClearDiaryCommand.class);

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.clearDiaryFitHelper();

        model.commit(MESSAGE_COMMIT);
        logger.info("Clear the current FitHelper diaries");

        return new CommandResult(MESSAGE_SUCCESS, CommandResult.DisplayedPage.DIARY, false);
    }
}

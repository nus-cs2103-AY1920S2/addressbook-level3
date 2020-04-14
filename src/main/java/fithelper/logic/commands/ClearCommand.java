package fithelper.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import fithelper.commons.core.LogsCenter;
import fithelper.model.FitHelper;
import fithelper.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Fit Helper log book has been cleared!";

    private static final String MESSAGE_COMMIT = "Clear FitHelper";

    private static final Logger logger = LogsCenter.getLogger(ClearCommand.class);

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setFitHelper(new FitHelper());
        model.clearVevents();
        model.commit(MESSAGE_COMMIT);
        logger.info("Clear the current FitHelper");

        return new CommandResult(MESSAGE_SUCCESS);
    }
}

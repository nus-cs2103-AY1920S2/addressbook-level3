package seedu.nova.logic.commands.commoncommands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.nova.commons.core.LogsCenter;
import seedu.nova.logic.commands.Command;
import seedu.nova.logic.commands.CommandResult;
import seedu.nova.logic.parser.ModeEnum;
import seedu.nova.model.Mode;
import seedu.nova.model.Model;
import seedu.nova.model.progresstracker.Ip;
import seedu.nova.model.progresstracker.ProgressTracker;
import seedu.nova.model.progresstracker.Tp;

/**
 * Class for navigation command
 */
public class NavCommand extends Command {
    public static final String COMMAND_WORD = "nav";
    public static final String MESSAGE_SUCCESS = "Changed mode to ";
    public static final String MESSAGE_SAME_MODE = "You are already in %1$s mode";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private ModeEnum modeEnum;

    public NavCommand(ModeEnum modeEnum) {
        this.modeEnum = modeEnum;
    }

    @Override
    public CommandResult execute(Model model) {
        logger.info("executing nav command to" + this.modeEnum.name());

        requireNonNull(model);

        Mode mode = model.getMode();
        boolean isCurrentMode = mode.getModeEnum().equals(this.modeEnum);
        boolean isProgressTrackerMode = this.modeEnum.equals(ModeEnum.PROGRESSTRACKER);

        if (isCurrentMode) {
            String modeName = modeEnum.name().toLowerCase();

            return new CommandResult(String.format(MESSAGE_SAME_MODE, modeName), false, false);
        } else if (isProgressTrackerMode) {
            mode.setModeEnum(this.modeEnum);

            ProgressTracker pt = model.getProgressTracker();
            Ip ip = pt.getIp();
            Tp tp = pt.getTp();
            double ipProgress = ip.getProgress();
            double tpProgress = tp.getProgress();

            String modeName = mode.getModeEnum().name();
            String commandMessage = MESSAGE_SUCCESS + modeName + "\n";

            //Get the progress for each project
            String messageProgresstracker = "Projects: \n"
                    + "  IP Project: " + ipProgress + "%\n"
                    + "  TP Project: " + tpProgress + "%";

            return new CommandResult(commandMessage + messageProgresstracker, true, false);
        } else {
            //Set mode and get modeEnum from new mode
            model.setModeEnum(mode, this.modeEnum);
            ModeEnum modeEnum = model.getModeEnum(mode);
            String modeName = model.getModeName(modeEnum);

            return new CommandResult(MESSAGE_SUCCESS + modeName, true, false);
        }
    }
}

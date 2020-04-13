package seedu.nova.logic.commands.ptcommands;

import static java.util.Objects.requireNonNull;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_PROJECT;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_WEEK;

import java.util.logging.Logger;

import seedu.nova.commons.core.LogsCenter;
import seedu.nova.logic.commands.Command;
import seedu.nova.logic.commands.CommandResult;
import seedu.nova.logic.commands.exceptions.CommandException;
import seedu.nova.model.Model;

/**
 * Executes list command
 */
public class PtListCommand extends Command {
    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists tasks in the specified "
            + "project in the specified week. "
            + "Parameters: "
            + PREFIX_PROJECT + "PROJECT "
            + PREFIX_WEEK + "WEEK \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PROJECT + "Ip "
            + PREFIX_WEEK + "2";

    public static final String MESSAGE_NULLWEEK = "No task in specified week";

    public static final String MESSAGE_NOWEEK = "No week beyond week 13";

    public static final String MESSAGE_SUCCESS = "%s Project (Week %d):\n";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private int weekNum;
    private String project;

    /**
     * Creates PtListCommand object
     * @param weekNum week to be listed
     * @param project project to be listed
     */
    public PtListCommand(int weekNum, String project) {
        requireNonNull(project);

        this.weekNum = weekNum;
        this.project = project.trim().toLowerCase();
    }

    public int getWeekNum() {
        return weekNum;
    }

    public String getProject() {
        return project;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("executing list command for: project " + project + ", week " + weekNum);

        requireNonNull(model);
        boolean isOver13 = weekNum > 13;

        //if week is over 13, throw no week error message
        if (isOver13) {
            throw new CommandException(MESSAGE_NOWEEK);
        } else {
            String listResult = model.listPtTask(this.project, weekNum);

            boolean noTask = listResult.equals("");

            if (noTask) {
                throw new CommandException(MESSAGE_NULLWEEK);
            }

            String header = String.format(MESSAGE_SUCCESS, this.project.toUpperCase(), weekNum);
            String result = header + listResult;

            return new CommandResult(result, false, false);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PtListCommand)) {
            return false;
        } else {
            boolean isSameProject = ((PtListCommand) obj).getProject().equals(this.getProject());
            boolean isSameWeek = ((PtListCommand) obj).getWeekNum() == this.getWeekNum();

            return isSameProject && isSameWeek;
        }
    }
}

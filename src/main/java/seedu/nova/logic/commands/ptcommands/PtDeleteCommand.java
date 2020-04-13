package seedu.nova.logic.commands.ptcommands;

import static java.util.Objects.requireNonNull;

import static seedu.nova.logic.parser.CliSyntax.PREFIX_PROJECT;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_TASK;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_WEEK;

import java.util.logging.Logger;

import seedu.nova.commons.core.LogsCenter;
import seedu.nova.logic.commands.Command;
import seedu.nova.logic.commands.CommandResult;
import seedu.nova.logic.commands.exceptions.CommandException;
import seedu.nova.model.Model;

/**
 * Adds task to specified week
 */
public class PtDeleteCommand extends Command {
    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes task in the specified "
            + "project in the specified week. "
            + "Parameters: "
            + PREFIX_PROJECT + "PROJECT "
            + PREFIX_WEEK + "WEEK "
            + PREFIX_TASK + "TASK \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PROJECT + "Ip "
            + PREFIX_WEEK + "2 "
            + PREFIX_TASK + "1";

    public static final String MESSAGE_NOWEEK = "No week beyond week 13";

    public static final String MESSAGE_FAILURE = "No task with that index";

    public static final String MESSAGE_SUCCESS = "Deleted task %d in week %d of %s";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private int weekNum;
    private String project;
    private int taskNum;

    /**
     * Creates PtDeleteCommand object
     * @param weekNum week of PtTask to be deleted
     * @param project project of PtTask to be deleted
     * @param taskNum task number of PtTask to be deleted
     */
    public PtDeleteCommand(int weekNum, String project, int taskNum) {
        requireNonNull(project);

        this.weekNum = weekNum;
        this.project = project.trim().toLowerCase();
        this.taskNum = taskNum;
    }

    public int getWeekNum() {
        return weekNum;
    }

    public String getProject() {
        return project;
    }

    public int getTaskNum() {
        return taskNum;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("executing delete command for: project " + project + ", week " + weekNum + ", task " + taskNum);

        requireNonNull(model);
        boolean isOver13 = weekNum > 13;

        //if week is over 13, throw no week error message
        if (isOver13) {
            throw new CommandException(MESSAGE_NOWEEK);
        } else {
            boolean isDeleteSuccess = model.deletePtTask(this.project, weekNum, taskNum);

            if (!isDeleteSuccess) {
                throw new CommandException(MESSAGE_FAILURE);
            }

            String projectName = this.project.toUpperCase();
            String result = String.format(MESSAGE_SUCCESS, taskNum, weekNum, projectName);

            return new CommandResult(result, false, false);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PtDeleteCommand)) {
            return false;
        } else {
            boolean isSameProject = ((PtDeleteCommand) obj).getProject().equals(this.getProject());
            boolean isSameWeek = ((PtDeleteCommand) obj).getWeekNum() == this.getWeekNum();
            boolean isSameTaskNum = ((PtDeleteCommand) obj).getTaskNum() == this.getTaskNum();

            return isSameProject && isSameWeek && isSameTaskNum;
        }
    }
}

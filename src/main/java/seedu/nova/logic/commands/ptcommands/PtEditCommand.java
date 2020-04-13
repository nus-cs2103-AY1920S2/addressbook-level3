package seedu.nova.logic.commands.ptcommands;

import static java.util.Objects.requireNonNull;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_DESC;
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
public class PtEditCommand extends Command {
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits task in the "
            + "project in the specified week. "
            + "Parameters: "
            + PREFIX_PROJECT + "PROJECT "
            + PREFIX_WEEK + "WEEK "
            + PREFIX_TASK + "TASK "
            + PREFIX_DESC + "TASK DESCRIPTION \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PROJECT + "Ip "
            + PREFIX_WEEK + "2 "
            + PREFIX_TASK + "1 "
            + PREFIX_DESC + "Implement javafx";

    public static final String MESSAGE_NOWEEK = "No week beyond week 13";

    public static final String MESSAGE_FAILURE = "No task with that index";

    public static final String MESSAGE_SUCCESS = "Edited task %d in week %d of %s";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private int weekNum;
    private String project;
    private String taskDesc;
    private int taskNum;

    /**
     * Creates PtEditCommand object
     * @param weekNum week of PtTask to be edited
     * @param project project of PtTask to be edited
     * @param taskDesc taskDesc of PtTask to be edited
     * @param taskNum task number of PtTask to be edited
     */
    public PtEditCommand(int weekNum, String project, String taskDesc, int taskNum) {
        requireNonNull(project);
        requireNonNull(taskDesc);

        this.weekNum = weekNum;
        this.project = project.trim().toLowerCase();
        this.taskDesc = taskDesc;
        this.taskNum = taskNum;
    }

    public int getWeekNum() {
        return weekNum;
    }

    public String getProject() {
        return project;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public int getTaskNum() {
        return taskNum;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("executing edit command for: project " + project + ", week " + weekNum + ", task " + taskNum);

        requireNonNull(model);
        boolean isOver13 = weekNum > 13;

        //if week is over 13, throw no week error message
        if (isOver13) {
            throw new CommandException(MESSAGE_NOWEEK);
        } else {
            boolean isEditSuccess = model.editPtTask(this.project, weekNum, taskNum, taskDesc);

            if (!isEditSuccess) {
                throw new CommandException(MESSAGE_FAILURE);
            }

            String projectName = this.project.toUpperCase();
            String result = String.format(MESSAGE_SUCCESS, taskNum, weekNum, projectName);

            return new CommandResult(result, false, false);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PtEditCommand)) {
            return false;
        } else {
            boolean isSameProject = ((PtEditCommand) obj).getProject().equals(this.getProject());
            boolean isSameWeek = ((PtEditCommand) obj).getWeekNum() == this.getWeekNum();
            boolean isSameTaskDesc = ((PtEditCommand) obj).getTaskDesc().equals(this.getTaskDesc());
            boolean isSameTaskNum = ((PtEditCommand) obj).getTaskNum() == this.getTaskNum();

            return isSameProject && isSameWeek && isSameTaskNum && isSameTaskDesc;
        }
    }
}


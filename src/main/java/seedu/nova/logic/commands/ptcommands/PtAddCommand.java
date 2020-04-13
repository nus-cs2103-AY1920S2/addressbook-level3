package seedu.nova.logic.commands.ptcommands;

import static java.util.Objects.requireNonNull;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_PROJECT;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_WEEK;

import java.util.logging.Logger;

import seedu.nova.commons.core.LogsCenter;
import seedu.nova.logic.commands.Command;
import seedu.nova.logic.commands.CommandResult;
import seedu.nova.logic.commands.exceptions.CommandException;
import seedu.nova.model.Model;
import seedu.nova.model.progresstracker.Project;
import seedu.nova.model.progresstracker.PtNote;
import seedu.nova.model.progresstracker.PtTask;
import seedu.nova.model.progresstracker.TaskDesc;

/**
 * Adds task to specified week
 */
public class PtAddCommand extends Command {
    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds task in the "
            + "project in the specified week. "
            + "Parameters: "
            + PREFIX_PROJECT + "PROJECT "
            + PREFIX_WEEK + "WEEK "
            + PREFIX_DESC + "TASK DESCRIPTION \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PROJECT + "Ip "
            + PREFIX_WEEK + "2 "
            + PREFIX_DESC + "Implement javafx";

    public static final String MESSAGE_NOWEEK = "No week beyond week 13";

    public static final String MESSAGE_SUCCESS = "Added task to week %d of %s";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private int weekNum;
    private String project;
    private String taskDesc;

    /**
     * Creates an PtAddCommand object
     * @param weekNum week to add PtTask to
     * @param project project to add PtTask to
     * @param taskDesc taskDesc for PtTask to be added
     */
    public PtAddCommand(int weekNum, String project, String taskDesc) {
        requireNonNull(project);
        requireNonNull(taskDesc);

        this.weekNum = weekNum;
        this.project = project.trim().toLowerCase();
        this.taskDesc = taskDesc;
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

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("executing add PtTask command for: " + project + " week " + weekNum);

        requireNonNull(model);
        boolean isOver13 = weekNum > 13;

        //if week is more than 13, throw no week error message
        if (isOver13) {
            throw new CommandException(MESSAGE_NOWEEK);
        } else {
            Project project;
            boolean isIpProject = this.project.equals("ip");

            if (isIpProject) {
                project = model.getProgressTrackerIp();
            } else {
                project = model.getProgressTrackerTp();
            }

            //Create new task
            TaskDesc taskDesc = new TaskDesc(this.taskDesc);
            PtTask newTask = new PtTask(taskDesc, project, new PtNote(""), this.weekNum, false);

            model.addPtTask(this.project, weekNum, newTask);

            String projectName = this.project.toUpperCase();
            String result = String.format(MESSAGE_SUCCESS, weekNum, projectName);

            return new CommandResult(result, false, false);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PtAddCommand)) {
            return false;
        } else {
            boolean isSameProject = ((PtAddCommand) obj).getProject().equals(this.getProject());
            boolean isSameWeek = ((PtAddCommand) obj).getWeekNum() == this.getWeekNum();
            boolean isSameTaskDesc = ((PtAddCommand) obj).getTaskDesc().equals(this.getTaskDesc());

            return isSameProject && isSameWeek && isSameTaskDesc;
        }
    }
}

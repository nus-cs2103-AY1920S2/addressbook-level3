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
 * Deletes note in specified task
 */
public class PtDeleteNoteCommand extends Command {
    public static final String COMMAND_WORD = "deleteNote";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes note in specified task in the "
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

    public static final String MESSAGE_FAILURE = "Command failed. Please check that there is a task "
            + "or note in the specified index";

    public static final String MESSAGE_SUCCESS = "Deleted note to task %d in week %d of %s";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private int weekNum;
    private int taskNum;
    private String project;

    /**
     * Creates PtDeleteNoteCommand object
     * @param weekNum week of PtTask with note to be deleted
     * @param taskNum task number of PtTask with note to be deleted
     * @param project project of PtTask with note to be deleted
     */
    public PtDeleteNoteCommand(int weekNum, int taskNum, String project) {
        requireNonNull(project);

        this.weekNum = weekNum;
        this.taskNum = taskNum;
        this.project = project.trim().toLowerCase();
    }

    public int getWeekNum() {
        return weekNum;
    }

    public int getTaskNum() {
        return taskNum;
    }

    public String getProject() {
        return project;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("executing deleteNote command for: project " + project + ", week " + weekNum + ", task " + taskNum);

        requireNonNull(model);
        boolean isOver13 = weekNum > 13;

        //if week is over 13, throw no week error message
        if (isOver13) {
            throw new CommandException(MESSAGE_NOWEEK);
        } else {
            boolean isDeleteNoteSuccess = model.deletePtNote(this.project, weekNum, taskNum);

            if (!isDeleteNoteSuccess) {
                throw new CommandException(MESSAGE_FAILURE);
            }

            String projectName = this.project.toUpperCase();
            String result = String.format(MESSAGE_SUCCESS, taskNum, weekNum, projectName);

            return new CommandResult(result, false, false);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PtDeleteNoteCommand)) {
            return false;
        } else {
            boolean isSameProject = ((PtDeleteNoteCommand) obj).getProject().equals(this.getProject());
            boolean isSameWeek = ((PtDeleteNoteCommand) obj).getWeekNum() == this.getWeekNum();
            boolean isSameTaskNum = ((PtDeleteNoteCommand) obj).getTaskNum() == (this.getTaskNum());

            return isSameProject && isSameWeek && isSameTaskNum;
        }
    }
}



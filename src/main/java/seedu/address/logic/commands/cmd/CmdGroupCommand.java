package seedu.address.logic.commands.cmd;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.calendar.CalCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.facilitator.FacilCommand;
import seedu.address.logic.commands.lesson.LessonCommand;
import seedu.address.logic.commands.module.ModuleCommand;
import seedu.address.logic.commands.task.TaskCommand;
import seedu.address.model.Model;

/**
 * Lists all commands from a specific command group.
 */
public class CmdGroupCommand extends CmdCommand {

    public static final String MESSAGE_USAGE = COMMAND_GROUP_CMD + " " + COMMAND_WORD_GROUP
            + ": Lists all the formats of the specified group of commands.\n"
            + "Parameters: COMMAND_GROUP\n"
            + "Examples: " + COMMAND_GROUP_CMD + " " + COMMAND_WORD_GROUP + " " + COMMAND_GROUP_CLASS;

    public static final String MESSAGE_CMD_GROUP_SUCCESS = "%s command(s) listed: \n%s";

    private final String commandGroup;

    public CmdGroupCommand(String commandGroup) {
        this.commandGroup = commandGroup;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!Command.ALL_COMMAND_GROUPS.contains(commandGroup)) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_CMD_GROUP, commandGroup));
        }

        switch (commandGroup) {
        case COMMAND_GROUP_CAL:
            return new CommandResult(String.format(MESSAGE_CMD_GROUP_SUCCESS, commandGroup,
                    getCommands(CalCommand.ALL_COMMAND_FORMATS)), CommandType.CMD);

        case COMMAND_GROUP_CLASS:
            return new CommandResult(String.format(MESSAGE_CMD_GROUP_SUCCESS, commandGroup,
                    getCommands(LessonCommand.ALL_COMMAND_FORMATS)), CommandType.CMD);

        case COMMAND_GROUP_FACIL:
            return new CommandResult(String.format(MESSAGE_CMD_GROUP_SUCCESS, commandGroup,
                    getCommands(FacilCommand.ALL_COMMAND_FORMATS)), CommandType.CMD);

        case COMMAND_GROUP_MOD:
            return new CommandResult(String.format(MESSAGE_CMD_GROUP_SUCCESS, commandGroup,
                    getCommands(ModuleCommand.ALL_COMMAND_FORMATS)), CommandType.CMD);

        case COMMAND_GROUP_TASK:
            return new CommandResult(String.format(MESSAGE_CMD_GROUP_SUCCESS, commandGroup,
                    getCommands(TaskCommand.ALL_COMMAND_FORMATS)), CommandType.CMD);

        case COMMAND_GROUP_CMD:
            return new CommandResult(String.format(MESSAGE_CMD_GROUP_SUCCESS, commandGroup,
                    getCommands(CmdCommand.ALL_COMMAND_FORMATS)), CommandType.CMD);

        case COMMAND_GROUP_EXIT:
            return new CommandResult(String.format(MESSAGE_CMD_GROUP_SUCCESS, commandGroup,
                    getCommands(List.of(COMMAND_GROUP_EXIT))), CommandType.CMD);

        case COMMAND_GROUP_UNDO:
            return new CommandResult(String.format(MESSAGE_CMD_GROUP_SUCCESS, commandGroup,
                    getCommands(List.of(COMMAND_GROUP_UNDO))), CommandType.CMD);

        case COMMAND_GROUP_REDO:
            return new CommandResult(String.format(MESSAGE_CMD_GROUP_SUCCESS, commandGroup,
                    getCommands(List.of(COMMAND_GROUP_REDO))), CommandType.CMD);

        case COMMAND_GROUP_CLEAR:
            return new CommandResult(String.format(MESSAGE_CMD_GROUP_SUCCESS, commandGroup,
                    getCommands(List.of(COMMAND_GROUP_CLEAR))), CommandType.CMD);

        case COMMAND_GROUP_HELP:
            return new CommandResult(String.format(MESSAGE_CMD_GROUP_SUCCESS, commandGroup,
                    getCommands(List.of(COMMAND_GROUP_HELP))), CommandType.CMD);

        default:
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_CMD_GROUP, commandGroup));
        }
    }

    private String getCommands(List<String> commands) {
        StringBuilder ret = new StringBuilder();

        for (String str : commands) {
            ret.append(str).append("\n");
        }

        return ret.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof CmdGroupCommand)) {
            return false;
        }

        CmdGroupCommand e = (CmdGroupCommand) other;

        return this.commandGroup.equals(e.commandGroup);
    }
}

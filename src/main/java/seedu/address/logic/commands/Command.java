package seedu.address.logic.commands;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    public static final String COMMAND_GROUP_CAL = "cal";
    public static final String COMMAND_GROUP_CLASS = "class";
    public static final String COMMAND_GROUP_CLEAR = "clear";
    public static final String COMMAND_GROUP_CMD = "cmd";
    public static final String COMMAND_GROUP_EXIT = "exit";
    public static final String COMMAND_GROUP_FACIL = "facil";
    public static final String COMMAND_GROUP_HELP = "help";
    public static final String COMMAND_GROUP_MOD = "mod";
    public static final String COMMAND_GROUP_TASK = "task";
    public static final String COMMAND_GROUP_UNDO = "undo";
    public static final String COMMAND_GROUP_REDO = "redo";
    public static final List<String> ALL_COMMAND_GROUPS = List.of(
            COMMAND_GROUP_CAL, COMMAND_GROUP_CLASS, COMMAND_GROUP_CLEAR, COMMAND_GROUP_CMD, COMMAND_GROUP_EXIT,
            COMMAND_GROUP_FACIL, COMMAND_GROUP_HELP, COMMAND_GROUP_MOD, COMMAND_GROUP_TASK, COMMAND_GROUP_UNDO,
            COMMAND_GROUP_REDO);

    public static final String COMMAND_WORD_ADD = "add";
    public static final String COMMAND_WORD_ALL = "all";
    public static final String COMMAND_WORD_CLEAR = "clear";
    public static final String COMMAND_WORD_DELETE = "delete";
    public static final String COMMAND_WORD_EDIT = "edit";
    public static final String COMMAND_WORD_DONE = "done";
    public static final String COMMAND_WORD_UNDONE = "undone";
    public static final String COMMAND_WORD_FIND = "find";
    public static final String COMMAND_WORD_SEARCH = "search";
    public static final String COMMAND_WORD_GROUP = "group";
    public static final String COMMAND_WORD_HELP = "help";
    public static final String COMMAND_WORD_LIST = "list";
    public static final String COMMAND_WORD_VIEW = "view";
    public static final String COMMAND_WORD_MODULE = "module";

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model) throws CommandException;
}

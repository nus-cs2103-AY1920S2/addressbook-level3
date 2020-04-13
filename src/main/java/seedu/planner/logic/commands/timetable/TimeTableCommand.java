package seedu.planner.logic.commands.timetable;

import seedu.planner.logic.commands.Command;


//@@author thetruevincentchow

/**
 * Base command for subcommands involving queries about timetables.
 */
public abstract class TimeTableCommand extends Command {

    public static final String COMMAND_WORD = "timetable";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Manage timetables of the currently active student\n"
            + "Subcommands: add, remove, active, list\n"
            + "Examples: \n"
            + "    " + getQualifiedCommand("active") + " year/1 sem/1\n"
            + "    " + getQualifiedCommand("active") + " year/2 sem/2\n"
            + "    " + getQualifiedCommand("active") + " year/3 sem/st1\n"
            + "    " + getQualifiedCommand("active") + " year/4 sem/st2\n";

    /**
     * Returns the {@code COMMAND_WORD} concatenated with the name of the input {@code subCommand}.
     *
     * @param subCommand
     * @return Qualified name
     */
    public static String getQualifiedCommand(String subCommand) {
        return COMMAND_WORD + " " + subCommand;
    }
}
//@@author

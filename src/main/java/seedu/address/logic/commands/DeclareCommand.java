package seedu.address.logic.commands;

public abstract class DeclareCommand extends Command {

    public static final String COMMAND_WORD = "declare";

    // TODO: fill in usage message
    public static final String MESSAGE_USAGE = COMMAND_WORD
        //+ ": Deletes the person identified by the index number used in the displayed person list.\n"
        //+ "Parameters: INDEX (must be a positive integer)\n"
        + ":\n"
        + "Subcommands: major\n"
        + "Example: " + COMMAND_WORD + " major CS";
}

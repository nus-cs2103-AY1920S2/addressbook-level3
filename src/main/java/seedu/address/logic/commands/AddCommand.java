package seedu.address.logic.commands;


/**
 * Adds a person to the address book.
 */
public abstract class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a property to the current interview session. "
            + "Includes: "
            + "adding an Interviewee object"
            + "adding an Attribute object"
            + "adding a Question object";
}

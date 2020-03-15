package seedu.address.logic.commands;

/**
 * Edits the details of an existing person in the address book.
 */
public abstract class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the properties of an interview session "
            + "Includes: "
            + "editing an Interviewee object"
            + "editing an Attribute object"
            + "editing a Question object";

}

package seedu.address.logic.commands;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public abstract class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes the properties of an interview session "
            + "Includes: "
            + "deleting an Interviewee object"
            + "deleting an Attribute object"
            + "deleting a Question object";


}

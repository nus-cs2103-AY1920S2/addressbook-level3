package seedu.address.logic.commands;


/**
 * Lists the HireLah data requested by the user.
 */
public abstract class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists the HireLah data requested by the user "
            + "Includes: "
            + "listing Interviewees"
            + "listing Attributes"
            + "listing Questions"
            + "listing Sessions";

}

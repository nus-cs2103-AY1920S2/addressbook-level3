package csdev.couponstash.logic.commands;

import csdev.couponstash.logic.commands.exceptions.CommandException;
import csdev.couponstash.model.Model;

/**
 * This class represents the "saved" command
 * in Coupon Stash. The "saved" command shows the
 * user how much he/she has saved from a certain date.
 * Coupon Stash will look through the Coupons
 * to produce the final "saved" amount displayed
 */
public class SavedCommand extends Command {
    // the word used to run this command
    public static final String COMMAND_WORD = "saved";

    /**
     * Executes the SavedCommand with a given Model representing
     * the current state of the Coupon Stash application
     * @param model {@code Model} which the command should operate on.
     * @return Returns the CommandResult that encompasses the
     *     message that should be shown to the user, as well as
     *     any other external actions that should occur.
     * @throws CommandException CommandException is thrown when
     *     the date provided is in the future, according to the
     *     system time retrieved by the application (most
     *     likely an error by the user, as you cannot see
     *     what you have saved in the future)
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult("Hello", false, false);
    }
}

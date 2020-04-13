package seedu.nova.logic.commands.sccommands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

import seedu.nova.logic.commands.Command;
import seedu.nova.logic.commands.CommandResult;
import seedu.nova.logic.commands.exceptions.CommandException;
import seedu.nova.model.Model;

/**
 * Find free slot
 */
public class ScViewFreeSlotCommand extends Command {
    /**
     * The constant COMMAND_WORD.
     */
    public static final String COMMAND_WORD = "freeslot";
    private static final String MESSAGE_DATE_OUT_OF_RANGE = "The date is not within the schedule";
    private static final String MESSAGE_NO_EVENT = "You have no event on that day";

    private final LocalDate date;

    /**
     * Instantiates a new Sc view day command.
     *
     * @param date the date
     */
    public ScViewFreeSlotCommand(LocalDate date) {

        this.date = date;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        requireNonNull(model);

        if (!model.isWithinSem(date)) {
            throw new CommandException(MESSAGE_DATE_OUT_OF_RANGE);
        }

        String freeSlotString = model.viewFreeSlot(date);

        if (freeSlotString.equals("")) {
            return new CommandResult(MESSAGE_NO_EVENT);
        } else {
            return new CommandResult(freeSlotString);
        }
    }
}

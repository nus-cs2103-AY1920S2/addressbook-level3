package fithelper.logic.commands;

import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_CALORIE;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_DURATION;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_LOCATION;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_NAME;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_REMARK;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_TIME;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_TYPE;
import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import fithelper.commons.core.LogsCenter;
import fithelper.commons.exceptions.IllegalValueException;
import fithelper.logic.exceptions.CommandException;
import fithelper.model.Model;
import fithelper.model.entry.Entry;

/**
 * Adds a entry to FitHelper.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an entry to FitHelper. "
            + "Parameters: "
            + PREFIX_TYPE + "TYPE "
            + PREFIX_NAME + "NAME "
            + PREFIX_TIME + "TIME "
            + PREFIX_LOCATION + "LOCATION "
            + PREFIX_CALORIE + "CALORIE "
            + "[" + PREFIX_DURATION + "DURATION] "
            + "[" + PREFIX_REMARK + "REMARK]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TYPE + "food "
            + PREFIX_NAME + "noodles "
            + PREFIX_TIME + "2020-04-01-15:30 "
            + PREFIX_LOCATION + "Utown canteen "
            + PREFIX_CALORIE + "100 "
            + PREFIX_REMARK + "too expensive "
            + PREFIX_DURATION + "1";

    public static final String MESSAGE_DUPLICATE_ENTRY = "This entry already exists in FitHelper";
    public static final String MESSAGE_SUCCESS = "New Entry added: %1$s";
    public static final String MESSAGE_TIME_CLASHES = "No entries can have time clashes";
    private static final String MESSAGE_COMMIT = "Add an entry";

    private static final Logger logger = LogsCenter.getLogger(AddCommand.class);

    private final Entry toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Entry}
     */
    public AddCommand(Entry entry) {
        requireNonNull(entry);
        toAdd = entry;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException, IllegalValueException {
        requireNonNull(model);

        if (model.hasEntry(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ENTRY);
        }

        if (model.hasTimeClashes(toAdd)) {
            throw new CommandException(MESSAGE_TIME_CLASHES);
        }

        model.addEntry(toAdd);
        model.addVEvent(toAdd);

        model.commit(MESSAGE_COMMIT);
        logger.info(String.format("Added a new entry [%s]", toAdd.toString()));

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}

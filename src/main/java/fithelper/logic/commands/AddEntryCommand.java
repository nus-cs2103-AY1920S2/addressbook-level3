package fithelper.logic.commands;

import static java.util.Objects.requireNonNull;

import static fithelper.logic.parser.CliSyntax.PREFIX_ENTRYCALORIE;
import static fithelper.logic.parser.CliSyntax.PREFIX_ENTRYLOCATION;
import static fithelper.logic.parser.CliSyntax.PREFIX_ENTRYNAME;
import static fithelper.logic.parser.CliSyntax.PREFIX_ENTRYTIME;

import fithelper.logic.commands.exceptions.CommandException;
import fithelper.model.Model;
import fithelper.model.entry.Entry;

/**
 * Adds a person to the address book.
 */
public class AddEntryCommand extends Command {

    public static final String COMMAND_WORD = "addEntry";

    public static final String MESSAGE_SUCCESS = "New Entry added: %1$s";
    public static final String MESSAGE_DUPLICATE_EXERCISE = "This Entry already exists in the list";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an Entry to the list. "
            + "Parameters: "
            + PREFIX_ENTRYNAME + "NAME "
            + PREFIX_ENTRYTIME + "TIME "
            + PREFIX_ENTRYLOCATION + "LOCATION "
            + PREFIX_ENTRYCALORIE + "WEIGHT\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ENTRYNAME + "Running "
            + PREFIX_ENTRYTIME + "next Wednesday "
            + PREFIX_ENTRYLOCATION + "utown gym"
            + PREFIX_ENTRYCALORIE + "500kcal ";

    private final Entry toAdd;

    /**
     * Creates an AddEntryCommand to add the specified {@code Entry}
     */
    public AddEntryCommand(Entry entry) {
        requireNonNull(entry);
        toAdd = entry;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.addEntry(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }
}

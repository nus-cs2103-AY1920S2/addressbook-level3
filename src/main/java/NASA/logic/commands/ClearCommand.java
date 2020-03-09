package NASA.logic.commands;

import static java.util.Objects.requireNonNull;

import NASA.logic.commands.Command;
import NASA.logic.commands.CommandResult;
import NASA.model.NasaBook;
import NASA.model.Model;

/**
 * Clears the nasa book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Nasa book has been cleared!";


    @Override
    public NASA.logic.commands.CommandResult execute(Model model) {
        requireNonNull(model);
        model.setNasaBook(new NasaBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

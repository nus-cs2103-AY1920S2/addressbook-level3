package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.nusmodule.Capulator;

public class CapCommand extends Command {

    public static final String COMMAND_WORD = "cap";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Calculates your current CAP based on the modules";

    public static final String MESSAGE_SUCCESS = "CAP calculated";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        double result = model.getCap();
        String message = MESSAGE_SUCCESS + "\n Current CAP: " + result;
        return new CommandResult(message);
    }
}

package seedu.address.logic.commands.tutorials;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_MOD;
import static seedu.address.commons.core.Messages.MESSAGE_TUTORIAL_TIMING_CLASH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_BEGIN_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_WEEKDAY;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.tutorial.Tutorial;
import seedu.address.model.mod.Mod;

/**
 * Adds a Tutorial to TAble.
 */
public class AddTutorialCommand extends Command {
    public static final String COMMAND_WORD = "addTutorial";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a tutorial to TAble.\n"
            + "Parameters: "
            + PREFIX_MODULE_CODE + "MODULE_CODE "
            + PREFIX_TUTORIAL_NAME + "TUTORIAL_NAME (capped at 8 alphanumeric characters) "
            + PREFIX_TUTORIAL_WEEKDAY + "TUTORIAL_WEEKDAY_VALUE (1-7) "
            + PREFIX_TUTORIAL_BEGIN_TIME + "TUTORIAL_BEGIN_TIME "
            + PREFIX_TUTORIAL_END_TIME + "TUTORIAL_END_TIME "
            + PREFIX_PLACE + "PLACE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE_CODE + "CS2103 "
            + PREFIX_TUTORIAL_NAME + "T03 "
            + PREFIX_TUTORIAL_WEEKDAY + "3 "
            + PREFIX_TUTORIAL_BEGIN_TIME + "15:00 "
            + PREFIX_TUTORIAL_END_TIME + "16:00 "
            + PREFIX_PLACE + "SR3 ";

    public static final String MESSAGE_SUCCESS = "New tutorial added!\n%1$s";
    public static final String MESSAGE_DUPLICATE_TUTORIAL = "This tutorial already exists in TAble.\n"
            + "If you are adding another session of the same tutorial, please use a different tutorial name "
            + "(eg. T02A and T02B).";

    private final Tutorial toAdd;

    /**
     * Creates an AddTutorialCommand to add the specified {@code tutorial}
     */
    public AddTutorialCommand(Tutorial tutorial) {
        requireNonNull(tutorial);
        toAdd = tutorial;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasTutorial(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TUTORIAL);
        }

        if (!model.hasMod(new Mod(toAdd.getModCode(), ""))) {
            throw new CommandException(MESSAGE_MISSING_MOD);
        }

        if (model.hasSameTiming(toAdd)) {
            throw new CommandException(MESSAGE_TUTORIAL_TIMING_CLASH);
        }

        model.addTutorial(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTutorialCommand // instanceof handles nulls
                && toAdd.equals(((AddTutorialCommand) other).toAdd));
    }
}

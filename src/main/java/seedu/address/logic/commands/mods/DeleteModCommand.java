package seedu.address.logic.commands.mods;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_MOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import java.util.List;
import java.util.Optional;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.tutorial.Tutorial;
import seedu.address.model.mod.Mod;
import seedu.address.model.mod.ModCode;

/**
 * Deletes an existing Mod from TAble.
 */

public class DeleteModCommand extends Command {
    public static final String COMMAND_WORD = "deleteMod";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes the module identified by the index number used in the displayed ModTAble.\n"
        + "Parameters: "
        + PREFIX_MODULE_CODE + "MODULE_CODE"
        + "\nExample: " + COMMAND_WORD + " "
        + PREFIX_MODULE_CODE + "CS2103";

    public static final String MESSAGE_DELETE_MOD_SUCCESS = "Deleted module!\n%1$s";
    public static final String MESSAGE_MOD_HAS_TUTORIAL = "Mod cannot be deleted as "
        + "there are still tutorials associated with it. ";

    private final ModCode modCode;

    public DeleteModCommand(ModCode modCode) {
        this.modCode = modCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasMod(new Mod(modCode, ""))) {
            throw new CommandException(MESSAGE_MISSING_MOD);
        }

        List<Tutorial> tutorialList = model.getFilteredTutorialList();
        if (tutorialList.stream().map(Tutorial::getModCode).anyMatch(x -> x.equals(modCode))) {
            throw new CommandException(MESSAGE_MOD_HAS_TUTORIAL);
        }

        Optional<Mod> optionalMod = model.findMod(modCode);
        assert optionalMod.isPresent();
        Mod modToGet = optionalMod.get();
        model.deleteMod(modToGet);
        return new CommandResult(String.format(MESSAGE_DELETE_MOD_SUCCESS, modToGet));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DeleteModCommand
            && modCode.equals(((DeleteModCommand) other).modCode));
    }
}

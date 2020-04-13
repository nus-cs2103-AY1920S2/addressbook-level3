package seedu.address.logic.commands.mods;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_MOD;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_NOTE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MODS;

import java.util.Optional;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.mod.Mod;
import seedu.address.model.mod.ModCode;

/**
 * Edits the note in a Mod.
 */

public class NoteModCommand extends Command {

    public static final String COMMAND_WORD = "noteMod";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the notes of the module identified "
        + "by the module code. Module must exist in TAble.\n"
        + "Existing values will be overwritten by the input values.\n"
        + "Parameters: " + PREFIX_MODULE_CODE + "MODULE_CODE "
        + PREFIX_MODULE_NOTE + "NOTE\n"
        + "Example: " + COMMAND_WORD + " " + PREFIX_MODULE_CODE + "CS2103 "
        + PREFIX_MODULE_NOTE + "Fun module!! ";

    public static final String MESSAGE_EDIT_MODNOTE_SUCCESS = "Edited Module: %1$s";

    private final ModCode modCode;
    private final String note;

    /**
     * @param modCode module in the filtered module list to edit
     * @param note details to append the module note with
     */
    public NoteModCommand(ModCode modCode, String note) {
        requireAllNonNull(modCode, note);
        this.modCode = modCode;
        this.note = note;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasMod(new Mod(modCode, ""))) {
            throw new CommandException(MESSAGE_MISSING_MOD);
        }

        Optional<Mod> optionalModToEdit = model.findMod(modCode);
        assert optionalModToEdit.isPresent();
        Mod modToEdit = optionalModToEdit.get();

        Mod editedMod = new Mod(modToEdit, note);
        model.setMod(modToEdit, editedMod);
        model.updateFilteredModList(PREDICATE_SHOW_ALL_MODS);
        return new CommandResult(String.format(MESSAGE_EDIT_MODNOTE_SUCCESS, editedMod));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NoteModCommand)) {
            return false;
        }

        // state check
        NoteModCommand e = (NoteModCommand) other;
        return modCode.equals(e.modCode)
            && note.equals(e.note);
    }
}

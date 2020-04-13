package seedu.address.logic.commands.mods;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_MOD;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_LINK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_LINK_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MODS;

import java.util.Optional;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.mod.Mod;
import seedu.address.model.mod.ModCode;
import seedu.address.model.mod.ModLink;

/**
 * Adds a corresponding Pair of String, ModLink into the links of a Mod.
 */
public class AddModLinkCommand extends Command {
    public static final String COMMAND_WORD = "addModLink";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a module link to a module in TAble.\n"
        + "Parameters: "
        + PREFIX_MODULE_CODE + "MODULE_CODE "
        + PREFIX_MODULE_LINK + "MODULE_LINK "
        + PREFIX_MODULE_LINK_NAME + "MODULE_LINK_NAME\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_MODULE_CODE + "CS2103 "
        + PREFIX_MODULE_LINK + "http://www.comp.nus.edu.sg/~cs2103 "
        + PREFIX_MODULE_LINK_NAME + "Module Website";

    public static final String MESSAGE_SUCCESS = "Link has been added to module\n%1$s";

    private final ModCode modCode;
    private final String linkName;
    private final ModLink link;

    /**
     * Creates an AddModCommand to add the specified module corresponding to {@code modCode}
     */
    public AddModLinkCommand(ModCode modCode, String modLinkName, ModLink link) {
        requireAllNonNull(modCode, modLinkName, link);
        this.modCode = modCode;
        this.linkName = modLinkName;
        this.link = link;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasMod(new Mod(modCode, ""))) {
            throw new CommandException(MESSAGE_MISSING_MOD);
        }

        Optional<Mod> optionalMod = model.findMod(modCode);
        assert optionalMod.isPresent();
        Mod modToEdit = optionalMod.get();

        Mod editedMod = new Mod(modToEdit, linkName, link);
        model.setMod(modToEdit, editedMod);
        model.updateFilteredModList(PREDICATE_SHOW_ALL_MODS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, editedMod));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof AddModLinkCommand // instanceof handles nulls
            && modCode.equals(((AddModLinkCommand) other).modCode));
    }
}

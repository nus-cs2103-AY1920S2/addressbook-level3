package seedu.address.logic.commands.mods;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_MOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import java.util.List;
import java.util.Optional;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.mod.Mod;
import seedu.address.model.mod.ModCode;
import seedu.address.model.mod.ModLink;
import seedu.address.model.mod.ModLinkPair;

/**
 * Copies a ModLink into the user's clipboard.
 */
public class CopyModLinkCommand extends Command {
    public static final String COMMAND_WORD = "copyModLink";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Copies the URL link of a Mod identified by the index number used, "
        + "which can be viewed using viewModInfo.\n"
        + "Parameters: "
        + PREFIX_INDEX + " "
        + PREFIX_MODULE_CODE + "MODULE_CODE"
        + "\nExample: " + COMMAND_WORD + " 2 modCode/CS2103";

    public static final String MESSAGE_COPIED_LINK_SUCCESS = "Link copied to clipboard!\n%1$s";

    private final Index targetIndex;
    private final ModCode modCode;

    public CopyModLinkCommand(Index targetIndex, ModCode modCode) {
        this.targetIndex = targetIndex;
        this.modCode = modCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasMod(new Mod(modCode, ""))) {
            throw new CommandException(MESSAGE_MISSING_MOD);
        }

        Optional<Mod> optionalMod = model.findMod(modCode);
        assert optionalMod.isPresent();
        Mod modToGet = optionalMod.get();

        List<ModLinkPair> modLinkPairList = modToGet.getLinks();
        if (modLinkPairList.size() == 0 || targetIndex.getZeroBased() >= modLinkPairList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MODLINK_INDEX);
        }
        ModLink link = modLinkPairList.get(targetIndex.getZeroBased()).getValue();

        // Cannot be tested as it can only function on event thread
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(link.toString());
        clipboard.setContent(url);

        return new CommandResult(String.format(MESSAGE_COPIED_LINK_SUCCESS, link));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof CopyModLinkCommand
            && targetIndex.equals(((CopyModLinkCommand) other).targetIndex)
            && modCode.equals(((CopyModLinkCommand) other).modCode));
    }
}

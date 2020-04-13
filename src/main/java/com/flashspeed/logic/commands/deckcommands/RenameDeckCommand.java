package com.flashspeed.logic.commands.deckcommands;

import static com.flashspeed.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.util.List;

import com.flashspeed.commons.core.Messages;
import com.flashspeed.commons.core.index.Index;
import com.flashspeed.logic.commands.Command;
import com.flashspeed.logic.commands.CommandResult;
import com.flashspeed.logic.commands.exceptions.CommandException;
import com.flashspeed.model.Model;
import com.flashspeed.model.deck.Deck;
import com.flashspeed.model.deck.Name;
import com.flashspeed.model.util.View;

/**
 * Renames the name of a deck in the library.
 */
public class RenameDeckCommand extends Command {

    public static final String COMMAND_WORD = "rename";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Renames the name of a deck in the library.\n"
            + "Existing name will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "NAME \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "Japanese 2";

    public static final String MESSAGE_RENAME_DECK_SUCCESS = "Edited Deck: %1$s";
    public static final String MESSAGE_NOT_EDITED = "New deck name must be provided.";
    public static final String MESSAGE_DUPLICATE_DECK = "This deck name already exists in the library.";
    public static final String MESSAGE_NOT_IN_VIEW_MODE = "Cannot rename deck in the play view";

    private final Index index;
    private final Name name;

    /**
     * @param index of the deck in the library list to edit
     * @param name new name to edit the deck with
     */
    public RenameDeckCommand(Index index, Name name) {
        requireAllNonNull(index, name);
        this.index = index;
        this.name = name;
    }

    // need to remake this method
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.getView() == View.PLAY) {
            throw new CommandException(MESSAGE_NOT_IN_VIEW_MODE);
        }

        List<Deck> lastShownList = model.getFilteredDeckList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DECK_DISPLAYED_INDEX);
        }

        boolean result = model.renameDeck(index, name);
        if (!result) {
            throw new CommandException(MESSAGE_DUPLICATE_DECK);
        }
        Deck editedDeck = lastShownList.get(index.getZeroBased());
        model.updateFilteredDeckList(Model.PREDICATE_SHOW_ALL_DECKS);

        return new CommandResult(String.format(MESSAGE_RENAME_DECK_SUCCESS, editedDeck));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RenameDeckCommand // instanceof handles nulls
                && index.equals(((RenameDeckCommand) other).index)
                && name.equals(((RenameDeckCommand) other).name));
    }
}

package com.flashspeed.logic.commands.deckcommands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import com.flashspeed.commons.core.Messages;
import com.flashspeed.commons.core.index.Index;
import com.flashspeed.logic.commands.Command;
import com.flashspeed.logic.commands.CommandResult;
import com.flashspeed.logic.commands.exceptions.CommandException;
import com.flashspeed.model.Model;
import com.flashspeed.model.deck.Deck;
import com.flashspeed.model.util.View;

/**
 * Selects a deck in the library.
 */
public class SelectDeckCommand extends Command {

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects a deck in the library.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example:" + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Deck selected: %1$s";
    public static final String MESSAGE_NOT_IN_VIEW_MODE = "Cannot select deck in the play view";

    private final Index targetIdx;

    /**
     * Creates an AddCommand to add the specified {@code Deck}
     */
    public SelectDeckCommand(Index targetIdx) {
        requireNonNull(targetIdx);
        this.targetIdx = targetIdx;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.getView() == View.PLAY) {
            throw new CommandException(MESSAGE_NOT_IN_VIEW_MODE);
        }

        List<Deck> lastShownList = model.getFilteredDeckList();

        if (targetIdx.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DECK_DISPLAYED_INDEX);
        }

        model.selectDeck(targetIdx);
        Deck selectedDeck = model.getDeck(targetIdx);
        return new CommandResult(String.format(MESSAGE_SUCCESS, selectedDeck));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectDeckCommand // instanceof handles nulls
                && targetIdx.equals(((SelectDeckCommand) other).targetIdx));
    }
}

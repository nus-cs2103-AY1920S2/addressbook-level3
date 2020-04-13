package com.flashspeed.logic.commands.cardcommands;

import static java.util.Objects.requireNonNull;

import com.flashspeed.commons.core.Messages;
import com.flashspeed.logic.commands.Command;
import com.flashspeed.logic.commands.CommandResult;
import com.flashspeed.logic.commands.exceptions.CommandException;
import com.flashspeed.model.Model;
import com.flashspeed.model.deck.card.Card;
import com.flashspeed.model.util.View;

/**
 * Adds a card to the deck.
 */
public class AddCardCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a card to the current deck.\n"
            + "Parameters: "
            + "FRONT:BACK\n"
            + "Example: " + COMMAND_WORD + " "
            + "ありがとう:thanks";

    public static final String MESSAGE_SUCCESS = "New card added: %1$s";
    public static final String MESSAGE_DUPLICATE_CARD = "This card already exists in the deck";
    public static final String MESSAGE_NOT_IN_VIEW_MODE = "Cannot add card in the play view";

    private final Card toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Card}
     */
    public AddCardCommand(Card card) {
        requireNonNull(card);
        toAdd = card;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.getView() == View.PLAY) {
            throw new CommandException(MESSAGE_NOT_IN_VIEW_MODE);
        }

        if (model.getView() != View.DECK) {
            throw new CommandException(Messages.MESSAGE_NOT_IN_DECK_VIEW);
        }

        if (model.hasCard(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_CARD);
        }

        model.addCard(toAdd);

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCardCommand // instanceof handles nulls
                && toAdd.equals(((AddCardCommand) other).toAdd));
    }
}

package cardibuddy.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import cardibuddy.commons.core.Messages;
import cardibuddy.commons.core.index.Index;
import cardibuddy.logic.commands.exceptions.CommandException;
import cardibuddy.model.Model;
import cardibuddy.model.deck.Deck;

/**
 * Opens the Deck that corresponds with the index and the subsequent Flashcards.
 */
public class OpenCommand extends Command {

    public static final String COMMAND_WORD = "open";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Opens the deck identified by the index number used in the displayed cardibuddy book.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + "1";

    public static final String MESSAGE_OPEN_DECK_SUCCESS = "Opened Deck: %1$s";

    private final Index targetIndex;

    public OpenCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Deck> lastShownList = model.getFilteredDeckList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DECK_DISPLAYED_INDEX);
        }

        Deck deckToOpen = lastShownList.get(targetIndex.getZeroBased());
        deckToOpen.getFlashcards();
        return new DeckCommandResult(MESSAGE_OPEN_DECK_SUCCESS, true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OpenCommand // instanceof handles nulls
                && targetIndex.equals(((OpenCommand) other).targetIndex)); // state check
    }
}

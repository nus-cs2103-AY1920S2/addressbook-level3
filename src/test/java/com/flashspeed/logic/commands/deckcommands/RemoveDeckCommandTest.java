package com.flashspeed.logic.commands.deckcommands;

import static com.flashspeed.testutil.Assert.assertThrows;
import static com.flashspeed.testutil.TypicalIndexes.INDEX_FIRST_DECK;
import static com.flashspeed.testutil.TypicalIndexes.INDEX_SECOND_DECK;
import static com.flashspeed.testutil.TypicalIndexes.INDEX_THIRD_DECK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import com.flashspeed.commons.core.GuiSettings;
import com.flashspeed.commons.core.Messages;
import com.flashspeed.commons.core.index.Index;
import com.flashspeed.logic.commands.CommandResult;
import com.flashspeed.logic.commands.exceptions.CommandException;
import com.flashspeed.model.GameManager;
import com.flashspeed.model.Library;
import com.flashspeed.model.Model;
import com.flashspeed.model.ReadOnlyLibrary;
import com.flashspeed.model.ReadOnlyUserPrefs;
import com.flashspeed.model.Statistics;
import com.flashspeed.model.deck.Deck;
import com.flashspeed.model.deck.Name;
import com.flashspeed.model.deck.card.BackFace;
import com.flashspeed.model.deck.card.Card;
import com.flashspeed.model.util.View;
import com.flashspeed.testutil.DeckUtils;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

public class RemoveDeckCommandTest {

    @Test
    public void execute_deckAcceptedByModel_deleteSuccessful() throws Exception {
        ModelStubAcceptingDeckDeleted modelStub = new ModelStubAcceptingDeckDeleted();
        Deck validDeckToDelete = DeckUtils.getTypicalLibrary().getDeck(Index.fromZeroBased(1));
        CommandResult commandResult = new RemoveDeckCommand(Index.fromZeroBased(1)).execute(modelStub);

        assertEquals(String.format(RemoveDeckCommand.MESSAGE_DELETE_DECK_SUCCESS, validDeckToDelete),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_notInViewMode_throwsCommandException() {
        ModelStubPlayMode modelStub = new ModelStubPlayMode();
        RemoveDeckCommand removeDeckCommand = new RemoveDeckCommand(Index.fromZeroBased(1));

        assertThrows(CommandException.class,
                RemoveDeckCommand.MESSAGE_NOT_IN_VIEW_MODE, () -> removeDeckCommand.execute(modelStub));
    }

    @Test
    public void execute_targetIndexLargerThanDeckSize_throwsCommandException() {
        ModelStubAcceptingDeckDeleted modelStub = new ModelStubAcceptingDeckDeleted();
        Index invalidIndex = INDEX_THIRD_DECK;
        RemoveDeckCommand removeDeckCommand = new RemoveDeckCommand(invalidIndex);

        assertThrows(CommandException.class,
                Messages.MESSAGE_INVALID_DECK_DISPLAYED_INDEX, () -> removeDeckCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Index first = INDEX_FIRST_DECK;
        Index second = INDEX_SECOND_DECK;

        RemoveDeckCommand removeFirstCommand = new RemoveDeckCommand(first);
        RemoveDeckCommand removeSecondCommand = new RemoveDeckCommand(second);

        // same object -> returns true
        assertTrue(removeFirstCommand.equals(removeFirstCommand));

        // same values -> returns true
        RemoveDeckCommand removeFirstCommandCopy = new RemoveDeckCommand(first);
        assertTrue(removeFirstCommand.equals(removeFirstCommandCopy));

        // different types -> returns false
        assertFalse(removeFirstCommand.equals(1));

        // null -> returns false
        assertFalse(removeFirstCommand.equals(null));

        // different deck -> returns false
        assertFalse(removeFirstCommand.equals(removeSecondCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getLibraryFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setLibraryFilePath(Path libraryFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setLibrary(ReadOnlyLibrary newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyLibrary getLibrary() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void returnToLibrary() {
            throw new AssertionError("This method should not be called");
        }

        //=========== Deck Functions =============================================================
        @Override
        public boolean hasDeck(Deck deck) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteDeck(Deck target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDeck(Deck target, Deck editedDeck) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void createDeck(Deck deck) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void selectDeck(Index targetIdx) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Deck getCurrentDeck() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public Deck getDeck(Index index) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public boolean renameDeck(Index targetIndex, Name name) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public ReadOnlyProperty<Deck> selectedDeckProperty() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public ReadOnlyProperty<View> currentViewProperty() {
            throw new AssertionError("This method should not be called");
        }

        //=========== Card Functions  =============================================================
        @Override
        public boolean hasCard(Card card) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void deleteCard(Card target) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void addCard(Card card) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void replaceCard(Card target, Card card) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public ReadOnlyProperty<Card> playingCardProperty() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public ReadOnlyProperty<Boolean> flippedProperty() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public ReadOnlyProperty<Integer> cardAttemptedProperty() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public ReadOnlyProperty<Integer> cardRemainingProperty() {
            throw new AssertionError("This method should not be called");
        }


        @Override
        public View getView() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void setCurrentView(View view) {
            throw new AssertionError("This method should not be called");
        }

        //=========== Filtered Deck List Accessors =============================================================

        @Override
        public ObservableList<Deck> getFilteredDeckList() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public ObservableList<Card> getFilteredCardList() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void updateFilteredDeckList(Predicate<Deck> predicate) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void setSelectedDeck(Deck deck) {
            throw new AssertionError("This method should not be called");
        }

        //=========== Play View Functions =============================================================

        @Override
        public void setFlipped(Boolean value) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void setCardAttempted(int value) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void setCardRemaining(int value) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void setPlayingCard(Card card) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public Card getCard(Index index) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public Card play(Index index) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public BackFace flip() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public Card answerYes() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public Statistics stop() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public GameManager getGame() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public Card answerNo() {
            throw new AssertionError("This method should not be called");
        }

    }

    /**
     * A Model stub that always accepts a deck being removed.
     */
    private class ModelStubAcceptingDeckDeleted extends ModelStub {
        private Library library = DeckUtils.getTypicalLibrary();

        public Library getLibrary() {
            return library;
        }

        @Override
        public View getView() {
            return View.DECK;
        }

        @Override
        public ObservableList<Deck> getFilteredDeckList() {
            return new FilteredList<>(this.library.getDeckList());
        }

        @Override
        public void deleteDeck(Deck target) {
            library.deleteDeck(target);
        }
    }

    /**
     * A Model stub that cannot delete a deck due to being in Play Mode
     */
    private class ModelStubPlayMode extends ModelStub {
        private Library library = DeckUtils.getTypicalLibrary();

        public Library getLibrary() {
            return library;
        }

        @Override
        public View getView() {
            return View.PLAY;
        }

        @Override
        public ObservableList<Deck> getFilteredDeckList() {
            return new FilteredList<>(this.library.getDeckList());
        }

        @Override
        public void deleteDeck(Deck target) {
            library.deleteDeck(target);
        }
    }
}

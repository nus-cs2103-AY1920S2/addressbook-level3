package com.flashspeed.logic.commands.gamecommands;

import static com.flashspeed.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import com.flashspeed.commons.core.GuiSettings;
import com.flashspeed.commons.core.index.Index;
import com.flashspeed.logic.commands.CommandResult;
import com.flashspeed.logic.commands.exceptions.CommandException;
import com.flashspeed.model.GameManager;
import com.flashspeed.model.Model;
import com.flashspeed.model.ReadOnlyLibrary;
import com.flashspeed.model.ReadOnlyUserPrefs;
import com.flashspeed.model.Statistics;
import com.flashspeed.model.deck.Deck;
import com.flashspeed.model.deck.Name;
import com.flashspeed.model.deck.card.BackFace;
import com.flashspeed.model.deck.card.Card;
import com.flashspeed.model.util.View;
import com.flashspeed.testutil.CardUtils;
import com.flashspeed.testutil.DeckUtils;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;

public class AnswerNoCommandTest {

    @Test
    public void execute_play_answerNoSuccessful() throws Exception {
        ModelStubAcceptingGameAnswerNo modelStub = new ModelStubAcceptingGameAnswerNo();
        CommandResult commandResult = new AnswerNoCommand().execute(modelStub);

        assertEquals(String.format(AnswerNoCommand.MESSAGE_SUCCESS), commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_notInPlayMode_throwsCommandException() {
        ModelStubNotPlayMode modelStub = new ModelStubNotPlayMode();
        AnswerNoCommand answerNoCommand = new AnswerNoCommand();

        assertThrows(CommandException.class,
                AnswerNoCommand.MESSAGE_NOT_PLAY_MODE, () -> answerNoCommand.execute(modelStub));
    }

    @Test
    public void execute_gameNotFlipped_throwsCommandException() {
        ModelStubCardNotFlipped modelStub = new ModelStubCardNotFlipped();
        AnswerNoCommand answerNoCommand = new AnswerNoCommand();

        assertThrows(CommandException.class,
                AnswerNoCommand.MESSAGE_NOT_FLIPPED, () -> answerNoCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        AnswerNoCommand answerNoCommand = new AnswerNoCommand();

        // same object -> returns true
        assertTrue(answerNoCommand.equals(answerNoCommand));

        // same values -> returns true
        AnswerNoCommand answerNoCommandCopy = new AnswerNoCommand();
        assertTrue(answerNoCommand.equals(answerNoCommandCopy));

        // different types -> returns false
        assertFalse(answerNoCommand.equals(1));

        // null -> returns false
        assertFalse(answerNoCommand.equals(null));
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
     * A Model stub that always answer no.
     */
    private class ModelStubAcceptingGameAnswerNo extends ModelStub {

        @Override
        public View getView() {
            return View.PLAY;
        }

        @Override
        public GameManager getGame() {
            Deck testDeck = DeckUtils.getTypicalJapDeck();
            GameManager newGame = new GameManager(testDeck);
            newGame.flip();
            return newGame;
        }

        @Override
        public Card answerNo() {
            return CardUtils.JAP_CARD_1;
        }
    }

    /**
     * A Model stub that cannot play due to not in play mode.
     */
    private class ModelStubNotPlayMode extends ModelStub {

        @Override
        public View getView() {
            return View.DECK;
        }

        @Override
        public GameManager getGame() {
            Deck testDeck = DeckUtils.getTypicalJapDeck();
            GameManager newGame = new GameManager(testDeck);
            newGame.flip();
            return newGame;
        }

        @Override
        public Card answerNo() {
            return CardUtils.JAP_CARD_1;
        }
    }

    /**
     * A Model stub that cannot answer no since card is not flipped.
     */
    private class ModelStubCardNotFlipped extends ModelStub {

        @Override
        public View getView() {
            return View.PLAY;
        }

        @Override
        public GameManager getGame() {
            Deck testDeck = DeckUtils.getTypicalJapDeck();
            GameManager newGame = new GameManager(testDeck);
            return newGame;
        }

        @Override
        public Card answerNo() {
            return CardUtils.JAP_CARD_1;
        }
    }
}

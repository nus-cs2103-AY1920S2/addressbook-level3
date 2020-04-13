package com.flashspeed.logic.commands.cardcommands;

import static com.flashspeed.testutil.Assert.assertThrows;
import static com.flashspeed.testutil.TypicalIndexes.INDEX_FIRST_CARD;
import static com.flashspeed.testutil.TypicalIndexes.INDEX_THIRD_CARD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import com.flashspeed.commons.core.GuiSettings;
import com.flashspeed.commons.core.Messages;
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
import com.flashspeed.testutil.CardBuilder;
import com.flashspeed.testutil.DeckBuilder;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;

public class DeleteCardCommandTest {

    @Test
    public void execute_deleteAcceptedByModel_successful() throws Exception {
        ModelStubAcceptingCardDeleted modelStub = new ModelStubAcceptingCardDeleted();

        Index validIndex = INDEX_FIRST_CARD;
        Card cardToDelete = modelStub.getCard(validIndex);
        CommandResult commandResult = new DeleteCardCommand(validIndex).execute(modelStub);

        assertEquals(String.format(DeleteCardCommand.MESSAGE_SUCCESS, cardToDelete), commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_inPlayView_throwsCommandException() {
        ModelStubPlayMode modelStub = new ModelStubPlayMode();
        Index validIndex = INDEX_FIRST_CARD;
        DeleteCardCommand deleteCardCommand = new DeleteCardCommand(validIndex);

        assertThrows(CommandException.class,
                DeleteCardCommand.MESSAGE_NOT_IN_VIEW_MODE, () -> deleteCardCommand.execute(modelStub));
    }

    @Test
    public void execute_inLibraryView_throwsCommandException() {
        ModelStubLibraryView modelStub = new ModelStubLibraryView();
        DeleteCardCommand deleteCardCommand = new DeleteCardCommand(INDEX_FIRST_CARD);

        assertThrows(CommandException.class,
                Messages.MESSAGE_NOT_IN_DECK_VIEW, () -> deleteCardCommand.execute(modelStub));
    }

    @Test
    public void execute_targetIndexLargerThanDeckSize_throwsCommandException() {
        ModelStubAcceptingCardDeleted modelStub = new ModelStubAcceptingCardDeleted();
        Index invalidIndex = INDEX_THIRD_CARD;
        DeleteCardCommand deleteCardCommand = new DeleteCardCommand(invalidIndex);

        assertThrows(CommandException.class,
                Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX, () -> deleteCardCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Card card1 = new CardBuilder().withFrontFace("Hello").withBackFace("안녕하세요").build();
        Card card2 = new CardBuilder().withFrontFace("Yes").withBackFace("네").build();

        AddCardCommand addCard1Command = new AddCardCommand(card1);
        AddCardCommand addCard2Command = new AddCardCommand(card2);

        // same object -> returns true
        assertTrue(addCard1Command.equals(addCard1Command));

        // same values -> returns true
        AddCardCommand addCard1CommandCopy = new AddCardCommand(card1);
        assertTrue(addCard1Command.equals(addCard1CommandCopy));

        // different types -> returns false
        assertFalse(addCard1Command.equals(1));

        // null -> returns false
        assertFalse(addCard1Command.equals(null));

        // different person -> returns false
        assertFalse(addCard1Command.equals(addCard2Command));
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
     * A Model stub that always accepts a card being deleted.
     * The Deck in this model contains 2 cards
     */
    private class ModelStubAcceptingCardDeleted extends ModelStub {
        final ArrayList<Card> cardsList = new ArrayList<>();
        final Deck deck;

        public ModelStubAcceptingCardDeleted() {
            Card card1 = new CardBuilder().withFrontFace("Hello").withBackFace("안녕하세요").build();
            Card card2 = new CardBuilder().withFrontFace("Yes").withBackFace("네").build();
            cardsList.add(card1);
            cardsList.add(card2);
            deck = new DeckBuilder().withCards(cardsList).build();
        }

        @Override
        public View getView() {
            return View.DECK;
        }

        @Override
        public Deck getCurrentDeck() {
            return deck;
        }

        @Override
        public Card getCard(Index index) {
            return deck.getCard(index);
        }

        @Override
        public void deleteCard(Card cardToDelete) {
            if (deck == null) {
                return;
            }
            deck.remove(cardToDelete);
        }
    }

    /**
     * A Model stub that cannot add a card due to being in Play Mode
     */
    private class ModelStubPlayMode extends ModelStub {
        final ArrayList<Card> cardsList = new ArrayList<>();
        final Deck deck;

        public ModelStubPlayMode() {
            Card card1 = new CardBuilder().withFrontFace("Hello").withBackFace("안녕하세요").build();
            Card card2 = new CardBuilder().withFrontFace("Yes").withBackFace("네").build();
            cardsList.add(card1);
            cardsList.add(card2);
            deck = new DeckBuilder().withCards(cardsList).build();
        }

        @Override
        public View getView() {
            return View.PLAY;
        }

        @Override
        public Deck getCurrentDeck() {
            return deck;
        }

        @Override
        public Card getCard(Index index) {
            return deck.getCard(index);
        }

        @Override
        public void deleteCard(Card cardToDelete) {
            if (deck == null) {
                return;
            }
            deck.remove(cardToDelete);
        }
    }

    /**
     * A Model stub that cannot add a card due to being in Library View
     */
    private class ModelStubLibraryView extends ModelStub {
        final ArrayList<Card> cardsList = new ArrayList<>();
        final Deck deck;

        public ModelStubLibraryView() {
            Card card1 = new CardBuilder().withFrontFace("Hello").withBackFace("안녕하세요").build();
            Card card2 = new CardBuilder().withFrontFace("Yes").withBackFace("네").build();
            cardsList.add(card1);
            cardsList.add(card2);
            deck = new DeckBuilder().withCards(cardsList).build();
        }

        @Override
        public Deck getCurrentDeck() {
            return deck;
        }

        @Override
        public View getView() {
            return View.LIBRARY;
        }

        @Override
        public Card getCard(Index index) {
            return deck.getCard(index);
        }

        @Override
        public void deleteCard(Card cardToDelete) {
            if (deck == null) {
                return;
            }
            deck.remove(cardToDelete);
        }
    }
}

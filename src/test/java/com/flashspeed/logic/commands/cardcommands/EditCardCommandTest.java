package com.flashspeed.logic.commands.cardcommands;

import static com.flashspeed.testutil.Assert.assertThrows;
import static com.flashspeed.testutil.TypicalIndexes.INDEX_FIRST_CARD;
import static com.flashspeed.testutil.TypicalIndexes.INDEX_SECOND_CARD;
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
import com.flashspeed.model.deck.card.FrontFace;
import com.flashspeed.model.util.View;
import com.flashspeed.testutil.CardBuilder;
import com.flashspeed.testutil.CardUtils;
import com.flashspeed.testutil.DeckBuilder;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;

public class EditCardCommandTest {

    @Test
    public void execute_editBothSidesAcceptedByModel_successful() throws Exception {
        ModelStubAcceptingCardEdited modelStub = new ModelStubAcceptingCardEdited();
        Index validIndex = INDEX_FIRST_CARD;
        FrontFace front = new FrontFace("Hello");
        BackFace back = new BackFace("안녕");;
        Card editedCard = new Card(front, back);

        CommandResult commandResult = new EditCardCommand(validIndex, front, back).execute(modelStub);

        assertEquals(String.format(EditCardCommand.MESSAGE_SUCCESS, editedCard), commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_editFrontOnlyAcceptedByModel_successful() throws Exception {
        ModelStubAcceptingCardEdited modelStub = new ModelStubAcceptingCardEdited();

        Index validIndex = INDEX_FIRST_CARD;
        FrontFace emptyFront = new FrontFace("");
        BackFace back = new BackFace("안녕");

        CommandResult commandResult = new EditCardCommand(validIndex, emptyFront, back).execute(modelStub);
        Card newCard = modelStub.getCard(validIndex);

        assertEquals(String.format(EditCardCommand.MESSAGE_SUCCESS, newCard), commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_editBackOnlyAcceptedByModel_successful() throws Exception {
        ModelStubAcceptingCardEdited modelStub = new ModelStubAcceptingCardEdited();

        Index validIndex = INDEX_FIRST_CARD;
        FrontFace front = new FrontFace("Hello");
        BackFace emptyBack = new BackFace("");;

        CommandResult commandResult = new EditCardCommand(validIndex, front, emptyBack).execute(modelStub);
        Card newCard = modelStub.getCard(validIndex);

        assertEquals(String.format(EditCardCommand.MESSAGE_SUCCESS, newCard), commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_notInViewMode_throwsCommandException() {
        ModelStubPlayMode modelStub = new ModelStubPlayMode();
        Index validIndex = INDEX_FIRST_CARD;
        FrontFace front = new FrontFace("Hello");
        BackFace back = new BackFace("안녕");;
        EditCardCommand editCardCommand = new EditCardCommand(validIndex, front, back);

        assertThrows(CommandException.class,
                EditCardCommand.MESSAGE_NOT_IN_VIEW_MODE, () -> editCardCommand.execute(modelStub));
    }

    @Test
    public void execute_notInDeckView_throwsCommandException() {
        ModelStubLibraryView modelStub = new ModelStubLibraryView();
        Index validIndex = INDEX_FIRST_CARD;
        FrontFace front = new FrontFace("Hello");
        BackFace back = new BackFace("안녕");;
        EditCardCommand editCardCommand = new EditCardCommand(validIndex, front, back);

        assertThrows(CommandException.class,
                Messages.MESSAGE_NOT_IN_DECK_VIEW, () -> editCardCommand.execute(modelStub));
    }

    @Test
    public void execute_targetIndexLargerThanDeckSize_throwsCommandException() {
        ModelStubAcceptingCardEdited modelStub = new ModelStubAcceptingCardEdited();
        Index invalidIndex = INDEX_THIRD_CARD;
        FrontFace front = new FrontFace("Hello");
        BackFace back = new BackFace("안녕");;
        EditCardCommand editCardCommand = new EditCardCommand(invalidIndex, front, back);

        assertThrows(CommandException.class,
                Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX, () -> editCardCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        final EditCardCommand standardCommand = new EditCardCommand(
                INDEX_FIRST_CARD, CardUtils.JAP_CARD_1.getFrontFace(), CardUtils.JAP_CARD_1.getBackFace());

        EditCardCommand commandWithSameValues = new EditCardCommand(
                INDEX_FIRST_CARD, CardUtils.JAP_CARD_1.getFrontFace(), CardUtils.JAP_CARD_1.getBackFace());
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new DeleteCardCommand(Index.fromOneBased(1))));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCardCommand(
                INDEX_SECOND_CARD, CardUtils.JAP_CARD_1.getFrontFace(), CardUtils.JAP_CARD_1.getBackFace())));
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
    private class ModelStubAcceptingCardEdited extends ModelStub {
        final ArrayList<Card> cardsList = new ArrayList<>();
        final Deck deck;

        public ModelStubAcceptingCardEdited() {
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
        public void replaceCard(Card oldCard, Card newCard) {
            if (deck == null) {
                return;
            }
            deck.replace(oldCard, newCard);
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
        public void replaceCard(Card oldCard, Card newCard) {
            if (deck == null) {
                return;
            }
            deck.replace(oldCard, newCard);
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
        public View getView() {
            return View.LIBRARY;
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
        public void replaceCard(Card oldCard, Card newCard) {
            if (deck == null) {
                return;
            }
            deck.replace(oldCard, newCard);
        }
    }
}

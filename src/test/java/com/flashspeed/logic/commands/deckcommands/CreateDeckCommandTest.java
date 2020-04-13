package com.flashspeed.logic.commands.deckcommands;

import static com.flashspeed.testutil.Assert.assertThrows;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.flashspeed.testutil.DeckBuilder;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;

public class CreateDeckCommandTest {

    @Test
    public void constructor_nullDeck_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CreateDeckCommand(null));
    }

    @Test
    public void execute_deckAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingDeckAdded modelStub = new ModelStubAcceptingDeckAdded();
        Deck validDeck = new DeckBuilder().withName("Test").build();

        CommandResult commandResult = new CreateDeckCommand(validDeck).execute(modelStub);

        assertEquals(String.format(CreateDeckCommand.MESSAGE_SUCCESS, validDeck), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validDeck), modelStub.decksAdded);
    }

    @Test
    public void execute_notInViewMode_throwsCommandException() {
        ModelStubPlayMode modelStub = new ModelStubPlayMode();
        Deck validDeck = new DeckBuilder().withName("Test").build();
        CreateDeckCommand createDeckCommand = new CreateDeckCommand(validDeck);

        assertThrows(CommandException.class,
                CreateDeckCommand.MESSAGE_NOT_IN_VIEW_MODE, () -> createDeckCommand.execute(modelStub));
    }

    @Test
    public void execute_duplicateDeck_throwsCommandException() {
        Deck validDeck = new DeckBuilder().withName("Test").build();
        ModelStub modelStub = new ModelStubWithDeck(validDeck);
        CreateDeckCommand createDeckCommand = new CreateDeckCommand(validDeck);

        assertThrows(CommandException.class,
                CreateDeckCommand.MESSAGE_DUPLICATE_DECK, () -> createDeckCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Deck deck1 = new DeckBuilder().withName("Malay").build();
        Deck deck2 = new DeckBuilder().withName("Japanese").build();

        CreateDeckCommand createDeck1Command = new CreateDeckCommand(deck1);
        CreateDeckCommand createDeck2Command = new CreateDeckCommand(deck2);

        // same object -> returns true
        assertTrue(createDeck1Command.equals(createDeck1Command));

        // same values -> returns true
        CreateDeckCommand createDeck1CommandCopy = new CreateDeckCommand(deck1);
        assertTrue(createDeck1Command.equals(createDeck1CommandCopy));

        // different types -> returns false
        assertFalse(createDeck1Command.equals(1));

        // null -> returns false
        assertFalse(createDeck1Command.equals(null));

        // different deck -> returns false
        assertFalse(createDeck1Command.equals(createDeck2Command));
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
     * A Model stub that always accepts a deck being created.
     */
    private class ModelStubAcceptingDeckAdded extends ModelStub {
        final ArrayList<Deck> decksAdded = new ArrayList<>();

        @Override
        public View getView() {
            return View.LIBRARY;
        }

        @Override
        public boolean hasDeck(Deck deck) {
            requireNonNull(deck);
            return decksAdded.stream().anyMatch(deck::equals);
        }

        @Override
        public void createDeck(Deck deck) {
            requireNonNull(deck);
            decksAdded.add(deck);
        }
    }

    /**
     * A Model stub that cannot add a deck due to being in Play Mode
     */
    private class ModelStubPlayMode extends ModelStub {
        final ArrayList<Deck> decksAdded = new ArrayList<>();

        @Override
        public View getView() {
            return View.PLAY;
        }

        @Override
        public boolean hasDeck(Deck deck) {
            requireNonNull(deck);
            return decksAdded.stream().anyMatch(deck::equals);
        }

        @Override
        public void createDeck(Deck deck) {
            requireNonNull(deck);
            decksAdded.add(deck);
        }
    }

    /**
     * A Model stub that always accepts a card being added.
     */
    private class ModelStubWithDeck extends ModelStub {
        final ArrayList<Deck> decksAdded = new ArrayList<>();

        public ModelStubWithDeck(Deck deck) {
            decksAdded.add(deck);
        }

        @Override
        public View getView() {
            return View.DECK;
        }

        @Override
        public boolean hasDeck(Deck deck) {
            requireNonNull(deck);
            return decksAdded.stream().anyMatch(deck::equals);
        }

        @Override
        public void createDeck(Deck deck) {
            requireNonNull(deck);
            decksAdded.add(deck);
        }
    }
}

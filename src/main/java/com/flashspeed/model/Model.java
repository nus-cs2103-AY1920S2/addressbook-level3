package com.flashspeed.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import com.flashspeed.commons.core.GuiSettings;
import com.flashspeed.commons.core.index.Index;
import com.flashspeed.model.deck.Deck;
import com.flashspeed.model.deck.Name;
import com.flashspeed.model.deck.card.BackFace;
import com.flashspeed.model.deck.card.Card;
import com.flashspeed.model.util.View;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Deck> PREDICATE_SHOW_ALL_DECKS = unused -> true;

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' library file path.
     */
    Path getLibraryFilePath();

    /**
     * Sets the user prefs' library file path.
     */
    void setLibraryFilePath(Path libraryFilePath);

    /**
     * Replaces library data with the data in {@code library}.
     */
    void setLibrary(ReadOnlyLibrary library);

    /**
     * Returns the library
     * */
    ReadOnlyLibrary getLibrary();

    /**
     * Returns true if a deck with the same identity as {@code deck} exists in the library.
     */
    boolean hasDeck(Deck deck);

    /**
     * Deletes the given deck.
     * The deck must exist in the library.
     */
    void deleteDeck(Deck target);

    /**
     * Adds the given deck.
     * {@code deck} must not already exist in the library.
     */
    void createDeck(Deck deck);

    /**
     * Selects a deck.
     */
    void selectDeck(Index targetIdx);

    /**
     * Returns the current deck;
     */
    Deck getCurrentDeck();

    /**
     * Returns the deck at index of library;
     */
    Deck getDeck(Index index);

    /** Renames the deck at index in library.
     * @return true if there is no deck with the same name, false otherwise
     */
    boolean renameDeck(Index targetIndex, Name name);

    /**
     * Brings the user from deck view to library view.
     */
    void returnToLibrary();

    /**
     * Checks if a card with the same identity as {@code card} exists in the deck.
     * @return true if {@code card} exists in the deck
     */
    boolean hasCard(Card card);

    /**
     * Deletes the given card.
     * {@code target} must exist in the deck.
     */
    void deleteCard(Card target);

    /**
     * Adds the given card to the deck.
     * {@code card} must not already exist in the deck.
     */
    void addCard(Card card);

    /**
     * Replaces the given old card with the new card.
     * {@code target} must exist in the deck.
     * {@code card} must not already exist in the deck.
     */
    void replaceCard(Card target, Card card);

    /**
     * Gets the current view of the model.
     * @return The current view of the model.
     */
    View getView();

    /**
     * Replaces the given deck {@code target} with {@code editedDeck}.
     * {@code target} must exist in the library.
     * The deck identity of {@code editedDeck} must not be the same as another existing deck in the library.
     */
    void setDeck(Deck target, Deck editedDeck);

    /** Returns an unmodifiable view of the filtered deck list. */
    ObservableList<Deck> getFilteredDeckList();

    /** Returns an unmodifiable view of the filtered card list. */
    ObservableList<Card> getFilteredCardList();

    /**
     * Updates the filter of the filtered deck list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredDeckList(Predicate<Deck> predicate);


    /**
     * Returns the card with the given index.
     */
    Card getCard(Index index);

    /**
     * Starts a game session with a given deck index.
     * @param index index of the deck to play with.
     * @return a randomly selected card from the deck
     */
    Card play(Index index);

    //=========== Play View ======================================================================
    /**
     * Flips the card to the back face.
     * @return true if the card has not been flipped, false otherwise
     */
    BackFace flip();

    /**
     * Returns the next card after user answers Yes.
     * @return the next card or null if card list is empty
     */
    Card answerYes();

    /**
     * Returns the next card after user answers No.
     * @return the next card or null if card list is empty
     */
    Card answerNo();

    /**
     * Returns the game manager object.
     */
    GameManager getGame();

    /**
     * Stops the game session.
     * @return the statistics report.
     */
    Statistics stop();


    //=========== SimpleObjectProperty ======================================================================

    /**
     * Return selected Deck Read-only Property
     */
    ReadOnlyProperty<Deck> selectedDeckProperty();

    /**
     * Return the current View Read-only Property
     */
    ReadOnlyProperty<View> currentViewProperty();

    /**
     * Return the Card that is been playing Read-only Property
     */
    ReadOnlyProperty<Card> playingCardProperty();

    /**
     * Return the Flipped status Read-only Property
     */
    ReadOnlyProperty<Boolean> flippedProperty();

    /**
     * Return the no of Cards attempted Read-only Property
     */
    ReadOnlyProperty<Integer> cardAttemptedProperty();

    /**
     * Return the no of Cards remaining Read-only Property
     */
    ReadOnlyProperty<Integer> cardRemainingProperty();

    /**
     *      * Sets the selected deck Read-only Property
     */
    void setSelectedDeck(Deck deck);

    /**
     * Sets the value of currentView in Read-only Property
     * Toggle currentView between LIBRARY and PLAY only to switch between CardListPanel and PlayPanel
     */
    void setCurrentView(View view);

    /**
     * Sets the playing card Read-only property
     */
    void setPlayingCard(Card card);

    /**
     * Sets the value of flipped Read-only Property
     */
    void setFlipped(Boolean value);


    /**
     * Sets the number of cards attempted Read-only Property
     */
    void setCardAttempted(int value);

    /**
     * Sets the number of remaining cards Read-only Property
     */
    void setCardRemaining(int value);



}

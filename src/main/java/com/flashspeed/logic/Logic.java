package com.flashspeed.logic;

import java.nio.file.Path;

import com.flashspeed.commons.core.GuiSettings;
import com.flashspeed.logic.commands.CommandResult;
import com.flashspeed.logic.commands.exceptions.CommandException;
import com.flashspeed.logic.parser.exceptions.ParseException;
import com.flashspeed.model.ReadOnlyLibrary;
import com.flashspeed.model.deck.Deck;
import com.flashspeed.model.deck.card.Card;
import com.flashspeed.model.util.View;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;

/**
 * API of the Logic component
 */
public interface Logic {

    /**
     * Executes the command and returns the result
     * @param commandText The command as entered by the user
     * @return the result of the command execution
     * @throws CommandException If an error occurs during command execution
     * @throws ParseException If an error occurs during parsing
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns a Read-Only copy of the current Library
     */
    ReadOnlyLibrary getLibrary();

    /**
     * Returns an unmodifiable view of the filtered list of decks
     */
    ObservableList<Deck> getFilteredDeckList();

    /**
     * Returns the user prefs' library file path
     */
    Path getLibraryFilePath();

    /**
     * Returns the user prefs' GUI settings
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the currently selected Deck
     */
    Deck getCurrentDeck();

    /**
     * Returns the current View of the Model
     */
    View getView();

    /**
     * Sets selected deck in the Model
     * @param deck target deck
     */
    void setSelectedDeck(Deck deck);

    //===Read-Only Property components for UI==============================================

    /**
     * Returns the selected Deck Read-only Property
     */
    ReadOnlyProperty<Deck> selectedDeckProperty();

    /**
     * Returns the current View Read-only Property
     */
    ReadOnlyProperty<View> currentViewProperty();

    /**
     * Returns the currently playing Card Read-only Property
     */
    ReadOnlyProperty<Card> playingCardProperty();

    /**
     * Returns the current Card flipped status Read-Only Property
     */
    ReadOnlyProperty<Boolean> flippedProperty();

    /**
     * Returns the no of Cards attempted Read-Only Property
     */
    ReadOnlyProperty<Integer> cardAttemptedProperty();

    /**
     * Returns the no of Cards remaining Read-Only Property
     */
    ReadOnlyProperty<Integer> cardRemainingProperty();

}

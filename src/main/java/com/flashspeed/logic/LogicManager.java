package com.flashspeed.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import com.flashspeed.commons.core.GuiSettings;
import com.flashspeed.commons.core.LogsCenter;
import com.flashspeed.logic.commands.Command;
import com.flashspeed.logic.commands.CommandResult;
import com.flashspeed.logic.commands.exceptions.CommandException;
import com.flashspeed.logic.parser.MasterParser;
import com.flashspeed.logic.parser.exceptions.ParseException;
import com.flashspeed.model.Model;
import com.flashspeed.model.ReadOnlyLibrary;
import com.flashspeed.model.deck.Deck;
import com.flashspeed.model.deck.card.Card;
import com.flashspeed.model.util.View;
import com.flashspeed.storage.Storage;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;


/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final MasterParser masterParser;

    /**
     * Initializes LogicManager with the given Model and Storage.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        this.masterParser = new MasterParser();
    }

    /**
     * Executes the command and returns the result
     * @param commandText The command as entered by the user
     * @return the result of the command execution
     * @throws CommandException If an error occurs during command execution
     * @throws ParseException If an error occurs during parsing
     */
    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command;
        command = masterParser.parseCommand(commandText);
        commandResult = command.execute(model);
        try {
            storage.saveLibrary(model.getLibrary());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    /**
     * Returns a Read-Only copy of the current Library
     */
    @Override
    public ReadOnlyLibrary getLibrary() {
        return model.getLibrary();
    }

    /**
     * Returns an unmodifiable view of the filtered list of decks
     */
    @Override
    public ObservableList<Deck> getFilteredDeckList() {
        return model.getFilteredDeckList();
    }

    /**
     * Returns the user prefs' library file path
     */
    @Override
    public Path getLibraryFilePath() {
        return model.getLibraryFilePath();
    }

    /**
     * Returns the user prefs' GUI settings
     */
    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    /**
     * Sets the user prefs' GUI settings
     */
    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    /**
     * Returns the currently selected Deck
     */
    @Override
    public Deck getCurrentDeck() {
        return model.getCurrentDeck();
    }

    /**
     * Returns the current View of the Model
     */
    @Override
    public View getView() {
        return model.getView();
    }

    /**
     * Sets selected deck in the Model
     * @param deck target deck
     */
    @Override
    public void setSelectedDeck(Deck deck) {
        model.setSelectedDeck(deck);
    }

    //===Read-Only Property components for UI==============================================

    /**
     * Returns the selected Deck Read-only Property
     */
    @Override
    public ReadOnlyProperty<Deck> selectedDeckProperty() {
        return model.selectedDeckProperty();
    }

    /**
     * Returns the current View Read-only Property
     */
    @Override
    public ReadOnlyProperty<View> currentViewProperty() {
        return model.currentViewProperty();
    }

    /**
     * Returns the currently playing Card Read-only Property
     */
    @Override
    public ReadOnlyProperty<Card> playingCardProperty() {
        return model.playingCardProperty();
    }

    /**
     * Returns the current Card flipped status Read-Only Property
     */
    @Override
    public ReadOnlyProperty<Boolean> flippedProperty() {
        return model.flippedProperty();
    }

    /**
     * Returns the no of Cards attempted Read-Only Property
     */
    @Override
    public ReadOnlyProperty<Integer> cardAttemptedProperty() {
        return model.cardAttemptedProperty();
    }

    /**
     * Returns the no of Cards remaining Read-Only Property
     */
    @Override
    public ReadOnlyProperty<Integer> cardRemainingProperty() {
        return model.cardRemainingProperty();
    }

}

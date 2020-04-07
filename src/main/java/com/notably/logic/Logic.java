package com.notably.logic;

import java.nio.file.Path;

import com.notably.commons.GuiSettings;
import com.notably.logic.commands.exceptions.CommandException;
import com.notably.logic.parser.exceptions.ParseException;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    void execute(String commandText) throws CommandException, ParseException;

    /**
     * Edit the body of the current opened block.
     * @param bodyContent The content of the body entered by the user.
     */
    void editCurrentBlockBody(String bodyContent);

    /**
     * Gets the path of the block data file.
     */
    Path getBlockDataFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}

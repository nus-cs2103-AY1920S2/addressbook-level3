package com.notably.logic.commands;

import com.notably.logic.commands.exceptions.CommandException;
import com.notably.model.Model;

import javafx.application.Platform;

/**
 * Represent a command that exits the program.
 */
public class ExitCommand extends Command {
    public static final String COMMAND_WORD = "exit";

    @Override
    public void execute(Model notablyModel) throws CommandException {
        Platform.exit();
    }
}

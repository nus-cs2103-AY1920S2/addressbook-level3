package com.notably.logic.commands;

import static java.util.Objects.requireNonNull;

import com.notably.model.Model;

import javafx.application.Platform;

/**
 * Represent a command that exits the program.
 */
public class ExitCommand extends Command {
    public static final String COMMAND_WORD = "exit";

    @Override
    public void execute(Model notablyModel) {
        requireNonNull(notablyModel);
        Platform.exit();
    }
}

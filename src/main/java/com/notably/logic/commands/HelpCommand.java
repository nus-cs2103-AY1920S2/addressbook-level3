package com.notably.logic.commands;

import static java.util.Objects.requireNonNull;

import com.notably.model.Model;

/**
 * Represent a command that show/disables the help window.
 */
public class HelpCommand extends Command {
    public static final String COMMAND_WORD = "help";

    @Override
    public void execute(Model notablyModel) {
        requireNonNull(notablyModel);
        if (notablyModel.isHelpOpen()) {
            notablyModel.setHelpOpen(false);
        }
        notablyModel.setHelpOpen(true);
    }
}

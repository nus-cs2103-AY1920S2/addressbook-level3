package com.notably.logic.commands;

import com.notably.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Address Book as requested ...";

    @Override
    public void execute(Model model) {
        // TODO: Handle exit command execution in MainWindow.executeCommand()
    }

}

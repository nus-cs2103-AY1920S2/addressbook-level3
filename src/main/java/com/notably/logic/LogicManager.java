package com.notably.logic;

import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;

import com.notably.commons.core.GuiSettings;
import com.notably.commons.core.LogsCenter;
import com.notably.logic.commands.Command;
import com.notably.logic.commands.exceptions.CommandException;
import com.notably.logic.parser.NotablyParser;
import com.notably.logic.parser.exceptions.ParseException;
import com.notably.model.Model;
import com.notably.storage.Storage;


/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final NotablyParser notablyParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        notablyParser = new NotablyParser();
    }

    @Override
    public void execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        List<? extends Command> commands = notablyParser.parseCommand(commandText);
        for (Command command : commands) {
            command.execute(model);
        }
        //TODO: refactor to BlockStorage
        //  try {
        //      storage.saveAddressBook(model.getAddressBook());
        //  } catch (IOException ioe) {
        //      throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        //  }
    }

    //TODO: refactor this method to StoragePath.
    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}

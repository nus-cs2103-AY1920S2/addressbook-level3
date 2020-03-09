package NASA.logic;

import NASA.logic.parser.NasaBookParser;
import NASA.model.ModelNasa;
import javafx.collections.ObservableList;
import NASA.commons.core.GuiSettings;
import NASA.commons.core.LogsCenter;
import NASA.logic.commands.Command;
import NASA.logic.commands.CommandResult;
import NASA.logic.commands.exceptions.CommandException;
import NASA.logic.parser.NasaBookParser;
import NASA.logic.parser.exceptions.ParseException;
import NASA.model.Model;
import NASA.model.ReadOnlyNasaBook;
import NASA.model.module.Module;
import NASA.storage.Storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final ModelNasa model;
    private final Storage storage;
    private final NasaBookParser nasaBookParser;

    public LogicManager(ModelNasa model, Storage storage) {
        this.model = model;
        this.storage = storage;
        nasaBookParser = new NasaBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = nasaBookParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveNasaBook(model.getNasaBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyNasaBook getNasaBook() {
        return model.getNasaBook();
    }

    @Override
    public ObservableList<Module> getFilteredPersonList() {
        return model.getFilteredModuleList();
    }

    @Override
    public Path getNasaBookFilePath() {
        return model.getNasaBookFilePath();
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

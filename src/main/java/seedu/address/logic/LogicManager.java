package seedu.address.logic;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.autocomplete.Autocomplete;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.history.CommandHistory;
import seedu.address.logic.parser.FitBizParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyFitBiz;
import seedu.address.model.client.Client;
import seedu.address.model.schedule.ScheduleDay;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final FitBizParser fitBizParser;
    private final CommandHistory commandHistory;
    private final Autocomplete autoComplete;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        fitBizParser = new FitBizParser();
        commandHistory = new CommandHistory();
        autoComplete = new Autocomplete();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = fitBizParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveFitBiz(model.getFitBiz());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyFitBiz getFitBiz() {
        return model.getFitBiz();
    }

    @Override
    public ObservableList<Client> getFilteredClientList() {
        return model.getFilteredClientList();
    }

    @Override
    public Client getClientInView() {
        return model.getClientInView();
    }

    @Override
    public Boolean hasClientInView() {
        return model.hasClientInView();
    }

    @Override
    public Path getFitBizFilePath() {
        return model.getFitBizFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public ObservableList<ScheduleDay> getScheduleDayList() {
        return model.getScheduleDayList();
    }

    @Override
    public void openUrlInDefaultWebBrowser(String url) {
        String os = System.getProperty("os.name").toLowerCase().substring(0, 3);
        logger.info("External URL : redirecting user to " + url);

        switch (os) {
        case "win": // windows
        case "mac": // macOS
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                try {
                    Desktop.getDesktop().browse(new URI(url));
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
            }
            break;
        case "lin": // linux
        case "uni": // unix
            try {
                new ProcessBuilder("x-www-browser", url).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            break;
        default:
            logger.warning("External URL : failed to redirect user to " + url);
            break;
        }
    }

    @Override
    public CommandHistory getCommandHistory() {
        return commandHistory;
    }

    @Override
    public Autocomplete getAutocomplete() {
        return autoComplete;
    }
}

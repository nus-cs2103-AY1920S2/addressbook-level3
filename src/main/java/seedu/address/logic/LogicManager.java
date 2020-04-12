package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ModdyParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.CourseManager;
import seedu.address.model.ModuleManager;
import seedu.address.model.ProfileList;
import seedu.address.model.ProfileManager;
import seedu.address.model.profile.Profile;
import seedu.address.model.profile.course.module.exceptions.DateTimeException;
import seedu.address.model.profile.course.module.personal.Deadline;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final ProfileManager profileManager;
    private final Storage storage;
    private final ModdyParser moddyParser;
    private final CourseManager courseManager;
    private final ModuleManager moduleManager;

    public LogicManager(ProfileManager profileManager, Storage storage, CourseManager courseManager,
                        ModuleManager moduleManager) {
        this.profileManager = profileManager;
        this.storage = storage;
        this.courseManager = courseManager;
        this.moduleManager = moduleManager;
        moddyParser = new ModdyParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException, DateTimeException {
        //logging, safe to ignore
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        //parse user input from String to a Command
        Command command = moddyParser.parseCommand(commandText);
        //executes the Command and stores the result
        commandResult = command.execute(profileManager, courseManager, moduleManager);

        try {
            //can assume that previous line of code modifies model in some way
            //since it is being stored here
            //storage.saveAddressBook(model.getAddressBook());
            storage.saveProfileList(profileManager.getProfileList());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ProfileList getProfileList() {
        return profileManager.getProfileList();
    }

    /*@Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }*/

    @Override
    public ObservableList<Profile> getFilteredPersonList() {
        return profileManager.getFilteredPersonList();
    }

    @Override
    public Path getProfileListFilePath() {
        return profileManager.getProfileListFilePath();
    }

    /*@Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }*/

    @Override
    public GuiSettings getGuiSettings() {
        return profileManager.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        profileManager.setGuiSettings(guiSettings);
    }

    @Override
    public ObservableList<Deadline> getFilteredDeadlineList() {
        if (profileManager.getFilteredPersonList().size() == 1) { //profile exists
            if (profileManager.getFirstProfile().getCurModules() != null) {
                profileManager.loadDeadlines();
            }
        }
        System.out.println(profileManager.getSortedDeadlineList());
        return profileManager.getSortedDeadlineList();
    }

    @Override
    public Optional<Object> getDisplayedView() {
        return profileManager.getDisplayedView();
    }

    @Override
    public ProfileManager getProfileManager() {
        return profileManager;
    }

}

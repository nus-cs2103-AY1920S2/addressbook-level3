package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.SharkieParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserData;
import seedu.address.model.ReadOnlyWallet;
import seedu.address.model.UserData;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.User;
import seedu.address.model.transaction.Transaction;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final SharkieParser sharkieParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        sharkieParser = new SharkieParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]----------------");

        CommandResult commandResult;
        Command command = sharkieParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveAddressBook(model.getAddressBook());
            storage.saveWallet(model.getWallet());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public void storeUserData(String name, String phone, String email) throws IllegalArgumentException, IOException {
        if (!(Name.isValidName(name) && Phone.isValidPhone(phone) && Email.isValidEmail(email))) {
            throw new IllegalArgumentException();
        } else {
            User user = new User(new Name(name), new Phone(phone), new Email(email));
            UserData userData = new UserData(user);
            model.setUserData(userData);
            storage.saveUserData(userData);
        }
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableList<Transaction> getFilteredTransactionList() {
        return model.getFilteredTransactionList();
    }

    @Override
    public ObservableList<Transaction> getTransactionList() {
        return model.getTransactionList();
    }

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

    @Override
    public ReadOnlyUserData getUserData() {
        return model.getUserData();
    }

    @Override
    public void setUserData(UserData userData) {
        model.setUserData(userData);
    }

    @Override
    public boolean isUserDataNull() {
        return model.getUserData().isEmpty();
    }

    @Override
    public ReadOnlyWallet getWallet() {
        return model.getWallet();
    }

    @Override
    public Path getWalletFilePath() {
        return model.getWalletFilePath();
    }
}

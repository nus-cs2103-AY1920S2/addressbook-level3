package seedu.expensela.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.expensela.commons.core.GuiSettings;
import seedu.expensela.commons.core.LogsCenter;
import seedu.expensela.logic.commands.Command;
import seedu.expensela.logic.commands.CommandResult;
import seedu.expensela.logic.commands.exceptions.CommandException;
import seedu.expensela.logic.parser.ExpenseLaParser;
import seedu.expensela.logic.parser.exceptions.ParseException;
import seedu.expensela.model.Balance;
import seedu.expensela.model.Filter;
import seedu.expensela.model.Model;
import seedu.expensela.model.ReadOnlyExpenseLa;
import seedu.expensela.model.ToggleView;
import seedu.expensela.model.monthlydata.MonthlyData;
import seedu.expensela.model.transaction.Transaction;
import seedu.expensela.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final ExpenseLaParser expenseLaParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        expenseLaParser = new ExpenseLaParser();
    }

    @Override
    public String getCommandFromHistory(int offset) {
        return model.getCommandFromHistory(offset);
    }

    @Override
    public void addToCommandHistory(String command) {
        model.addToCommandHistory(command);
    }

    @Override
    public void deleteFromCommandHistory(String command) {
        model.deleteFromCommandHistory(command);
    }

    @Override
    public int getCommandHistorySize() {
        return model.getCommandHistorySize();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = expenseLaParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveExpenseLa(model.getExpenseLa());
            storage.saveGlobalData(model.getGlobalData());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyExpenseLa getExpenseLa() {
        return model.getExpenseLa();
    }

    @Override
    public ObservableList<Transaction> getFilteredTransactionList() {
        return model.getFilteredTransactionList();
    }

    @Override
    public MonthlyData getMonthlyData() {
        return model.getMonthlyData();
    }

    @Override
    public void setMonthlyData(MonthlyData monthlyData) {
        model.setMonthlyData(monthlyData);
    }

    @Override
    public Filter getFilter() {
        return model.getFilter();
    }

    @Override
    public void setFilter(Filter filter) {
        model.setFilter(filter);
    }

    @Override
    public boolean getIsFilterMonth() {
        return model.getIsFilterMonth();
    }

    @Override
    public ToggleView getToggleView() {
        return model.getToggleView();
    }

    @Override
    public Path getExpenseLaFilePath() {
        return model.getExpenseLaFilePath();
    }

    @Override
    public Path getGlobalDataFilePath() {
        return model.getGlobalDataFilePath();
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
    public Balance getTotalBalance() {
        return model.getTotalBalance();
    }

    @Override
    public void setTotalBalance(Balance totalBalance) {
        model.updateTotalBalance(totalBalance);
    }

}

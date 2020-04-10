package seedu.delino.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.delino.commons.core.GuiSettings;
import seedu.delino.commons.core.LogsCenter;
import seedu.delino.logic.commands.Command;
import seedu.delino.logic.commands.CommandResult;
import seedu.delino.logic.commands.exceptions.CommandException;
import seedu.delino.logic.parser.OrderBookParser;
import seedu.delino.logic.parser.exceptions.ParseException;
import seedu.delino.model.Model;
import seedu.delino.model.ReadOnlyOrderBook;
import seedu.delino.model.ReadOnlyReturnOrderBook;
import seedu.delino.model.parcel.order.Order;
import seedu.delino.model.parcel.returnorder.ReturnOrder;
import seedu.delino.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final OrderBookParser orderBookParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        orderBookParser = new OrderBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = orderBookParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveOrderBook(model.getOrderBook());
            //@@author Exeexe93
            storage.saveReturnOrderBook(model.getReturnOrderBook());
            //@@author
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyOrderBook getOrderBook() {
        return model.getOrderBook();
    }

    //@@author Exeexe93
    @Override
    public ReadOnlyReturnOrderBook getReturnOrderBook() {
        return model.getReturnOrderBook();
    }
    //@@author

    @Override
    public ObservableList<Order> getFilteredOrderList() {
        return model.getFilteredOrderList();
    }

    //@@author Exeexe93
    @Override
    public ObservableList<ReturnOrder> getFilteredReturnOrderList() {
        return model.getFilteredReturnOrderList();
    }
    //@@author

    @Override
    public Path getOrderBookFilePath() {
        return model.getOrderBookFilePath();
    }

    //@@author Exeexe93
    @Override
    public Path getReturnOrderBookFilePath() {
        return model.getReturnOrderBookFilePath();
    }
    //@@author

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public List<String> getStartUpMessages() {
        return model.getStartUpMessages();
    }

}

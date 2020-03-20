package seedu.eylah.expensesplitter.logic;

import java.io.IOException;
import java.util.logging.Logger;

import seedu.eylah.commons.core.LogsCenter;
import seedu.eylah.expensesplitter.logic.commands.Command;
import seedu.eylah.expensesplitter.logic.commands.CommandResult;
import seedu.eylah.expensesplitter.logic.commands.exceptions.CommandException;
import seedu.eylah.expensesplitter.logic.parser.ExpenseSplitterParser;
import seedu.eylah.expensesplitter.logic.parser.exceptions.ParseException;
import seedu.eylah.expensesplitter.model.Model;
import seedu.eylah.expensesplitter.storage.PersonAmountStorage;

/**
 * Logic Manager for ExpenseSpliter.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final PersonAmountStorage storage;
    private final ExpenseSplitterParser expenseSplitterParser;

    public LogicManager(Model model, PersonAmountStorage storage) {
        this.model = model;
        this.storage = storage;
        expenseSplitterParser = new ExpenseSplitterParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = expenseSplitterParser.parseCommand(commandText);
        commandResult = command.execute(model);


        try {
            storage.saveAddressBook(model.getAddressBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }



        return commandResult;
    }
}

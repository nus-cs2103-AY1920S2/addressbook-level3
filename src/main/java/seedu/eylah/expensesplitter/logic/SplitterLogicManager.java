package seedu.eylah.expensesplitter.logic;

import java.io.IOException;
import java.util.logging.Logger;

import seedu.eylah.commons.core.LogsCenter;
import seedu.eylah.commons.logic.command.Command;
import seedu.eylah.commons.logic.command.CommandResult;
import seedu.eylah.commons.logic.command.exception.CommandException;
import seedu.eylah.commons.logic.parser.exception.ParseException;
import seedu.eylah.expensesplitter.logic.parser.ExpenseSplitterParser;
import seedu.eylah.expensesplitter.model.SplitterModel;
import seedu.eylah.expensesplitter.storage.SplitterStorage;

/**
 * Logic Manager for ExpenseSplitter.
 */
public class SplitterLogicManager implements SplitterLogic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file";
    private final Logger logger = LogsCenter.getLogger(SplitterLogicManager.class);

    private final SplitterModel splitterModel;
    private final SplitterStorage splitterStorage;
    private final ExpenseSplitterParser expenseSplitterParser;

    public SplitterLogicManager(SplitterModel splitterModel, SplitterStorage splitterStorage) {
        this.splitterModel = splitterModel;
        this.splitterStorage = splitterStorage;
        expenseSplitterParser = new ExpenseSplitterParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command<SplitterModel> command = expenseSplitterParser.parseCommand(commandText);

        commandResult = command.execute(splitterModel);

        try {
            splitterStorage.savePersonAmountBook(splitterModel.getPersonAmountBook());
            splitterStorage.saveReceiptBook(splitterModel.getReceiptBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }
}

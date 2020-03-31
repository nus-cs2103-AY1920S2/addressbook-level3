package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.InsertCommandParser;
import seedu.address.logic.parser.ReturnCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;

/**
 * Import the csv file in order book.
 */
public class ImportCommand extends Command {

    public static final String OT_RETURN = "return";
    public static final String OT_ORDER = "order";

    public static final String COMMAND_WORD = "import";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Import the data in .csv file into Delino\n"
            + "Parameters: fileName.csv\n"
            + "Example: " + COMMAND_WORD + " orders.csv";

    private static final Logger logger = LogsCenter.getLogger(ImportCommand.class);

    private final List<String> ordersData;
    private int invalidCounter;
    private int processedOrderCounter;
    private int processedReturnOrderCounter;
    private int duplicateCounter;

    public ImportCommand(List<String> orderData) {
        requireNonNull(orderData);
        this.ordersData = orderData;
        this.invalidCounter = 0;
        this.processedOrderCounter = 0;
        this.processedReturnOrderCounter = 0;
        this.duplicateCounter = 0;
    }

    @Override
    public CommandResult execute(Model model) {

        requireNonNull(model);
        processData(model);
        return new CommandResult(printResult(processedOrderCounter, processedReturnOrderCounter, duplicateCounter,
                invalidCounter));
    }

    /**
     * Process the data, add either new order or return order into the model and increase the respective counter.
     * @param model model to be edited if encounter order and return for order type.
     */
    private void processData(Model model) {
        logger.fine("Processing the csv data...");
        for (String data : ordersData) {
            data = data.replaceAll(",", " ").stripTrailing();
            try {
                if (data.startsWith(OT_ORDER)) {
                    logger.fine("Passing data to InsertCommandParser for adding a new order");
                    new InsertCommandParser().parse(data.substring(5)).execute(model);
                    processedOrderCounter++;
                } else if (data.startsWith(OT_RETURN)) {
                    logger.fine("Passing data to ReturnCommandParser for adding a new return order");
                    new ReturnCommandParser().parse(data.substring(6)).execute(model);
                    processedReturnOrderCounter++;
                } else {
                    logger.info("Invalid order type encountered!" + data);
                    // Invalid order type
                    invalidCounter++;
                }
            } catch (ParseException pe) {
                logger.info("Failed to process the data: " + data);
                invalidCounter++;
            } catch (CommandException ce) {
                logger.info("Duplicate order or return order encountered: " + data);
                duplicateCounter++;
            }
        }
    }

    /**
     * Print the result based on the various counters.
     * @return message to pass back to user.
     */
    public static String printResult(int processedOrderCounter, int processedReturnOrderCounter, int duplicateCounter,
                               int invalidCounter) {
        logger.fine("Generating the message for the user...");
        String message = processedOrderCounter + " delivery order(s) and " + processedReturnOrderCounter
                + " return order(s) being imported.\n";
        if (duplicateCounter != 0) {
            message = message + duplicateCounter + " duplicate order(s) found!\n";
        }
        if (invalidCounter != 0) {
            message = message + invalidCounter + " invalid order(s) found!\n";
            message = message + "Please refer to the user guide for the correct format of the data in csv file.";
        }
        logger.fine("Result of importing the file: \n" + message);
        return message;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && ordersData.equals(((ImportCommand) other).ordersData));
    }
}

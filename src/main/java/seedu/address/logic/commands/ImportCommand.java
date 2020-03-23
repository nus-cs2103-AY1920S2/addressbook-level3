package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddCommandParser;
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

        for (String data : ordersData) {
            data = data.replaceAll(",", " ").stripTrailing();
            try {
                //int endOfOrderType = data.indexOf("tid/");
                //String orderType = data.substring(0, endOfOrderType).stripTrailing();

                //if (orderType.equals(OT_ORDER)) {
                if (data.startsWith(OT_ORDER)) {
                    //new AddCommandParser().parse(data.substring(endOfOrderType - 1)).execute(model);
                    new AddCommandParser().parse(data.substring(6)).execute(model);
                    processedOrderCounter++;
                    //} else if (orderType.equals(OT_RETURN)) {
                } else if (data.startsWith(OT_RETURN)) {
                    //new ReturnCommandParser().parse(data.substring(endOfOrderType - 1)).execute(model);
                    processedReturnOrderCounter++;
                } else {
                    // Invalid data type
                    invalidCounter++;
                }
            } catch (ParseException pe) {
                invalidCounter++;
            } catch (CommandException ce) {
                duplicateCounter++;
            }

        }

        return new CommandResult(printResult());
    }

    /**
     * Print the result based on the various counters.
     * @return message to pass back to user.
     */
    private String printResult() {
        String message = processedOrderCounter + " delivery order(s) and " + processedReturnOrderCounter
                + " return order(s) being imported.\n";
        if (duplicateCounter != 0) {
            message = message + duplicateCounter + " duplicate order(s) found!\n";
        }
        if (invalidCounter != 0) {
            message = message + invalidCounter + " invalid order(s) found!\n";
            message = message + "Please refer to the user guide for the correct format of the data in csv file.";
        }

        return message;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && ordersData.equals(((ImportCommand) other).ordersData));
    }
}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

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
                if (data.startsWith(OT_ORDER)) {
                    new InsertCommandParser().parse(data.substring(5)).execute(model);
                    processedOrderCounter++;
                } else if (data.startsWith(OT_RETURN)) {
                    new ReturnCommandParser().parse(data.substring(6)).execute(model);
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

        return new CommandResult(printResult(processedOrderCounter, processedReturnOrderCounter, duplicateCounter,
                invalidCounter));
    }

    /**
     * Print the result based on the various counters.
     * @return message to pass back to user.
     */
    public static String printResult(int processedOrderCounter, int processedReturnOrderCounter, int duplicateCounter,
                               int invalidCounter) {
        String message = processedOrderCounter + " delivery order(s) and " + processedReturnOrderCounter
                + " return order(s) being imported.\n";
        if (duplicateCounter != 0) {
            message = message + duplicateCounter + " duplicate order(s) found!\n";
        }
        if (invalidCounter != 0) {
            message = message + invalidCounter + " invalid order(s) found!\n";
            message = message + "Please refer to the user guide for the correct format of the data in csv file.";
        }
        System.out.println(message);
        return message;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && ordersData.equals(((ImportCommand) other).ordersData));
    }
}

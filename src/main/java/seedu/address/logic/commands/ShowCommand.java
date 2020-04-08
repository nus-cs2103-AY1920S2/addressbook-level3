package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.parcel.order.Order;
import seedu.address.model.parcel.returnorder.ReturnOrder;

/**
 * Show the courier his/her delivery statistics
 * with the given date provided in the command
 *
 * @author Amoscheong97
 */
public class ShowCommand extends Command {

    public static final String TODAY = "today";

    public static final String ALL = "all";

    public static final DateTimeFormatter FORMAT_CHECKER = DateTimeFormatter
            .ofPattern("uuuu-MM-dd").withResolverStyle(ResolverStyle.STRICT);

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MMMM dd, yyyy");

    public static final String COMMAND_WORD = "show";

    public static final String MESSAGE_USAGE = "Example 1: " + COMMAND_WORD + " START_DATE " + "END_DATE \n"
            + "START_DATE should be before or equals to the END_DATE \n"
            + "Example 2: " + COMMAND_WORD + " today \n"
            + "Example 3: " + COMMAND_WORD + " all";

    public static final String SHOW_MESSAGE = "Showing your delivery statistics";

    public static final String PARSE_DATE_ERROR_MESSAGE = "Please provide a valid date! \n";

    public static final String ONE_TODAY_IS_ENOUGH = "One 'today' argument is enough. No need for two \n";

    public static final String ILLEGAL_ARGUMENT = "Please provide only one or two dates. \n";

    public static final String WRONG_DATE_ORDER = "The Start Date should not be after than the End Date! \n";

    public static final String MESSAGE_ALL = " for all the lists";

    public static final String MESSAGE_INCLUSIVE = " from ";

    public static final String TO = " to ";

    public static final String MESSAGE_TODAY = " for " + TODAY;

    public static final String MESSAGE_ONE_DATE = " for ";

    public static final String ALL_DATES = "All Dates";

    private static LocalDate dateNow = LocalDate.now();

    private static LocalDate endDate;

    private static LocalDate startDate;

    private static boolean showAll;

    private String argument;

    private String intendedMessage;

    private boolean isCommandSuccessful;

    public ShowCommand(String arguments) {
        // Reset if it was initialized
        // previously
        showAll = false;

        String argumentTrimmed = arguments.trim();
        argument = argumentTrimmed;
        requireNonNull(argumentTrimmed);

        parseData(argumentTrimmed);
    }

    /**
     * Check if the arguments received is "today"
     * @param arguments String
     * @return boolean
     */
    public boolean isToday(String arguments) {
        return arguments.equals(TODAY);
    }

    /**
     * Check if the value for showAll is true.
     * It determines if the user wanted to see all the
     * orders in the list
     *
     * @return boolean
     */
    public static boolean isAll() {
        return showAll;
    }

    /**
     * Overloaded method that initializes the showAll variable
     * and returns true or false, depending on the user input
     * @param arguments String
     * @return boolean
     */
    public static boolean isAll(String arguments) {
        showAll = arguments.equals(ALL);
        return showAll;
    }

    /**
     * Return the startDate
     * @return LocalDate
     */
    public static LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Return the endDate
     * @return LocalDate
     */
    public static LocalDate getEndDate() {
        return endDate;
    }

    public static DateTimeFormatter getFormatter() {
        return DATE_FORMATTER;
    }
    /**
     * Make sense of the String that is parsed in
     * @param argText arguments parsed in
     */
    public void parseData(String argText) {
        if (isToday(argText)) {
            intendedMessage = SHOW_MESSAGE + MESSAGE_TODAY;
            isCommandSuccessful = true;
            startDate = dateNow;
            endDate = dateNow;
        } else if (isAll(argText)) {
            intendedMessage = SHOW_MESSAGE + MESSAGE_ALL;
            isCommandSuccessful = true;
        } else {
            String[] arrOfDate = argText.replaceAll("\\s+", " ").split("\\s");
            try {
                // Only accept one or two arguments
                if (arrOfDate[0].equals("") || arrOfDate.length > 2) {
                    throw new ParseException(ILLEGAL_ARGUMENT);
                }

                if (arrOfDate.length == 2) {
                    // `show today today` command is not allowed
                    if (arrOfDate[0].equals(arrOfDate[1]) && arrOfDate[0].equals(TODAY)) {
                        throw new ParseException(ONE_TODAY_IS_ENOUGH);
                    }
                }

                // check if it is today's date
                startDate = isToday(arrOfDate[0]) ? dateNow
                        : LocalDate.parse(arrOfDate[0], FORMAT_CHECKER);

                // Check the number of arguments provided
                // If only one date is provided, assign that particular
                // date to both startDate and endDate
                // Otherwise, assign the dates respectively when there
                // are two arguments
                endDate = (arrOfDate.length == 1)
                        ? startDate
                        : (isToday(arrOfDate[1])
                        ? dateNow
                        : LocalDate.parse(arrOfDate[1], FORMAT_CHECKER));

                // Check if the startDate is after the endDate
                if (startDate.compareTo(endDate) > 0) {
                    throw new ParseException(WRONG_DATE_ORDER);
                }

                // Check if the start date is equals to the end date
                // Specifically check if the dates provided are equal
                // and also check if the arguments or dates provided
                // is today.
                intendedMessage = startDate.compareTo(endDate) == 0
                        ? (startDate.compareTo(dateNow) == 0
                        ? SHOW_MESSAGE + MESSAGE_TODAY
                        : SHOW_MESSAGE
                        + MESSAGE_ONE_DATE
                        + startDate.format(DATE_FORMATTER))
                        : SHOW_MESSAGE + MESSAGE_INCLUSIVE
                        + startDate.format(DATE_FORMATTER)
                        + TO + endDate.format(DATE_FORMATTER);

                isCommandSuccessful = true;
            } catch (DateTimeParseException ex) {
                intendedMessage = PARSE_DATE_ERROR_MESSAGE + MESSAGE_USAGE;
                isCommandSuccessful = false;
            } catch (ParseException pex) {
                intendedMessage = pex.getMessage() + MESSAGE_USAGE;
                isCommandSuccessful = false;
            }
        }
    }

    /**
     * Overloaded method for Delivery Orders
     *
     * The method is used by the filter method to
     * filter the dates given by the user
     *
     * @param order Delivery order
     * @return boolean
     */
    public static boolean filterListByDates(Order order) {
        return showAll ? listAll() : isDateInclusive(order);
    }

    /**
     * Overloaded method for Return Orders
     *
     * The method is used by the filter method to
     * filter the dates given by the user
     *
     * @param order Return order
     * @return boolean
     */
    public static boolean filterListByDates(ReturnOrder order) {
        return showAll ? listAll() : isDateInclusive(order);
    }
    /**
     * Overloaded method for Delivery Order.
     * Checks if the Order is to within the start and end date
     * @param order Delivery Order to be delivered
     * @return boolean True if is within the date, false if not
     *
     */
    public static boolean isDateInclusive(Order order) {
        LocalDate date = order.getTimestamp().getOrderTimeStamp().toLocalDate();
        return date.compareTo(startDate) >= 0 && date.compareTo(endDate) <= 0;
    }

    /**
     * Overloaded method for Return Order.
     * Checks if the Order is to be returned today using the isBeforeDeadline() method.
     * @param order Return Order to be returned
     * @return boolean True if is Today's order, false if not
     *
     */
    public static boolean isDateInclusive(ReturnOrder order) {
        LocalDate date = order.getTimestamp().getOrderTimeStamp().toLocalDate();
        return date.compareTo(startDate) >= 0 && date.compareTo(endDate) <= 0;
    }

    /**
     * Get all the orders from both lists
     *
     * @return boolean Always return true
     */
    public static boolean listAll() {
        return true;
    }


    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(intendedMessage, false, false, false, isCommandSuccessful);
    }
}

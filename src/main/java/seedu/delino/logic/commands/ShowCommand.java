package seedu.delino.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.logging.Logger;

import seedu.delino.logic.parser.exceptions.ParseException;
import seedu.delino.model.Model;
import seedu.delino.model.parcel.order.Order;
import seedu.delino.model.parcel.returnorder.ReturnOrder;

//@@author Amoscheong97
/**
 * Show the courier his/her delivery statistics
 * with the given date provided in the command
 *
 * UI Design ideas from https://www.youtube.com/watch?v=UDi051XyQ-U&t=339s
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

    private static final Logger logger = Logger.getLogger(ShowCommand.class.getName());

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

        logger.fine("Parse data for validating arguments");
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
            logger.fine("Argument is today");
            setSuccessMessage(MESSAGE_TODAY);
            startDate = dateNow;
            endDate = dateNow;
        } else if (isAll(argText)) {
            logger.fine("Argument is all");
            setSuccessMessage(MESSAGE_ALL);
        } else {
            try {
                String[] arrOfDate = argText.replaceAll("\\s+", " ").split("\\s");

                checkValidInput(arrOfDate);
                initStartDate(arrOfDate[0]);
                initEndDate(arrOfDate);
                validateDates();
                initializeMessage();
            } catch (DateTimeParseException ex) {
                logger.info("Invalid date is in the input");
                setFailureMessage(PARSE_DATE_ERROR_MESSAGE);
            } catch (ParseException pex) {
                setFailureMessage(pex.getMessage());
            }
        }
    }

    /**
     * Validate the input given
     * @param arrOfDate
     * @throws ParseException
     */
    public void checkValidInput(String[] arrOfDate) throws ParseException {
        logger.fine("Check if the input is valid or not");
        illegalArguments(arrOfDate);
        checkNumberOfToday(arrOfDate);
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
        logger.fine("Filter list by the dates given");
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
        logger.fine("Filter list by the dates given");
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
        logger.fine("Check if the order timestamp is within the dates given");
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
        logger.fine("Check if the Return order timestamp is within the dates given");
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

    /**
     * Check for the number of arguments parsed in
     * @param dates Arr of dates
     * @throws ParseException
     *
     */
    public static void illegalArguments(String[] dates) throws ParseException {
        // Only accept one or two arguments
        if (dates[0].equals("") || dates.length > 2) {
            logger.info("There are either no arguments or more than 2 arguments");
            throw new ParseException(ILLEGAL_ARGUMENT);
        }
    }

    /**
     * If the user types in 'today'. Check that he or she only writes one today
     * @param dates
     * @throws ParseException
     *
     */
    public static void checkNumberOfToday(String[] dates) throws ParseException {
        if (dates.length == 2) {
            // `show today today` command is not allowed
            if (dates[0].equals(dates[1]) && dates[0].equals(TODAY)) {
                logger.info("User attempts to input more than 1 'today' ");
                throw new ParseException(ONE_TODAY_IS_ENOUGH);
            }
        }
    }

    /**
     * Initialize the startDate.
     * Also check if the date provided is today
     *
     * @param start
     */
    public void initStartDate(String start) {
        // Null value is not allowed for start date
        assert start != null;
        logger.fine("Initialize Start Date");
        // check if it is today's date
        startDate = isToday(start) ? dateNow
                : LocalDate.parse(start, FORMAT_CHECKER);
    }

    /** Check the number of arguments provided
     * If only one date is provided, assign that particular
     * date to both startDate and endDate
     * Otherwise, assign the dates respectively when there
     * are two arguments
     *
     * @param arrOfDate
     */
    public void initEndDate(String[] arrOfDate) {
        // Null value is not allowed for end date
        assert arrOfDate != null;
        logger.fine("Initialize End Date");
        // Checking the number of dates provided
        endDate = (arrOfDate.length == 1)
                ? startDate
                : (isToday(arrOfDate[1])
                ? dateNow
                : LocalDate.parse(arrOfDate[1], FORMAT_CHECKER));
    }

    /**
     * Compare startDate and endDate.
     * @throws ParseException startDate is after endDate
     */
    public static void validateDates() throws ParseException {
        // Check if the startDate is after the endDate
        if (startDate.compareTo(endDate) > 0) {
            logger.info("The Start Date is found to be after the End Date");
            throw new ParseException(WRONG_DATE_ORDER);
        }
    }

    /**
     * Set the message to indicate a success
     * @param successMessage
     */
    public void setSuccessMessage(String successMessage) {
        logger.fine("Set Success Message");
        intendedMessage = SHOW_MESSAGE + successMessage;
        isCommandSuccessful = true;
    }

    /**
     * Set the message to indicate a failure
     * @param failureMessage
     */
    public void setFailureMessage(String failureMessage) {
        logger.fine("Set Failure Message");
        intendedMessage = failureMessage + MESSAGE_USAGE;
        isCommandSuccessful = false;
    }
    /**
     * Initializes the correct message
     *
     * Check if the start date is equals to the end date
     * Specifically check if the dates provided are equal
     * and also check if the arguments or dates provided
     * is today.
     *
     */
    public void initializeMessage() {
        logger.fine("Begin to initialize the message for user");
        if (startDate.compareTo(endDate) == 0) {
            if (startDate.compareTo(dateNow) == 0) {
                setSuccessMessage(MESSAGE_TODAY);
            } else {
                setSuccessMessage(MESSAGE_ONE_DATE
                        + startDate.format(DATE_FORMATTER));
            }

        } else {
            setSuccessMessage(MESSAGE_INCLUSIVE
                    + startDate.format(DATE_FORMATTER)
                    + TO + endDate.format(DATE_FORMATTER));
        }
    }

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(intendedMessage, false, false, false, isCommandSuccessful);
    }
}

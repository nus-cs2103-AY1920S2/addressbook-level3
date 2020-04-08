package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;

/**
 * Show the courier his/her delivery statistics
 * with the given date provided in the command
 *
 * @author Amos Cheong Jit Hon
 */
public class ShowCommand extends Command {

    public static final String TODAY = "today";

    public static final String ALL = "all";

    public static final DateTimeFormatter FORMAT_CHECKER = DateTimeFormatter
            .ofPattern("uuuu-MM-dd").withResolverStyle(ResolverStyle.STRICT);

    public static final String COMMAND_WORD = "show";

    public static final String MESSAGE_USAGE = "Example 1: " + COMMAND_WORD + " START_DATE " + "END_DATE \n"
            + "START_DATE should be before or equals to the END_DATE \n"
            + "Example 2: " + COMMAND_WORD + " today \n"
            + "Example 3: " + COMMAND_WORD + " all";

    public static final String SHOW_MESSAGE = "Showing your delivery statistics";

    public static final String PARSE_DATE_ERROR_MESSAGE = "Please provide a valid date! \n";

    public static final String ILLEGAL_ARGUMENT = "Please provide only one or two dates. \n";

    public static final String WRONG_DATE_ORDER = "The Start Date should not be after than the End Date! \n";

    public static final String MESSAGE_ALL = " for all the lists";

    public static final String MESSAGE_INCLUSIVE = " within the given dates (including the start and end dates)";

    public static final String MESSAGE_TODAY = " for " + TODAY;

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
                if (arrOfDate[0].equals("") || arrOfDate.length > 2) {
                    throw new ParseException(ILLEGAL_ARGUMENT);
                }

                startDate = isToday(arrOfDate[0]) ? dateNow
                        : LocalDate.parse(arrOfDate[0], FORMAT_CHECKER);

                endDate = (arrOfDate.length == 1)
                        ? startDate
                        : (isToday(arrOfDate[1])
                        ? dateNow
                        : LocalDate.parse(arrOfDate[1], FORMAT_CHECKER));

                if (startDate.compareTo(endDate) > 0) {
                    throw new ParseException(WRONG_DATE_ORDER);
                }

                intendedMessage = startDate.compareTo(endDate) == 0
                        ? SHOW_MESSAGE + MESSAGE_TODAY
                        : SHOW_MESSAGE + MESSAGE_INCLUSIVE;

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

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(intendedMessage, false, false, false, isCommandSuccessful);
    }
}

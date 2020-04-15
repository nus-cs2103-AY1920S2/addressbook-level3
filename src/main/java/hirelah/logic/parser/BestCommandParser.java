package hirelah.logic.parser;

import static hirelah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static hirelah.logic.parser.CliSyntax.PREFIX_ATTRIBUTE;
import static hirelah.logic.parser.CliSyntax.PREFIX_METRIC;
import static hirelah.model.hirelah.BestParameter.ATTRIBUTE;
import static hirelah.model.hirelah.BestParameter.METRIC;
import static hirelah.model.hirelah.BestParameter.OVERALL;

import java.util.List;

import hirelah.logic.commands.BestCommand;
import hirelah.logic.parser.exceptions.ParseException;

/**
 * BestParser describes the behavior of the parser that is assigned
 * to parse the command for finding best interviewees.
 */

public class BestCommandParser implements Parser<BestCommand> {
    public static final String MESSAGE_MULTIPLE_PARAMETERS_PROVIDED = "Multiple parameters for comparisons provided.";
    public static final String MESSAGE_NON_POSITIVE_SIZE = "The size of the interviewees provided must be positive.";
    public static final String MESSAGE_SIZE_NOT_AN_INTEGER = "The size of the interviewees provided is not an integer.";

    /**
     * Parses the argument provided by the client.
     * @param arguments The argument entered by the client.
     * @return The corresponding BestCommand.
     * @throws ParseException If there is an invalid argument entered by the client.
     */

    public BestCommand parse(String arguments) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(arguments, PREFIX_METRIC, PREFIX_ATTRIBUTE);
        if (arguments.trim().equals("")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, BestCommand.MESSAGE_USAGE));
        } else {
            List<String> metricPrefixes = argMultimap.getAllValues(PREFIX_METRIC);
            List<String> attributePrefixes = argMultimap.getAllValues(PREFIX_ATTRIBUTE);

            int numOfParams = getNumberOfTotalParams(metricPrefixes, attributePrefixes);
            int numOfInterviewees = parseNumberOfInterviewees(argMultimap.getPreamble());

            if (numOfParams > 1) {
                throw new ParseException(MESSAGE_MULTIPLE_PARAMETERS_PROVIDED);
            } else if (numOfParams == 0) {
                return new BestCommand(numOfInterviewees, OVERALL);
            } else {
                if (metricPrefixes.size() == 1) {
                    return new BestCommand(numOfInterviewees, metricPrefixes.get(0), METRIC);
                } else {
                    return new BestCommand(numOfInterviewees, attributePrefixes.get(0), ATTRIBUTE);
                }
            }
        }
    }

    /**
     * Obtain the number of parameters a client provides.
     * @param metricPrefixes The metrics' prefixes list.
     * @param attributePrefixes The attributes' prefixes lsit.
     * @return The number of total parameters provided by the client.
     */
    private int getNumberOfTotalParams(List<String> metricPrefixes, List<String> attributePrefixes) {
        return metricPrefixes.size() + attributePrefixes.size();
    }

    /**
     * Parses the number of interviewees entered by the client to the corresponding integer.
     *
     * @param numberOfInterviewees The String representing the size of the interviewees entered by the client.
     * @return The corresponding integer value.
     * @throws ParseException If the value entered is not a number or the number is less than or equal to zero.
     */
    private int parseNumberOfInterviewees(String numberOfInterviewees) throws ParseException {
        try {
            int result = Integer.parseInt(numberOfInterviewees);
            if (result <= 0) {
                throw new ParseException(MESSAGE_NON_POSITIVE_SIZE);
            }

            return result;
        } catch (NumberFormatException e) {
            throw new ParseException(MESSAGE_SIZE_NOT_AN_INTEGER);
        }
    }
}

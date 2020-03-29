package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.NavigationQuestionCommand;
import seedu.address.logic.commands.NavigationTimeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input command and creates a new Command object
 */
public class NavigationCommandParser implements Parser<Command> {

    private static final String INPUT_WORD = "to";
    private static final String INPUT_FORMAT = INPUT_WORD + ": Navigate to a particular remark of an interviewee.\n"
            + "Includes:\n"
            + "navigating to a particular time of an interview with an interviewee\n"
            + "\tParameters: questionNumber\n"
            + "\tExample:  " + INPUT_WORD + " q10"
            + "navigating to the answer to a particular question from an interviewee\n"
            + "\tParameters: minutes.seconds of the interview\n"
            + "\tExample:  " + INPUT_WORD + " 30.00";

    /**
     * Parses the given {@code String} of commandWord in the context of the Command
     * and returns an Command object that will navigate to a certain part
     * of an interviewee's remark for execution.
     *
     * @param commandWord the command word to be parsed
     * @throws ParseException if the user input is not a valid command word
     */
    public Command parse(String commandWord) throws ParseException {

        final String navigationCommandWord = commandWord.trim().toLowerCase();

        Pattern question = Pattern.compile("q(?<questionNumber>[0-9]*\\S+)");
        Pattern time = Pattern.compile("(?<minutes>[0-9]+).(?<seconds>[0-9][0-9])");
        final Matcher questionMatcher = question.matcher(navigationCommandWord);
        final Matcher timeMatcher = time.matcher(navigationCommandWord);

        if (questionMatcher.matches()) {
            String questionNumber = questionMatcher.group("questionNumber");
            return new NavigationQuestionCommand(questionNumber);
        } else if (timeMatcher.matches()) {
            Integer minutes = Integer.parseInt(timeMatcher.group("minutes"));
            Integer seconds = Integer.parseInt(timeMatcher.group("seconds"));
            Duration minutesDuration = Duration.ofMinutes(minutes);
            Duration secondsDuration = Duration.ofSeconds(seconds);
            Duration query = minutesDuration.plus(secondsDuration);
            return new NavigationTimeCommand(query);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, INPUT_FORMAT));
        }
    }
}

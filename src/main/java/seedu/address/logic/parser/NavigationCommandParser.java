package seedu.address.logic.parser;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.NavigationQuestionCommand;
import seedu.address.logic.commands.NavigationTimeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

/**
 * Parses input command and creates a new NavigationQuestionCommand object
 */
public class NavigationCommandParser implements Parser<Command> {

    /**
     * Parses the given {@code String} of commandWord in the context of the ListCommand
     * and returns an ListCommand object for execution.
     *
     * @param commandWord the command word to be parsed
     * @throws ParseException if the user input is not a valid command word
     */
    public Command parse(String commandWord) throws ParseException {

        final String navigationCommandWord = commandWord.trim().toLowerCase();

        Pattern question = Pattern.compile("q(?<questionNumber>.+)");
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
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }

    }
}

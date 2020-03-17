package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.EditQuestionCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditQuestionCommand object
 */
public class EditQuestionCommandParser {
    private static final Pattern BASIC_EDIT_QUESTION_COMMAND_FORMAT =
            Pattern.compile("(?<questionNumber>\\S+)(?<newQuestion>.+)");

    /**
     * Parses the given {@code String} of arguments in the context of the EditQuestionCommand
     * and returns an EditQuestionCommand object for execution.
     *
     * @param arguments the arguments to be parsed
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditQuestionCommand parse(String arguments) throws ParseException {
        Matcher matcher = BASIC_EDIT_QUESTION_COMMAND_FORMAT.matcher(arguments.trim());

        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditQuestionCommand.MESSAGE_USAGE));
        }
        final String questionNumber = matcher.group("questionNumber");
        final String newQuestion = matcher.group("newQuestion");

        return new EditQuestionCommand(questionNumber.trim(), newQuestion.trim());
    }
}

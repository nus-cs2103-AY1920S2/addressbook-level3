package seedu.address.logic.parser;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditAttributeCommand;
import seedu.address.logic.commands.EditQuestionCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class EditQuestionCommandParser {
    private static final Pattern BASIC_EDIT_QUESTION_COMMAND_FORMAT = Pattern.compile("(?<questionNumber>\\S+)(?<newQuestion>.+)");

    public EditAttributeCommand parse(String arguments) throws ParseException {
        Matcher matcher = BASIC_EDIT_QUESTION_COMMAND_FORMAT.matcher(arguments.trim());

        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditQuestionCommand.MESSAGE_USAGE));
        }
        final String questionNumber = matcher.group("questionNumber");
        final String newQuestion = matcher.group("newQuestion");

        return new EditAttributeCommand(questionNumber.trim(), newQuestion.trim());
    }
}

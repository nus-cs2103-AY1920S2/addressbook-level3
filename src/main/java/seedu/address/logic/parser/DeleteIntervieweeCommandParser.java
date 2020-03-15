package seedu.address.logic.parser;

import seedu.address.logic.commands.AddIntervieweeCommand;
import seedu.address.logic.commands.DeleteIntervieweeCommand;
import seedu.address.logic.commands.DeleteQuestionCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class DeleteIntervieweeCommandParser {
    public DeleteIntervieweeCommand parse(String arguments) throws ParseException {
        return new DeleteIntervieweeCommand(arguments.trim());
    }
}

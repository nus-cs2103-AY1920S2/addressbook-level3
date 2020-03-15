package seedu.address.logic.parser;

import seedu.address.logic.commands.DeleteQuestionCommand;

public class DeleteQuestionCommandParser {
    public DeleteQuestionCommand parse(String arguments) {
        return new DeleteQuestionCommand(arguments.trim());
    }
}

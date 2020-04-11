package seedu.expensela.logic.parser;

import static seedu.expensela.commons.core.Messages.MESSAGE_FAILED_IMPORT;

import seedu.expensela.logic.commands.ImportCommand;
import seedu.expensela.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ImportCommand object
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ImportCommand
     * and returns a ImportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ImportCommand parse(String args) throws ParseException {
        try {
            String importFilePath = ParserUtil.parseImport(args);
            return new ImportCommand(importFilePath);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_FAILED_IMPORT, ImportCommand.MESSAGE_USAGE), pe);
        }
    }

}

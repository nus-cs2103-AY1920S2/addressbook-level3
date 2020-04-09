package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.ParserUtil.parseCsvFile;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author Exeexe93
/**
 * Parses input arguments and creates a new ImportCommand object
 */
public class ImportCommandParser implements Parser<ImportCommand> {
    private static String pattern = ".+\\.csv$";
    private static final Logger logger = LogsCenter.getLogger(ImportCommandParser.class);
    /**
     * Parses the given {@code String} of arguments in the context of the ImportCommand
     * and returns an ImportCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ImportCommand parse(String args) throws ParseException {
        requireNonNull(args);
        final String filePath = args.trim();

        if (!isValidInput(args)) {
            logger.info("No .csv file extension at the back of the string: " + args);
            throw new ParseException(MESSAGE_USAGE);
        }

        logger.info("Pass arguments to ParseUtil");
        return new ImportCommand(parseCsvFile(filePath));
    }

    /**
     * Check if the input string has the .csv extension behind.
     * @param input that user key in for the file name.
     * @return true of there are .csv extension behind, otherwise, return false.
     */
    private boolean isValidInput(String input) {
        logger.fine("Checking if the input with the \".csv\" extension.");
        return input.matches(pattern);
    }
}

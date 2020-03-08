package fithelper.logic.parser;

import static java.util.Objects.requireNonNull;
import static fithelper.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static fithelper.logic.parser.CliSyntax.PREFIX_REMARK;

import fithelper.commons.core.index.Index;
import fithelper.commons.exceptions.IllegalValueException;
import fithelper.logic.commands.RemarkCommand;
import fithelper.logic.parser.exceptions.ParseException;
import fithelper.model.person.Remark;

/**
 * Parses input arguments and creates a new {@code RemarkCommand} object
 */
public class RemarkCommandParser {

    /**
     * Parse an input string to a RemarkCommand
     *
     * @param args user input String
     * @return a RemarkCommand
     * @throws ParseException when the input is of invalid format
     */
    public RemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_REMARK);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RemarkCommand.MESSAGE_USAGE), ive);
        }

        //String remark = argMultimap.getValue(PREFIX_REMARK).orElse("");
        Remark remark = new Remark(argMultimap.getValue(PREFIX_REMARK).orElse(""));

        return new RemarkCommand(index, remark);
    }
}

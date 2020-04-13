package seedu.nova.logic.parser.abparsers;

import static java.util.Objects.requireNonNull;
import static seedu.nova.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.nova.commons.core.index.Index;
import seedu.nova.logic.commands.abcommands.AbRemarkCommand;
import seedu.nova.logic.parser.ArgumentMultimap;
import seedu.nova.logic.parser.ArgumentTokenizer;
import seedu.nova.logic.parser.CliSyntax;
import seedu.nova.logic.parser.Parser;
import seedu.nova.logic.parser.ParserUtil;
import seedu.nova.logic.parser.exceptions.ParseException;
import seedu.nova.model.person.Remark;

/**
 * Parses input arguments and creates a new {@code AbRemarkCommand} object
 */
public class AbRemarkCommandParser implements Parser<AbRemarkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code AbRemarkCommand}
     * and returns a {@code AbRemarkCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AbRemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                CliSyntax.PREFIX_INDEX, CliSyntax.PREFIX_REMARK);

        Index index;

        if (argMultimap.getValue(CliSyntax.PREFIX_INDEX).isPresent()) {
            index = ParserUtil.parseIndex(argMultimap.getValue(CliSyntax.PREFIX_INDEX).get());
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AbRemarkCommand.MESSAGE_USAGE));
        }

        String value = argMultimap.getValue(CliSyntax.PREFIX_REMARK).orElse("");
        Remark remark = new Remark(value);

        return new AbRemarkCommand(index, remark);
    }
}

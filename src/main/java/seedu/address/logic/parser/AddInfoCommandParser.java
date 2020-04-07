package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import java.util.ArrayList;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddInfoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Remark;

/**
 * Parses input arguments and creates a new {@code AddInfoCommand} object
 */
public class AddInfoCommandParser implements Parser<AddInfoCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code AddInfoCommand}
     * and returns a {@code AddInfoCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddInfoCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_REMARK);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddInfoCommand.MESSAGE_USAGE), ive);
        }

        if (!argMultimap.getValue(PREFIX_REMARK).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddInfoCommand.MESSAGE_USAGE));
        }

        ArrayList<Remark> remark = ParserUtil.parseRemarks(argMultimap.getAllValues(PREFIX_REMARK));

        return new AddInfoCommand(index, remark);
    }

}

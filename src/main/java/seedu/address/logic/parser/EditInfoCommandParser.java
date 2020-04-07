package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_NO_LINE_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINE_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditInfoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Remark;

/**
 * Parses input arguments and creates a new {@code EditInfoCommand} object
 */
public class EditInfoCommandParser implements Parser<EditInfoCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code EditInfoCommand}
     * and returns a {@code EditInfoCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditInfoCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_LINE_NUMBER, PREFIX_REMARK);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditInfoCommand.MESSAGE_USAGE), ive);
        }

        if (!argMultimap.getValue(PREFIX_LINE_NUMBER).isPresent() || argMultimap
                .getAllValues(PREFIX_LINE_NUMBER).get(0).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_NO_LINE_NUMBER, EditInfoCommand.MESSAGE_USAGE));
        }
        int line = Integer.parseInt(argMultimap.getAllValues(PREFIX_LINE_NUMBER).get(0));
        String remark = argMultimap.getValue(PREFIX_REMARK).orElse("");

        return new EditInfoCommand(index, line, new Remark(remark));
    }

}

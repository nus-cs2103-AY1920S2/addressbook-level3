package NASA.logic.parser;

import NASA.commons.core.index.Index;
import NASA.logic.commands.EditModuleCommand;
import NASA.logic.parser.exceptions.ParseException;
import static NASA.logic.parser.CliSyntax.PREFIX_MODULE;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class EditModuleCommandParser {

    //TODO modify implementation
    public EditModuleCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditModuleCommand.MESSAGE_USAGE),
                    pe);
        }

        return new EditModuleCommand(index);
    }
}

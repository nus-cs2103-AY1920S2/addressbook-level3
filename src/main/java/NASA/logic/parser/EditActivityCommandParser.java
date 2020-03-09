package NASA.logic.parser;

import NASA.commons.core.index.Index;
import NASA.logic.commands.EditActivityCommand;
import NASA.logic.parser.exceptions.ParseException;
import NASA.model.module.ModuleCode;

import static NASA.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static NASA.logic.parser.CliSyntax.PREFIX_MODULE;
import static java.util.Objects.requireNonNull;

public class EditActivityCommandParser {

    //TODO add implementation
    public EditActivityCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditActivityCommand.MESSAGE_USAGE),
                    pe);
        }

        ModuleCode moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE).get());
        return new EditActivityCommand(index, moduleCode);
    }
}

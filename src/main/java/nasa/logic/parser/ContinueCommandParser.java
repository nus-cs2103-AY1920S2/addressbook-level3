package nasa.logic.parser;

import static java.util.Objects.requireNonNull;
import static nasa.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nasa.logic.parser.CliSyntax.PREFIX_MODULE;

import nasa.commons.core.index.Index;
import nasa.logic.commands.ContinueCommand;
import nasa.logic.parser.exceptions.ParseException;
import nasa.model.module.ModuleCode;

public class ContinueCommandParser implements Parser<ContinueCommand> {

    public ContinueCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_MODULE);

        Index index;
        ModuleCode moduleCode;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE).get());
            return new ContinueCommand(index, moduleCode);
        } catch (Exception e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ContinueCommand.MESSAGE_USAGE));
        }
    }
}

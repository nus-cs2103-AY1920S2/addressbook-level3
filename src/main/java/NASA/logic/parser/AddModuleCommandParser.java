package NASA.logic.parser;

import NASA.logic.commands.AddModuleCommand;
import NASA.logic.parser.exceptions.ParseException;

import NASA.logic.parser.ArgumentMultimap;
import NASA.logic.parser.ArgumentTokenizer;
import NASA.logic.parser.ParserUtil;
import NASA.logic.parser.exceptions.ParseException;

import NASA.model.module.Module;
import NASA.model.module.ModuleName;
import NASA.model.module.ModuleCode;

import static NASA.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.stream.Stream;

import static NASA.logic.parser.CliSyntax.PREFIX_MODULE;
import static NASA.logic.parser.CliSyntax.PREFIX_MODULE_NAME;

public class AddModuleCommandParser implements Parser<AddModuleCommand> {

    @Override
    public AddModuleCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE, PREFIX_MODULE_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE, PREFIX_MODULE_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddModuleCommand.MESSAGE_USAGE));
        }

        ModuleCode moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE).get());
        ModuleName moduleName = ParserUtil.parseModuleName(argMultimap.getValue(PREFIX_MODULE_NAME).get());
        Module module = new Module(moduleCode, moduleName);
        return new AddModuleCommand(module);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

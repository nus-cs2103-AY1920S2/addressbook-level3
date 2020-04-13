package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.logic.commands.modulecommand.AddModuleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.nusmodule.Grade;
import seedu.address.model.nusmodule.ModuleCode;
import seedu.address.model.nusmodule.NusModule;
import seedu.address.searcher.Module;
import seedu.address.searcher.Search;

/**
 * Parses input arguments and creates a new AddModuleCommand object
 */
public class AddModuleCommandParser implements Parser<AddModuleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddModuleCommand
     * and returns an AddModuleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddModuleCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap1 =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE_CODE, PREFIX_GRADE);

        ArgumentMultimap argMultimap2 =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE_CODE);

        if (arePrefixesPresent(argMultimap1, PREFIX_MODULE_CODE, PREFIX_GRADE)
                && argMultimap1.getPreamble().isEmpty()) {

            ModuleCode moduleCode = ParserUtil.parseModuleCode(
                    argMultimap1.getValue(PREFIX_MODULE_CODE).get());
            Module moduleInfo = Search.findModule(moduleCode.toString());
            int moduleCredit = moduleInfo.getCredits();

            Grade grade = ParserUtil.parseGrade(argMultimap1.getValue(PREFIX_GRADE).get());

            NusModule module = new NusModule(moduleCode, moduleCredit,
                    Optional.of(grade), new ArrayList<>());

            return new AddModuleCommand(module);

        } else if (arePrefixesPresent(argMultimap2, PREFIX_MODULE_CODE)
                && argMultimap2.getPreamble().isEmpty()) {

            ModuleCode moduleCode = ParserUtil.parseModuleCode(
                    argMultimap2.getValue(PREFIX_MODULE_CODE).get());
            Module moduleInfo = Search.findModule(moduleCode.toString());
            int moduleCredit = moduleInfo.getCredits();

            NusModule module = new NusModule(moduleCode, moduleCredit,
                    Optional.empty(), new ArrayList<>());

            return new AddModuleCommand(module);

        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddModuleCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

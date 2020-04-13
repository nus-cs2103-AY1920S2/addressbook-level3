package seedu.address.logic.parser.mods;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_REPEATED_PREFIXES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_LINK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_LINK_NAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.mods.AddModLinkCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.mod.ModCode;
import seedu.address.model.mod.ModLink;

/**
 * Parses input arguments and creates a new AddModLinkCommand object
 */

public class AddModLinkCommandParser implements Parser<AddModLinkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddModLinkCommand
     * and returns an AddModLinkCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddModLinkCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_MODULE_CODE, PREFIX_MODULE_LINK, PREFIX_MODULE_LINK_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE_CODE, PREFIX_MODULE_LINK, PREFIX_MODULE_LINK_NAME)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddModLinkCommand.MESSAGE_USAGE));
        }

        if (!arePrefixesUnique(argMultimap, PREFIX_MODULE_CODE, PREFIX_MODULE_LINK, PREFIX_MODULE_LINK_NAME)) {
            throw new ParseException(String.format(MESSAGE_REPEATED_PREFIXES,
                    AddModLinkCommand.MESSAGE_USAGE));
        }

        ModCode modCode = ParserUtil.parseModCode(argMultimap.getValue(PREFIX_MODULE_CODE).get());
        String modLinkName = argMultimap.getValue(PREFIX_MODULE_LINK_NAME).get();
        ModLink link = ParserUtil.parseModLink(argMultimap.getValue(PREFIX_MODULE_LINK).get());


        return new AddModLinkCommand(modCode, modLinkName, link);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if at least one of the prefixes is repeated in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesUnique(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).filter(argumentMultimap::isRepeated).count() == 0;
    }
}

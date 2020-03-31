package seedu.address.logic.parser;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteSupplierCommand;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOOD_NAME;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.good.GoodName;

/**
 * Parses input arguments and creates a new DeleteSupplierCommand object
 */
public class DeleteCommandParser implements Parser<DeleteSupplierCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteSupplierCommand
     * and returns a DeleteSupplierCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteSupplierCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_GOOD_NAME);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteSupplierCommand.MESSAGE_USAGE), pe);
        }

        DeleteSupplierCommand.DeleteSupplierGoodName deleteSupplierGoodName =
                new DeleteSupplierCommand.DeleteSupplierGoodName();

        parseGoodNamesForEdit(argMultimap.getAllValues(PREFIX_GOOD_NAME))
                .ifPresent(deleteSupplierGoodName::setGoodNames);

        return new DeleteSupplierCommand(index, deleteSupplierGoodName);
    }

    /**
     * Parses {@code Collection<String> goodNames} into a {@code Set<GoodName>} if {@code goodNames} is non-empty.
     */
    private Optional<Set<GoodName>> parseGoodNamesForEdit(Collection<String> goodNames) throws ParseException {
        assert goodNames != null;

        if (goodNames.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> goodNameList = goodNames.size() == 1 && goodNames.contains("") ? Collections.emptyList() :
                goodNames;
        return Optional.of(ParserUtil.parseGoodNames(goodNameList));
    }

}

package seedu.expensela.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.expensela.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.expensela.logic.parser.CliSyntax.*;

import seedu.expensela.commons.core.index.Index;
import seedu.expensela.logic.commands.EditCommand;
import seedu.expensela.logic.commands.EditCommand.editTransaction;
import seedu.expensela.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_AMOUNT, PREFIX_DATE, PREFIX_REMARK, PREFIX_CATEGORY);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditCommand.editTransaction editTransaction = new editTransaction();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editTransaction.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
            editTransaction.setAmount(ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get()));
        }
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            editTransaction.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_REMARK).isPresent()) {
            editTransaction.setRemark(ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK).get()));
        }
        if (argMultimap.getValue(PREFIX_CATEGORY).isPresent()) {
            editTransaction.setCategory(ParserUtil.parseCategory(argMultimap.getValue(PREFIX_CATEGORY).get()));
        }

        if (!editTransaction.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editTransaction);
    }

}

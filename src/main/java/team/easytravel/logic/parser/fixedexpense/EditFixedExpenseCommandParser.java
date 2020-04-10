package team.easytravel.logic.parser.fixedexpense;

import static java.util.Objects.requireNonNull;
import static team.easytravel.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_CURRENCY;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import team.easytravel.commons.core.index.Index;
import team.easytravel.logic.commands.fixedexpense.EditFixedExpenseCommand;
import team.easytravel.logic.parser.ArgumentMultimap;
import team.easytravel.logic.parser.ArgumentTokenizer;
import team.easytravel.logic.parser.Parser;
import team.easytravel.logic.parser.ParserUtil;
import team.easytravel.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditFixedExpenseCommand.
 */
public class EditFixedExpenseCommandParser implements Parser<EditFixedExpenseCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditFixedExpenseCommand
     * and returns an EditFixedExpenseCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditFixedExpenseCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_AMOUNT, PREFIX_CURRENCY, PREFIX_DESCRIPTION, PREFIX_CATEGORY);

        Index index;
        boolean isOverseasAmount = false;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditFixedExpenseCommand.MESSAGE_USAGE), pe);
        }

        EditFixedExpenseCommand.EditFixedExpenseDescriptor editFixedExpenseDescriptor =
                new EditFixedExpenseCommand.EditFixedExpenseDescriptor();

        if (argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {

            if (argMultimap.getValue(PREFIX_CURRENCY).isEmpty()) {
                throw new ParseException(EditFixedExpenseCommand.MESSAGE_CURRENCY_NOT_PRESENT);
            } else if (argMultimap.getValue(PREFIX_CURRENCY).get().toLowerCase().equals("sgd")) {
                editFixedExpenseDescriptor.setAmount(ParserUtil
                        .parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get()));
            } else if (argMultimap.getValue(PREFIX_CURRENCY).get().toLowerCase().equals("other")) {
                isOverseasAmount = true;
                editFixedExpenseDescriptor.setAmount(ParserUtil
                        .parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get()));
            } else {
                throw new ParseException(EditFixedExpenseCommand.MESSAGE_INVALID_CURRENCY);
            }


        }
        if (argMultimap.getValue(PREFIX_CATEGORY).isPresent()) {
            editFixedExpenseDescriptor.setFixedExpenseCategory(ParserUtil
                    .parseFixedExpenseCategory(argMultimap.getValue(PREFIX_CATEGORY).get()));
        }
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editFixedExpenseDescriptor.setDescription(ParserUtil
                    .parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }

        if (!editFixedExpenseDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditFixedExpenseCommand.MESSAGE_NOT_EDITED);
        }

        return new EditFixedExpenseCommand(index, editFixedExpenseDescriptor, isOverseasAmount);
    }
}

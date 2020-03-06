package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PORTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditDayDescriptor;
import seedu.address.logic.commands.NomCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class NomCommandParser implements Parser<NomCommand> {

    public static final String COMMAND_WORD = "nom";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    public NomCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DATE, PREFIX_PORTION);

        Index index;

        // we need to have something to parse for date
        EditDayDescriptor editDayDescriptor = new EditDayDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editDayDescriptor.addToDailyFoodLog(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }

    }

}

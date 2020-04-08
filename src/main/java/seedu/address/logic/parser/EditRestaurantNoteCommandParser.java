package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_NO_LINE_NUMBER_BAD;
import static seedu.address.commons.core.Messages.MESSAGE_NO_LINE_NUMBER_GOOD;
import static seedu.address.commons.core.Messages.MESSAGE_NO_LINE_NUMBER_REC;
import static seedu.address.commons.core.Messages.MESSAGE_NO_LINE_NUMBER_RESTAURANT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BAD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINE_NUMBER_BAD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINE_NUMBER_GOOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINE_NUMBER_RECOMMENDED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECOMMENDED;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditRestaurantNoteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.restaurant.Note;

/**
 * Parses input arguments and creates a new {@code EditRestaurantNoteCommand} object
 */
public class EditRestaurantNoteCommandParser implements Parser<EditRestaurantNoteCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code EditRestaurantNoteCommand}
     * and returns a {@code EditRestaurantNoteCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditRestaurantNoteCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_LINE_NUMBER_RECOMMENDED, PREFIX_RECOMMENDED, PREFIX_LINE_NUMBER_GOOD, PREFIX_GOOD,
                PREFIX_LINE_NUMBER_BAD, PREFIX_BAD);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditRestaurantNoteCommand.MESSAGE_USAGE), ive);
        }

        //There should be at least one line number indicated for the relevant food notes.
        if (!argMultimap.getValue(PREFIX_LINE_NUMBER_RECOMMENDED).isPresent()
                && !argMultimap.getValue(PREFIX_LINE_NUMBER_GOOD).isPresent()
                && !argMultimap.getValue(PREFIX_LINE_NUMBER_BAD).isPresent()) {
            throw new ParseException(String.format(MESSAGE_NO_LINE_NUMBER_RESTAURANT,
                    EditRestaurantNoteCommand.MESSAGE_USAGE));
        }

        int lineRec = -1;
        String recommendedFood = "";
        //Checks if user wants to edit recommended food
        if (argMultimap.getValue(PREFIX_LINE_NUMBER_RECOMMENDED).isPresent()) {
            //Checks if line number is provided
            if (argMultimap.getAllValues(PREFIX_LINE_NUMBER_RECOMMENDED).get(0).isEmpty()) {
                throw new ParseException(String.format(MESSAGE_NO_LINE_NUMBER_REC,
                        EditRestaurantNoteCommand.MESSAGE_USAGE));
            } else if (!argMultimap.getValue(PREFIX_RECOMMENDED).isPresent()
                    || (argMultimap.getValue(PREFIX_RECOMMENDED).isPresent()
                    && argMultimap.getAllValues(PREFIX_RECOMMENDED).get(0).isEmpty())) {
                //Checks if recommended food is provided
                throw new ParseException(EditRestaurantNoteCommand.MESSAGE_EMPTY_REC);
            } else {
                lineRec = Integer.parseInt(argMultimap.getValue(PREFIX_LINE_NUMBER_RECOMMENDED).get());
                recommendedFood = argMultimap.getValue(PREFIX_RECOMMENDED).get();
            }
        }

        int lineGood = -1;
        String goodFood = "";
        //Checks if user wants to edit good food
        if (argMultimap.getValue(PREFIX_LINE_NUMBER_GOOD).isPresent()) {
            //Checks if line number is provided
            if (argMultimap.getAllValues(PREFIX_LINE_NUMBER_GOOD).get(0).isEmpty()) {
                throw new ParseException(String.format(MESSAGE_NO_LINE_NUMBER_GOOD,
                        EditRestaurantNoteCommand.MESSAGE_USAGE));
            } else if (!argMultimap.getValue(PREFIX_GOOD).isPresent()
                    || (argMultimap.getValue(PREFIX_GOOD).isPresent()
                    && argMultimap.getAllValues(PREFIX_GOOD).get(0).isEmpty())) {
                //Checks if good food is provided
                throw new ParseException(EditRestaurantNoteCommand.MESSAGE_EMPTY_GOOD);
            } else {
                lineGood = Integer.parseInt(argMultimap.getValue(PREFIX_LINE_NUMBER_GOOD).get());
                goodFood = argMultimap.getValue(PREFIX_GOOD).get();
            }
        }

        int lineBad = -1;
        String badFood = "";
        //Checks if user wants to edit bad food
        if (argMultimap.getValue(PREFIX_LINE_NUMBER_BAD).isPresent()) {
            //Checks if line number is empty
            if (argMultimap.getAllValues(PREFIX_LINE_NUMBER_BAD).get(0).isEmpty()) {
                throw new ParseException(String.format(MESSAGE_NO_LINE_NUMBER_BAD,
                        EditRestaurantNoteCommand.MESSAGE_USAGE));
            } else if (!argMultimap.getValue(PREFIX_BAD).isPresent()
                    || (argMultimap.getValue(PREFIX_BAD).isPresent()
                    && argMultimap.getAllValues(PREFIX_BAD).get(0).isEmpty())) {
                //Checks if bad food is empty
                throw new ParseException(EditRestaurantNoteCommand.MESSAGE_EMPTY_BAD);
            } else {
                lineBad = Integer.parseInt(argMultimap.getValue(PREFIX_LINE_NUMBER_BAD).get());
                badFood = argMultimap.getValue(PREFIX_BAD).get();
            }
        }

        return new EditRestaurantNoteCommand(index, lineRec, lineGood, lineBad,
                new Note(recommendedFood), new Note(goodFood), new Note(badFood));
    }

}

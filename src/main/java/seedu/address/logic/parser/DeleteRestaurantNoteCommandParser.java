package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_NO_LINE_NUMBER_BAD;
import static seedu.address.commons.core.Messages.MESSAGE_NO_LINE_NUMBER_GOOD;
import static seedu.address.commons.core.Messages.MESSAGE_NO_LINE_NUMBER_REC;
import static seedu.address.commons.core.Messages.MESSAGE_NO_LINE_NUMBER_RESTAURANT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINE_NUMBER_BAD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINE_NUMBER_GOOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINE_NUMBER_RECOMMENDED;

import java.util.ArrayList;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteRestaurantNoteCommand;
import seedu.address.logic.commands.EditRestaurantNoteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code DeleteRestaurantNoteCommand} object
 */
public class DeleteRestaurantNoteCommandParser implements Parser<DeleteRestaurantNoteCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code DeleteRestaurantNoteCommand}
     * and returns a {@code DeleteRestaurantNoteCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteRestaurantNoteCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_LINE_NUMBER_RECOMMENDED, PREFIX_LINE_NUMBER_GOOD, PREFIX_LINE_NUMBER_BAD);

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
                    DeleteRestaurantNoteCommand.MESSAGE_USAGE));
        }

        ArrayList<Integer> lineRec = new ArrayList<>();
        //Checks if user wants to edit recommended food
        if (argMultimap.getValue(PREFIX_LINE_NUMBER_RECOMMENDED).isPresent()) {
            //Checks if line number is provided
            if (argMultimap.getAllValues(PREFIX_LINE_NUMBER_RECOMMENDED).get(0).isEmpty()) {
                throw new ParseException(String.format(MESSAGE_NO_LINE_NUMBER_REC,
                        DeleteRestaurantNoteCommand.MESSAGE_USAGE));
            } else {
                lineRec = ParserUtil.parseLines(argMultimap.getAllValues(PREFIX_LINE_NUMBER_RECOMMENDED));
            }
        }

        ArrayList<Integer> lineGood = new ArrayList<>();
        //Checks if user wants to edit good food
        if (argMultimap.getValue(PREFIX_LINE_NUMBER_GOOD).isPresent()) {
            //Checks if line number is provided
            if (argMultimap.getAllValues(PREFIX_LINE_NUMBER_GOOD).get(0).isEmpty()) {
                throw new ParseException(String.format(MESSAGE_NO_LINE_NUMBER_GOOD,
                        DeleteRestaurantNoteCommand.MESSAGE_USAGE));
            } else {
                lineGood = ParserUtil.parseLines(argMultimap.getAllValues(PREFIX_LINE_NUMBER_GOOD));
            }
        }

        ArrayList<Integer> lineBad = new ArrayList<>();
        //Checks if user wants to edit bad food
        if (argMultimap.getValue(PREFIX_LINE_NUMBER_BAD).isPresent()) {
            //Checks if line number is provided
            if (argMultimap.getAllValues(PREFIX_LINE_NUMBER_BAD).get(0).isEmpty()) {
                throw new ParseException(String.format(MESSAGE_NO_LINE_NUMBER_BAD,
                        DeleteRestaurantNoteCommand.MESSAGE_USAGE));
            } else {
                lineBad = ParserUtil.parseLines(argMultimap.getAllValues(PREFIX_LINE_NUMBER_BAD));
            }
        }

        return new DeleteRestaurantNoteCommand(index, lineRec, lineGood, lineBad);
    }
}

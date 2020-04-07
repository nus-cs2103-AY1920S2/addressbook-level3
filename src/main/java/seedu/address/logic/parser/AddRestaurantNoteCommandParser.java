package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_NO_PREFIX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BAD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECOMMENDED;

import java.util.ArrayList;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddRestaurantNoteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.restaurant.Note;

/**
 * Parses input arguments and creates a new {@code AddInfoCommand} object
 */
public class AddRestaurantNoteCommandParser implements Parser<AddRestaurantNoteCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code AddRestaurantNoteCommand}
     * and returns a {@code RemarkCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddRestaurantNoteCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_RECOMMENDED, PREFIX_GOOD, PREFIX_BAD);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddRestaurantNoteCommand.MESSAGE_USAGE), ive);
        }

        if (!argMultimap.getValue(PREFIX_RECOMMENDED).isPresent() && !argMultimap.getValue(PREFIX_GOOD).isPresent()
                && !argMultimap.getValue(PREFIX_BAD).isPresent()) {
            throw new ParseException(String.format(MESSAGE_NO_PREFIX, AddRestaurantNoteCommand.MESSAGE_USAGE));
        }
        ArrayList<Note> recommendedFood = new ArrayList<>();
        ArrayList<Note> goodFood = new ArrayList<>();
        ArrayList<Note> badFood = new ArrayList<>();

        if (argMultimap.getValue(PREFIX_RECOMMENDED).isPresent()) {
            if (argMultimap.getValue(PREFIX_RECOMMENDED).isEmpty()) {
                throw new ParseException(AddRestaurantNoteCommand.MESSAGE_EMPTY_REC);
            }
            recommendedFood = ParserUtil.parseNotes(argMultimap.getAllValues(PREFIX_RECOMMENDED));
        }
        if (argMultimap.getValue(PREFIX_GOOD).isPresent()) {
            if (argMultimap.getValue(PREFIX_GOOD).isEmpty()) {
                throw new ParseException(AddRestaurantNoteCommand.MESSAGE_EMPTY_GOOD);
            }
            goodFood = ParserUtil.parseNotes(argMultimap.getAllValues(PREFIX_GOOD));
        }
        if (argMultimap.getValue(PREFIX_BAD).isPresent()) {
            if (argMultimap.getValue(PREFIX_BAD).isEmpty()) {
                throw new ParseException(AddRestaurantNoteCommand.MESSAGE_EMPTY_BAD);
            }
            badFood = ParserUtil.parseNotes(argMultimap.getAllValues(PREFIX_BAD));
        }

        return new AddRestaurantNoteCommand(index, recommendedFood, goodFood, badFood);
    }

}

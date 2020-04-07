package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_NO_PREFIX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BAD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECOMMENDED;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddRestaurantNoteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.restaurant.Note;

import java.util.ArrayList;

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

        ArrayList<Note> recommendedFood = ParserUtil.parseNotes(argMultimap.getAllValues(PREFIX_RECOMMENDED));
        ArrayList<Note> goodFood = ParserUtil.parseNotes(argMultimap.getAllValues(PREFIX_GOOD));
        ArrayList<Note> badFood = ParserUtil.parseNotes(argMultimap.getAllValues(PREFIX_BAD));

        return new AddRestaurantNoteCommand(index, recommendedFood, goodFood, badFood);
    }

}

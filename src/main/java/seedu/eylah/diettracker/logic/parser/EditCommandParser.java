package seedu.eylah.diettracker.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.eylah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eylah.diettracker.logic.commands.EditCommand.MESSAGE_NOT_EDITED;
import static seedu.eylah.diettracker.logic.parser.DietCliSyntax.PREFIX_CALORIES;
import static seedu.eylah.diettracker.logic.parser.DietCliSyntax.PREFIX_INDEX;
import static seedu.eylah.diettracker.logic.parser.DietCliSyntax.PREFIX_NAME;
import static seedu.eylah.diettracker.logic.parser.DietCliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.eylah.commons.core.index.Index;
import seedu.eylah.commons.logic.parser.ArgumentMultimap;
import seedu.eylah.commons.logic.parser.ArgumentTokenizer;
import seedu.eylah.commons.logic.parser.Parser;
import seedu.eylah.commons.logic.parser.Prefix;
import seedu.eylah.commons.logic.parser.exception.ParseException;
import seedu.eylah.diettracker.logic.commands.EditCommand;
import seedu.eylah.diettracker.model.tag.Tag;

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
                ArgumentTokenizer
                        .tokenize(args, PREFIX_INDEX, PREFIX_NAME, PREFIX_CALORIES, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
        } catch (NoSuchElementException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), e);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditCommand.EditFoodDescriptor editFoodDescriptor = new EditCommand.EditFoodDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editFoodDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_CALORIES).isPresent()) {
            editFoodDescriptor.setCalories(ParserUtil.parseCalories(argMultimap.getValue(PREFIX_CALORIES).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editFoodDescriptor::setTags);

        if (!editFoodDescriptor.isAnyFieldEdited()) {
            throw new ParseException(MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editFoodDescriptor);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}

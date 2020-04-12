package seedu.address.logic.parser.parserEdit;

import seedu.address.logic.commands.commandEdit.EditCourseCommand;
import seedu.address.logic.commands.commandEdit.EditCourseCommand.EditCourseDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.modelObjectTags.ID;
import seedu.address.model.tag.Tag;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCourseCommandParser implements Parser<EditCourseCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand and returns an
     * EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCourseCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_AMOUNT, PREFIX_TAG);

        ID id;

        try {
            id = ParserUtil.parseID(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCourseCommand.MESSAGE_USAGE), pe);
        }

        EditCourseDescriptor editCourseDescriptor = new EditCourseDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editCourseDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
            editCourseDescriptor.setAmount(ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get()));
        }

        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editCourseDescriptor::setTags);

        if (!editCourseDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCourseCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCourseCommand(id, editCourseDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty. If
     * {@code tags} contain only one element which is an empty string, it will be parsed into a {@code
     * Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet =
                tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}

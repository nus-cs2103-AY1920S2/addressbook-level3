package team.easytravel.logic.parser.activity;

import static java.util.Objects.requireNonNull;
import static team.easytravel.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_ACTIVITY_DURATION;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_ACTIVITY_LOCATION;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_ACTIVITY_TAG;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_ACTIVITY_TITLE;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import team.easytravel.commons.core.index.Index;
import team.easytravel.logic.commands.activity.EditActivityCommand;
import team.easytravel.logic.parser.ArgumentMultimap;
import team.easytravel.logic.parser.ArgumentTokenizer;
import team.easytravel.logic.parser.Parser;
import team.easytravel.logic.parser.ParserUtil;
import team.easytravel.logic.parser.exceptions.ParseException;
import team.easytravel.model.util.attributes.tag.Tag;

/**
 * Parses input arguments and creates a new EditActivityCommand.
 */
public class EditActivityCommandParser implements Parser<EditActivityCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditActivityCommand
     * and returns an EditActivityCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditActivityCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_ACTIVITY_TITLE, PREFIX_ACTIVITY_DURATION, PREFIX_ACTIVITY_LOCATION, PREFIX_ACTIVITY_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditActivityCommand.MESSAGE_USAGE), pe);
        }

        EditActivityCommand.EditActivityDescriptor editActivityDescriptor =
                new EditActivityCommand.EditActivityDescriptor();

        if (argMultimap.getValue(PREFIX_ACTIVITY_TITLE).isPresent()) {
            editActivityDescriptor.setTitle(ParserUtil
                    .parseTitle(argMultimap.getValue(PREFIX_ACTIVITY_TITLE).get()));
        }
        if (argMultimap.getValue(PREFIX_ACTIVITY_DURATION).isPresent()) {
            editActivityDescriptor.setDuration(ParserUtil
                    .parseDuration(argMultimap.getValue(PREFIX_ACTIVITY_DURATION).get()));
        }
        if (argMultimap.getValue(PREFIX_ACTIVITY_LOCATION).isPresent()) {
            editActivityDescriptor.setLocation(ParserUtil
                    .parseLocation(argMultimap.getValue(PREFIX_ACTIVITY_LOCATION).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_ACTIVITY_TAG)).ifPresent(editActivityDescriptor::setTags);

        if (!editActivityDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditActivityCommand.MESSAGE_NOT_EDITED);
        }

        return new EditActivityCommand(index, editActivityDescriptor);
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

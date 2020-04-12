package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPORT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.client.Sport;
import seedu.address.model.client.TagAndSportContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * @author tohkerwei
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG, PREFIX_SPORT);

        boolean hasTag = argMultimap.getValue(PREFIX_TAG).isPresent();
        boolean hasSport = argMultimap.getValue(PREFIX_SPORT).isPresent();

        if (!hasTag && !hasSport) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        List<String> tags = getTagsFromMap(argMultimap);
        List<String> sports = getSportsFromMap(argMultimap);

        return new FilterCommand(new TagAndSportContainsKeywordsPredicate(tags, sports));
    }

    /**
     * Returns a list of string of tags
     * @param argMultimap map of input
     * @return List of strings of tags
     * @throws ParseException if there are no tags
     */
    public List<String> getTagsFromMap(ArgumentMultimap argMultimap) throws ParseException {
        Set<Tag> tagSet = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        List<String> tags = new ArrayList<String>();
        for (Tag tag : tagSet) {
            tags.add(tag.tagName);
        }
        return tags;
    }

    /**
     * Returns a list of strings of sports
     * @param argMultimap map of input
     * @return List of strings of sports
     * @throws ParseException if there are no sports
     */
    public List<String> getSportsFromMap(ArgumentMultimap argMultimap) throws ParseException {
        Set<Sport> sportSet = ParserUtil.parseSports(argMultimap.getAllValues(PREFIX_SPORT));
        List<String> sports = new ArrayList<String>();
        for (Sport sport : sportSet) {
            String[] sportString = sport.sportName.split(" ", 0);
            sports.addAll(Arrays.asList(sportString));
        }
        return sports;
    }
}

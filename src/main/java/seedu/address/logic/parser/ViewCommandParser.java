package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.InsightType;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewCommand object.
 */
public class ViewCommandParser implements Parser<ViewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand.
     *
     * @return A ViewCommand object for execution.
     * @throws ParseException If the user input does not conform to the expected format.
     */
    public ViewCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_GROUP, PREFIX_MEMBER);

        boolean groupIndicated = !(argMultimap.getValue(PREFIX_GROUP).isEmpty());
        boolean personIndicated = !(argMultimap.getValue(PREFIX_MEMBER).isEmpty());
        if (groupIndicated && personIndicated) {
            throw new ParseException(ViewCommand.MESSAGE_EXTRA_PREFIX);
        }

        String parameter = argMultimap.getPreamble();
        if (!parameter.equals(ViewCommand.KEYWORD_PLACE) && !parameter.equals(ViewCommand.KEYWORD_ACTIVITY)
                && !parameter.equals(ViewCommand.KEYWORD_RECENT) && !parameter.equals(ViewCommand.KEYWORD_ALL)
                && !parameter.equals(ViewCommand.KEYWORD_TIME)) {
            throw new ParseException(ViewCommand.MESSAGE_INVALID_PARAMETER + ViewCommand.MESSAGE_USAGE);
        }

        boolean indexRequired = parameter.equals(ViewCommand.KEYWORD_PLACE)
                || parameter.equals(ViewCommand.KEYWORD_ACTIVITY);
        boolean indexForbidden = parameter.equals(ViewCommand.KEYWORD_ALL)
                || parameter.equals(ViewCommand.KEYWORD_TIME);
        if (indexRequired && (!groupIndicated && !personIndicated)) {
            throw new ParseException(ViewCommand.MESSAGE_INDEX_REQUIRED);
        } else if (indexForbidden && (groupIndicated || personIndicated)) {
            throw new ParseException(ViewCommand.MESSAGE_INDEX_FORBIDDEN);
        }

        String indexString;
        InsightType insightType;
        if (groupIndicated) {
            indexString = argMultimap.getValue(PREFIX_GROUP).get();
            insightType = InsightType.GROUP;
        } else if (personIndicated) {
            indexString = argMultimap.getValue(PREFIX_MEMBER).get();
            insightType = InsightType.PERSON;
        } else {
            indexString = "1"; //default index when index not required
            insightType = InsightType.ALL;
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(indexString);
        } catch (ParseException pe) {
            if (groupIndicated) {
                throw new ParseException(MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
            } else {
                throw new ParseException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }

        return new ViewCommand(index, parameter, insightType);
    }
}

package seedu.address.logic.parser;

import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.modelObjectTags.ID;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class SelectCommandParser implements Parser<SelectCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand and returns a
     * FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer
                        .tokenize(args, PREFIX_STUDENTID, PREFIX_TEACHERID, PREFIX_COURSEID, PREFIX_FINANCEID,
                                PREFIX_ASSIGNMENTID);

        // TODO: Check for keyword "in" exists as well
        List<ArgumentTokenizer.PrefixPosition> positions = ArgumentTokenizer.findAllPrefixPositions(args,
                PREFIX_STUDENTID, PREFIX_TEACHERID, PREFIX_COURSEID, PREFIX_FINANCEID,
                PREFIX_ASSIGNMENTID);

        if (positions.size() > 2 || positions.size() == 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }

        Collections.sort(positions);

        List<ID> selectMetaDataIDs = new ArrayList<ID>();

        for (ArgumentTokenizer.PrefixPosition position : positions) {
            String[] nameKeywords = ParserUtil.parseString(argMultimap.getValue(position.getPrefix()).get())
                    .split("\\s+");
            ID id = new ID(nameKeywords[0]);
            selectMetaDataIDs.add(id);
        }

        return new SelectCommand(positions, selectMetaDataIDs);
    }
}

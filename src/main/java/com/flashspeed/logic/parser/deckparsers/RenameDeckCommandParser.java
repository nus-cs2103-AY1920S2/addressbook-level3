package com.flashspeed.logic.parser.deckparsers;

import static com.flashspeed.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static java.util.Objects.requireNonNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.flashspeed.commons.core.index.Index;
import com.flashspeed.logic.commands.deckcommands.RenameDeckCommand;
import com.flashspeed.logic.parser.Parser;
import com.flashspeed.logic.parser.ParserUtil;
import com.flashspeed.logic.parser.exceptions.ParseException;
import com.flashspeed.model.deck.Name;

/**
 * Parses input arguments and creates a new RenameDeckCommand object
 */
public class RenameDeckCommandParser implements Parser<RenameDeckCommand> {

    /**
     * Gets pattern INDEX NAME, spaces between INDEX and NAME is handled.
     */
    private static final Pattern COMMAND_FORMAT = Pattern.compile("(?<index>\\d+)(\\s+)(?<name>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of the RenameDeckCommand
     * and returns an RenameDeckCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RenameDeckCommand parse(String args) throws ParseException {
        requireNonNull(args);

        final Matcher matcher = COMMAND_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RenameDeckCommand.MESSAGE_USAGE));
        }

        final String indexStr = matcher.group("index");
        final String newName = matcher.group("name");

        if (newName.isBlank()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RenameDeckCommand.MESSAGE_USAGE));
        }

        Name name = new Name(newName);

        try {
            Index index = ParserUtil.parseIndex(indexStr);
            return new RenameDeckCommand(index, name);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RenameDeckCommand.MESSAGE_USAGE), pe);
        }
    }
}

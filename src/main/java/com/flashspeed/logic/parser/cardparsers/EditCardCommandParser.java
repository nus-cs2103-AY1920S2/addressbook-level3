package com.flashspeed.logic.parser.cardparsers;

import static com.flashspeed.commons.core.Messages.MESSAGE_INPUT_CONTAINS_COLON;
import static com.flashspeed.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.flashspeed.commons.core.index.Index;
import com.flashspeed.logic.commands.cardcommands.EditCardCommand;
import com.flashspeed.logic.parser.Parser;
import com.flashspeed.logic.parser.ParserUtil;
import com.flashspeed.logic.parser.exceptions.ParseException;
import com.flashspeed.model.deck.card.BackFace;
import com.flashspeed.model.deck.card.FrontFace;

/**
 * Parses input arguments and creates a new EditCardCommand object.
 */
public class EditCardCommandParser implements Parser<EditCardCommand> {

    /**
     * Gets pattern INDEX FRONT:BACK, spaces before and after ":" is handled.
     * Also, can choose whether to allow no space between INDEX and FRONT:BACK.
     * Allows for fast edit (INDEX :BACK) or (INDEX FRONT:).
     */
    private static final Pattern COMMAND_FORMAT = Pattern.compile("(?<index>\\d+)(\\s+)(?<front>.*)"
            + "(\\s*[\u003a\u02d0\u02d1\u02f8\u05c3\u2236\u2360\ua789\ufe13\uff1a\ufe55]\\s*)"
            + "(?<back>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCardCommand
     * and returns a DeleteCardCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public EditCardCommand parse(String args) throws ParseException {
        final Matcher matcher = COMMAND_FORMAT.matcher(args.strip());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCardCommand.MESSAGE_USAGE));
        }

        final String indexStr = matcher.group("index");
        final String frontValue = matcher.group("front");
        final String backValue = matcher.group("back");

        if (frontValue.isBlank() && backValue.isBlank()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCardCommand.MESSAGE_USAGE));
        }

        if (frontValue.contains(":") || backValue.contains(":")) {
            throw new ParseException(String.format(MESSAGE_INPUT_CONTAINS_COLON, EditCardCommand.MESSAGE_USAGE));
        }

        FrontFace front = new FrontFace(frontValue);
        BackFace back = new BackFace(backValue);

        try {
            Index index = ParserUtil.parseIndex(indexStr);
            return new EditCardCommand(index, front, back);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCardCommand.MESSAGE_USAGE), pe);
        }
    }
}

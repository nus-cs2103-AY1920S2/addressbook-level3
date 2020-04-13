package com.flashspeed.logic.parser.cardparsers;

import static com.flashspeed.commons.core.Messages.MESSAGE_INPUT_CONTAINS_COLON;
import static com.flashspeed.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.flashspeed.logic.commands.cardcommands.AddCardCommand;
import com.flashspeed.logic.commands.cardcommands.EditCardCommand;
import com.flashspeed.logic.parser.Parser;
import com.flashspeed.logic.parser.exceptions.ParseException;
import com.flashspeed.model.deck.card.BackFace;
import com.flashspeed.model.deck.card.Card;
import com.flashspeed.model.deck.card.FrontFace;

/**
 * Parses input arguments and creates a new AddCardCommand object.
 */
public class AddCardCommandParser implements Parser<AddCardCommand> {

    /**
     * Gets pattern FRONT:BACK, spaces before and after ":" are handled.
     */
    private static final Pattern COMMAND_FORMAT = Pattern.compile(
            "(?<front>.*)"
            + "(\\s*[\u003a\u02d0\u02d1\u02f8\u05c3\u2236\u2360\ua789\ufe13\uff1a\ufe55]\\s*)"
            + "(?<back>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of the AddCardCommand
     * and returns an AddCardCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public AddCardCommand parse(String args) throws ParseException {
        final Matcher matcher = COMMAND_FORMAT.matcher(args.strip());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCardCommand.MESSAGE_USAGE));
        }

        final String frontValue = matcher.group("front");
        final String backValue = matcher.group("back");

        if (frontValue.isBlank() || backValue.isBlank()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCardCommand.MESSAGE_USAGE));
        }

        if (frontValue.contains(":") || backValue.contains(":")) {
            throw new ParseException(String.format(MESSAGE_INPUT_CONTAINS_COLON, EditCardCommand.MESSAGE_USAGE));
        }

        FrontFace front = new FrontFace(frontValue);
        BackFace back = new BackFace(backValue);

        Card card = new Card(front, back);
        return new AddCardCommand(card);
    }
}

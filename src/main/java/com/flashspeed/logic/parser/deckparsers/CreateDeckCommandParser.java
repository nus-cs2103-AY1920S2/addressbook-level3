package com.flashspeed.logic.parser.deckparsers;

import static com.flashspeed.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import com.flashspeed.logic.commands.deckcommands.CreateDeckCommand;
import com.flashspeed.logic.parser.Parser;
import com.flashspeed.logic.parser.ParserUtil;
import com.flashspeed.logic.parser.exceptions.ParseException;
import com.flashspeed.model.deck.Deck;
import com.flashspeed.model.deck.Name;

/**
 * Parses input arguments and creates a new CreateDeckCommand object.
 */
public class CreateDeckCommandParser implements Parser<CreateDeckCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CreateDeckCommand
     * and returns an CreateDeckCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CreateDeckCommand parse(String args) throws ParseException {
        String strippedArgs = args.strip();
        if (strippedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateDeckCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(strippedArgs);

        Deck deck = new Deck(name);
        return new CreateDeckCommand(deck);
    }
}

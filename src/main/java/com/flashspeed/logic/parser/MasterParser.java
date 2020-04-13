package com.flashspeed.logic.parser;

import static com.flashspeed.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static com.flashspeed.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.flashspeed.logic.commands.Command;
import com.flashspeed.logic.commands.ExitCommand;
import com.flashspeed.logic.commands.HelpCommand;
import com.flashspeed.logic.commands.ResetLibraryCommand;
import com.flashspeed.logic.commands.ReturnToLibraryCommand;
import com.flashspeed.logic.commands.cardcommands.AddCardCommand;
import com.flashspeed.logic.commands.cardcommands.DeleteCardCommand;
import com.flashspeed.logic.commands.cardcommands.EditCardCommand;
import com.flashspeed.logic.commands.deckcommands.CreateDeckCommand;
import com.flashspeed.logic.commands.deckcommands.RemoveDeckCommand;
import com.flashspeed.logic.commands.deckcommands.RenameDeckCommand;
import com.flashspeed.logic.commands.deckcommands.SelectDeckCommand;

import com.flashspeed.logic.commands.gamecommands.AnswerNoCommand;
import com.flashspeed.logic.commands.gamecommands.AnswerYesCommand;
import com.flashspeed.logic.commands.gamecommands.FlipCommand;
import com.flashspeed.logic.commands.gamecommands.PlayCommand;
import com.flashspeed.logic.commands.gamecommands.StopCommand;
import com.flashspeed.logic.parser.cardparsers.AddCardCommandParser;
import com.flashspeed.logic.parser.cardparsers.DeleteCardCommandParser;
import com.flashspeed.logic.parser.cardparsers.EditCardCommandParser;
import com.flashspeed.logic.parser.deckparsers.CreateDeckCommandParser;
import com.flashspeed.logic.parser.deckparsers.RemoveDeckCommandParser;
import com.flashspeed.logic.parser.deckparsers.RenameDeckCommandParser;
import com.flashspeed.logic.parser.deckparsers.SelectDeckCommandParser;
import com.flashspeed.logic.parser.exceptions.ParseException;
import com.flashspeed.logic.parser.gameparsers.PlayCommandParser;

/**
 * Parses input for FlashSpeed.
 */
public class MasterParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        // to handle get of commandWord and args
        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        /* List of commands:
        Deck:
          - CreateDeck
          - RemoveDeck
          - RenameDeck
          - SelectDeck
        Card:
          - AddCard
          - DeleteCard
          - EditCard
        Game:
          - Play
          - Flip
          - AnswerYes
          - AnswerNO
          - Stop
        General:
          - Help
          - Exit
          - ReturnToLibrary

         */
        switch (commandWord) {

        // Deck functions
        case CreateDeckCommand.COMMAND_WORD:
            return new CreateDeckCommandParser().parse(arguments);

        case RemoveDeckCommand.COMMAND_WORD:
            return new RemoveDeckCommandParser().parse(arguments);

        case RenameDeckCommand.COMMAND_WORD:
            return new RenameDeckCommandParser().parse(arguments);

        case SelectDeckCommand.COMMAND_WORD:
            return new SelectDeckCommandParser().parse(arguments);

        // Card functions
        case AddCardCommand.COMMAND_WORD:
            return new AddCardCommandParser().parse(arguments);

        case DeleteCardCommand.COMMAND_WORD:
            return new DeleteCardCommandParser().parse(arguments);

        case EditCardCommand.COMMAND_WORD:
            return new EditCardCommandParser().parse(arguments);

        // Game functions
        case PlayCommand.COMMAND_WORD:
            return new PlayCommandParser().parse(arguments);

        case FlipCommand.COMMAND_WORD:
            return new FlipCommand();

        case AnswerYesCommand.COMMAND_WORD:
            return new AnswerYesCommand();

        case AnswerNoCommand.COMMAND_WORD:
            return new AnswerNoCommand();
        case StopCommand.COMMAND_WORD:
            return new StopCommand();

        // General
        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ResetLibraryCommand.COMMAND_WORD:
            return new ResetLibraryCommand();

        case ReturnToLibraryCommand.COMMAND_WORD:
            return new ReturnToLibraryCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}

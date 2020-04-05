package cookbuddy.logic.parser;

import static cookbuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import cookbuddy.logic.commands.CountCommand;
import cookbuddy.logic.commands.DeleteCommand;
import cookbuddy.logic.commands.DoneCommand;
import cookbuddy.logic.commands.ExitCommand;
import cookbuddy.logic.commands.FavCommand;
import cookbuddy.logic.commands.FindCommand;
import cookbuddy.logic.commands.HelpCommand;
import cookbuddy.logic.commands.ListCommand;
import cookbuddy.logic.commands.ModifyCommand;
import cookbuddy.logic.commands.NewCommand;
import cookbuddy.logic.commands.RandomCommand;
import cookbuddy.logic.commands.ResetCommand;
import cookbuddy.logic.commands.TimeCommand;
import cookbuddy.logic.commands.UnFavCommand;
import cookbuddy.logic.commands.UndoCommand;
import cookbuddy.logic.commands.ViewCommand;
import cookbuddy.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */

public class HelpCommandParser implements Parser<HelpCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public HelpCommand parse(String args) throws ParseException {
        try {
            String command = ParserUtil.parseHelp(args);
            String preface = "Showing usage for the " + command + " command:\n\n";

            switch (command) {
            case NewCommand.COMMAND_WORD:
                return new HelpCommand(preface + NewCommand.MESSAGE_USAGE);

            case ModifyCommand.COMMAND_WORD:
                return new HelpCommand(preface + ModifyCommand.MESSAGE_USAGE);

            case DeleteCommand.COMMAND_WORD:
                return new HelpCommand(preface + DeleteCommand.MESSAGE_USAGE);

            case DoneCommand.COMMAND_WORD:
                return new HelpCommand(preface + DoneCommand.MESSAGE_USAGE);

            case UndoCommand.COMMAND_WORD:
                return new HelpCommand(preface + UndoCommand.MESSAGE_USAGE);

            case FavCommand.COMMAND_WORD:
                return new HelpCommand(preface + FavCommand.MESSAGE_USAGE);

            case UnFavCommand.COMMAND_WORD:
                return new HelpCommand(preface + UnFavCommand.MESSAGE_USAGE);

            case ResetCommand.COMMAND_WORD:
                return new HelpCommand(preface + "TBA_RESET");

            case ViewCommand.COMMAND_WORD:
                return new HelpCommand(preface + ViewCommand.MESSAGE_USAGE);

            case FindCommand.COMMAND_WORD:
                return new HelpCommand(preface + FindCommand.MESSAGE_USAGE);

            case ListCommand.COMMAND_WORD:
                return new HelpCommand(preface + "TBA_LIST");

            case RandomCommand.COMMAND_WORD:
                return new HelpCommand(preface + RandomCommand.MESSAGE_USAGE);

            case TimeCommand.COMMAND_WORD:
                return new HelpCommand(preface + TimeCommand.MESSAGE_USAGE);

            case CountCommand.COMMAND_WORD:
                return new HelpCommand(preface + "TBA_COUNT");

            case ExitCommand.COMMAND_WORD:
                return new HelpCommand(preface + "TBA_EXIT");

            default:
                throw new ParseException("Invalid Command chosen!");
            }


        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, pe.getLocalizedMessage()));
        }
    }

}

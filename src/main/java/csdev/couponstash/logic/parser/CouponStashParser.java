package csdev.couponstash.logic.parser;

import static csdev.couponstash.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static csdev.couponstash.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import csdev.couponstash.commons.moneysymbol.MoneySymbol;
import csdev.couponstash.logic.commands.AddCommand;
import csdev.couponstash.logic.commands.ArchiveCommand;
import csdev.couponstash.logic.commands.ClearCommand;
import csdev.couponstash.logic.commands.Command;
import csdev.couponstash.logic.commands.CopyCommand;
import csdev.couponstash.logic.commands.DeleteCommand;
import csdev.couponstash.logic.commands.EditCommand;
import csdev.couponstash.logic.commands.ExitCommand;
import csdev.couponstash.logic.commands.ExpandCommand;
import csdev.couponstash.logic.commands.ExpiringCommand;
import csdev.couponstash.logic.commands.FindCommand;
import csdev.couponstash.logic.commands.GoToCommand;
import csdev.couponstash.logic.commands.HelpCommand;
import csdev.couponstash.logic.commands.ListCommand;
import csdev.couponstash.logic.commands.RedoCommand;
import csdev.couponstash.logic.commands.SavedCommand;
import csdev.couponstash.logic.commands.SetCurrencyCommand;
import csdev.couponstash.logic.commands.ShareCommand;
import csdev.couponstash.logic.commands.SortCommand;
import csdev.couponstash.logic.commands.UnarchiveCommand;
import csdev.couponstash.logic.commands.UndoCommand;
import csdev.couponstash.logic.commands.UsedCommand;
import csdev.couponstash.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class CouponStashParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private final MoneySymbol moneySymbol;

    /**
     * Constructor for a CouponStashParser. Requires the
     * money symbol set in UserPrefs as this will be
     * used in the parsing of many commands like
     * AddCommandParser and EditCommandParser.
     * @param moneySymbol MoneySymbol representing the money symbol.
     */
    public CouponStashParser(MoneySymbol moneySymbol) {
        this.moneySymbol = moneySymbol;
    }

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

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser(this.moneySymbol.getString()).parse(arguments);

        case ArchiveCommand.COMMAND_WORD:
            return new ArchiveCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case CopyCommand.COMMAND_WORD:
            return new CopyCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser(this.moneySymbol.getString()).parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case ExpandCommand.COMMAND_WORD:
            return new ExpandCommandParser().parse(arguments);

        case ExpiringCommand.COMMAND_WORD:
            return new ExpiringCommandParser().parse(arguments);

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case GoToCommand.COMMAND_WORD:
            return new GoToCommandParser().parse(arguments);

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ListCommand.COMMAND_WORD:
            return new ListCommandParser().parse(arguments);

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case SavedCommand.COMMAND_WORD:
            return new SavedCommandParser().parse(arguments);

        case SetCurrencyCommand.COMMAND_WORD:
            return new SetCurrencyCommandParser().parse(arguments);

        case ShareCommand.COMMAND_WORD:
            return new ShareCommandParser().parse(arguments);

        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments);

        case UnarchiveCommand.COMMAND_WORD:
            return new UnarchiveCommandParser().parse(arguments);

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case UsedCommand.COMMAND_WORD:
            return new UsedCommandParser(this.moneySymbol.getString()).parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}

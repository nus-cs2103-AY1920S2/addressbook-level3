package seedu.nova.logic.parser.abparsers;

import static seedu.nova.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.nova.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Pattern;

import seedu.nova.logic.commands.Command;
import seedu.nova.logic.commands.abcommands.AbAddCommand;
import seedu.nova.logic.commands.abcommands.AbClearCommand;
import seedu.nova.logic.commands.abcommands.AbDeleteCommand;
import seedu.nova.logic.commands.abcommands.AbEditCommand;
import seedu.nova.logic.commands.abcommands.AbFindCommand;
import seedu.nova.logic.commands.abcommands.AbListCommand;
import seedu.nova.logic.commands.abcommands.AbRedoCommand;
import seedu.nova.logic.commands.abcommands.AbRemarkCommand;
import seedu.nova.logic.commands.abcommands.AbUndoCommand;
import seedu.nova.logic.parser.exceptions.ParseException;

/**
 * Parses user input for address book feature.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param commandWord command under address book feature
     * @param arguments arguments under address book feature
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    //public Command parseCommand(String userInput) throws ParseException {
    public Command parseCommand(String commandWord, String arguments) throws ParseException {
        switch (commandWord) {

        case AbAddCommand.COMMAND_WORD:
            return new AbAddCommandParser().parse(arguments);

        case AbEditCommand.COMMAND_WORD:
            return new AbEditCommandParser().parse(arguments);

        case AbDeleteCommand.COMMAND_WORD:
            return new AbDeleteCommandParser().parse(arguments);

        case AbClearCommand.COMMAND_WORD:
            if (arguments.trim().equals("")) {
                return new AbClearCommand();
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AbClearCommand.MESSAGE_USAGE));
            }

        case AbFindCommand.COMMAND_WORD:
            return new AbFindCommandParser().parse(arguments);

        case AbListCommand.COMMAND_WORD:
            String[] classmateList = {"classmate"};
            String[] teammateList = {"teammate"};
            if (arguments.trim().equals("")) {
                return new AbListCommand();
            } else {
                return new AbListCategoryCommandParser().parse(arguments);
            }

        case AbUndoCommand.COMMAND_WORD:
            if (arguments.trim().equals("")) {
                return new AbUndoCommand();
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AbUndoCommand.MESSAGE_USAGE));
            }

        case AbRedoCommand.COMMAND_WORD:
            if (arguments.trim().equals("")) {
                return new AbRedoCommand();
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AbRedoCommand.MESSAGE_USAGE));
            }

        case AbRemarkCommand.COMMAND_WORD:
            return new AbRemarkCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}

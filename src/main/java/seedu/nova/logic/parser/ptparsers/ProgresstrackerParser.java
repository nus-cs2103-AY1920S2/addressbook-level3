package seedu.nova.logic.parser.ptparsers;

import static seedu.nova.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import seedu.nova.logic.commands.Command;
import seedu.nova.logic.commands.ptcommands.PtAddCommand;
import seedu.nova.logic.commands.ptcommands.PtAddNoteCommand;
import seedu.nova.logic.commands.ptcommands.PtDeleteCommand;
import seedu.nova.logic.commands.ptcommands.PtDeleteNoteCommand;
import seedu.nova.logic.commands.ptcommands.PtDoneCommand;
import seedu.nova.logic.commands.ptcommands.PtEditCommand;
import seedu.nova.logic.commands.ptcommands.PtEditNoteCommand;
import seedu.nova.logic.commands.ptcommands.PtListCommand;
import seedu.nova.logic.parser.exceptions.ParseException;

/**
 * Parses user input for progress tracker feature.
 */
public class ProgresstrackerParser {

    /**
     * Parses user input into command for execution.
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String commandWord, String arguments) throws ParseException {
        switch (commandWord) {

        case PtListCommand.COMMAND_WORD:
            return new PtListCommandParser().parse(arguments);

        case PtAddCommand.COMMAND_WORD:
            return new PtAddCommandParser().parse(arguments);

        case PtDeleteCommand.COMMAND_WORD:
            return new PtDeleteCommandParser().parse(arguments);

        case PtDoneCommand.COMMAND_WORD:
            return new PtDoneCommandParser().parse(arguments);

        case PtEditCommand.COMMAND_WORD:
            return new PtEditCommandParser().parse(arguments);

        case PtAddNoteCommand.COMMAND_WORD:
            return new PtAddNoteCommandParser().parse(arguments);

        case PtDeleteNoteCommand.COMMAND_WORD:
            return new PtDeleteNoteCommandParser().parse(arguments);

        case PtEditNoteCommand.COMMAND_WORD:
            return new PtEditNoteCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}



package seedu.nova.logic.parser.scparser;

import static seedu.nova.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import seedu.nova.logic.commands.Command;
import seedu.nova.logic.commands.sccommands.ScViewCommand;
import seedu.nova.logic.commands.sccommands.ScViewFreeSlotCommand;
import seedu.nova.logic.commands.sccommands.eventcommands.EventAddConsultationCommand;
import seedu.nova.logic.commands.sccommands.eventcommands.EventAddLessonCommand;
import seedu.nova.logic.commands.sccommands.eventcommands.EventAddMeetingCommand;
import seedu.nova.logic.commands.sccommands.eventcommands.EventAddNoteCommand;
import seedu.nova.logic.commands.sccommands.eventcommands.EventAddStudyCommand;
import seedu.nova.logic.commands.sccommands.eventcommands.EventDeleteCommand;
import seedu.nova.logic.parser.exceptions.ParseException;
import seedu.nova.logic.parser.scparser.eventparsers.EventAddConsultationCommandParser;
import seedu.nova.logic.parser.scparser.eventparsers.EventAddLessonCommandParser;
import seedu.nova.logic.parser.scparser.eventparsers.EventAddMeetingCommandParser;
import seedu.nova.logic.parser.scparser.eventparsers.EventAddNoteCommandParser;
import seedu.nova.logic.parser.scparser.eventparsers.EventAddStudyCommandParser;
import seedu.nova.logic.parser.scparser.eventparsers.EventDeleteCommandParser;

/**
 * The type Schedule parser.
 */
public class ScheduleParser {

    /**
     * Parses command.
     *
     * @param commandWord the command word
     * @param arguments   the arguments
     * @return the command
     * @throws ParseException the parse exception
     */
    public Command parseCommand(String commandWord, String arguments) throws ParseException {

        switch (commandWord) {
        case ScViewCommand.COMMAND_WORD:
            return new ScViewCommandParser().parse(arguments);
        case ScViewFreeSlotCommand.COMMAND_WORD:
            return new ScViewFreeSlotCommandParser().parse(arguments);

        case EventAddMeetingCommand.COMMAND_WORD:
            return new EventAddMeetingCommandParser().parse(arguments);

        case EventAddConsultationCommand.COMMAND_WORD:
            return new EventAddConsultationCommandParser().parse(arguments);

        case EventAddStudyCommand.COMMAND_WORD:
            return new EventAddStudyCommandParser().parse(arguments);

        case EventAddLessonCommand.COMMAND_WORD:
            return new EventAddLessonCommandParser().parse(arguments);

        case EventDeleteCommand.COMMAND_WORD:
            return new EventDeleteCommandParser().parse(arguments);

        case EventAddNoteCommand.COMMAND_WORD:
            return new EventAddNoteCommandParser().parse(arguments);
        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}

package seedu.address.logic.parser;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.*;
import seedu.address.logic.commands.commandAdd.*;
import seedu.address.logic.commands.commandAssign.AssignCommandBase;
import seedu.address.logic.commands.commandClear.ClearCommand;
import seedu.address.logic.commands.commandDelete.*;
import seedu.address.logic.commands.commandDone.DoneCommandBase;
import seedu.address.logic.commands.commandEdit.*;
import seedu.address.logic.commands.commandFind.*;
import seedu.address.logic.commands.commandList.*;
import seedu.address.logic.commands.commandSwitch.*;
import seedu.address.logic.commands.commandUnassign.UnassignCommandBase;
import seedu.address.logic.commands.commandUndone.UndoneCommandBase;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.parserAdd.*;
import seedu.address.logic.parser.parserDelete.*;
import seedu.address.logic.parser.parserDone.DoneCommandParser;
import seedu.address.logic.parser.parserEdit.*;
import seedu.address.logic.parser.parserFind.*;
import seedu.address.logic.parser.parserUndone.UndoneCommandParser;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

/**
 * Parses user input.
 */
public class AddressBookParser {
    private final Logger logger = LogsCenter.getLogger(AddressBookParser.class);

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern
            .compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        logger.info("userInput: " + userInput);
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");

        final String arguments = matcher.group("arguments");

        switch (commandWord) {
            case AssignCommandBase.COMMAND_WORD:
                return new AssignCommandParser().parse(arguments);

            case UnassignCommandBase.COMMAND_WORD:
                return new UnassignCommandParser().parse(arguments);

            case DoneCommandBase.COMMAND_WORD:
                return new DoneCommandParser().parse(arguments);

            case UndoneCommandBase.COMMAND_WORD:
                return new UndoneCommandParser().parse(arguments);

            // Add Operations
            case AddStaffCommand.COMMAND_WORD:
                return new AddStaffCommandParser().parse(arguments);

            case AddStudentCommand.COMMAND_WORD:
                return new AddStudentCommandParser().parse(arguments);

            case AddFinanceCommand.COMMAND_WORD:
                return new AddFinanceCommandParser().parse(arguments);

            case AddCourseCommand.COMMAND_WORD:
                return new AddCourseCommandParser().parse(arguments);

            case AddAssignmentCommand.COMMAND_WORD:
                return new AddAssignmentCommandParser().parse(arguments);

            // Delete Operations
            case DeleteStaffCommand.COMMAND_WORD:
                return new DeleteStaffCommandParser().parse(arguments);

            case DeleteStudentCommand.COMMAND_WORD:
                return new DeleteStudentCommandParser().parse(arguments);

            case DeleteFinanceCommand.COMMAND_WORD:
                return new DeleteFinanceCommandParser().parse(arguments);

            case DeleteCourseCommand.COMMAND_WORD:
                return new DeleteCourseCommandParser().parse(arguments);

            case DeleteAssignmentCommand.COMMAND_WORD:
                return new DeleteAssignmentCommandParser().parse(arguments);

            // Find Operations
            case FindStaffCommand.COMMAND_WORD:
                return new FindTeacherCommandParser().parse(arguments);

            case FindStudentCommand.COMMAND_WORD:
                return new FindStudentCommandParser().parse(arguments);

            case FindFinanceCommand.COMMAND_WORD:
                return new FindFinanceCommandParser().parse(arguments);

            case FindCourseCommand.COMMAND_WORD:
                return new FindCourseCommandParser().parse(arguments);

            case FindAssignmentCommand.COMMAND_WORD:
                return new FindAssignmentCommandParser().parse(arguments);


            // Finance Specific Operations
            case CalculateExpensesFinanceCommand.COMMAND_WORD:
                return new CalculateExpensesFinanceCommand();

            case CalculateEarningsFinanceCommand.COMMAND_WORD:
                return new CalculateEarningsFinanceCommand();

            // List Operations
            case ListStaffCommand.COMMAND_WORD:
                return new ListStaffCommand();

            case ListStudentCommand.COMMAND_WORD:
                return new ListStudentCommand();

            case ListFinanceCommand.COMMAND_WORD:
                return new ListFinanceCommand();

            case ListCourseCommand.COMMAND_WORD:
                return new ListCourseCommand();

            case ListAssignmentCommand.COMMAND_WORD:
                return new ListAssignmentCommand();

            // Clear Operations
            case ClearCommand.COMMAND_WORD:
                return new ClearCommand();

            // Edit Operations
            case EditStaffCommand.COMMAND_WORD:
                return new EditStaffCommandParser().parse(arguments);

            case EditStudentCommand.COMMAND_WORD:
                return new EditStudentCommandParser().parse(arguments);

            case EditFinanceCommand.COMMAND_WORD:
                return new EditFinanceCommandParser().parse(arguments);

            case EditCourseCommand.COMMAND_WORD:
                return new EditCourseCommandParser().parse(arguments);

            case EditAssignmentCommand.COMMAND_WORD:
                return new EditAssignmentCommandParser().parse(arguments);

            case SelectCommand.COMMAND_WORD:
                return new SelectCommandParser().parse(arguments);

            case ExitCommand.COMMAND_WORD:
                return new ExitCommand();

            case HelpCommand.COMMAND_WORD:
                return new HelpCommand();

            case UndoCommand.COMMAND_WORD:
                return new UndoCommand();

            case RedoCommand.COMMAND_WORD:
                return new RedoCommand();

            //Switch operations
            case SwitchStudentCommand.COMMAND_WORD:
                return new SwitchStudentCommand();

            case SwitchStudentCommand.COMMAND_WORD_ALT:
                return new SwitchStudentCommand();

            case SwitchStaffCommand.COMMAND_WORD:
                return new SwitchStaffCommand();

            case SwitchStaffCommand.COMMAND_WORD_ALT:
                return new SwitchStaffCommand();

            case SwitchCourseCommand.COMMAND_WORD:
                return new SwitchCourseCommand();

            case SwitchCourseCommand.COMMAND_WORD_ALT:
                return new SwitchCourseCommand();

            case SwitchFinanceCommand.COMMAND_WORD:
                return new SwitchFinanceCommand();

            case SwitchFinanceCommand.COMMAND_WORD_ALT:
                return new SwitchFinanceCommand();

            case SwitchAssignmentCommand.COMMAND_WORD:
                return new SwitchAssignmentCommand();

            case SwitchAssignmentCommand.COMMAND_WORD_ALT:
                return new SwitchAssignmentCommand();

            case SwitchSummaryCommand.COMMAND_WORD:
                return new SwitchSummaryCommand();

            case SwitchSummaryCommand.COMMAND_WORD_ALT:
                return new SwitchSummaryCommand();

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}

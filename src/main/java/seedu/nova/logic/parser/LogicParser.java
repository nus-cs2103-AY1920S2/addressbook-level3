package seedu.nova.logic.parser;

import static seedu.nova.commons.core.Messages.MESSAGE_EMPTY_ARGUMENT;
import static seedu.nova.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.nova.logic.commands.Command;
import seedu.nova.logic.commands.commoncommands.ExitCommand;
import seedu.nova.logic.commands.commoncommands.NavCommand;
import seedu.nova.logic.parser.abparsers.AddressBookParser;
import seedu.nova.logic.parser.exceptions.ParseException;
import seedu.nova.logic.parser.plannerparser.PlannerParser;
import seedu.nova.logic.parser.ptparsers.ProgresstrackerParser;
import seedu.nova.logic.parser.scparser.ScheduleParser;
import seedu.nova.model.Mode;
import seedu.nova.model.Model;

/**
 * Class for logic parser
 */
public class LogicParser {
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private final NavCommandParser navCommandParser;
    private final AddressBookParser addressBookParser;
    private final seedu.nova.logic.parser.scparser.ScheduleParser scheduleParser;
    private final ProgresstrackerParser progresstrackerParser;
    private final PlannerParser plannerParser;
    private Model model;

    public LogicParser(Model model) {
        //Create parser objects for each page/ feature
        navCommandParser = new NavCommandParser();
        addressBookParser = new AddressBookParser();
        scheduleParser = new ScheduleParser();
        progresstrackerParser = new ProgresstrackerParser();
        plannerParser = new PlannerParser();
        this.model = model;
    }

    /**
     * Parses user input into command for execution.
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        Mode mode = model.getMode();
        ModeEnum modeEnum = model.getModeEnum(mode);
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        boolean noSuchMode = (!modeEnum.equals(ModeEnum.ADDRESSBOOK)) && (!modeEnum.equals(ModeEnum.PROGRESSTRACKER))
                && (!modeEnum.equals(ModeEnum.SCHEDULE)) && (!modeEnum.equals(ModeEnum.HOME))
                && (!modeEnum.equals(ModeEnum.PLANNER));

        if (!matcher.matches()) {
            throw new ParseException(MESSAGE_EMPTY_ARGUMENT);
        }

        if (noSuchMode) {
            throw new ParseException("No such mode");
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        if (commandWord.equals(NavCommand.COMMAND_WORD)) {
            return navCommandParser.parse(arguments.trim());
        } else if (commandWord.equals(ExitCommand.COMMAND_WORD)) {
            return new ExitCommand();
        } else {
            //check mode
            switch (modeEnum) {

            case HOME:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);

            case ADDRESSBOOK:
                //return addressBookParser.parseCommand(userInput);
                return addressBookParser.parseCommand(commandWord, arguments);

            case SCHEDULE:
                return scheduleParser.parseCommand(commandWord, arguments);

            case PROGRESSTRACKER:
                return progresstrackerParser.parseCommand(commandWord, arguments);

            case PLANNER:
                return plannerParser.parseCommand(commandWord, arguments);
            default:
                throw new ParseException("No such mode");
            }
        }
    }
}

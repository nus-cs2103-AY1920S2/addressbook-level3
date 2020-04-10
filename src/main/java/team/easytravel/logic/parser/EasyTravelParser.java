package team.easytravel.logic.parser;

import static team.easytravel.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.easytravel.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import team.easytravel.logic.commands.Command;
import team.easytravel.logic.commands.ExitCommand;
import team.easytravel.logic.commands.HelpCommand;
import team.easytravel.logic.commands.accommodationbooking.AddAccommodationBookingCommand;
import team.easytravel.logic.commands.accommodationbooking.ClearAccommodationBookingCommand;
import team.easytravel.logic.commands.accommodationbooking.DeleteAccommodationBookingCommand;
import team.easytravel.logic.commands.accommodationbooking.EditAccommodationBookingCommand;
import team.easytravel.logic.commands.accommodationbooking.ListAccommodationBookingCommand;
import team.easytravel.logic.commands.accommodationbooking.SortAccommodationBookingCommand;
import team.easytravel.logic.commands.activity.AddActivityCommand;
import team.easytravel.logic.commands.activity.ClearActivityCommand;
import team.easytravel.logic.commands.activity.DeleteActivityCommand;
import team.easytravel.logic.commands.activity.EditActivityCommand;
import team.easytravel.logic.commands.activity.FindActivityCommand;
import team.easytravel.logic.commands.activity.FindActivityTagCommand;
import team.easytravel.logic.commands.activity.ListActivityCommand;
import team.easytravel.logic.commands.activity.SortActivityCommand;
import team.easytravel.logic.commands.fixedexpense.AddFixedExpenseCommand;
import team.easytravel.logic.commands.fixedexpense.ClearFixedExpenseCommand;
import team.easytravel.logic.commands.fixedexpense.DeleteFixedExpenseCommand;
import team.easytravel.logic.commands.fixedexpense.EditFixedExpenseCommand;
import team.easytravel.logic.commands.fixedexpense.ListFixedExpenseCommand;
import team.easytravel.logic.commands.fixedexpense.SortFixedExpenseCommand;
import team.easytravel.logic.commands.packinglist.AddItemCommand;
import team.easytravel.logic.commands.packinglist.AddPresetCommand;
import team.easytravel.logic.commands.packinglist.CheckItemCommand;
import team.easytravel.logic.commands.packinglist.ClearItemCommand;
import team.easytravel.logic.commands.packinglist.DeleteItemCommand;
import team.easytravel.logic.commands.packinglist.EditItemCommand;
import team.easytravel.logic.commands.packinglist.FindItemCategoryCommand;
import team.easytravel.logic.commands.packinglist.FindItemCommand;
import team.easytravel.logic.commands.packinglist.ListItemCommand;
import team.easytravel.logic.commands.packinglist.ListPresetCommand;
import team.easytravel.logic.commands.packinglist.SortItemCommand;
import team.easytravel.logic.commands.packinglist.UncheckItemCommand;
import team.easytravel.logic.commands.schedule.ListScheduleCommand;
import team.easytravel.logic.commands.schedule.ScheduleCommand;
import team.easytravel.logic.commands.schedule.UnscheduleCommand;
import team.easytravel.logic.commands.transportbooking.AddTransportBookingCommand;
import team.easytravel.logic.commands.transportbooking.ClearTransportBookingCommand;
import team.easytravel.logic.commands.transportbooking.DeleteTransportBookingCommand;
import team.easytravel.logic.commands.transportbooking.EditTransportBookingCommand;
import team.easytravel.logic.commands.transportbooking.ListTransportBookingCommand;
import team.easytravel.logic.commands.transportbooking.SortTransportBookingCommand;
import team.easytravel.logic.commands.trip.CheckBudgetStatus;
import team.easytravel.logic.commands.trip.CheckStatusCommand;
import team.easytravel.logic.commands.trip.DeleteTripCommand;
import team.easytravel.logic.commands.trip.EditBudgetCommand;
import team.easytravel.logic.commands.trip.RenameCommand;
import team.easytravel.logic.commands.trip.SetTripCommand;
import team.easytravel.logic.parser.accommodationbooking.AddAccommodationBookingCommandParser;
import team.easytravel.logic.parser.accommodationbooking.DeleteAccommodationBookingCommandParser;
import team.easytravel.logic.parser.accommodationbooking.EditAccommodationBookingCommandParser;
import team.easytravel.logic.parser.accommodationbooking.SortAccommodationBookingParser;
import team.easytravel.logic.parser.activity.AddActivityCommandParser;
import team.easytravel.logic.parser.activity.DeleteActivityCommandParser;
import team.easytravel.logic.parser.activity.EditActivityCommandParser;
import team.easytravel.logic.parser.activity.FindActivityCommandParser;
import team.easytravel.logic.parser.activity.FindActivityTagCommandParser;
import team.easytravel.logic.parser.activity.SortActivityCommandParser;
import team.easytravel.logic.parser.exceptions.ParseException;
import team.easytravel.logic.parser.fixedexpense.AddFixedExpenseCommandParser;
import team.easytravel.logic.parser.fixedexpense.DeleteFixedExpenseCommandParser;
import team.easytravel.logic.parser.fixedexpense.EditFixedExpenseCommandParser;
import team.easytravel.logic.parser.fixedexpense.SortFixedExpenseCommandParser;
import team.easytravel.logic.parser.packinglist.AddItemCommandParser;
import team.easytravel.logic.parser.packinglist.AddPresetCommandParser;
import team.easytravel.logic.parser.packinglist.CheckItemCommandParser;
import team.easytravel.logic.parser.packinglist.DeleteItemCommandParser;
import team.easytravel.logic.parser.packinglist.EditItemCommandParser;
import team.easytravel.logic.parser.packinglist.FindItemCategoryCommandParser;
import team.easytravel.logic.parser.packinglist.FindItemCommandParser;
import team.easytravel.logic.parser.packinglist.SortItemCommandParser;
import team.easytravel.logic.parser.packinglist.UncheckItemCommandParser;
import team.easytravel.logic.parser.schedule.ScheduleCommandParser;
import team.easytravel.logic.parser.schedule.UnscheduleCommandParser;
import team.easytravel.logic.parser.transportbooking.AddTransportBookingCommandParser;
import team.easytravel.logic.parser.transportbooking.DeleteTransportBookingCommandParser;
import team.easytravel.logic.parser.transportbooking.EditTransportBookingCommandParser;
import team.easytravel.logic.parser.transportbooking.SortTransportBookingCommandParser;
import team.easytravel.logic.parser.trip.EditBudgetCommandParser;
import team.easytravel.logic.parser.trip.RenameCommandParser;
import team.easytravel.logic.parser.trip.SetTripCommandParser;

/**
 * Parses user input.
 */
public class EasyTravelParser {

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

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        // ========================== Easy Travel Commands =========================
        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        // ========================== Trip Commands =========================
        case SetTripCommand.COMMAND_WORD:
            return new SetTripCommandParser().parse(arguments);

        case DeleteTripCommand.COMMAND_WORD:
            return new DeleteTripCommand();

        case CheckStatusCommand.COMMAND_WORD:
            return new CheckStatusCommand();

        case EditBudgetCommand.COMMAND_WORD:
            return new EditBudgetCommandParser().parse(arguments);

        case RenameCommand.COMMAND_WORD:
            return new RenameCommandParser().parse(arguments);
        // ========================== Schedule Commands =========================
        case ScheduleCommand.COMMAND_WORD:
            return new ScheduleCommandParser().parse(arguments);

        case UnscheduleCommand.COMMAND_WORD:
            return new UnscheduleCommandParser().parse(arguments);

        case ListScheduleCommand.COMMAND_WORD:
            return new ListScheduleCommand();

        // ========================== Fixed Expense Commands =========================
        case AddFixedExpenseCommand.COMMAND_WORD:
            return new AddFixedExpenseCommandParser().parse(arguments);

        case ClearFixedExpenseCommand.COMMAND_WORD:
            return new ClearFixedExpenseCommand();

        case DeleteFixedExpenseCommand.COMMAND_WORD:
            return new DeleteFixedExpenseCommandParser().parse(arguments);

        case EditFixedExpenseCommand.COMMAND_WORD:
            return new EditFixedExpenseCommandParser().parse(arguments);

        case CheckBudgetStatus.COMMAND_WORD:
            return new CheckBudgetStatus();

        case SortFixedExpenseCommand.COMMAND_WORD:
            return new SortFixedExpenseCommandParser().parse(arguments);

        case ListFixedExpenseCommand.COMMAND_WORD:
            return new ListFixedExpenseCommand();

        // ========================== Transport Booking Commands =========================
        case AddTransportBookingCommand.COMMAND_WORD:
            return new AddTransportBookingCommandParser().parse(arguments);

        case EditTransportBookingCommand.COMMAND_WORD:
            return new EditTransportBookingCommandParser().parse(arguments);

        case DeleteTransportBookingCommand.COMMAND_WORD:
            return new DeleteTransportBookingCommandParser().parse(arguments);

        case ClearTransportBookingCommand.COMMAND_WORD:
            return new ClearTransportBookingCommand();

        case SortTransportBookingCommand.COMMAND_WORD:
            return new SortTransportBookingCommandParser().parse(arguments);

        case ListTransportBookingCommand.COMMAND_WORD:
            return new ListTransportBookingCommand();

        // ========================== Packing List Commands =========================
        case AddItemCommand.COMMAND_WORD:
            return new AddItemCommandParser().parse(arguments);

        case AddPresetCommand.COMMAND_WORD:
            return new AddPresetCommandParser().parse(arguments);

        case CheckItemCommand.COMMAND_WORD:
            return new CheckItemCommandParser().parse(arguments);

        case ClearItemCommand.COMMAND_WORD:
            return new ClearItemCommand();

        case DeleteItemCommand.COMMAND_WORD:
            return new DeleteItemCommandParser().parse(arguments);

        case EditItemCommand.COMMAND_WORD:
            return new EditItemCommandParser().parse(arguments);

        case FindItemCommand.COMMAND_WORD:
            return new FindItemCommandParser().parse(arguments);

        case FindItemCategoryCommand.COMMAND_WORD:
            return new FindItemCategoryCommandParser().parse(arguments);

        case UncheckItemCommand.COMMAND_WORD:
            return new UncheckItemCommandParser().parse(arguments);

        case ListItemCommand.COMMAND_WORD:
            return new ListItemCommand();

        case ListPresetCommand.COMMAND_WORD:
            return new ListPresetCommand();

        case SortItemCommand.COMMAND_WORD:
            return new SortItemCommandParser().parse(arguments);


        // ========================== Activity Commands =========================
        case AddActivityCommand.COMMAND_WORD:
            return new AddActivityCommandParser().parse(arguments);

        case DeleteActivityCommand.COMMAND_WORD:
            return new DeleteActivityCommandParser().parse(arguments);

        case ClearActivityCommand.COMMAND_WORD:
            return new ClearActivityCommand();

        case EditActivityCommand.COMMAND_WORD:
            return new EditActivityCommandParser().parse(arguments);

        case FindActivityCommand.COMMAND_WORD:
            return new FindActivityCommandParser().parse(arguments);

        case FindActivityTagCommand.COMMAND_WORD:
            return new FindActivityTagCommandParser().parse(arguments);

        case ListActivityCommand.COMMAND_WORD:
            return new ListActivityCommand();

        case SortActivityCommand.COMMAND_WORD:
            return new SortActivityCommandParser().parse(arguments);

        // ========================== Accommodation Commands =========================
        case AddAccommodationBookingCommand.COMMAND_WORD:
            return new AddAccommodationBookingCommandParser().parse(arguments);

        case ClearAccommodationBookingCommand.COMMAND_WORD:
            return new ClearAccommodationBookingCommand();

        case DeleteAccommodationBookingCommand.COMMAND_WORD:
            return new DeleteAccommodationBookingCommandParser().parse(arguments);

        case EditAccommodationBookingCommand.COMMAND_WORD:
            return new EditAccommodationBookingCommandParser().parse(arguments);

        case SortAccommodationBookingCommand.COMMAND_WORD:
            return new SortAccommodationBookingParser().parse(arguments);

        case ListAccommodationBookingCommand.COMMAND_WORD:
            return new ListAccommodationBookingCommand();

        // ========================== Invalid Commands =========================
        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}

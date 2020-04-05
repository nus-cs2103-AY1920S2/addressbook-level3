package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HELP;

import java.util.ArrayList;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";
    public static final String COMMAND_FUNCTION1 = "Shows program usage instructions";
    public static final String COMMAND_FUNCTION2 = "Shows a command in detail.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": " + COMMAND_FUNCTION1 + ".\n"
            + COMMAND_WORD + " h/INDEX: " + COMMAND_FUNCTION2 + "\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_HELP + "3 ";

    public static final String MESSAGE = "Here are the list of commands: \n"
            + "1. " + HelpCommand.COMMAND_WORD + ": " + HelpCommand.COMMAND_FUNCTION1 + "\n"
            + "2. " + HelpCommand.COMMAND_WORD + " h/INDEX: shows command usage in detail "
            + "(e.g. \"help h/3\" for undo)\n"
            + "3. " + UndoCommand.COMMAND_WORD + ": " + UndoCommand.COMMAND_FUNCTION + "\n"
            + "4. " + RedoCommand.COMMAND_WORD + ": " + RedoCommand.COMMAND_FUNCTION + "\n"
            + "5. " + ClearCommand.COMMAND_WORD + ": " + ClearCommand.COMMAND_FUNCTION + "\n"
            + "6. " + ExitCommand.COMMAND_WORD + ": " + ExitCommand.COMMAND_FUNCTION + "\n"
            + "\nAddress Book Commands:\n"
            + "7. " + AddCommand.COMMAND_WORD + ": " + AddCommand.COMMAND_FUNCTION + "\n"
            + "8. " + DeleteCommand.COMMAND_WORD + ": " + DeleteCommand.COMMAND_FUNCTION + "\n"
            + "9. " + EditCommand.COMMAND_WORD + ": " + EditCommand.COMMAND_FUNCTION + "\n"
            + "10. " + AddInfoCommand.COMMAND_WORD + ": " + AddInfoCommand.COMMAND_FUNCTION + "\n"
            + "11. " + EditInfoCommand.COMMAND_WORD + ": " + EditInfoCommand.COMMAND_FUNCTION + "\n"
            + "12. " + DeleteInfoCommand.COMMAND_WORD + ": " + DeleteInfoCommand.COMMAND_FUNCTION + "\n"
            + "13. " + FindCommand.COMMAND_WORD + ": " + FindCommand.COMMAND_FUNCTION + "\n"
            + "14. " + ListCommand.COMMAND_WORD + ": " + ListCommand.COMMAND_FUNCTION + "\n"
            + "15. " + GetCommand.COMMAND_WORD + ": " + GetCommand.COMMAND_FUNCTION + "\n"
            + "16. " + ShowBirthdayCommand.COMMAND_WORD + ": " + ShowBirthdayCommand.COMMAND_FUNCTION + "\n"
            + "\nRestaurant Book Commands:\n"
            + "17. " + AddRestaurantCommand.COMMAND_WORD + ": " + AddRestaurantCommand.COMMAND_FUNCTION + "\n"
            + "18. " + DeleteRestaurantCommand.COMMAND_WORD + ": " + DeleteRestaurantCommand.COMMAND_FUNCTION + "\n"
            + "19. " + AddRestaurantNoteCommand.COMMAND_WORD + ": " + AddRestaurantNoteCommand.COMMAND_FUNCTION + "\n"
            + "20. " + VisitedRestaurantCommand.COMMAND_WORD + ": " + VisitedRestaurantCommand.COMMAND_FUNCTION + "\n"
            + "21. " + ListRestaurantCommand.COMMAND_WORD + ": " + ListRestaurantCommand.COMMAND_FUNCTION + "\n"
            + "22. " + "(rt)search [Coming Soon]\n"
            + "\nSchoolwork Tracker Commands:\n"
            + "23. " + AddAssignmentCommand.COMMAND_WORD + ": " + AddAssignmentCommand.COMMAND_FUNCTION + "\n"
            + "24. " + "(st)delete [Coming Soon]\n"
            + "25. " + ListAssignmentCommand.COMMAND_WORD + ": " + ListAssignmentCommand.COMMAND_FUNCTION + "\n"
            + "26. " + DoneCommand.COMMAND_WORD + ": " + DoneCommand.COMMAND_FUNCTION + "\n"
            + "27. " + ScheduleCommand.COMMAND_WORD + ": " + ScheduleCommand.COMMAND_FUNCTION + "\n"
            + "\nEvent Book Commands:\n"
            + "28. " + AddEventCommand.COMMAND_WORD + ": " + AddEventCommand.COMMAND_FUNCTION + "\n"
            + "29. " + ListEventCommand.COMMAND_WORD + ": " + ListEventCommand.COMMAND_FUNCTION + "\n";

    public static final ArrayList<String> COMMANDS = new ArrayList<>();
    public static final ArrayList<String> LIST_OF_COMMANDS = commandList(COMMANDS);

    public static final String MESSAGE_INVALID_INDEX = "Invalid index.";

    private final int type;

    /**
     * Creates an HelpCommand to add the specified {@code Person}
     */
    public HelpCommand(int type) {
        requireNonNull(type);
        this.type = type;
    }

    /**
     * Creates a list of all commands
     * @return list of commands
     */
    public static ArrayList<String> commandList(ArrayList<String> commands) {

        commands.add("1. " + HelpCommand.MESSAGE_USAGE + "\n");
        commands.add("2. " + HelpCommand.MESSAGE_USAGE + "\n");
        commands.add("3. " + UndoCommand.MESSAGE_USAGE + "\n");
        commands.add("4. " + RedoCommand.MESSAGE_USAGE + "\n");
        commands.add("5. " + ClearCommand.MESSAGE_USAGE + "\n");
        commands.add("6. " + ExitCommand.MESSAGE_USAGE + "\n");
        commands.add("7. " + AddCommand.MESSAGE_USAGE + "\n");
        commands.add("8. " + DeleteCommand.MESSAGE_USAGE + "\n");
        commands.add("9. " + EditCommand.MESSAGE_USAGE + "\n");
        commands.add("10. " + AddInfoCommand.MESSAGE_USAGE + "\n");
        commands.add("11. " + EditInfoCommand.MESSAGE_USAGE + "\n");
        commands.add("12. " + DeleteInfoCommand.MESSAGE_USAGE + "\n");
        commands.add("13. " + FindCommand.MESSAGE_USAGE + "\n");
        commands.add("14. " + ListCommand.MESSAGE_USAGE + "\n");
        commands.add("15. " + GetCommand.MESSAGE_USAGE + "\n");
        commands.add("16. " + ShowBirthdayCommand.MESSAGE_USAGE + "\n");
        commands.add("17. " + AddRestaurantCommand.MESSAGE_USAGE + "\n");
        commands.add("18. " + DeleteRestaurantCommand.MESSAGE_USAGE + "\n");
        commands.add("19. " + AddRestaurantNoteCommand.MESSAGE_USAGE + "\n");
        commands.add("20. " + VisitedRestaurantCommand.MESSAGE_USAGE + "\n");
        commands.add("21. " + ListRestaurantCommand.MESSAGE_USAGE + "\n");
        commands.add("22. " + "Coming Soon" + "\n");
        commands.add("23. " + AddAssignmentCommand.MESSAGE_USAGE + "\n");
        commands.add("24. " + "Coming Soon" + "\n");
        commands.add("25. " + ListAssignmentCommand.MESSAGE_USAGE + "\n");
        commands.add("26. " + DoneCommand.MESSAGE_USAGE + "\n");
        commands.add("27. " + ScheduleCommand.MESSAGE_USAGE + "\n");
        commands.add("28. " + AddEventCommand.MESSAGE_USAGE + "\n");
        commands.add("29. " + ListEventCommand.MESSAGE_USAGE + "\n");

        return commands;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        if (type > COMMANDS.size()) {
            throw new CommandException(MESSAGE_INVALID_INDEX);
        }

        if (type < 0) {
            return new CommandResult(MESSAGE, true, false, false, false,
                    false, false, false, false);
        }

        return new CommandResult(LIST_OF_COMMANDS.get(type - 1) + "\n"
                + "Type \"help\" to return to the list of commands.", true, false, false,
                false, false, false, false, false);
    }

}

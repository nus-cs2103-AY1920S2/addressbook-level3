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
            + "10. " + "(ab)addtag [Coming Soon]\n"
            + "11. " + "(ab)rmtag [Coming Soon]\n"
            + "12. " + AddInfoCommand.COMMAND_WORD + ": " + AddInfoCommand.COMMAND_FUNCTION + "\n"
            + "13. " + EditInfoCommand.COMMAND_WORD + ": " + EditInfoCommand.COMMAND_FUNCTION + "\n"
            + "14. " + DeleteInfoCommand.COMMAND_WORD + ": " + DeleteInfoCommand.COMMAND_FUNCTION + "\n"
            + "15. " + FindCommand.COMMAND_WORD + ": " + FindCommand.COMMAND_FUNCTION + "\n"
            + "16. " + ListCommand.COMMAND_WORD + ": " + ListCommand.COMMAND_FUNCTION + "\n"
            + "17. " + GetCommand.COMMAND_WORD + ": " + GetCommand.COMMAND_FUNCTION + "\n"
            + "18. " + ShowBirthdayCommand.COMMAND_WORD + ": " + ShowBirthdayCommand.COMMAND_FUNCTION + "\n"
            + "\nRestaurant Book Commands:\n"
            + "19. " + AddRestaurantCommand.COMMAND_WORD + ": " + AddRestaurantCommand.COMMAND_FUNCTION + "\n"
            + "20. " + DeleteRestaurantCommand.COMMAND_WORD + ": " + DeleteRestaurantCommand.COMMAND_FUNCTION + "\n"
            + "21. " + AddRestaurantNoteCommand.COMMAND_WORD + ": " + AddRestaurantNoteCommand.COMMAND_FUNCTION + "\n"
            + "22. " + VisitedRestaurantCommand.COMMAND_WORD + ": " + VisitedRestaurantCommand.COMMAND_FUNCTION + "\n"
            + "23. " + ListRestaurantCommand.COMMAND_WORD + ": " + ListRestaurantCommand.COMMAND_FUNCTION + "\n"
            + "24. " + "(rt)search [Coming Soon]\n"
            + "\nSchoolwork Tracker Commands:\n"
            + "25. " + AddAssignmentCommand.COMMAND_WORD + ": " + AddAssignmentCommand.COMMAND_FUNCTION + "\n"
            + "26. " + DeleteAssignmentCommand.COMMAND_WORD + ": " + DeleteAssignmentCommand.COMMAND_FUNCTION + "\n"
            + "27. " + ListAssignmentCommand.COMMAND_WORD + ": " + ListAssignmentCommand.COMMAND_FUNCTION + "\n"
            + "28. " + DoneCommand.COMMAND_WORD + ": " + DoneCommand.COMMAND_FUNCTION + "\n"
            + "29. " + ScheduleCommand.COMMAND_WORD + ": " + ScheduleCommand.COMMAND_FUNCTION + "\n"
            + "\nEvent Book Commands:\n"
            + "30. " + AddEventCommand.COMMAND_WORD + ": " + AddEventCommand.COMMAND_FUNCTION + "\n"
            + "31. " + ListEventCommand.COMMAND_WORD + ": " + ListEventCommand.COMMAND_FUNCTION + "\n";

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
        commands.add("10. " + "Coming Soon" + "\n");
        commands.add("11. " + "Coming Soon" + "\n");
        commands.add("12. " + AddInfoCommand.MESSAGE_USAGE + "\n");
        commands.add("13. " + EditInfoCommand.MESSAGE_USAGE + "\n");
        commands.add("14. " + DeleteInfoCommand.MESSAGE_USAGE + "\n");
        commands.add("15. " + FindCommand.MESSAGE_USAGE + "\n");
        commands.add("16. " + ListCommand.MESSAGE_USAGE + "\n");
        commands.add("17. " + GetCommand.MESSAGE_USAGE + "\n");
        commands.add("18. " + ShowBirthdayCommand.MESSAGE_USAGE + "\n");
        commands.add("19. " + AddRestaurantCommand.MESSAGE_USAGE + "\n");
        commands.add("20. " + DeleteRestaurantCommand.MESSAGE_USAGE + "\n");
        commands.add("21. " + AddRestaurantNoteCommand.MESSAGE_USAGE + "\n");
        commands.add("22. " + VisitedRestaurantCommand.MESSAGE_USAGE + "\n");
        commands.add("23. " + ListRestaurantCommand.MESSAGE_USAGE + "\n");
        commands.add("24. " + "Coming Soon" + "\n");
        commands.add("25. " + AddAssignmentCommand.MESSAGE_USAGE + "\n");
        commands.add("26. " + DeleteAssignmentCommand.MESSAGE_USAGE + "\n");
        commands.add("27. " + ListAssignmentCommand.MESSAGE_USAGE + "\n");
        commands.add("28. " + DoneCommand.MESSAGE_USAGE + "\n");
        commands.add("29. " + ScheduleCommand.MESSAGE_USAGE + "\n");
        commands.add("30. " + AddEventCommand.MESSAGE_USAGE + "\n");
        commands.add("31. " + ListEventCommand.MESSAGE_USAGE + "\n");

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

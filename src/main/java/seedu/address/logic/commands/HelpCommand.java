package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HELP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

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
            + "Example: " + COMMAND_WORD
            + COMMAND_WORD + ": " + COMMAND_FUNCTION2 + "\n"
            + "Parameters: [" + PREFIX_NAME + "NAME] "
            + "Example: " + COMMAND_WORD + PREFIX_HELP + "3 ";

    public static final String MESSAGE = "Here are the list of commands: \n"
            + "1. " + AddCommand.COMMAND_WORD + ": " + AddCommand.COMMAND_FUNCTION + "\n"
            + "2. " + EditCommand.COMMAND_WORD + ": " + EditCommand.COMMAND_FUNCTION + "\n"
            + "3. " + DeleteCommand.COMMAND_WORD + ": " + DeleteCommand.COMMAND_FUNCTION + "\n"
            + "4. " + ListCommand.COMMAND_WORD + ": " + ListCommand.COMMAND_FUNCTION + "\n"
            + "5. " + AddInfoCommand.COMMAND_WORD + ": " + AddInfoCommand.COMMAND_FUNCTION + "\n"
            + "6. " + EditInfoCommand.COMMAND_WORD + ": " + EditInfoCommand.COMMAND_FUNCTION + "\n"
            + "7. " + DeleteInfoCommand.COMMAND_WORD + ": " + DeleteInfoCommand.COMMAND_FUNCTION + "\n"
            + "8. " + GetCommand.COMMAND_WORD + ": " + GetCommand.COMMAND_FUNCTION + "\n"
            + "9. " + FindCommand.COMMAND_WORD + ": " + FindCommand.COMMAND_FUNCTION + "\n"
            + "10. " + ClearCommand.COMMAND_WORD + ": " + ClearCommand.COMMAND_FUNCTION + "\n"
            + "11. " + ShowBirthdayCommand.COMMAND_WORD + ": " + ShowBirthdayCommand.COMMAND_FUNCTION + "\n"
            + "12. " + AddAssignmentCommand.COMMAND_WORD + ": " + AddAssignmentCommand.COMMAND_FUNCTION + "\n"
            + "13. " + DoneCommand.COMMAND_WORD + ": " + DoneCommand.COMMAND_FUNCTION + "\n"
            + "14. " + ListAssignmentCommand.COMMAND_WORD + ": " + ListAssignmentCommand.COMMAND_FUNCTION + "\n"
            + "15. " + ScheduleCommand.COMMAND_WORD + ": " + ScheduleCommand.COMMAND_FUNCTION + "\n"
            + "16. " + AddRestaurantCommand.COMMAND_WORD + ": " + AddRestaurantCommand.COMMAND_FUNCTION + "\n"
            + "17. " + DeleteRestaurantCommand.COMMAND_WORD + ": " + DeleteRestaurantCommand.COMMAND_FUNCTION + "\n"
            + "18. " + ListRestaurantCommand.COMMAND_WORD + ": " + ListRestaurantCommand.COMMAND_FUNCTION + "\n"
            + "19. " + VisitedRestaurantCommand.COMMAND_WORD + ": " + VisitedRestaurantCommand.COMMAND_FUNCTION + "\n"
            + "20. " + AddRestaurantNoteCommand.COMMAND_WORD + ": " + AddRestaurantNoteCommand.COMMAND_FUNCTION + "\n"
            + "21. " + UndoCommand.COMMAND_WORD + ": " + UndoCommand.COMMAND_FUNCTION + "\n"
            + "22. " + RedoCommand.COMMAND_WORD + ": " + RedoCommand.COMMAND_FUNCTION + "\n"
            + "23. " + ExitCommand.COMMAND_WORD + ": " + ExitCommand.COMMAND_FUNCTION + "\n"
            + "24. " + HelpCommand.COMMAND_WORD + ": " + HelpCommand.COMMAND_FUNCTION1 + "\n"
            + " OR " + HelpCommand.COMMAND_FUNCTION2 + "\n"
            + "25. " + AddEventCommand.COMMAND_WORD + ": " + AddEventCommand.COMMAND_FUNCTION + "\n"
            + "26. " + ListEventCommand.COMMAND_WORD + ": " + ListEventCommand.COMMAND_FUNCTION + "\n"
            + "\nYou can find out more on how a command works by typing the command help, and "
            + "specifying the command index.\n"
            + "Example: For (ab)delete - " + COMMAND_WORD + " h/ 3";
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

        commands.add("1. " + AddCommand.MESSAGE_USAGE + "\n");
        commands.add("2. " + EditCommand.MESSAGE_USAGE + "\n");
        commands.add("3. " + DeleteCommand.MESSAGE_USAGE + "\n");
        commands.add("4. " + ListCommand.MESSAGE_USAGE + "\n");
        commands.add("5. " + AddInfoCommand.MESSAGE_USAGE + "\n");
        commands.add("6. " + EditInfoCommand.MESSAGE_USAGE + "\n");
        commands.add("7. " + DeleteInfoCommand.MESSAGE_USAGE + "\n");
        commands.add("8. " + GetCommand.MESSAGE_USAGE + "\n");
        commands.add("9. " + FindCommand.MESSAGE_USAGE + "\n");
        commands.add("10. " + ClearCommand.MESSAGE_USAGE + "\n");
        commands.add("11. " + ShowBirthdayCommand.MESSAGE_USAGE + "\n");
        commands.add("12. " + AddAssignmentCommand.MESSAGE_USAGE + "\n");
        commands.add("13. " + DoneCommand.MESSAGE_USAGE + "\n");
        commands.add("14. " + ListAssignmentCommand.MESSAGE_USAGE + "\n");
        commands.add("15. " + ScheduleCommand.MESSAGE_USAGE + "\n");
        commands.add("16. " + AddRestaurantCommand.MESSAGE_USAGE + "\n");
        commands.add("17. " + DeleteRestaurantCommand.MESSAGE_USAGE + "\n");
        commands.add("18. " + ListRestaurantCommand.MESSAGE_USAGE + "\n");
        commands.add("19. " + VisitedRestaurantCommand.MESSAGE_USAGE + "\n");
        commands.add("20. " + AddRestaurantNoteCommand.MESSAGE_USAGE + "\n");
        commands.add("21. " + UndoCommand.MESSAGE_USAGE + "\n");
        commands.add("22. " + RedoCommand.MESSAGE_USAGE + "\n");
        commands.add("23. " + ExitCommand.MESSAGE_USAGE + "\n");
        commands.add("24. " + HelpCommand.MESSAGE_USAGE + "\n");
        commands.add("25. " + AddEventCommand.MESSAGE_USAGE + "\n");
        commands.add("26. " + ListEventCommand.MESSAGE_USAGE + "\n");

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
                + "Type help to return to the list of commands.", true, false, false,
                false, false, false, false, false);
    }

}

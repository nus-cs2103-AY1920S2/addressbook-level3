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
            + "8. " + ClearCommand.COMMAND_WORD + ": " + ClearCommand.COMMAND_FUNCTION + "\n"
            + "9. " + GetCommand.COMMAND_WORD + ": " + GetCommand.COMMAND_FUNCTION + "\n"
            + "10. " + FindCommand.COMMAND_WORD + ": " + FindCommand.COMMAND_FUNCTION + "\n"
            + "11. " + AddAssignmentCommand.COMMAND_WORD + ": " + AddAssignmentCommand.COMMAND_FUNCTION + "\n"
            + "12. " + DoneCommand.COMMAND_WORD + ": " + DoneCommand.COMMAND_FUNCTION + "\n"
            + "13. " + ListAssignmentCommand.COMMAND_WORD + ": " + ListAssignmentCommand.COMMAND_FUNCTION + "\n"
            + "14. " + AddRestaurantCommand.COMMAND_WORD + ": " + AddRestaurantCommand.COMMAND_FUNCTION + "\n"
            + "15. " + ExitCommand.COMMAND_WORD + ": " + ExitCommand.COMMAND_FUNCTION + "\n"
            + "16. " + HelpCommand.COMMAND_WORD + ": " + HelpCommand.COMMAND_FUNCTION1
            + " OR " + HelpCommand.COMMAND_FUNCTION2 + "\n"
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
        commands.add("8. " + ClearCommand.MESSAGE_USAGE + "\n");
        commands.add("9. " + GetCommand.MESSAGE_USAGE + "\n");
        commands.add("10. " + FindCommand.MESSAGE_USAGE + "\n");
        commands.add("11. " + AddAssignmentCommand.MESSAGE_USAGE + "\n");
        commands.add("12. " + DoneCommand.MESSAGE_USAGE + "\n");
        commands.add("13. " + ListAssignmentCommand.MESSAGE_USAGE + "\n");
        commands.add("14. " + AddRestaurantCommand.MESSAGE_USAGE + "\n");
        commands.add("15. " + ExitCommand.MESSAGE_USAGE + "\n");
        commands.add("16. " + HelpCommand.MESSAGE_USAGE + "\n");

        return commands;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        if (type > COMMANDS.size()) {
            throw new CommandException(MESSAGE_INVALID_INDEX);
        }

        if (type < 0) {
            return new CommandResult(MESSAGE, true, false, false, false);
        }

        return new CommandResult(LIST_OF_COMMANDS.get(type - 1) + "\n"
                + "Type help to return to the list of commands.", true, false, false, false);
    }

}

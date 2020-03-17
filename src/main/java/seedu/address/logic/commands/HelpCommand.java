package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";
    public static final String COMMAND_FUNCTION = "Shows program usage instructions.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": " + COMMAND_FUNCTION + "\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE = "Here are the list of commands: \n"
            + "1. " + AddCommand.COMMAND_WORD + ": " + AddCommand.COMMAND_FUNCTION + "\n"
            + "2. " + EditCommand.COMMAND_WORD + ": " + EditCommand.COMMAND_FUNCTION + "\n"
            + "3. " + DeleteCommand.COMMAND_WORD + ": " + DeleteCommand.COMMAND_FUNCTION + "\n"
            + "4. " + ListCommand.COMMAND_WORD + ": " + ListCommand.COMMAND_FUNCTION + "\n"
            + "5. " + AddInfoCommand.COMMAND_WORD + ": " + AddInfoCommand.COMMAND_FUNCTION + "\n"
            + "6. " + EditInfoCommand.COMMAND_WORD + ": " + EditInfoCommand.COMMAND_FUNCTION + "\n"
            + "7. " + DeleteInfoCommand.COMMAND_WORD + ": " + DeleteInfoCommand.COMMAND_FUNCTION + "\n"
            + "8. " + ClearCommand.COMMAND_WORD + ": " + ClearCommand.COMMAND_FUNCTION + "\n"
            + "9. " + FindCommand.COMMAND_WORD + ": " + FindCommand.COMMAND_FUNCTION + "\n"
            + "10. " + ExitCommand.COMMAND_WORD + ": " + ExitCommand.COMMAND_FUNCTION + "\n"
            + "11. " + HelpCommand.COMMAND_WORD + ": " + HelpCommand.COMMAND_FUNCTION + "\n"
            + "12. " + AddRestaurantCommand.COMMAND_WORD + ": " + AddRestaurantCommand.COMMAND_FUNCTION + "\n"
            + "\nYou can find out more on how a command works by specifying the command index.\n"
            + "Example: For (ab)delete - " + COMMAND_WORD + " h/ 3";
    public static final ArrayList<String> LIST_OF_COMMANDS = commandList();

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
    public static ArrayList<String> commandList() {
        ArrayList<String> commands = new ArrayList<>();

        commands.add("1. " + AddCommand.MESSAGE_USAGE + "\n");
        commands.add("2. " + EditCommand.MESSAGE_USAGE + "\n");
        commands.add("3. " + DeleteCommand.MESSAGE_USAGE + "\n");
        commands.add("4. " + ListCommand.MESSAGE_USAGE + "\n");
        commands.add("5. " + AddInfoCommand.MESSAGE_USAGE + "\n");
        commands.add("6. " + EditInfoCommand.MESSAGE_USAGE + "\n");
        commands.add("7. " + DeleteInfoCommand.MESSAGE_USAGE + "\n");
        commands.add("8. " + ClearCommand.MESSAGE_USAGE + "\n");
        commands.add("9. " + FindCommand.MESSAGE_USAGE + "\n");
        commands.add("10. " + ExitCommand.MESSAGE_USAGE + "\n");
        commands.add("11. " + HelpCommand.MESSAGE_USAGE + "\n");

        return commands;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        if (type > 11) {
            throw new CommandException(MESSAGE_INVALID_INDEX);
        }

        if (type < 0) {
            return new CommandResult(MESSAGE, true, false);
        }

        return new CommandResult(LIST_OF_COMMANDS.get(type - 1) + "\n"
                + "Type help to return to the list of commands.", true, false);
    }

}

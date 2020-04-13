package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.storage.ExportFile;

/**
 * Exports application Life to CSV.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports application Life to CSV File specified. \n"
            + "All 3 parameters must be included for successful import: \n"
            + "l/LIFE_FILE_PATH \n"
            + "g/GROUP_FILE_PATH \n"
            + "e/EVENT_FILE_PATH.";

    public static final String MESSAGE_SUCCESS = "Files exported:"
            + "\n"
            + "%1$s \n"
            + "%2$s \n"
            + "%3$s";
    public static final String EMPTY_FILE = "Unable to export empty life/group/events."
            + " Please add contacts/groups and try again.";
    private final String groupFilePath;
    private final String lifeFilePath;
    private final String eventFilePath;

    /**
     * Creates an ImportCommand to add the specified {@code Person}
     */
    public ExportCommand(String lifeFilePath, String groupFilePath, String eventFilePath) {
        this.lifeFilePath = lifeFilePath;
        this.groupFilePath = groupFilePath;
        this.eventFilePath = eventFilePath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {
            if (!lifeFilePath.isEmpty()) {
                new ExportFile().exportCsv(String.valueOf(model.getAddressBookFilePath()), lifeFilePath);
            }
            if (!groupFilePath.isEmpty()) {
                new ExportFile().exportGroupCsv(String.valueOf(model.getAddressBookFilePath()), groupFilePath);
            }

            if (!eventFilePath.isEmpty()) {
                new ExportFile().exportEventCsv(String.valueOf(model.getAddressBookFilePath()), eventFilePath);
            }

            return new CommandResult(String.format(MESSAGE_SUCCESS, lifeFilePath, groupFilePath, eventFilePath));
        } catch (Exception e) {
            throw new CommandException(String.format(EMPTY_FILE), e);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExportCommand // instanceof handles nulls
                && lifeFilePath.equals(((ExportCommand) other).lifeFilePath));
    }
}

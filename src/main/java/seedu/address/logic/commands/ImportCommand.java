package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.storage.ImportFile;

/**
 * Imports application csv to the CoderLifeInsights.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD
                    + ": Imports application csv to the CoderLifeInsights. \n"
                    + "All 3 parameters must be included for successful import: \n"
                    + "l/LIFE_FILE_PATH \n"
                    + "g/GROUP_FILE_PATH \n"
                    + "e/EVENT_FILE_PATH.";

    public static final String MESSAGE_SUCCESS = "Files imported:"
            + "\n"
            + "%1$s \n"
            + "%2$s \n"
            + "%3$s";
    public static final String MESSAGE_DUPLICATE_PERSON =
            "Person already exists in the CoderLifeInsights. "
                    + "Please delete duplicate in your import file/current CoderLifeInsights.";
    public static final String MESSAGE_DUPLICATE_GROUP =
            "Group already exists in the address book. "
                    + "Please delete duplicate in your import file/current CoderLifeInsights.";
    public static final String MESSAGE_DUPLICATE_EVENT =
            "Event already exists in the address book. "
                    + "Please delete duplicate in your import file/current CoderLifeInsights.";
    private final String groupFilePath;
    private final String lifeFilePath;
    private final String eventFilePath;

    /**
     * Creates an ImportCommand to add the specified {@code Person}
     */
    public ImportCommand(String lifeFilePath, String groupFilePath, String eventFilePath) {
        this.lifeFilePath = lifeFilePath;
        this.groupFilePath = groupFilePath;
        this.eventFilePath = eventFilePath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!groupFilePath.isEmpty()) {
            List<Group> importedGroup = new ImportFile().importGroupCsv(groupFilePath);
            if (model.hasGroups(importedGroup)) {
                throw new CommandException(MESSAGE_DUPLICATE_GROUP);
            }
            model.importCsvGroupsToAddressBook(importedGroup);
        }

        if (!lifeFilePath.isEmpty()) {
            List<Person> importedPeople = new ImportFile().importCsv(lifeFilePath);
            if (model.hasPersons(importedPeople)) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            }
            model.importCsvToAddressBook(importedPeople);
        }

        if (!eventFilePath.isEmpty()) {
            List<Event> importedEvent = new ImportFile().importEventCsv(eventFilePath);
            if (model.hasEvents(importedEvent)) {
                throw new CommandException(MESSAGE_DUPLICATE_EVENT);
            }
            model.importCsvEventsToAddressBook(importedEvent);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, lifeFilePath, groupFilePath, eventFilePath));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && lifeFilePath.equals(((ImportCommand) other).lifeFilePath));
    }
}

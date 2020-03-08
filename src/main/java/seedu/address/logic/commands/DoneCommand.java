package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Description;
import seedu.address.model.person.Done;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Priority;
import seedu.address.model.tag.Tag;

/** Deletes a person identified using it's displayed index from the address book. */
public class DoneCommand extends Command {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD
                    + ": Marks the task identified by the index number used in the displayed task list as done.\n"
                    + "Parameters: 1-INDEXed (must be a positive integer)\n"
                    + "Example: "
                    + COMMAND_WORD
                    + " 1";

    public static final String MESSAGE_DONE_TASK_SUCCESS = "Done Task(s): ";

    private final Index[] targetIndices;

    public DoneCommand(Index[] targetIndices) {
        this.targetIndices = targetIndices;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        StringBuilder tasksDone = new StringBuilder(MESSAGE_DONE_TASK_SUCCESS);
        // List<Person> toDeletePersons = new ArrayList<>();
        for (Index targetIndex : targetIndices) {
            targetIndex.getZeroBased();
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            // Person person = lastShownList.get(targetIndex.getZeroBased());
            Person personToEdit = lastShownList.get(targetIndex.getZeroBased());
            Person editedPerson = createDoneTask(personToEdit);
            tasksDone.append(String.format("%n%s", editedPerson));
            model.setPerson(personToEdit, editedPerson);
        }
        // model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(tasksDone.toString());
    }

    private static Person createDoneTask(Person personToEdit) {
        assert personToEdit != null;

        Name updatedName = personToEdit.getName();
        Priority updatedPriority = personToEdit.getPriority();
        Email updatedEmail = personToEdit.getEmail();
        Description updatedDescription = personToEdit.getDescription();
        Set<Tag> updatedTags = personToEdit.getTags();

        return new Person(
                updatedName,
                updatedPriority,
                updatedEmail,
                updatedDescription,
                new Done("Y"),
                updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DoneCommand // instanceof handles nulls
                        && targetIndices.equals(
                                ((DoneCommand) other)
                                        .targetIndices)); // TODO check if non primitive data will
        // be checked
    }
}

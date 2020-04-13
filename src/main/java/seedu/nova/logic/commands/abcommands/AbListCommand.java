package seedu.nova.logic.commands.abcommands;

import static java.util.Objects.requireNonNull;
import static seedu.nova.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.nova.logic.commands.Command;
import seedu.nova.logic.commands.CommandResult;
import seedu.nova.model.Model;

/**
 * Lists all persons in the nova book to the user.
 */
public class AbListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        String listOfPeople = model.getAddressBook().getPersonList().size() > 0
                ? "Listed all contacts. Remarks will only appear with "
                + "list c\\classmate or list c\\teammate command.\n\n"
                : "There is no contact saved.";
        for (int i = 0; i < model.getAddressBook().getPersonList().size(); i++) {
            listOfPeople = listOfPeople + (i + 1) + ". " + model.getAddressBook().getPersonList().get(i) + "\n";
        }

        return new CommandResult(listOfPeople);
    }
}

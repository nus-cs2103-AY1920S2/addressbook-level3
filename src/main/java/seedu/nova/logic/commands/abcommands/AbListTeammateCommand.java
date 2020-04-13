package seedu.nova.logic.commands.abcommands;

import static java.util.Objects.requireNonNull;

import seedu.nova.logic.commands.CommandResult;
import seedu.nova.model.Model;
import seedu.nova.model.person.CategoryContainsKeywordsPredicate;

/**
 * Lists all persons in the address book to the user.
 */
public class AbListTeammateCommand extends AbListCategoryCommand {

    private final CategoryContainsKeywordsPredicate predicate;

    public AbListTeammateCommand(CategoryContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        String listOfPeople = model.getFilteredPersonList().size() > 0
                ? "Listed teammate:\n" : "There is no teammate contact saved.";
        for (int i = 0; i < model.getFilteredPersonList().size(); i++) {
            listOfPeople = listOfPeople + (i + 1) + ". " + model.getFilteredPersonList().get(i) + ".\n"
                    + "    Remark: " + model.getFilteredPersonList().get(i).getRemark()
                    + "\n";
        }

        return new CommandResult(listOfPeople);
    }
}

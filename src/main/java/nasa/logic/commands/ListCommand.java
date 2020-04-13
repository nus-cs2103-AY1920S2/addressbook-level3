package nasa.logic.commands;

import static java.util.Objects.requireNonNull;
import static nasa.model.Model.PREDICATE_SHOW_ALL_ACTIVITIES;
import static nasa.model.Model.PREDICATE_SHOW_ALL_MODULES;

import java.util.ArrayList;

import nasa.model.Model;
import nasa.model.module.ModuleCode;
import nasa.model.module.NameContainsKeywordsPredicate;

/**
 * Lists all modules and their activity lists to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all modules";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List all modules.\n"
            + "Parameters: none\n Example: " + COMMAND_WORD;

    private final ModuleCode moduleCode;

    public ListCommand(ModuleCode moduleCode) {
        this.moduleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (moduleCode == null) {
            model.updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
            model.updateFilteredActivityList(PREDICATE_SHOW_ALL_ACTIVITIES);
            model.updateHistory("list" + model.currentUiLocation());
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            ArrayList<String> list = new ArrayList<>();
            list.add(moduleCode.toString());
            if (model.hasModule(moduleCode)) {
                model.updateFilteredModuleList(new NameContainsKeywordsPredicate(list));
                model.updateHistory("list" + model.currentUiLocation());
                return new CommandResult(moduleCode + " listed successfully!");
            } else {
                return new CommandResult("Module cannot be found!");
            }
        }
    }
}

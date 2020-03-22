package nasa.logic.commands.addcommands;

import static java.util.Objects.requireNonNull;

import nasa.logic.commands.Command;
import nasa.logic.commands.CommandResult;
import nasa.logic.commands.exceptions.CommandException;
import nasa.model.Model;
import nasa.model.activity.Activity;
import nasa.model.module.ModuleCode;

/**
 * Adds any activity to a module's list.
 */
public class AddCommand extends Command {
    public static final String MESSAGE_SUCCESS = "New activity added!";
    public static final String MESSAGE_DUPLICATED_ACTIVITY =
        "This activity already exist in the module's activity list!";

    private final Activity toAdd;
    private final ModuleCode moduleCode;

    /**
     * Creates an AddCommand that adds {@code activity} to list of {@code moduleCode}.
     * @param activity Activity to be added
     * @param moduleCode Module where the activity is to be added
     */
    public AddCommand(Activity activity, ModuleCode moduleCode) {
        requireNonNull(activity);
        requireNonNull(moduleCode);
        toAdd = activity;
        this.moduleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        /*
         * Create a dummy moduleTask so as to add the activity to that
         * associated module in the ModelNasa model.
         */
        if (model.hasActivity(moduleCode, toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATED_ACTIVITY);
        }
        model.addActivity(moduleCode, toAdd);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd)
                && moduleCode.equals(((AddCommand) other).moduleCode));
    }
}

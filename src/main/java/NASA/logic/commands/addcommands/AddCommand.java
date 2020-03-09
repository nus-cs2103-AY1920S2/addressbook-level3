package NASA.logic.commands.addcommands;

import static java.util.Objects.requireNonNull;

import NASA.logic.commands.Command;
import NASA.logic.commands.CommandResult;
import NASA.logic.commands.exceptions.CommandException;
import NASA.model.ModelNasa;
import NASA.model.activity.Activity;
import NASA.model.module.Module;
import NASA.model.module.ModuleCode;
import NASA.model.module.ModuleName;

public class AddCommand extends Command {

    private final Activity toAdd;
    private final ModuleCode moduleCode;

    public static final String MESSAGE_SUCCESS = "New activity added!";
    public static final String MESSAGE_DUPLICATED_ACTIVITY = "This activity already exist in the module's activity list!";

    public AddCommand(Activity activity, ModuleCode moduleCode) {
        requireNonNull(activity);
        requireNonNull(moduleCode);
        toAdd = activity;
        this.moduleCode = moduleCode;
    }

    @Override
    public CommandResult execute(ModelNasa model) throws CommandException {
        requireNonNull(model);
        /*
         * Create a dummy moduleTask so as to add the activity to that
         * associated module in the ModelNasa model.
         */
        Module moduleTask = new Module(moduleCode, new ModuleName(""));
        if (model.hasActivity(moduleTask, toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATED_ACTIVITY);
        }
        model.addActivity(moduleTask, toAdd);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}

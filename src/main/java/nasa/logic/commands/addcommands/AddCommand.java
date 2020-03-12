package nasa.logic.commands.addcommands;

import static java.util.Objects.requireNonNull;

import nasa.logic.commands.Command;
import nasa.logic.commands.CommandResult;
import nasa.logic.commands.exceptions.CommandException;
import nasa.model.Model;
import nasa.model.activity.Activity;
import nasa.model.module.ModuleCode;

public class AddCommand extends Command {

    private final Activity toAdd;
    private final ModuleCode moduleCode;

    public static final String MESSAGE_SUCCESS = "New lesson activity added!";
    public static final String MESSAGE_DUPLICATED_ACTIVITY = "This activity already exist in the module's activity list!";

    public AddCommand(Activity activity, ModuleCode moduleCode) {
        requireNonNull(activity);
        requireNonNull(moduleCode);
        toAdd = activity;
        this.moduleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        // TODO add the necessary implementation once model is done
        return new CommandResult("");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}

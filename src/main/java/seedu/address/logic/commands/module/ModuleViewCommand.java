package seedu.address.logic.commands.module;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.facilitator.ModuleCodesContainKeywordPredicate;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;

/**
 * Shows all information of a module in Mod Manager to the user.
 */
public class ModuleViewCommand extends ModuleCommand {

    public static final String MESSAGE_USAGE = COMMAND_GROUP_MOD + " " + COMMAND_WORD_VIEW
            + ": Views a module identified by the index number or module code used in the displayed module list.\n"
            + ModuleCode.MESSAGE_CONSTRAINTS + "\n"
            + "Parameters: MOD_CODE \n"
            + "Example: " + COMMAND_GROUP_MOD + " " + COMMAND_WORD_VIEW + " CS2103T \n"
            + "Parameters: INDEX \n"
            + "Example: " + COMMAND_GROUP_MOD + " " + COMMAND_WORD_VIEW + " 1 ";
    public static final String MESSAGE_MODULE_DOES_NOT_EXIST = "The module %1$s does not exist in Mod Manager.";

    public static final String MESSAGE_SUCCESS = "Viewed module: %1$s";

    private final Index targetIndex;
    private final ModuleCode moduleCode;

    public ModuleViewCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
        this.moduleCode = null;
    }

    public ModuleViewCommand(ModuleCode moduleCode) {
        requireNonNull(moduleCode);
        this.targetIndex = null;
        this.moduleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Module> lastShownList = model.getFilteredModuleList();
        Optional<Module> module;

        if (targetIndex != null && targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
        } else if (targetIndex != null) {
            module = Optional.of(lastShownList.get(targetIndex.getZeroBased()));
        } else {
            module = model.findModule(moduleCode);
            if (module.isEmpty()) {
                throw new CommandException(String.format(MESSAGE_MODULE_DOES_NOT_EXIST, moduleCode));
            }
        }

        model.updateModule(module);
        model.updateFacilitatorListForModule(new ModuleCodesContainKeywordPredicate(
                module.get().getModuleCode().value));
        model.updateTaskListForModule(x -> x.getModuleCode().equals(module.get().getModuleCode()));
        return new CommandResult(String.format(MESSAGE_SUCCESS, module.get()), CommandType.MODULE);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ModuleViewCommand)) {
            return false;
        }

        ModuleViewCommand command = (ModuleViewCommand) other;

        // similar nullability check
        boolean indexNull = (targetIndex == null && command.targetIndex == null)
                || (targetIndex != null && command.targetIndex != null);
        boolean moduleCodeNull = (moduleCode == null && command.moduleCode == null)
                || (moduleCode != null && command.moduleCode != null);

        return indexNull && moduleCodeNull
                && (targetIndex == null || targetIndex.equals(command.targetIndex))
                && (moduleCode == null || moduleCode.equals(command.moduleCode));
    }
}

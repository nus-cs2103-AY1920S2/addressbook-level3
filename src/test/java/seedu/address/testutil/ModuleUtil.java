package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.module.ModuleEditCommand;
import seedu.address.model.module.Module;

/**
 * A utility class for Module.
 */
public class ModuleUtil {
    /**
     * Returns an add command string for adding the {@code module}.
     */
    public static String getModuleAddCommand(Module module) {
        return Command.COMMAND_WORD_ADD + " " + getModuleDetails(module);
    }

    /**
     * Returns the part of command string for the given {@code facilitator}'s details.
     */
    public static String getModuleDetails(Module module) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_MODULE_CODE).append(" ").append(module.getModuleCode().value).append(" ");
        sb.append(PREFIX_DESCRIPTION).append(" ").append(module.getDescription().value).append(" ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditModuleDescriptor}'s details.
     */
    public static String getEditModuleDescriptorDetails(ModuleEditCommand.EditModuleDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getModuleCode().ifPresent(moduleCode -> sb.append(PREFIX_MODULE_CODE)
                .append(" ").append(moduleCode.value).append(" "));
        descriptor.getDescription().ifPresent(description -> sb.append(PREFIX_DESCRIPTION)
                .append(" ").append(description.value).append(" "));
        return sb.toString();
    }
}

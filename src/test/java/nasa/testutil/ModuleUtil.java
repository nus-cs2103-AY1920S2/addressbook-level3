package nasa.testutil;

import static nasa.logic.parser.CliSyntax.PREFIX_MODULE;
import static nasa.logic.parser.CliSyntax.PREFIX_MODULE_NAME;

import nasa.logic.commands.module.AddModuleCommand;
import nasa.model.module.Module;

/**
 * A utility class for Module.
 */
public class ModuleUtil {

    /**
     * Returns an add command string for adding the {@code module}.
     */
    public static String getAddModuleCommand(Module module) {
        return AddModuleCommand.COMMAND_WORD + " " + getModuleDetails(module);
    }

    /**
     * Returns the part of command string for the given {@code module}'s details.
     */
    public static String getModuleDetails(Module module) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_MODULE + module.getModuleName().toString() + " ");
        sb.append(PREFIX_MODULE_NAME + module.getModuleCode().toString() + " ");
        /*
        module.getActivities().getActivityList().stream().forEach(

            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );

         */
        return sb.toString();
    }


    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    /*
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }

     */
}

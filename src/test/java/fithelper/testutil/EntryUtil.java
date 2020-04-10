package fithelper.testutil;

import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_CALORIE;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_LOCATION;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_NAME;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_TIME;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_TYPE;

import fithelper.logic.commands.AddCommand;
import fithelper.logic.commands.EditCommand.EditEntryDescriptor;
import fithelper.model.entry.Entry;

/**
 * A utility class for Entry.
 */
public class EntryUtil {

    /**
     * Returns an add command string for adding the {@code entry}.
     */
    public static String getAddCommand(Entry entry) {
        return AddCommand.COMMAND_WORD + " " + getEntryDetails(entry);
    }

    /**
     * Returns the part of command string for the given {@code entry}'s details.
     */
    public static String getEntryDetails(Entry entry) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + entry.getName().value + " ");
        sb.append(PREFIX_TYPE + entry.getType().toString() + " ");
        sb.append(PREFIX_TIME + entry.getTime().value + " ");
        sb.append(PREFIX_LOCATION + entry.getLocation().value + " ");
        sb.append(PREFIX_CALORIE + entry.getCalorie().toString() + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditEntryDescriptor}'s details.
     */
    public static String getEditEntryDescriptorDetails(EditEntryDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.value).append(" "));
        sb.append(PREFIX_TYPE).append(descriptor.getType().toString()).append(" ");
        descriptor.getTime().ifPresent(time -> sb.append(PREFIX_TIME).append(time.value).append(" "));
        descriptor.getLocation().ifPresent(location -> sb.append(PREFIX_LOCATION).append(location.value).append(" "));
        descriptor.getCalorie().ifPresent(calorie -> sb.append(PREFIX_CALORIE).append(calorie.value).append(" "));
        return sb.toString();
    }

}



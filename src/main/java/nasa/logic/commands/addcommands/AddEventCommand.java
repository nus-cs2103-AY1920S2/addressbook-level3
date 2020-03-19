package nasa.logic.commands.addcommands;

import static nasa.logic.parser.CliSyntax.PREFIX_ACTIVITY_NAME;
import static nasa.logic.parser.CliSyntax.PREFIX_END_DATE;
import static nasa.logic.parser.CliSyntax.PREFIX_MODULE;
import static nasa.logic.parser.CliSyntax.PREFIX_NOTE;
import static nasa.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static nasa.logic.parser.CliSyntax.PREFIX_START_DATE;

import nasa.model.activity.Event;
import nasa.model.module.ModuleCode;

/**
 * Adds an event activity to a module's list.
 */
public class AddEventCommand extends AddCommand {
    public static final String COMMAND_WORD = "event";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to the module's activity list.\n"
            + "Parameters: "
            + PREFIX_MODULE + "MODULE CODE "
            + PREFIX_START_DATE + "START DATE "
            + PREFIX_END_DATE + "END DATE "
            + PREFIX_ACTIVITY_NAME + "ACTIVITY NAME "
            + PREFIX_PRIORITY + "PRIORITY "
            + PREFIX_NOTE + "NOTE" + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE + "CS3233 "
            + PREFIX_START_DATE + "12-04-2020 02:00 "
            + PREFIX_END_DATE + "12-04-2020 04:00 "
            + PREFIX_ACTIVITY_NAME + "CP contest  "
            + PREFIX_PRIORITY + "1 "
            + PREFIX_NOTE + "Remember to study content before coming.";

    /**
     * Creates an AddCommand that adds {@code event} to list of {@code moduleCode}.
     * @param event Event to be added
     * @param moduleCode Module where the event is to be added
     */
    public AddEventCommand(Event event, ModuleCode moduleCode) {
        super(event, moduleCode);
    }
}

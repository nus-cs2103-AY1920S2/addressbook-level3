package nasa.logic.commands.addcommands;

import static java.util.Objects.requireNonNull;
import static nasa.logic.parser.CliSyntax.PREFIX_ACTIVITY_NAME;
import static nasa.logic.parser.CliSyntax.PREFIX_END_DATE;
import static nasa.logic.parser.CliSyntax.PREFIX_MODULE;
import static nasa.logic.parser.CliSyntax.PREFIX_NOTE;
import static nasa.logic.parser.CliSyntax.PREFIX_START_DATE;

import nasa.logic.commands.CommandResult;
import nasa.logic.commands.exceptions.CommandException;
import nasa.model.Model;
import nasa.model.activity.Event;
import nasa.model.module.ModuleCode;

/**
 * Adds an event activity to a module's list.
 */
public class AddEventCommand extends AddCommand {
    public static final String COMMAND_WORD = "event";

    public static final String MESSAGE_SUCCESS = "New event added!";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to the module's activity list.\n"
            + "Parameters: "
            + PREFIX_MODULE + "MODULE CODE "
            + PREFIX_START_DATE + "START DATE "
            + PREFIX_END_DATE + "END DATE "
            + PREFIX_ACTIVITY_NAME + "ACTIVITY NAME "
            + PREFIX_NOTE + "NOTE" + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE + "CS3233 "
            + PREFIX_START_DATE + "12-05-2020 02:00 "
            + PREFIX_END_DATE + "12-05-2020 04:00 "
            + PREFIX_ACTIVITY_NAME + "CP contest  "
            + PREFIX_NOTE + "Remember to study content before coming.";

    /**
     * Creates an AddCommand that adds {@code event} to list of {@code moduleCode}.
     * @param event Event to be added
     * @param moduleCode Module where the event is to be added
     */
    public AddEventCommand(Event event, ModuleCode moduleCode) {
        super(event, moduleCode);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasModule(moduleCode)) {
            throw new CommandException(MESSAGE_MODULE_NOT_FOUND);
        }

        model.addEvent(moduleCode, (Event) toAdd);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

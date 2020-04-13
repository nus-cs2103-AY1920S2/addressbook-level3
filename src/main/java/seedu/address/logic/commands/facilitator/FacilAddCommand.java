package seedu.address.logic.commands.facilitator;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OFFICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.facilitator.Facilitator;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.util.action.DoableActionType;
import seedu.address.model.util.action.FacilAction;

/**
 * Adds a facilitator to Mod Manager.
 */
public class FacilAddCommand extends FacilCommand {

    public static final String MESSAGE_USAGE = COMMAND_GROUP_FACIL + " " + COMMAND_WORD_ADD
            + ": Adds a facilitator to Mod Manager.\n"
            + "Parameters: "
            + PREFIX_NAME + " FACILITATOR_NAME "
            + "[" + PREFIX_PHONE + " PHONE] "
            + "[" + PREFIX_EMAIL + " EMAIL] "
            + "[" + PREFIX_OFFICE + " OFFICE] "
            + PREFIX_MODULE_CODE + " MOD_CODE " + "[MORE_MOD_CODES]...\n"
            + "Example: " + COMMAND_GROUP_FACIL + " " + COMMAND_WORD_ADD + " "
            + PREFIX_NAME + " Akshay Narayan "
            + PREFIX_PHONE + " 98765432 "
            + PREFIX_EMAIL + " akshay@comp.nus.edu.sg "
            + PREFIX_OFFICE + " COM2-03-56 "
            + PREFIX_MODULE_CODE + " CS2103T CS2101\n"
            + "Parameters: "
            + PREFIX_NAME + " FACILITATOR_NAME "
            + "[" + PREFIX_PHONE + " PHONE] "
            + "[" + PREFIX_EMAIL + " EMAIL] "
            + "[" + PREFIX_OFFICE + " OFFICE] "
            + PREFIX_MODULE_CODE + " MOD_CODE  "
            + "[" + PREFIX_MODULE_CODE + " MORE_MOD_CODES]...\n"
            + "Example: " + COMMAND_GROUP_FACIL + " " + COMMAND_WORD_ADD + " "
            + PREFIX_NAME + " Akshay Narayan "
            + PREFIX_PHONE + " 98765432 "
            + PREFIX_EMAIL + " akshay@comp.nus.edu.sg "
            + PREFIX_OFFICE + " COM2-03-56 "
            + PREFIX_MODULE_CODE + " CS2103T "
            + PREFIX_MODULE_CODE + " CS2101";

    public static final String MESSAGE_SUCCESS = "New facilitator added: %1$s";
    public static final String MESSAGE_NOT_ADDED =
            "At least one of the optional fields (phone, email, office) must be provided.";
    public static final String MESSAGE_DUPLICATE_FACILITATOR = "This facilitator already exists in Mod Manager.";
    public static final String MESSAGE_MODULE_DOES_NOT_EXIST = "The module %1$s does not exist in Mod Manager.";

    private final Facilitator toAdd;

    /**
     * Creates a FacilAddCommand to add the specified {@code facilitator}.
     */
    public FacilAddCommand(Facilitator facilitator) {
        requireNonNull(facilitator);
        toAdd = facilitator;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasFacilitator(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_FACILITATOR);
        }

        for (ModuleCode moduleCode : toAdd.getModuleCodes()) {
            if (!model.hasModuleCode(moduleCode.value)) {
                throw new CommandException(String.format(MESSAGE_MODULE_DOES_NOT_EXIST, moduleCode.value));
            }
        }

        model.addFacilitator(toAdd);

        FacilAction addFacilAction = new FacilAction(toAdd, DoableActionType.ADD);
        model.addAction(addFacilAction);

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), CommandType.FACILITATOR);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FacilAddCommand // instanceof handles nulls
                && toAdd.equals(((FacilAddCommand) other).toAdd));
    }
}

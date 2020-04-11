package seedu.address.logic.commands.modulecommand;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MODULES_TAKEN;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.nusmodule.Grade;
import seedu.address.model.nusmodule.ModuleCode;

/**
 * Updates a module's grade in our module book.
 */
public class GradeCommand extends Command {
    public static final String COMMAND_WORD = "grade";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds or changes a grade to a module in the module book. "
            + "Parameters: "
            + PREFIX_MODULE_CODE + "MODULE CODE "
            + PREFIX_GRADE + "GRADE "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE_CODE + "CS2103T "
            + PREFIX_GRADE + "A+ ";

    public static final String MESSAGE_SUCCESS = "Module grade updated: ";

    private final Grade gradeToBeUpdated;
    private final ModuleCode targetModuleCode;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public GradeCommand(ModuleCode moduleCode, Grade grade) {
        requireNonNull(grade);
        targetModuleCode = moduleCode;
        gradeToBeUpdated = grade;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasModule(targetModuleCode)) {
            throw new CommandException(Messages.MESSAGE_NO_SUCH_MODULE);
        }

        model.gradeModule(targetModuleCode, gradeToBeUpdated);
        model.updateModulesListTaken(PREDICATE_SHOW_ALL_MODULES_TAKEN);
        return new CommandResult(MESSAGE_SUCCESS + " "
                + targetModuleCode + " "
                + gradeToBeUpdated.getText());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GradeCommand // instanceof handles nulls
                && targetModuleCode.equals(((GradeCommand) other).targetModuleCode));
    }
}

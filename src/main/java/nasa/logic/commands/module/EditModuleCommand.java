package nasa.logic.commands.module;

import static java.util.Objects.requireNonNull;
import static nasa.logic.parser.CliSyntax.PREFIX_MODULE;
import static nasa.logic.parser.CliSyntax.PREFIX_MODULE_NAME;
import static nasa.model.Model.PREDICATE_SHOW_ALL_MODULES;

import java.util.Optional;

import javafx.collections.ObservableList;

import nasa.commons.util.CollectionUtil;
import nasa.logic.commands.Command;
import nasa.logic.commands.CommandResult;
import nasa.logic.commands.exceptions.CommandException;
import nasa.model.Model;
import nasa.model.activity.Deadline;
import nasa.model.activity.Event;
import nasa.model.activity.UniqueDeadlineList;
import nasa.model.activity.UniqueEventList;
import nasa.model.module.Module;
import nasa.model.module.ModuleCode;
import nasa.model.module.ModuleName;

/* @@author don-tay */
/**
 * Edits a module in the NASA book.
 */
public class EditModuleCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the module identified "
            + "by the module code in the displayed NASA application. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + PREFIX_MODULE + "MODULE CODE "
            + "[" + PREFIX_MODULE + "MODULE CODE] "
            + "[" + PREFIX_MODULE_NAME + "MODULE NAME]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE + "CS2030 "
            + PREFIX_MODULE + "CS2020 "
            + PREFIX_MODULE_NAME + "Data Structures and Algorithms";

    public static final String MESSAGE_EDIT_MODULE_SUCCESS = "Edited Module";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_MODULE = "This module already exists in the list.";
    public static final String EXCESS_MODULE_CODE = "Failed to edit module code. EXACTLY 2 module codes must be "
            + "entered to edit module code.";
    public static final String MESSAGE_MODULE_DOES_NOT_EXIST = "This module does not exist.";
    public static final String MESSAGE_NO_NEW_EDIT = "No new field is being edited.";

    private final ModuleCode moduleCode;
    private final EditModuleCommand.EditModuleDescriptor editModuleDescriptor;

    /**
     * Creates an EditModuleCommand to edit a module at specified {@code index}
     * @param moduleCode Module code of the module to be edited
     * @param editModuleDescriptor EditModuleDescriptor helper to edit the module
     */
    public EditModuleCommand(ModuleCode moduleCode, EditModuleDescriptor editModuleDescriptor) {
        requireNonNull(moduleCode);
        requireNonNull(editModuleDescriptor);

        this.moduleCode = moduleCode;
        this.editModuleDescriptor = new EditModuleDescriptor(editModuleDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        ModuleCode moduleCodeToEdit = this.moduleCode;
        Module moduleToEdit = model.getModule(moduleCodeToEdit);

        // case when module to be edited is not found in the NasaBook
        if (!model.hasModule(moduleCodeToEdit)) {
            throw new nasa.logic.commands.exceptions.CommandException(MESSAGE_MODULE_DOES_NOT_EXIST);
        }

        Module editedModule = createEditedModule(moduleToEdit, editModuleDescriptor);

        // case when edit made is exactly the same as original
        if (moduleToEdit.equals(editedModule)) {
            throw new nasa.logic.commands.exceptions.CommandException(MESSAGE_NO_NEW_EDIT);
        }

        // case when edited module exists in model, and module is not target {@code moduleCodeToEdit}
        if (!moduleCodeToEdit.equals(editedModule.getModuleCode()) && model.hasModule(editedModule.getModuleCode())) {
            throw new nasa.logic.commands.exceptions.CommandException(MESSAGE_DUPLICATE_MODULE);
        }


        model.setModule(moduleToEdit.getModuleCode(), editedModule);
        model.updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);

        return new CommandResult(String.format(MESSAGE_EDIT_MODULE_SUCCESS, editedModule));
    }

    /**
     * Creates and returns a {@code Module} with the details of {@code moduleToEdit}
     * edited with {@code editModuleDescriptor}.
     */
    private static Module createEditedModule(Module moduleToEdit,
                                             EditModuleCommand.EditModuleDescriptor editModuleDescriptor) {
        assert moduleToEdit != null;

        ModuleCode updatedModuleCode = editModuleDescriptor.getModuleCode().orElse(moduleToEdit.getModuleCode());
        ModuleName updatedModuleName = editModuleDescriptor.getModuleName().orElse(moduleToEdit.getModuleName());

        UniqueDeadlineList deadlineList = moduleToEdit.getDeadlineList();
        ObservableList<Deadline> obsDeadlineList = moduleToEdit.getFilteredDeadlineList();

        UniqueEventList eventList = moduleToEdit.getEventList();
        ObservableList<Event> obsEventList = moduleToEdit.getFilteredEventList();

        Module newModule = new Module(updatedModuleCode, updatedModuleName);

        newModule.setDeadlines(deadlineList);
        newModule.setDeadlines(obsDeadlineList);

        newModule.setEvents(eventList);
        newModule.setEvents(obsEventList);

        return newModule;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditModuleCommand // instanceof handles nulls
                && moduleCode.equals(((EditModuleCommand) other).moduleCode)
                && editModuleDescriptor.equals(((EditModuleCommand) other).editModuleDescriptor));
    }

    /**
     * Stores the details to edit the module with. Each non-empty field value will replace the
     * corresponding field value of the module.
     */
    public static class EditModuleDescriptor {
        private ModuleCode moduleCode;
        private ModuleName moduleName;

        public EditModuleDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditModuleDescriptor(EditModuleCommand.EditModuleDescriptor toCopy) {
            setModuleCode(toCopy.moduleCode);
            setModuleName(toCopy.moduleName);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(moduleCode, moduleName);
        }

        public void setModuleCode(ModuleCode moduleCode) {
            this.moduleCode = moduleCode;
        }

        public Optional<ModuleCode> getModuleCode() {
            return Optional.ofNullable(moduleCode);
        }

        public void setModuleName(ModuleName moduleName) {
            this.moduleName = moduleName;
        }

        public Optional<ModuleName> getModuleName() {
            return Optional.ofNullable(moduleName);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditModuleCommand.EditModuleDescriptor)) {
                return false;
            }

            // state check
            EditModuleCommand.EditModuleDescriptor e = (EditModuleCommand.EditModuleDescriptor) other;

            return getModuleCode().equals(e.getModuleCode()) && getModuleName().equals(e.getModuleName());
        }
    }
}

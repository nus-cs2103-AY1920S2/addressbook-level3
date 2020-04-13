package seedu.address.logic.commands.facilitator;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_EDITED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OFFICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_FACILITATORS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.facilitator.Email;
import seedu.address.model.facilitator.Facilitator;
import seedu.address.model.facilitator.Name;
import seedu.address.model.facilitator.Office;
import seedu.address.model.facilitator.Phone;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.util.action.DoableActionType;
import seedu.address.model.util.action.FacilAction;

/**
 * Edits the details of an existing facilitator in Mod Manager.
 */
public class FacilEditCommand extends FacilCommand {

    public static final String MESSAGE_USAGE = COMMAND_GROUP_FACIL + " " + COMMAND_WORD_EDIT
            + ": Edits the details of the facilitator identified "
            + "by the index number used in the displayed facilitator list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + " FACILITATOR_NAME] "
            + "[" + PREFIX_PHONE + " PHONE] "
            + "[" + PREFIX_EMAIL + " EMAIL] "
            + "[" + PREFIX_OFFICE + " OFFICE] "
            + "[" + PREFIX_MODULE_CODE + " MOD_CODES...]\n"
            + "Example: " + COMMAND_GROUP_FACIL + " " + COMMAND_WORD_EDIT + " 1 "
            + PREFIX_PHONE + " 91234567 "
            + PREFIX_EMAIL + " johndoe@example.com\n"
            + "Parameters: FACILITATOR_NAME"
            + "[" + PREFIX_NAME + " FACILITATOR_NAME] "
            + "[" + PREFIX_PHONE + " PHONE] "
            + "[" + PREFIX_EMAIL + " EMAIL] "
            + "[" + PREFIX_OFFICE + " OFFICE] "
            + "[" + PREFIX_MODULE_CODE + " MOD_CODES...]\n"
            + "Example: " + COMMAND_GROUP_FACIL + " " + COMMAND_WORD_EDIT + " Akshay Narayan "
            + PREFIX_PHONE + " 84841235";

    public static final String MESSAGE_EDIT_FACILITATOR_SUCCESS = "Edited Facilitator: %1$s";
    public static final String MESSAGE_DUPLICATE_FACILITATOR = "This facilitator already exists in Mod Manager.";
    public static final String MESSAGE_ALL_OPTIONAL_FIELDS_DELETED =
            "At least one of phone, email and office should not be empty.";
    public static final String MESSAGE_MODULE_DOES_NOT_EXIST = "The module %1$s does not exist in Mod Manager.";

    private final Index index;
    private final Name fname;
    private final EditFacilitatorDescriptor editFacilitatorDescriptor;

    /**
     * Creates a FacilEditCommand to edit the facilitator at the specified {@code index}.
     *
     * @param index of the facilitator in the filtered facilitator list to edit.
     * @param editFacilitatorDescriptor details to edit the facilitator with.
     */
    public FacilEditCommand(Index index, EditFacilitatorDescriptor editFacilitatorDescriptor) {
        requireNonNull(index);
        requireNonNull(editFacilitatorDescriptor);

        this.index = index;
        this.fname = null;
        this.editFacilitatorDescriptor = new EditFacilitatorDescriptor(editFacilitatorDescriptor);
    }

    public FacilEditCommand(Name fname, EditFacilitatorDescriptor editFacilitatorDescriptor) {
        requireNonNull(fname);
        requireNonNull(editFacilitatorDescriptor);

        this.index = null;
        this.fname = fname;
        this.editFacilitatorDescriptor = editFacilitatorDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Facilitator> lastShownList = model.getFilteredFacilitatorList();

        int mode = 0;
        if (index == null) {
            mode = 1;
            assert fname != null;
        }

        Facilitator facilitatorToEdit;

        if (mode == 0) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_FACILITATOR_DISPLAYED_INDEX);
            }
            facilitatorToEdit = lastShownList.get(index.getZeroBased());
        } else {
            model.updateFilteredFacilitatorList(PREDICATE_SHOW_ALL_FACILITATORS);
            lastShownList = model.getFilteredFacilitatorList();
            final List<Facilitator> fetch = new ArrayList<>();
            lastShownList.stream().filter(x -> x.getName().equals(fname)).forEach(fetch::add);

            if (fetch.isEmpty()) {
                // No facilitators with such name, so ask the user to confirm
                final List<String> words = List.of(fname.fullName.split("\\s+"));
                Predicate<Facilitator> predicate =
                    f -> words.stream().anyMatch(x -> StringUtil.containsWordIgnoreCase(f.getName().fullName, x));

                lastShownList.stream().filter(predicate).forEach(fetch::add);

                if (fetch.isEmpty()) {
                    throw new CommandException(String.format(Messages.MESSAGE_FACILITATOR_NOT_FOUND, fname));
                }

                return promptUserToConfirm(fetch);
            }

            assert fetch.size() == 1;
            facilitatorToEdit = fetch.get(0);
        }

        Facilitator editedFacilitator = createEditedFacilitator(facilitatorToEdit, editFacilitatorDescriptor);

        if (!facilitatorToEdit.isSameFacilitator(editedFacilitator) && model.hasFacilitator(editedFacilitator)) {
            throw new CommandException(MESSAGE_DUPLICATE_FACILITATOR);
        }

        if (editedFacilitator.getPhone().value == null && editedFacilitator.getEmail().value == null
                && editedFacilitator.getOffice().value == null) {
            throw new CommandException(MESSAGE_ALL_OPTIONAL_FIELDS_DELETED);
        }

        for (ModuleCode moduleCode : editedFacilitator.getModuleCodes()) {
            if (!model.hasModuleCode(moduleCode.value)) {
                throw new CommandException(String.format(MESSAGE_MODULE_DOES_NOT_EXIST, moduleCode.value));
            }
        }

        if (facilitatorToEdit.equals(editedFacilitator)) {
            throw new CommandException(MESSAGE_NOT_EDITED);
        }

        model.setFacilitator(facilitatorToEdit, editedFacilitator);
        model.updateFilteredFacilitatorList(PREDICATE_SHOW_ALL_FACILITATORS);

        FacilAction editFacilAction = new FacilAction(facilitatorToEdit, editedFacilitator, DoableActionType.EDIT);
        model.addAction(editFacilAction);

        return new CommandResult(String.format(MESSAGE_EDIT_FACILITATOR_SUCCESS, editedFacilitator),
                CommandType.FACILITATOR);
    }

    /**
     * Returns a CommandResult with type PROMPTING, asking the user to input the more precise information.
     */
    private CommandResult promptUserToConfirm(List<Facilitator> fetch) {
        StringBuilder builder = new StringBuilder(
                String.format(Messages.MESSAGE_PARTIAL_FACILITATOR_NAME_MATCHING_FOUND, fname));
        fetch.forEach(x -> builder.append("  ").append(x.getName().toString()).append('\n'));
        builder.append(Messages.MESSAGE_ASK_TO_CONFIRM_FACILITATOR);
        return new CommandResult(builder.toString(), CommandType.PROMPTING);
    }

    /**
     * Creates and returns a {@code Facilitator} with the details of {@code facilitatorToEdit}
     * edited with {@code editFacilitatorDescriptor}.
     */
    private static Facilitator createEditedFacilitator(Facilitator facilitatorToEdit,
                EditFacilitatorDescriptor editFacilitatorDescriptor) {
        assert facilitatorToEdit != null;

        Name updatedName = editFacilitatorDescriptor.getName().orElse(facilitatorToEdit.getName());
        Phone updatedPhone = editFacilitatorDescriptor.getPhone().orElse(facilitatorToEdit.getPhone());
        Email updatedEmail = editFacilitatorDescriptor.getEmail().orElse(facilitatorToEdit.getEmail());
        Office updatedOffice = editFacilitatorDescriptor.getOffice().orElse(facilitatorToEdit.getOffice());
        Set<ModuleCode> updatedModuleCodes = editFacilitatorDescriptor.getModuleCodes()
                .orElse(facilitatorToEdit.getModuleCodes());

        return new Facilitator(updatedName, updatedPhone, updatedEmail, updatedOffice, updatedModuleCodes);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FacilEditCommand)) {
            return false;
        }

        // state check
        FacilEditCommand e = (FacilEditCommand) other;
        if ((index != null && e.index == null) || (index == null && e.index != null)) {
            return false;
        }
        if ((fname != null && e.fname == null) || (fname == null && e.fname != null)) {
            return false;
        }

        return (index == null || index.equals(e.index))
                && (fname == null || fname.equals(e.fname))
                && editFacilitatorDescriptor.equals(e.editFacilitatorDescriptor);
    }

    /**
     * Stores the details to edit the facilitator with. Each non-empty field value will replace the
     * corresponding field value of the facilitator.
     */
    public static class EditFacilitatorDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Office office;
        private Set<ModuleCode> moduleCodes;

        public EditFacilitatorDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code moduleCodes} is used internally.
         */
        public EditFacilitatorDescriptor(EditFacilitatorDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setOffice(toCopy.office);
            setModuleCodes(toCopy.moduleCodes);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setOffice(Office office) {
            this.office = office;
        }

        public Optional<Office> getOffice() {
            return Optional.ofNullable(office);
        }

        /**
         * Sets {@code moduleCodes} to this object's {@code moduleCodes}.
         * A defensive copy of {@code moduleCodes} is used internally.
         */
        public void setModuleCodes(Set<ModuleCode> moduleCodes) {
            this.moduleCodes = (moduleCodes != null) ? new HashSet<>(moduleCodes) : null;
        }

        /**
         * Returns an unmodifiable module code set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code moduleCodes} is null.
         */
        public Optional<Set<ModuleCode>> getModuleCodes() {
            return (moduleCodes != null) ? Optional.of(Collections.unmodifiableSet(moduleCodes)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditFacilitatorDescriptor)) {
                return false;
            }

            // state check
            EditFacilitatorDescriptor e = (EditFacilitatorDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getOffice().equals(e.getOffice())
                    && getModuleCodes().equals(e.getModuleCodes());
        }
    }
}

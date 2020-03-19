package nasa.logic.commands;

import static java.util.Objects.requireNonNull;
import static nasa.logic.parser.CliSyntax.PREFIX_MODULE;
import static nasa.logic.parser.CliSyntax.PREFIX_MODULE_NAME;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import nasa.commons.core.index.Index;
import nasa.logic.commands.exceptions.CommandException;
import nasa.model.Model;

/**
 * Edits a module in the NASA book.
 */
public class EditModuleCommand extends Command {

    public static final String COMMAND_WORD = "medit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the module identified "
            + "by the index number used in the displayed NASA application. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + PREFIX_MODULE + "MODULE CODE"
            + "INDEX (must be a positive integer)"
            + "[" + PREFIX_MODULE + "MODULE CODE] "
            + "[" + PREFIX_MODULE_NAME + "MODULE NAME]\n"
            + "Example: " + COMMAND_WORD
            + PREFIX_MODULE + "CS2030"
            + "1"
            + PREFIX_MODULE + "CS2020"
            + PREFIX_MODULE_NAME + "Data Structures and Algorithms II";

    public static final String MESSAGE_EDIT_ACTIVITY_SUCCESS = "Edited Activity";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This activity already exists in the module activity list.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * Creates an EditModuleCommand to edit a module at specified {@code index}
     * @param index Index of module to be edited
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        // TODO add the necessary implementation once model is done
        /*
                List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));
         */
        return new CommandResult("");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditModuleCommand // instanceof handles nulls
                && index.equals(((EditModuleCommand) other).index));
    }
}

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditCommand.EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags);
    }


/**
 * Stores the details to edit the person with. Each non-empty field value will replace the
 * corresponding field value of the person.
 */
public static class EditPersonDescriptor {
    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;

    public EditPersonDescriptor() {}

    /**
     * Copy constructor.
     * A defensive copy of {@code tags} is used internally.
     */
    public EditPersonDescriptor(EditCommand.EditPersonDescriptor toCopy) {
        setName(toCopy.name);
        setPhone(toCopy.phone);
        setEmail(toCopy.email);
        setAddress(toCopy.address);
        setTags(toCopy.tags);
    }

    /**
     * Returns true if at least one field is edited.
     */
    public boolean isAnyFieldEdited() {
        return CollectionUtil.isAnyNonNull(name, phone, email, address, tags);
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

    public void setAddress(Address address) {
        this.address = address;
    }

    public Optional<Address> getAddress() {
        return Optional.ofNullable(address);
    }

    /**
     * Sets {@code tags} to this object's {@code tags}.
     * A defensive copy of {@code tags} is used internally.
     */
    public void setTags(Set<Tag> tags) {
        this.tags = (tags != null) ? new HashSet<>(tags) : null;
    }

    /**
     * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     * Returns {@code Optional#empty()} if {@code tags} is null.
     */
    public Optional<Set<Tag>> getTags() {
        return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand.EditPersonDescriptor)) {
            return false;
        }

        // state check
        EditCommand.EditPersonDescriptor e = (EditCommand.EditPersonDescriptor) other;

        return getName().equals(e.getName())
                && getPhone().equals(e.getPhone())
                && getEmail().equals(e.getEmail())
                && getAddress().equals(e.getAddress())
                && getTags().equals(e.getTags());
    }
}
}


package seedu.address.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Organization;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;

/**
 * Stores the details to edit the person with. Each non-empty field value will replace the
 * corresponding field value of the person.
 */
public class EditPersonDescriptor {
    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Birthday birthday;
    private Organization organization;
    private ArrayList<Remark> remarks;
    private Set<Tag> tags;
    private Set<Tag> tagsToBeDeleted;
    private Set<Tag> emptyTags;

    public EditPersonDescriptor() {}

    /**
     * Copy constructor.
     * A defensive copy of {@code tags} is used internally.
     */
    public EditPersonDescriptor(EditPersonDescriptor toCopy) {
        setName(toCopy.name);
        setPhone(toCopy.phone);
        setEmail(toCopy.email);
        setAddress(toCopy.address);
        setBirthday(toCopy.birthday);
        setOrganization(toCopy.organization);
        setRemarks(toCopy.remarks);
        setTagsToBeAdded(toCopy.tags);
        setTagsToBeDeleted(toCopy.tagsToBeDeleted);
        setTagsToEmpty(toCopy.emptyTags);
    }

    /**
     * Returns true if at least one field is edited.
     */
    public boolean isAnyFieldEdited() {
        return CollectionUtil.isAnyNonNull(name, phone, email, address, remarks, birthday, organization, tags,
            tagsToBeDeleted, emptyTags, organization);
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

    public void setBirthday(Birthday birthday) {
        this.birthday = birthday;
    }

    public Optional<Birthday> getBirthday() {
        return Optional.ofNullable(birthday);
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Optional<Organization> getOrganization() {
        return Optional.ofNullable(organization);
    }

    /**
     * Sets {@code remarks} to this object's {@code remarks}.
     * A defensive copy of {@code remarks} is used internally.
     */
    public void setRemarks(ArrayList<Remark> remarks) {
        this.remarks = (remarks != null) ? new ArrayList<>(remarks) : null;
    }

    /**
     * Returns an unmodifiable remark set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     * Returns {@code Optional#empty()} if {@code remarks} is null.
     */
    public Optional<ArrayList<Remark>> getRemarks() {
        return (remarks != null) ? Optional.of(remarks) : Optional.empty();
    }

    /**
     * Sets {@code tags} to this object's {@code tags}.
     * A defensive copy of {@code tags} is used internally.
     */
    public void setTagsToBeAdded(Set<Tag> tags) {
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

    /**
     * Sets {@code tags} that are to be deleted to this object's {@code tagsToBeDeleted}
     * A defensive copy of {@code tagsToBeDeleted} is used internally.
     */
    public void setTagsToBeDeleted(Set<Tag> tags) {
        this.tagsToBeDeleted = (tags != null) ? new HashSet<>(tags) : null;
    }

    /**
     * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     * Returns {@code Optional#empty()} if {@code tagsToBeDeleted} is null.
     */
    public Optional<Set<Tag>> getTagsToBeDeleted() {
        return (tagsToBeDeleted != null) ? Optional.of(Collections.unmodifiableSet(tagsToBeDeleted)) : Optional.empty();
    }

    /**
     * Sets {@code tags} to this object's {@code emptyTags}.
     * A defensive copy of {@code emptyTags} is used internally.
     */
    public void setTagsToEmpty(Set<Tag> tags) {
        this.emptyTags = (tags != null) ? new HashSet<>(tags) : null;
    }

    /**
     * Returns an unmodifiable empty tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     * Returns {@code Optional#empty()} if {@code emptyTags} is null.
     */
    public Optional<Set<Tag>> getEmptyTagSet() {
        return (emptyTags != null) ? Optional.of(Collections.unmodifiableSet(emptyTags)) : null;
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    public Person createEditedPerson(Person personToEdit) {
        assert personToEdit != null;

        Name updatedName = getName().orElse(personToEdit.getName());
        Phone updatedPhone = getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = getAddress().orElse(personToEdit.getAddress());
        ArrayList<Remark> updatedRemark = getRemarks().orElse(personToEdit.getRemark());
        Birthday updatedBirthday = getBirthday().orElse(personToEdit.getBirthday());
        Organization updatedOrganization = getOrganization().orElse(personToEdit.getOrganization());

        Set<Tag> updatedTags = new HashSet<>(personToEdit.getTags());

        if (getEmptyTagSet() != null) {
            updatedTags.clear();
        }

        updatedTags.addAll(getTags().orElse(new HashSet<>()));
        updatedTags.removeAll(getTagsToBeDeleted().orElse(new HashSet<>()));

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedRemark,
                updatedBirthday, updatedOrganization, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditPersonDescriptor)) {
            return false;
        }

        // state check
        EditPersonDescriptor e = (EditPersonDescriptor) other;

        return getName().equals(e.getName())
                && getPhone().equals(e.getPhone())
                && getEmail().equals(e.getEmail())
                && getAddress().equals(e.getAddress())
                && getBirthday().equals(e.getBirthday())
                && getOrganization().equals(e.getOrganization())
                && getRemarks().equals(e.getRemarks())
                && getTags().equals(e.getTags());
    }
}

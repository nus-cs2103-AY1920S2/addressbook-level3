package seedu.address.testutil;

import seedu.address.logic.commands.edit.EditUserDescriptor;
import seedu.address.model.item.Person;
import seedu.address.model.item.field.Cap;
import seedu.address.model.item.field.Description;
import seedu.address.model.item.field.DisplayPicture;
import seedu.address.model.item.field.Email;
import seedu.address.model.item.field.Github;
import seedu.address.model.item.field.Major;
import seedu.address.model.item.field.Name;
import seedu.address.model.item.field.Phone;
import seedu.address.model.item.field.Time;
import seedu.address.model.item.field.University;

/**
 * A utility class to help with building EditUserDescriptor objects.
 */
public class EditUserDescriptorBuilder {

    private EditUserDescriptor descriptor;

    public EditUserDescriptorBuilder() {
        descriptor = new EditUserDescriptor();
    }

    public EditUserDescriptorBuilder(EditUserDescriptor descriptor) {
        this.descriptor = new EditUserDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditUserDescriptor} with fields containing {@code person}'s details
     */
    public EditUserDescriptorBuilder(Person person) {
        descriptor = new EditUserDescriptor();
        descriptor.setDisplayPicture(person.getDisplayPicture());
        descriptor.setName(person.getName());
        descriptor.setDescription(person.getDescription());;
        descriptor.setPhone(person.getPhone());
        descriptor.setEmail(person.getEmail());
        descriptor.setGithub(person.getGithub());
        descriptor.setUni(person.getUniversity());
        descriptor.setMajor(person.getMajor());
        descriptor.setFrom(person.getFrom());
        descriptor.setTo(person.getTo());
        descriptor.setCap(person.getCap());
    }

    /**
     * Sets the {@code Name} of the {@code EditUserDescriptor} that we are building.
     */
    public EditUserDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditUserDescriptor} that we are building.
     */
    public EditUserDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditUserDescriptor} that we are building.
     */
    public EditUserDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Github} of the {@code EditUserDescriptor} that we are building.
     */
    public EditUserDescriptorBuilder withGithub(String github) {
        descriptor.setGithub(new Github(github));
        return this;
    }

    /**
     * Sets the {@code DisplayPicture} of the {@code EditUserDescriptor} that we are building.
     */
    public EditUserDescriptorBuilder withDisplayPicture(String displayPicture) {
        descriptor.setDisplayPicture(new DisplayPicture(displayPicture));
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code EditUserDescriptor} that we are building.
     */
    public EditUserDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
        return this;
    }

    /**
     * Sets the {@code University} of the {@code EditUserDescriptor} that we are building.
     */
    public EditUserDescriptorBuilder withUniversity(String university) {
        descriptor.setUni(new University(university));
        return this;
    }

    /**
     * Sets the {@code Cap} of the {@code EditUserDescriptor} that we are building.
     */
    public EditUserDescriptorBuilder withCap(String cap) {
        descriptor.setCap(new Cap(cap));
        return this;
    }

    /**
     * Sets the {@code Major} of the {@code EditUserDescriptor} that we are building.
     */
    public EditUserDescriptorBuilder withMajor(String major) {
        descriptor.setMajor(new Major(major));
        return this;
    }

    /**
     * Sets the {@code From} of the {@code EditUserDescriptor} that we are building.
     */
    public EditUserDescriptorBuilder withFrom(String from) {
        descriptor.setFrom(new Time(from));
        return this;
    }

    /**
     * Sets the {@code To} of the {@code EditUserDescriptor} that we are building.
     */
    public EditUserDescriptorBuilder withTo(String to) {
        descriptor.setTo(new Time(to));
        return this;
    }


    public EditUserDescriptor build() {
        return descriptor;
    }
}

package fithelper.testutil;

import fithelper.logic.commands.EditCommand.EditEntryDescriptor;
import fithelper.model.entry.Calorie;
import fithelper.model.entry.Entry;
import fithelper.model.entry.Location;
import fithelper.model.entry.Name;
import fithelper.model.entry.Time;
import fithelper.model.entry.Type;

/**
 * A utility class to help with building EditEntryDescriptor objects.
 */
public class EditEntryDescriptorBuilder {

    private EditEntryDescriptor descriptor;

    public EditEntryDescriptorBuilder() {
        descriptor = new EditEntryDescriptor();
    }

    public EditEntryDescriptorBuilder(EditEntryDescriptor descriptor) {
        this.descriptor = new EditEntryDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditEntryDescriptor} with fields containing {@code entry}'s details
     */
    public EditEntryDescriptorBuilder(Entry entry) {
        descriptor = new EditEntryDescriptor();
        descriptor.setName(entry.getName());
        descriptor.setLocation(entry.getLocation());
        descriptor.setCalorie(entry.getCalorie());
        descriptor.setTime(entry.getTime());
        descriptor.setType(entry.getType());
    }

    /**
     * Sets the {@code Name} of the {@code EditEntryDescriptor} that we are building.
     */
    public EditEntryDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Location} of the {@code EditEntryDescriptor} that we are building.
     */
    public EditEntryDescriptorBuilder withLocation(String location) {
        descriptor.setLocation(new Location(location));
        return this;
    }

    /**
     * Sets the {@code Calorie} of the {@code EditEntryDescriptor} that we are building.
     */
    public EditEntryDescriptorBuilder withCalorie(String calorie) {
        descriptor.setCalorie(new Calorie(calorie));
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code EditEntryDescriptor} that we are building.
     */
    public EditEntryDescriptorBuilder withTime(String time) {
        descriptor.setTime(new Time(time));
        return this;
    }

    /**
     * Sets the {@code Type} of the {@code EditEntryDescriptor} that we are building.
     */
    public EditEntryDescriptorBuilder withType(String type) {
        descriptor.setType(new Type(type));
        return this;
    }

    public EditEntryDescriptor build() {
        return descriptor;
    }
}

package team.easytravel.testutil.activity;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import team.easytravel.logic.commands.activity.EditActivityCommand.EditActivityDescriptor;
import team.easytravel.model.listmanagers.activity.Activity;
import team.easytravel.model.listmanagers.activity.Duration;
import team.easytravel.model.util.attributes.Location;
import team.easytravel.model.util.attributes.Title;
import team.easytravel.model.util.attributes.tag.Tag;


/**
 * A utility class to help with building EditActivityDescriptor objects.
 */
public class EditActivityDescriptorBuilder {

    private EditActivityDescriptor descriptor;

    public EditActivityDescriptorBuilder() {
        descriptor = new EditActivityDescriptor();
    }

    public EditActivityDescriptorBuilder(EditActivityDescriptor descriptor) {
        this.descriptor = new EditActivityDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditActivityDescriptor} with fields containing {@code activity}'s details
     */
    public EditActivityDescriptorBuilder(Activity activity) {
        descriptor = new EditActivityDescriptor();
        descriptor.setTitle(activity.getTitle());
        descriptor.setDuration(activity.getDuration());
        descriptor.setLocation(activity.getLocation());
        descriptor.setTags(activity.getTags());
    }

    /**
     * Sets the {@code title} of the {@code EditActivityDescriptor} that we are building.
     */
    public EditActivityDescriptorBuilder withTitle(String title) {
        descriptor.setTitle(new Title(title));
        return this;
    }

    /**
     * Sets the {@code duration} of the {@code EditActivityDescriptor} that we are building.
     */
    public EditActivityDescriptorBuilder withDuration(Integer duration) {
        descriptor.setDuration(new Duration(duration));
        return this;
    }

    /**
     * Sets the {@code Location} of the {@code EditActivityDescriptor} that we are building.
     */
    public EditActivityDescriptorBuilder withLocation(String location) {
        descriptor.setLocation(new Location(location));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditActivityDescriptor}
     * that we are building.
     */
    public EditActivityDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditActivityDescriptor build() {
        return descriptor;
    }
}

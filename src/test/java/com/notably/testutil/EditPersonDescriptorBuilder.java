//TODO: To be enabled or changed when refactoring is complete
//package com.notably.testutil;
//
//import java.util.Set;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//import com.notably.logic.commands.EditCommand.EditPersonDescriptor;
//import com.notably.model.tag.Tag;
//
///**
// * A utility class to help with building EditPersonDescriptor objects.
// */
//public class EditPersonDescriptorBuilder {
//
//    private EditPersonDescriptor descriptor;
//
//    public EditPersonDescriptorBuilder() {
//        descriptor = new EditPersonDescriptor();
//    }
//
//    public EditPersonDescriptorBuilder(EditPersonDescriptor descriptor) {
//        this.descriptor = new EditPersonDescriptor(descriptor);
//    }
//
//    /**
//     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
//     */
//    public EditPersonDescriptorBuilder(Object person) {
//
//    }
//
//    /**
//     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
//     */
//    public void withName(String name) {
//    }
//
//    /**
//     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
//     */
//    public void withPhone(String phone) {
//    }
//
//    /**
//     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
//     */
//    public void withEmail(String email) {
//    }
//
//    /**
//     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
//     */
//    public void withAddress(String address) {
//    }
//
//    /**
//     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
//     * that we are building.
//     */
//    public EditPersonDescriptorBuilder withTags(String... tags) {
//        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
//        descriptor.setTags(tagSet);
//        return this;
//    }
//
//    public EditPersonDescriptor build() {
//        return descriptor;
//    }
//}

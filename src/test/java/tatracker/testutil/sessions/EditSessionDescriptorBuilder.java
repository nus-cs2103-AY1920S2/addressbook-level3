//@@author Chuayijing

package tatracker.testutil.sessions;

import java.time.LocalDate;
import java.time.LocalTime;

import tatracker.logic.commands.session.EditSessionCommand.EditSessionDescriptor;
import tatracker.model.session.Session;
import tatracker.model.session.SessionType;

/**
 * A utility class to help with building EditStudentDescriptor objects.
 */
public class EditSessionDescriptorBuilder {

    private EditSessionDescriptor descriptor;

    public EditSessionDescriptorBuilder() {
        descriptor = new EditSessionDescriptor();
    }

    public EditSessionDescriptorBuilder(EditSessionDescriptor descriptor) {
        this.descriptor = new EditSessionDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditSessionDescriptor} with fields containing {@code session}'s details
     */
    public EditSessionDescriptorBuilder(Session session) {
        descriptor = new EditSessionDescriptor();
        descriptor.setDate(session.getDate());
        descriptor.setStartTime(session.getStartDateTime().toLocalTime());
        descriptor.setEndTime(session.getEndDateTime().toLocalTime());
        descriptor.setRecurring(session.getRecurring());
        descriptor.setModuleCode(session.getModuleCode());
        descriptor.setSessionType(session.getSessionType());
        descriptor.setDescription(session.getDescription());
    }

    /**
     * Sets the {@code startTime} of the {@code EditSessionDescriptor} that we are building.
     */
    public EditSessionDescriptorBuilder withDate(LocalDate date) {
        descriptor.setDate(date);
        return this;
    }

    /**
     * Sets the {@code startTime} of the {@code EditSessionDescriptor} that we are building.
     */
    public EditSessionDescriptorBuilder withStartTime(LocalTime startTime) {
        descriptor.setStartTime(startTime);
        return this;
    }

    /**
     * Sets the {@code endTime} of the {@code EditSessionDescriptor} that we are building.
     */
    public EditSessionDescriptorBuilder withEndTime(LocalTime endTime) {
        descriptor.setEndTime(endTime);
        return this;
    }

    /**
     * Sets the {@code ModuleCode} of the {@code EditSessionDescriptorBuilder} that we are building.
     */
    public EditSessionDescriptorBuilder withModule(String module) {
        descriptor.setModuleCode(module);
        return this;
    }

    /**
     * Sets the {@code SessionType} of the {@code EditSessionDescriptorBuilder} that we are building.
     */
    public EditSessionDescriptorBuilder withSessionType(String type) {
        descriptor.setSessionType(SessionType.getSessionType(type));
        return this;
    }

    /**
     * Sets the {@code description} of the {@code EditSessionDescriptorBuilder} that we are building.
     */
    public EditSessionDescriptorBuilder withDescription(String d) {
        descriptor.setDescription(d);
        return this;
    }

    /**
     * Sets the {@code Recurring Week} of the {@code EditSessionDescriptorBuilder} that we are building.
     */
    public EditSessionDescriptorBuilder withRecurring(int weeks) {
        descriptor.setRecurring(weeks);
        return this;
    }

    /**
     * Builds the Session.
     */
    public EditSessionDescriptor build() {
        return descriptor;
    }

}

package seedu.address.testutil;

import seedu.address.logic.commands.reminders.EditReminderCommand.EditReminderDescriptor;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.reminder.Description;
import seedu.address.model.reminder.Reminder;

/**
 * A utility class to help with building EditReminderDescriptor objects.
 */
public class EditReminderDescriptorBuilder {

    private EditReminderDescriptor descriptor;

    public EditReminderDescriptorBuilder() {
        descriptor = new EditReminderDescriptor();
    }

    public EditReminderDescriptorBuilder(EditReminderDescriptor descriptor) {
        this.descriptor = new EditReminderDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditReminderDescriptor} with fields containing {@code reminder}'s details
     */
    public EditReminderDescriptorBuilder(Reminder reminder) {
        descriptor = new EditReminderDescriptor();
        descriptor.setDescription(reminder.getDescription());
        descriptor.setDate(reminder.getDate());
        descriptor.setTime(reminder.getTime());
    }

    /**
     * Sets the {@code Description} of the {@code EditReminderDescriptor} that we are building.
     */
    public EditReminderDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code EditReminderDescriptor} that we are building.
     */
    public EditReminderDescriptorBuilder withDate(String date) {
        try {
            descriptor.setDate(ParserUtil.parseDate(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code EditReminderDescriptor} that we are building.
     */
    public EditReminderDescriptorBuilder withTime(String time) {
        try {
            descriptor.setTime(ParserUtil.parseTime(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this;
    }

    public EditReminderDescriptor build() {
        return descriptor;
    }
}

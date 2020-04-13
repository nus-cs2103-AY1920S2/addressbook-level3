package seedu.address.testutil;

import seedu.address.logic.commands.consults.EditConsultCommand;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Location;
import seedu.address.model.event.consult.Consult;

/**
 * A utility class to help with building EditConsultDescriptor objects.
 */
public class EditConsultDescriptorBuilder {
    private EditConsultCommand.EditConsultDescriptor descriptor;

    public EditConsultDescriptorBuilder() {
        descriptor = new EditConsultCommand.EditConsultDescriptor();
    }

    public EditConsultDescriptorBuilder(EditConsultCommand.EditConsultDescriptor descriptor) {
        this.descriptor = new EditConsultCommand.EditConsultDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditConsultDescriptor} with fields containing {@code consult}'s details
     */
    public EditConsultDescriptorBuilder(Consult consult) {
        descriptor = new EditConsultCommand.EditConsultDescriptor();
        descriptor.setBeginDateTime(consult.getBeginDateTime());
        descriptor.setEndDateTime(consult.getEndDateTime());
        descriptor.setLocation(consult.getLocation());
    }

    /**
     * Sets the {@code Description} of the {@code EditReminderDescriptor} that we are building.
     */
    public EditConsultDescriptorBuilder withBeginDateTime(String beginDateTime) {
        try {
            descriptor.setBeginDateTime(ParserUtil.parseDateTime(beginDateTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code EditReminderDescriptor} that we are building.
     * @return
     */
    public EditConsultDescriptorBuilder withEndDateTime(String endDateTime) {
        try {
            descriptor.setEndDateTime(ParserUtil.parseDateTime(endDateTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code EditReminderDescriptor} that we are building.
     * @return EditConsultDescriptorBuilder
     */
    public EditConsultDescriptorBuilder withLocation(String location) {
        descriptor.setLocation(new Location(location));
        return this;
    }

    public EditConsultCommand.EditConsultDescriptor build() {
        return descriptor;
    }
}

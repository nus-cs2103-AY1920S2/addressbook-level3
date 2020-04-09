package tatracker.testutil.sessions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import tatracker.logic.commands.session.EditSessionCommand.EditSessionDescriptor;
import tatracker.logic.commands.student.EditStudentCommand.EditStudentDescriptor;
import tatracker.logic.parser.ParserUtil;
import tatracker.logic.parser.exceptions.ParseException;
import tatracker.model.session.Session;
import tatracker.model.session.SessionType;
import tatracker.model.student.Student;

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
        descriptor.setStartTime(session.getStartDateTime());
        descriptor.setEndTime(session.getEndDateTime());
        descriptor.setRecurring(session.getRecurring());
        descriptor.setModuleCode(session.getModuleCode());
        descriptor.setSessionType(session.getSessionType());
        descriptor.setDescription(session.getDescription());
        descriptor.setIsDateChanged(descriptor.getIsDateChanged());
    }

    /**
     * Sets the {@code startTime} of the {@code EditSessionDescriptor} that we are building.
     */
    public EditSessionDescriptorBuilder withStartTime(LocalDateTime startTime) throws ParseException {
        //LocalDate date = ParserUtil.parseDate(startTime);
        //LocalTime time = ParserUtil.parseTime(startTime);
//        descriptor.setStartTime(LocalDateTime.of(date, time));
        descriptor.setStartTime(startTime);
        return this;
    }

    /**
     * Sets the {@code endTime} of the {@code EditSessionDescriptor} that we are building.
     */
    public EditSessionDescriptorBuilder withEndTime(LocalDateTime endTime) throws ParseException {
        //LocalDate date = ParserUtil.parseDate(endTime);
        //LocalTime time = ParserUtil.parseTime(endTime);
//        descriptor.setEndTime(LocalDateTime.of(date, time));
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
    public EditSessionDescriptorBuilder withSessionType(String type) throws ParseException {
        SessionType s = ParserUtil.parseSessionType(type);
        descriptor.setSessionType(s);
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

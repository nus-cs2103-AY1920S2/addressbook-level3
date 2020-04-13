package tatracker.testutil;

import static tatracker.logic.parser.Prefixes.EMAIL;
import static tatracker.logic.parser.Prefixes.GROUP;
import static tatracker.logic.parser.Prefixes.MATRIC;
import static tatracker.logic.parser.Prefixes.MODULE;
import static tatracker.logic.parser.Prefixes.NAME;
import static tatracker.logic.parser.Prefixes.PHONE;
import static tatracker.logic.parser.Prefixes.RATING;
import static tatracker.logic.parser.Prefixes.TAG;

import java.util.Set;

import tatracker.logic.commands.student.AddStudentCommand;
import tatracker.logic.commands.student.EditStudentCommand.EditStudentDescriptor;
import tatracker.model.group.Group;
import tatracker.model.module.Module;
import tatracker.model.student.Student;
import tatracker.model.tag.Tag;

/**
 * A utility class for Student.
 */
public class StudentUtil {

    private static final Group TEST_GROUP = new Group("W17-4");
    private static final Module TEST_MODULE = new Module("CS2103T");

    /**
     * Returns an add command string for adding the {@code student}.
     */
    public static String getAddCommand(Student student) {
        return AddStudentCommand.DETAILS.getFullCommandWord() + " " + getStudentDetails(student) + " "
                + GROUP + " " + TEST_GROUP.getIdentifier() + " "
                + MODULE + " " + TEST_MODULE.getIdentifier();
    }

    /**
     * Returns the part of command string for the given {@code student}'s details.
     */
    public static String getStudentDetails(Student student) {
        StringBuilder sb = new StringBuilder();
        sb.append(NAME + student.getName().fullName + " ");
        sb.append(PHONE + student.getPhone().value + " ");
        sb.append(EMAIL + student.getEmail().value + " ");
        sb.append(MATRIC + student.getMatric().value + " ");
        sb.append(RATING + String.valueOf(student.getRating().value) + " ");
        student.getTags().forEach(s -> sb.append(TAG + s.tagName + " "));
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditStudentDescriptor}'s details.
     */
    public static String getEditStudentDescriptorDetails(EditStudentDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(EMAIL).append(email.value).append(" "));
        descriptor.getRating().ifPresent(rating -> sb.append(RATING).append(rating.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(TAG);
            } else {
                tags.forEach(s -> sb.append(TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}

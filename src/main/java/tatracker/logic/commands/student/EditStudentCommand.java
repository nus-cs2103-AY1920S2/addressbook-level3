// @@author potatocombat

package tatracker.logic.commands.student;

import static java.util.Objects.requireNonNull;
import static tatracker.commons.core.Messages.MESSAGE_INVALID_GROUP_CODE;
import static tatracker.commons.core.Messages.MESSAGE_INVALID_MODULE_CODE;
import static tatracker.commons.core.Messages.MESSAGE_INVALID_STUDENT;
import static tatracker.commons.core.Messages.MESSAGE_NOT_EDITED;
import static tatracker.logic.parser.Prefixes.EMAIL;
import static tatracker.logic.parser.Prefixes.GROUP;
import static tatracker.logic.parser.Prefixes.MATRIC;
import static tatracker.logic.parser.Prefixes.MODULE;
import static tatracker.logic.parser.Prefixes.NAME;
import static tatracker.logic.parser.Prefixes.PHONE;
import static tatracker.logic.parser.Prefixes.RATING;
import static tatracker.logic.parser.Prefixes.TAG;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import tatracker.commons.util.CollectionUtil;
import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandDetails;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.CommandResult.Action;
import tatracker.logic.commands.CommandWords;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.model.Model;
import tatracker.model.student.Email;
import tatracker.model.student.Matric;
import tatracker.model.student.Name;
import tatracker.model.student.Phone;
import tatracker.model.student.Rating;
import tatracker.model.student.Student;
import tatracker.model.tag.Tag;

/**
 * Edits the details of an existing student in the TA-Tracker.
 */
public class EditStudentCommand extends Command {

    // @@author potatocombat

    public static final CommandDetails DETAILS = new CommandDetails(
            CommandWords.STUDENT,
            CommandWords.EDIT_MODEL,
            "Edits the student with the given matric number inside the given module group",
            List.of(MATRIC, MODULE, GROUP),
            List.of(NAME, PHONE, EMAIL, RATING, TAG),
            MATRIC, MODULE, GROUP, NAME, PHONE, EMAIL, RATING, TAG
    );

    public static final String MESSAGE_EDIT_STUDENT_SUCCESS = "Edited student: %s (%s)\nIn %s [%s]";

    private final Matric matric;
    private final String moduleCode;
    private final String groupCode;

    private final EditStudentDescriptor editStudentDescriptor;

    /**
     * Creates an EditStudentCommand to edit the specified {@code Student} in the given module group.
     * @param matric id of the student in the filtered student list to edit
     * @param moduleCode of the module containing the student.
     * @param groupCode of the group containing the student.
     * @param editStudentDescriptor details to edit the student with
     */
    public EditStudentCommand(Matric matric, String moduleCode, String groupCode,
                              EditStudentDescriptor editStudentDescriptor) {
        requireNonNull(matric);
        requireNonNull(moduleCode);
        requireNonNull(groupCode);
        requireNonNull(editStudentDescriptor);

        this.matric = matric;
        this.moduleCode = moduleCode;
        this.groupCode = groupCode;

        this.editStudentDescriptor = new EditStudentDescriptor(editStudentDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!editStudentDescriptor.isAnyFieldEdited()) {
            throw new CommandException(MESSAGE_NOT_EDITED);
        }

        if (!model.hasModule(moduleCode)) {
            throw new CommandException(MESSAGE_INVALID_MODULE_CODE);
        }

        if (!model.hasGroup(groupCode, moduleCode)) {
            throw new CommandException(MESSAGE_INVALID_GROUP_CODE);
        }

        if (!model.hasStudent(matric, groupCode, moduleCode)) {
            throw new CommandException(MESSAGE_INVALID_STUDENT);
        }

        Student studentToEdit = model.getStudent(matric, groupCode, moduleCode);
        Student editedStudent = createEditedStudent(studentToEdit, editStudentDescriptor);

        model.setStudent(studentToEdit, editedStudent, groupCode, moduleCode);

        model.updateFilteredGroupList(moduleCode);
        model.updateFilteredStudentList(groupCode, moduleCode);

        return new CommandResult(getSuccessMessage(editedStudent), Action.GOTO_STUDENT);
    }

    /**
     * Creates and returns a {@code Student} with the details of {@code studentToEdit}
     * edited with {@code editStudentDescriptor}.
     */
    private static Student createEditedStudent(Student studentToEdit, EditStudentDescriptor editStudentDescriptor) {
        assert studentToEdit != null;

        Matric sameMatric = studentToEdit.getMatric();

        Name updatedName = editStudentDescriptor.getName().orElse(studentToEdit.getName());
        Phone updatedPhone = editStudentDescriptor.getPhone().orElse(studentToEdit.getPhone());
        Email updatedEmail = editStudentDescriptor.getEmail().orElse(studentToEdit.getEmail());
        Rating updatedRating = editStudentDescriptor.getRating().orElse(studentToEdit.getRating());
        Set<Tag> updatedTags = editStudentDescriptor.getTags().orElse(studentToEdit.getTags());

        return new Student(sameMatric, updatedName, updatedPhone, updatedEmail, updatedRating, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditStudentCommand)) {
            return false;
        }

        // state check
        EditStudentCommand otherCommand = (EditStudentCommand) other;
        return matric.equals(otherCommand.matric)
                && groupCode.equals(otherCommand.groupCode)
                && moduleCode.equals(otherCommand.moduleCode)
                && editStudentDescriptor.equals(otherCommand.editStudentDescriptor);
    }

    private String getSuccessMessage(Student student) {
        return String.format(MESSAGE_EDIT_STUDENT_SUCCESS,
                student.getName(),
                student.getMatric(),
                moduleCode,
                groupCode);
    }

    /**
     * Stores the details to edit the student with. Each non-empty field value will replace the
     * corresponding field value of the student.
     */
    public static class EditStudentDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Rating rating;
        private Set<Tag> tags;

        public EditStudentDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditStudentDescriptor(EditStudentDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setRating(toCopy.rating);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, rating, tags);
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

        public void setRating(Rating rating) {
            this.rating = rating;
        }

        public Optional<Rating> getRating() {
            return Optional.ofNullable(rating);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
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

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditStudentDescriptor)) {
                return false;
            }

            // state check
            EditStudentDescriptor e = (EditStudentDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getRating().equals(e.getRating())
                    && getTags().equals(e.getTags());
        }
    }
}


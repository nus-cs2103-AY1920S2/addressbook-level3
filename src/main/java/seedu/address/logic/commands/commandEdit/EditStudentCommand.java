package seedu.address.logic.commands.commandEdit;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.Constants;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.modelObjectTags.Gender;
import seedu.address.model.modelObjectTags.ID;
import seedu.address.model.modelObjectTags.Name;
import seedu.address.model.modelStudent.Student;
import seedu.address.model.tag.Tag;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

/**
 * Edits the details of an existing student in the address book.
 */
public class EditStudentCommand extends EditCommand {

    public static final String COMMAND_WORD = "edit-student";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Edits the details of the student identified "
                    + "by the ID number used in the displayed student list. "
                    + "Existing values will be overwritten by the input values.\n"
                    + "Parameters: ID (must be an existing positive integer) "
                    + "[" + PREFIX_NAME + "NAME] "
                    + "[" + PREFIX_GENDER + "GENDER] "
                    + "[" + PREFIX_TAG + "TAG]...\n"
                    + "Example: " + COMMAND_WORD + " 16100 "
                    + PREFIX_NAME + "Bob Ross "
                    + PREFIX_GENDER + "m "
                    + PREFIX_TAG + "freshman ";

    public static final String MESSAGE_EDIT_STUDENT_SUCCESS = "Edited Student: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_STUDENT = "This student already exists in the address book.";

    private ID targetID;
    private EditStudentDescriptor editStudentDescriptor;
    private Student toEdit;
    private Student editedStudent;

    /**
     * @param targetID              of the student in the filtered student list to edit
     * @param editStudentDescriptor details to edit the student with
     */
    public EditStudentCommand(ID targetID, EditStudentDescriptor editStudentDescriptor) {
        requireNonNull(targetID);
        requireNonNull(editStudentDescriptor);
        this.targetID = targetID;
        this.editStudentDescriptor = new EditStudentDescriptor(editStudentDescriptor);
    }

    /**
     * Creates and returns a {@code Student} with the details of {@code studentToEdit} edited with
     * {@code editStudentDescriptor}.
     */
    private static Student createEditedStudent(Student studentToEdit,
                                               EditStudentDescriptor editStudentDescriptor) {
        assert studentToEdit != null;

        Name updatedName = editStudentDescriptor.getName().orElse(studentToEdit.getName());
        Set<Tag> updatedTags = editStudentDescriptor.getTags().orElse(studentToEdit.getTags());
        Gender updatedGender = editStudentDescriptor.getGender().orElse(studentToEdit.getGender());
        // fields that cannot edit
        ID id = studentToEdit.getId();
        Set<ID> assignedCoursesID = studentToEdit.getAssignedCoursesID();
        return new Student(updatedName, id, updatedGender, assignedCoursesID, updatedTags);
    }

    @Override
    protected void generateOppositeCommand() {
        oppositeCommand = new EditStudentCommand(targetID, new EditStudentDescriptor(toEdit));
    }

    @Override
    protected void preprocessUndoableCommand(Model model) throws CommandException {
        requireNonNull(model);
        if (toEdit == null) {
            if (!ID.isValidId(targetID.toString())) {
                throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_ID);
            }
            if (!model.has(targetID, Constants.ENTITY_TYPE.STUDENT)) {
                throw new CommandException(Messages.MESSAGE_NOTFOUND_STUDENT_DISPLAYED_ID);
            }
            this.toEdit = (Student) model.get(targetID, Constants.ENTITY_TYPE.STUDENT);
            this.editedStudent = createEditedStudent(this.toEdit, editStudentDescriptor);
        }

        if (!this.toEdit.weakEquals(this.editedStudent) && model.has(this.editedStudent)) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDENT);
        }

    }

    @Override
    public CommandResult executeUndoableCommand(Model model) throws CommandException {
        requireNonNull(model);
        model.set(toEdit, editedStudent);
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        return new CommandResult(String.format(MESSAGE_EDIT_STUDENT_SUCCESS, editedStudent));
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
        EditStudentCommand e = (EditStudentCommand) other;
        return targetID.equals(e.targetID)
                && editStudentDescriptor.equals(e.editStudentDescriptor);
    }

    /**
     * Stores the details to edit the student with. Each non-empty field value will replace the
     * corresponding field value of the student.
     */
    public static class EditStudentDescriptor {

        private Name name;
        private Gender gender;
        private ID studentID;
        private Set<Tag> tags;

        public EditStudentDescriptor() {
        }

        /**
         * Copy constructor. A defensive copy of {@code tags} is used internally.
         */
        public EditStudentDescriptor(EditStudentDescriptor toCopy) {
            setName(toCopy.name);
            setGender(toCopy.gender);
            setID(toCopy.studentID);
            setTags(toCopy.tags);
        }

        /**
         * Copy constructor. A defensive copy of {@code tags} is used internally.
         */
        public EditStudentDescriptor(Student toCopy) {
            setName(toCopy.getName());
            setGender(toCopy.getGender());
            setID(toCopy.getId());
            setTags(toCopy.getTags());
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, gender, tags);
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Gender> getGender() {
            return Optional.ofNullable(gender);
        }

        public void setGender(Gender gender) {
            this.gender = gender;
        }

        public Optional<ID> getID() {
            return Optional.ofNullable(studentID);
        }

        public void setID(ID studentID) {
            this.studentID = studentID;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException} if
         * modification is attempted. Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        /**
         * Sets {@code tags} to this object's {@code tags}. A defensive copy of {@code tags} is used
         * internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
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
                    && getGender().equals(e.getGender())
                    && getID().equals(e.getID())
                    && getTags().equals(e.getTags());
        }
    }
}

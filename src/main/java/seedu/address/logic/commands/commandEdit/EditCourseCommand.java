package seedu.address.logic.commands.commandEdit;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.Constants;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.modelObjectTags.Amount;
import seedu.address.model.modelObjectTags.ID;
import seedu.address.model.modelObjectTags.Name;
import seedu.address.model.tag.Tag;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_COURSES;

/**
 * Edits the details of an existing course in the address book.
 */
public class EditCourseCommand extends EditCommand {

    public static final String COMMAND_WORD = "edit-course";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Edits the details of the course identified "
                    + "by the ID number used in the displayed course list. "
                    + "Existing values will be overwritten by the input values.\n"
                    + "Parameters: ID (must be an existing positive integer) "
                    + "[" + PREFIX_NAME + "NAME] "
                    + "[" + PREFIX_AMOUNT + "AMOUNT] "
                    + "[" + PREFIX_TAG + "TAG]...\n"
                    + "Example: " + COMMAND_WORD + " 16100 "
                    + PREFIX_NAME + "Java 101 "
                    + PREFIX_AMOUNT + "1000 "
                    + PREFIX_TAG + "intermediate";

    public static final String MESSAGE_EDIT_COURSE_SUCCESS = "Edited Assignment: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_COURSE = "This course already exists in the address book.";

    private ID targetID;
    private EditCourseDescriptor editCourseDescriptor;
    private Course toEdit;
    private Course editedCourse;

    /**
     * @param targetID             of the course in the filtered course list to edit
     * @param editCourseDescriptor details to edit the course with
     */
    public EditCourseCommand(ID targetID, EditCourseDescriptor editCourseDescriptor) {
        requireNonNull(targetID);
        requireNonNull(editCourseDescriptor);

        this.targetID = targetID;
        this.editCourseDescriptor = new EditCourseDescriptor(editCourseDescriptor);
    }

    /**
     * Creates and returns a {@code Assignment} with the details of {@code courseToEdit} edited with
     * {@code editCourseDescriptor}.
     */
    private static Course createEditedCourse(Course courseToEdit,
                                             EditCourseDescriptor editCourseDescriptor) {
        assert courseToEdit != null;

        Name updatedName = editCourseDescriptor.getName().orElse(courseToEdit.getName());
        Amount updatedAmount = editCourseDescriptor.getAmount().orElse(courseToEdit.getAmount());
        Set<Tag> updatedTags = editCourseDescriptor.getTags().orElse(courseToEdit.getTags());

        // fields that cannot edit
        ID courseID = courseToEdit.getId();
        ID assignedStaffID = courseToEdit.getAssignedStaffID();
        Set<ID> assignedAssignmentsID = courseToEdit.getAssignedAssignmentsID();
        Set<ID> assignedStudentsID = courseToEdit.getAssignedStudentsID();
        return new Course(updatedName, courseID, updatedAmount,
                assignedStaffID, assignedStudentsID, assignedAssignmentsID, updatedTags);
    }

    @Override
    protected void generateOppositeCommand() {
        oppositeCommand = new EditCourseCommand(targetID, new EditCourseCommand.EditCourseDescriptor(toEdit));
    }

    @Override
    protected void preprocessUndoableCommand(Model model) throws CommandException {
        requireNonNull(model);
        if (toEdit == null) {
            if (!ID.isValidId(targetID.toString())) {
                throw new CommandException(Messages.MESSAGE_INVALID_COURSE_DISPLAYED_ID);
            }
            if (!model.has(targetID, Constants.ENTITY_TYPE.COURSE)) {
                throw new CommandException(Messages.MESSAGE_NOTFOUND_COURSE_DISPLAYED_ID);
            }
            this.toEdit = (Course) model.get(targetID, Constants.ENTITY_TYPE.COURSE);
            this.editedCourse = createEditedCourse(toEdit, editCourseDescriptor);
        }
        if (!toEdit.weakEquals(editedCourse) && model.has(editedCourse)) {
            throw new CommandException(MESSAGE_DUPLICATE_COURSE);
        }
    }

    @Override
    public CommandResult executeUndoableCommand(Model model) throws CommandException {
        requireNonNull(model);
        model.set(toEdit, editedCourse);
        model.updateFilteredCourseList(PREDICATE_SHOW_ALL_COURSES);
        return new CommandResult(String.format(MESSAGE_EDIT_COURSE_SUCCESS, editedCourse));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCourseCommand)) {
            return false;
        }

        // state check
        EditCourseCommand e = (EditCourseCommand) other;
        return targetID.equals(e.targetID)
                && editCourseDescriptor.equals(e.editCourseDescriptor);
    }

    /**
     * Stores the details to edit the course with. Each non-empty field value will replace the
     * corresponding field value of the course.
     */
    public static class EditCourseDescriptor {

        private Name name;
        private Amount amount;
        private Set<Tag> tags;

        public EditCourseDescriptor() {
        }

        /**
         * Copy constructor. A defensive copy of {@code tags} is used internally.
         */
        public EditCourseDescriptor(EditCourseDescriptor toCopy) {
            setName(toCopy.name);
            setAmount(toCopy.amount);
            setTags(toCopy.tags);
        }

        /**
         * Copy constructor. A defensive copy of {@code tags} is used internally.
         */
        public EditCourseDescriptor(Course toCopy) {
            setName(toCopy.getName());
            setAmount(toCopy.getAmount());
            setTags(toCopy.getTags());
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, amount, tags);
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Amount> getAmount() {
            return Optional.ofNullable(amount);
        }

        public void setAmount(Amount amount) {
            this.amount = amount;
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
            if (!(other instanceof EditCourseDescriptor)) {
                return false;
            }

            // state check
            EditCourseDescriptor e = (EditCourseDescriptor) other;

            return getName().equals(e.getName())
                    && getAmount().equals(e.getAmount())
                    && getTags().equals(e.getTags());
        }
    }
}

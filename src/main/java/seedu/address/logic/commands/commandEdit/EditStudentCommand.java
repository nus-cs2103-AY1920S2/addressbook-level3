package seedu.address.logic.commands.commandEdit;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.modelStudent.Student;
import seedu.address.model.person.AssignedCourse;
import seedu.address.model.person.Name;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing student in the address book.
 */
public class EditStudentCommand extends Command {

  public static final String COMMAND_WORD = "edit-student";

  public static final String MESSAGE_USAGE =
      COMMAND_WORD + ": Edits the details of the student identified "
          + "by the index number used in the displayed student list. "
          + "Existing values will be overwritten by the input values.\n"
          + "Parameters: INDEX (must be a positive integer) "
          + "[" + PREFIX_NAME + "NAME] "
          + "[" + PREFIX_COURSE + "COURSE] "
          + "[" + PREFIX_TAG + "TAG]...\n"
          + "Example: " + COMMAND_WORD + " 1 "
          + PREFIX_NAME + "Bob Ross "
          + PREFIX_COURSE + "Java Programming ";

  public static final String MESSAGE_EDIT_STUDENT_SUCCESS = "Edited Student: %1$s";
  public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
  public static final String MESSAGE_DUPLICATE_STUDENT = "This student already exists in the address book.";

  private final Index index;
  private final EditStudentDescriptor editStudentDescriptor;

  /**
   * @param index                 of the student in the filtered student list to edit
   * @param editStudentDescriptor details to edit the student with
   */
  public EditStudentCommand(Index index, EditStudentDescriptor editStudentDescriptor) {
    requireNonNull(index);
    requireNonNull(editStudentDescriptor);

    this.index = index;
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
    AssignedCourse updatedCourse = editStudentDescriptor.getCourse()
        .orElse(studentToEdit.getCourse());
    Set<Tag> updatedTags = editStudentDescriptor.getTags().orElse(studentToEdit.getTags());

    return new Student(updatedName, updatedCourse, updatedTags);
  }

  @Override
  public CommandResult execute(Model model) throws CommandException {
    requireNonNull(model);
    List<Student> lastShownList = model.getFilteredStudentList();

    if (index.getZeroBased() >= lastShownList.size()) {
      throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    Student studentToEdit = lastShownList.get(index.getZeroBased());
    Student editedStudent = createEditedStudent(studentToEdit, editStudentDescriptor);

    if (!studentToEdit.isSameStudent(editedStudent) && model.hasStudent(editedStudent)) {
      throw new CommandException(MESSAGE_DUPLICATE_STUDENT);
    }

    model.setStudent(studentToEdit, editedStudent);
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
    return index.equals(e.index)
        && editStudentDescriptor.equals(e.editStudentDescriptor);
  }

  /**
   * Stores the details to edit the student with. Each non-empty field value will replace the
   * corresponding field value of the student.
   */
  public static class EditStudentDescriptor {

    private Name name;
    private AssignedCourse course;
    private Set<Tag> tags;

    public EditStudentDescriptor() {
    }

    /**
     * Copy constructor. A defensive copy of {@code tags} is used internally.
     */
    public EditStudentDescriptor(EditStudentDescriptor toCopy) {
      setName(toCopy.name);
      setCourse(toCopy.course);
      setTags(toCopy.tags);
    }

    /**
     * Returns true if at least one field is edited.
     */
    public boolean isAnyFieldEdited() {
      return CollectionUtil.isAnyNonNull(name, course, tags);
    }

    public Optional<Name> getName() {
      return Optional.ofNullable(name);
    }

    public void setName(Name name) {
      this.name = name;
    }

    public Optional<AssignedCourse> getCourse() {
      return Optional.ofNullable(course);
    }

    public void setCourse(AssignedCourse course) {
      this.course = course;
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
          && getCourse().equals(e.getCourse())
          && getTags().equals(e.getTags());
    }
  }
}

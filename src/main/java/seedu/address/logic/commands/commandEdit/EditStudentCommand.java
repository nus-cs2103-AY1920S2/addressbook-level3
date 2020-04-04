package seedu.address.logic.commands.commandEdit;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import seedu.address.commons.core.Messages;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.modelStaff.Staff;
import seedu.address.model.modelStudent.Student;
import seedu.address.model.person.ID;
import seedu.address.model.person.Name;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing student in the address book.
 */
public class EditStudentCommand extends Command {

  public static final String COMMAND_WORD = "edit-student";

  public static final String MESSAGE_USAGE =
      COMMAND_WORD + ": Edits the details of the student identified "
          + "by the ID number used in the displayed student list. "
          + "Existing values will be overwritten by the input values.\n"
          + "Parameters: ID (must be an existing positive integer) "
          + "[" + PREFIX_NAME + "NAME] "
          + "[" + PREFIX_STUDENTID + "STUDENTID] "
          + "[" + PREFIX_TAG + "TAG]...\n"
          + "Example: " + COMMAND_WORD + " 16100 "
          + PREFIX_NAME + "Bob Ross "
          + PREFIX_STUDENTID + "123 ";

  public static final String MESSAGE_EDIT_STUDENT_SUCCESS = "Edited Student: %1$s";
  public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
  public static final String MESSAGE_DUPLICATE_STUDENT = "This student already exists in the address book.";

  private final ID targetID;
  private final EditStudentDescriptor editStudentDescriptor;

  /**
   * @param targetID                 of the student in the filtered student list to edit
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

    // fields that cannot edit
    ID id = studentToEdit.getId();
    Set<ID> assignedCoursesID = studentToEdit.getAssignedCoursesID();
    return new Student(updatedName, id, assignedCoursesID, updatedTags);
  }

  @Override
  public CommandResult execute(Model model) throws CommandException {
    requireNonNull(model);
    List<Student> lastShownList = model.getFilteredStudentList();

    if (!ID.isValidId(targetID.toString())) {
      throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_ID);
    }

    Student studentToEdit = getStudent(lastShownList);
    Student editedStudent = createEditedStudent(studentToEdit, editStudentDescriptor);

    if (!studentToEdit.weakEquals(editedStudent) && model.has(editedStudent)) {
      throw new CommandException(MESSAGE_DUPLICATE_STUDENT);
    }

    model.set(studentToEdit, editedStudent);
    model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
    return new CommandResult(String.format(MESSAGE_EDIT_STUDENT_SUCCESS, editedStudent));
  }

  // Find way to abstract this
  public Student getStudent(List<Student> lastShownList) throws CommandException {
    for (Student student : lastShownList) {
      if (student.getId().equals(this.targetID)) {
        return student;
      }
    }
    throw new CommandException("This staff ID does not exist");
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
    private ID studentID;
    private Set<Tag> tags;

    public EditStudentDescriptor() {
    }

    /**
     * Copy constructor. A defensive copy of {@code tags} is used internally.
     */
    public EditStudentDescriptor(EditStudentDescriptor toCopy) {
      setName(toCopy.name);
      setID(toCopy.studentID);
      setTags(toCopy.tags);
    }

    /**
     * Returns true if at least one field is edited.
     */
    public boolean isAnyFieldEdited() {
      return CollectionUtil.isAnyNonNull(name, studentID, tags);
    }

    public Optional<Name> getName() {
      return Optional.ofNullable(name);
    }

    public void setName(Name name) {
      this.name = name;
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
          && getID().equals(e.getID())
          && getTags().equals(e.getTags());
    }
  }
}

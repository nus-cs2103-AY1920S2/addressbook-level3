package seedu.address.logic.commands.commandEdit;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SALARY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
<<<<<<< HEAD
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STAFFS;
=======
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEACHERID;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TEACHERS;
>>>>>>> cc58058640d6b9fdcab1ce76c9dad9da09540efa

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
import seedu.address.model.modelStaff.Staff;
import seedu.address.model.modelStaff.Staff.Level;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.ID;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Salary;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing teacher in the address book.
 */
public class EditTeacherCommand extends Command {

  public static final String COMMAND_WORD = "edit-teacher";

  public static final String MESSAGE_USAGE =
      COMMAND_WORD + ": Edits the details of the teacher identified "
          + "by the index number used in the displayed teacher list. "
          + "Existing values will be overwritten by the input values.\n"
          + "Parameters: INDEX (must be a positive integer) "
          + "[" + PREFIX_NAME + "NAME] "
          + "[" + PREFIX_TEACHERID + "TEACHERID] "
          + "[" + PREFIX_PHONE + "PHONE] "
          + "[" + PREFIX_EMAIL + "EMAIL] "
          + "[" + PREFIX_SALARY + "SALARY] "
          + "[" + PREFIX_ADDRESS + "ADDRESS] "
          + "[" + PREFIX_TAG + "TAG]...\n"
          + "Example: " + COMMAND_WORD + " 1 "
          + PREFIX_PHONE + "91234567 "
          + PREFIX_EMAIL + "johndoe@example.com";

  public static final String MESSAGE_EDIT_TEACHER_SUCCESS = "Edited Teacher: %1$s";
  public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
  public static final String MESSAGE_DUPLICATE_TEACHER = "This teacher already exists in the address book.";

  private final Index index;
  private final EditTeacherDescriptor editTeacherDescriptor;

  /**
   * @param index                 of the teacher in the filtered teacher list to edit
   * @param editTeacherDescriptor details to edit the teacher with
   */
  public EditTeacherCommand(Index index, EditTeacherDescriptor editTeacherDescriptor) {
    requireNonNull(index);
    requireNonNull(editTeacherDescriptor);

    this.index = index;
    this.editTeacherDescriptor = new EditTeacherDescriptor(editTeacherDescriptor);
  }

  /**
   * Creates and returns a {@code Teacher} with the details of {@code teacherToEdit} edited with
   * {@code editTeacherDescriptor}.
   */
  private static Staff createEditedTeacher(Staff teacherToEdit,
      EditTeacherDescriptor editTeacherDescriptor) {
    assert teacherToEdit != null;

    Name updatedName = editTeacherDescriptor.getName().orElse(teacherToEdit.getName());
<<<<<<< HEAD
    Level updatedLevel = editTeacherDescriptor.getLevel().orElse(teacherToEdit.getLevel());
=======
    ID updatedID = editTeacherDescriptor.getID().orElse(teacherToEdit.getID());
>>>>>>> cc58058640d6b9fdcab1ce76c9dad9da09540efa
    Phone updatedPhone = editTeacherDescriptor.getPhone().orElse(teacherToEdit.getPhone());
    Email updatedEmail = editTeacherDescriptor.getEmail().orElse(teacherToEdit.getEmail());
    Salary updatedSalary = editTeacherDescriptor.getSalary().orElse(teacherToEdit.getSalary());
    Address updatedAddress = editTeacherDescriptor.getAddress().orElse(teacherToEdit.getAddress());
    Set<Tag> updatedTags = editTeacherDescriptor.getTags().orElse(teacherToEdit.getTags());

<<<<<<< HEAD
    return new Staff(updatedName, updatedLevel, updatedPhone, updatedEmail, updatedSalary, updatedAddress,
=======
    return new Teacher(updatedName, updatedID, updatedPhone, updatedEmail, updatedSalary, updatedAddress,
>>>>>>> cc58058640d6b9fdcab1ce76c9dad9da09540efa
        updatedTags);
  }

  @Override
  public CommandResult execute(Model model) throws CommandException {
    requireNonNull(model);
    List<Staff> lastShownList = model.getFilteredStaffList();

    if (index.getZeroBased() >= lastShownList.size()) {
      throw new CommandException(Messages.MESSAGE_INVALID_TEACHER_DISPLAYED_INDEX);
    }

    Staff teacherToEdit = lastShownList.get(index.getZeroBased());
    Staff editedTeacher = createEditedTeacher(teacherToEdit, editTeacherDescriptor);

<<<<<<< HEAD
    if (!teacherToEdit.weakEquals(editedTeacher) && model.hasStaff(editedTeacher)) {
      throw new CommandException(MESSAGE_DUPLICATE_TEACHER);
    }

    model.setStaff(teacherToEdit, editedTeacher);
    model.updateFilteredStaffList(PREDICATE_SHOW_ALL_STAFFS);
=======
    if (!teacherToEdit.weakEquals(editedTeacher) && model.has(editedTeacher)) {
      throw new CommandException(MESSAGE_DUPLICATE_TEACHER);
    }

    model.set(teacherToEdit, editedTeacher);
    model.updateFilteredTeacherList(PREDICATE_SHOW_ALL_TEACHERS);
>>>>>>> cc58058640d6b9fdcab1ce76c9dad9da09540efa
    return new CommandResult(String.format(MESSAGE_EDIT_TEACHER_SUCCESS, editedTeacher));
  }

  @Override
  public boolean equals(Object other) {
    // short circuit if same object
    if (other == this) {
      return true;
    }

    // instanceof handles nulls
    if (!(other instanceof EditTeacherCommand)) {
      return false;
    }

    // state check
    EditTeacherCommand e = (EditTeacherCommand) other;
    return index.equals(e.index)
        && editTeacherDescriptor.equals(e.editTeacherDescriptor);
  }

  /**
   * Stores the details to edit the teacher with. Each non-empty field value will replace the
   * corresponding field value of the teacher.
   */
  public static class EditTeacherDescriptor {

    private Name name;
<<<<<<< HEAD
    private Level level;
=======
    private ID teacherID;
>>>>>>> cc58058640d6b9fdcab1ce76c9dad9da09540efa
    private Phone phone;
    private Email email;
    private Salary salary;
    private Address address;
    private Set<Tag> tags;

    public EditTeacherDescriptor() {
    }

    /**
     * Copy constructor. A defensive copy of {@code tags} is used internally.
     */
    public EditTeacherDescriptor(EditTeacherDescriptor toCopy) {
      setName(toCopy.name);
<<<<<<< HEAD
      setLevel(toCopy.level);
=======
      setID(toCopy.teacherID);
>>>>>>> cc58058640d6b9fdcab1ce76c9dad9da09540efa
      setPhone(toCopy.phone);
      setEmail(toCopy.email);
      setSalary(toCopy.salary);
      setAddress(toCopy.address);
      setTags(toCopy.tags);
    }

    /**
     * Returns true if at least one field is edited.
     */
    public boolean isAnyFieldEdited() {
      return CollectionUtil.isAnyNonNull(name, phone, email, salary, address, tags);
    }

    public Optional<Name> getName() {
      return Optional.ofNullable(name);
    }

    public Optional<Level> getLevel() {
      return Optional.ofNullable(level);
    }

    public void setLevel(Level level) {
      this.level = level;
    }

    public void setName(Name name) {
      this.name = name;
    }

    public Optional<ID> getID() {
      return Optional.ofNullable(teacherID);
    }

    public void setID(ID teacherID) {
      this.teacherID = teacherID;
    }

    public Optional<Phone> getPhone() {
      return Optional.ofNullable(phone);
    }

    public void setPhone(Phone phone) {
      this.phone = phone;
    }

    public Optional<Email> getEmail() {
      return Optional.ofNullable(email);
    }

    public void setEmail(Email email) {
      this.email = email;
    }

    public Optional<Salary> getSalary() {
      return Optional.ofNullable(salary);
    }

    public void setSalary(Salary salary) {
      this.salary = salary;
    }

    public Optional<Address> getAddress() {
      return Optional.ofNullable(address);
    }

    public void setAddress(Address address) {
      this.address = address;
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
      if (!(other instanceof EditTeacherDescriptor)) {
        return false;
      }

      // state check
      EditTeacherDescriptor e = (EditTeacherDescriptor) other;

      return getName().equals(e.getName())
          && getID().equals(e.getID())
          && getPhone().equals(e.getPhone())
          && getEmail().equals(e.getEmail())
          && getSalary().equals(e.getSalary())
          && getAddress().equals(e.getAddress())
          && getTags().equals(e.getTags());
    }
  }
}

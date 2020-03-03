package seedu.address.model.modelTeacher;

import static java.util.Objects.requireNonNull;

import java.util.List;
import javafx.collections.ObservableList;

/**
 * Wraps all data at the address-book level Duplicates are not allowed (by .isSamePerson
 * comparison)
 */
public class TeacherAddressBook implements ReadOnlyTeacherAddressBook {

  private final UniqueTeacherList teachers;

  /*
   * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
   * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
   *
   * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
   *   among constructors.
   */ {
    teachers = new UniqueTeacherList();
  }

  public TeacherAddressBook() {
  }

  /**
   * Creates an AddressBook using the Persons in the {@code toBeCopied}
   */
  public TeacherAddressBook(ReadOnlyTeacherAddressBook toBeCopied) {
    this();
    resetData(toBeCopied);
  }

  //// list overwrite operations

  /**
   * Replaces the contents of the teacher list with {@code teachers}. {@code teachers} must not
   * contain duplicate teachers.
   */
  public void setTeachers(List<Teacher> teachers) {
    this.teachers.setTeachers(teachers);
  }

  /**
   * Resets the existing data of this {@code AddressBook} with {@code newData}.
   */
  public void resetData(ReadOnlyTeacherAddressBook newData) {
    requireNonNull(newData);

    setTeachers(newData.getTeacherList());
  }

  //// teacher-level operations

  /**
   * Returns true if a teacher with the same identity as {@code teacher} exists in the address
   * book.
   */
  public boolean hasTeachers(Teacher teacher) {
    requireNonNull(teacher);
    return teachers.contains(teacher);
  }

  /**
   * Adds a teacher to the address book. The teacher must not already exist in the address book.
   */
  public void addTeacher(Teacher p) {
    teachers.add(p);
  }

  /**
   * Replaces the given teacher {@code target} in the list with {@code editedTeacher}. {@code
   * target} must exist in the address book. The teacher identity of {@code editedTeacher} must not
   * be the same as another existing teacher in the address book.
   */
  public void setTeacher(Teacher target, Teacher editedTeacher) {
    requireNonNull(editedTeacher);

    teachers.setTeacher(target, editedTeacher);
  }

  /**
   * Removes {@code key} from this {@code AddressBook}. {@code key} must exist in the address book.
   */
  public void removeTeacher(Teacher key) {
    teachers.remove(key);
  }

  //// util methods

  @Override
  public String toString() {
    return teachers.asUnmodifiableObservableList().size() + " teachers";
    // TODO: refine later
  }

  @Override
  public ObservableList<Teacher> getTeacherList() {
    return teachers.asUnmodifiableObservableList();
  }

  @Override
  public boolean equals(Object other) {
    return other == this // short circuit if same object
        || (other instanceof TeacherAddressBook // instanceof handles nulls
        && teachers.equals(((TeacherAddressBook) other).teachers));
  }

  @Override
  public int hashCode() {
    return teachers.hashCode();
  }
}

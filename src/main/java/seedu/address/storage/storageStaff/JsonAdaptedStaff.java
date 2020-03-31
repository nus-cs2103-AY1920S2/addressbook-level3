package seedu.address.storage.storageStaff;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.modelStaff.Staff;
import seedu.address.model.person.Address;
import seedu.address.model.person.AssignedCourses;
import seedu.address.model.person.Email;
import seedu.address.model.person.ID;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Salary;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedStaff {

  public static final String MISSING_FIELD_MESSAGE_FORMAT = "Staff's %s field is missing!";

  private final String name;
<<<<<<< HEAD:src/main/java/seedu/address/storage/storageStaff/JsonAdaptedStaff.java
  private final String level;
=======
  private final String teacherID;
>>>>>>> cc58058640d6b9fdcab1ce76c9dad9da09540efa:src/main/java/seedu/address/storage/storageTeacher/JsonAdaptedTeacher.java
  private final String phone;
  private final String email;
  private final String salary;
  private final String address;
<<<<<<< HEAD:src/main/java/seedu/address/storage/storageStaff/JsonAdaptedStaff.java
  private final List<JsonStaffAdaptedTag> tagged = new ArrayList<>();
=======
  private final List<JsonTeacherAdaptedID> assignedCoursesID = new ArrayList<>();
  private final List<JsonTeacherAdaptedTag> tagged = new ArrayList<>();
>>>>>>> cc58058640d6b9fdcab1ce76c9dad9da09540efa:src/main/java/seedu/address/storage/storageTeacher/JsonAdaptedTeacher.java

  /**
   * Constructs a {@code JsonAdaptedPerson} with the given person details.
   */
  @JsonCreator
<<<<<<< HEAD:src/main/java/seedu/address/storage/storageStaff/JsonAdaptedStaff.java
  public JsonAdaptedStaff(@JsonProperty("name") String name, @JsonProperty("level") String level,
                          @JsonProperty("phone") String phone,
                          @JsonProperty("email") String email, @JsonProperty("salary") String salary,
                          @JsonProperty("address") String address,
                          @JsonProperty("tagged") List<JsonStaffAdaptedTag> tagged) {
    this.name = name;
    this.level = level;
=======
  public JsonAdaptedTeacher(@JsonProperty("name") String name, @JsonProperty("teacherID") String teacherID,
      @JsonProperty("phone") String phone,
      @JsonProperty("email") String email, @JsonProperty("salary") String salary,
      @JsonProperty("address") String address,
      @JsonProperty("assignedCoursesID") List<JsonTeacherAdaptedID> assignedCoursesID,
      @JsonProperty("tagged") List<JsonTeacherAdaptedTag> tagged) {
    this.name = name;
    this.teacherID = teacherID;
>>>>>>> cc58058640d6b9fdcab1ce76c9dad9da09540efa:src/main/java/seedu/address/storage/storageTeacher/JsonAdaptedTeacher.java
    this.phone = phone;
    this.email = email;
    this.salary = salary;
    this.address = address;
    if (assignedCoursesID != null) {
      this.assignedCoursesID.addAll(assignedCoursesID);
    }
    if (tagged != null) {
      this.tagged.addAll(tagged);
    }
  }

  /**
   * Converts a given {@code Staff} into this class for Jackson use.
   */
  public JsonAdaptedStaff(Staff source) {
    name = source.getName().fullName;
<<<<<<< HEAD:src/main/java/seedu/address/storage/storageStaff/JsonAdaptedStaff.java
    level = source.getLevel().toString();
=======
    teacherID = source.getID().value;
>>>>>>> cc58058640d6b9fdcab1ce76c9dad9da09540efa:src/main/java/seedu/address/storage/storageTeacher/JsonAdaptedTeacher.java
    phone = source.getPhone().value;
    email = source.getEmail().value;
    salary = source.getSalary().value;
    address = source.getAddress().value;
    assignedCoursesID.addAll(source.getAssignedCoursesID().stream()
        .map(JsonTeacherAdaptedID::new)
        .collect(Collectors.toList()));
    tagged.addAll(source.getTags().stream()
        .map(JsonStaffAdaptedTag::new)
        .collect(Collectors.toList()));
  }

  /**
   * Converts this Jackson-friendly adapted staff object into the model's {@code Staff} object.
   *
   * @throws IllegalValueException if there were any data constraints violated in the adapted
   *                               staff.
   */
<<<<<<< HEAD:src/main/java/seedu/address/storage/storageStaff/JsonAdaptedStaff.java
  public Staff toModelType() throws IllegalValueException {
    final List<Tag> staffTags = new ArrayList<>();
    for (JsonStaffAdaptedTag tag : tagged) {
      staffTags.add(tag.toModelType());
    }

=======
  public Teacher toModelType() throws IllegalValueException {
>>>>>>> cc58058640d6b9fdcab1ce76c9dad9da09540efa:src/main/java/seedu/address/storage/storageTeacher/JsonAdaptedTeacher.java
    if (name == null) {
      throw new IllegalValueException(
          String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
    }
    if (!Name.isValidName(name)) {
      throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
    }
    final Name modelName = new Name(name);

<<<<<<< HEAD:src/main/java/seedu/address/storage/storageStaff/JsonAdaptedStaff.java
    if (level == null) {
      throw new IllegalValueException("Missing level field, unidentified Staff");
    }
    /*
    if (!level.equals("TEACHER") || !level.equals("ADMIN")) {
      throw new IllegalValueException("Wrong level field for staff, level should be saved as 0 or 1");
    }
     */
    Staff.Level modelLevel = Staff.Level.TEACHER;
    if (level.equals("ADMIN")) {
      modelLevel = Staff.Level.ADMIN;
    }
=======
    if (!ID.isValidId(teacherID)) {
      throw new IllegalValueException(ID.MESSAGE_CONSTRAINTS);
    }
    final ID modelID = new ID(teacherID);
>>>>>>> cc58058640d6b9fdcab1ce76c9dad9da09540efa:src/main/java/seedu/address/storage/storageTeacher/JsonAdaptedTeacher.java

    if (phone == null) {
      throw new IllegalValueException(
          String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
    }
    if (!Phone.isValidPhone(phone)) {
      throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
    }
    final Phone modelPhone = new Phone(phone);

    if (email == null) {
      throw new IllegalValueException(
          String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
    }
    if (!Email.isValidEmail(email)) {
      throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
    }
    final Email modelEmail = new Email(email);

    if (!Salary.isValidSalary(salary)) {
      throw new IllegalValueException(Salary.MESSAGE_CONSTRAINTS);
    }
    final Salary modelSalary = new Salary(salary);

    if (address == null) {
      throw new IllegalValueException(
          String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
    }
    if (!Address.isValidAddress(address)) {
      throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
    }
    final Address modelAddress = new Address(address);

<<<<<<< HEAD:src/main/java/seedu/address/storage/storageStaff/JsonAdaptedStaff.java
    final Set<Tag> modelTags = new HashSet<>(staffTags);
    return new Staff(modelName, modelLevel, modelPhone, modelEmail, modelSalary, modelAddress, modelTags);
=======
    final List<ID> TeacherAssignedCoursesID = new ArrayList<>();
    for (JsonTeacherAdaptedID id : assignedCoursesID) {
      TeacherAssignedCoursesID.add(id.toModelType());
    }
    final Set<ID> modelAssignedCoursesID = new HashSet<>(TeacherAssignedCoursesID);

    final List<Tag> courseTags = new ArrayList<>();
    for (JsonTeacherAdaptedTag tag : tagged) {
      courseTags.add(tag.toModelType());
    }

    final Set<Tag> modelTags = new HashSet<>(courseTags);
    return new Teacher(modelName, modelID, modelPhone, modelEmail, modelSalary, modelAddress, modelAssignedCoursesID, modelTags);
>>>>>>> cc58058640d6b9fdcab1ce76c9dad9da09540efa:src/main/java/seedu/address/storage/storageTeacher/JsonAdaptedTeacher.java
  }

}

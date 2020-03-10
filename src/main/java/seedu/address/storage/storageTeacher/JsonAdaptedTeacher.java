package seedu.address.storage.storageTeacher;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.modelTeacher.Teacher;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Salary;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedTeacher {

  public static final String MISSING_FIELD_MESSAGE_FORMAT = "Teacher's %s field is missing!";

  private final String name;
  private final String phone;
  private final String email;
  private final String salary;
  private final String address;
  private final List<JsonTeacherAdaptedTag> tagged = new ArrayList<>();

  /**
   * Constructs a {@code JsonAdaptedPerson} with the given person details.
   */
  @JsonCreator
  public JsonAdaptedTeacher(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
      @JsonProperty("email") String email, @JsonProperty("salary") String salary,
      @JsonProperty("address") String address,
      @JsonProperty("tagged") List<JsonTeacherAdaptedTag> tagged) {
    this.name = name;
    this.phone = phone;
    this.email = email;
    this.salary = salary;
    this.address = address;
    if (tagged != null) {
      this.tagged.addAll(tagged);
    }
  }

  /**
   * Converts a given {@code Teacher} into this class for Jackson use.
   */
  public JsonAdaptedTeacher(Teacher source) {
    name = source.getName().fullName;
    phone = source.getPhone().value;
    email = source.getEmail().value;
    salary = source.getSalary().value;
    address = source.getAddress().value;
    tagged.addAll(source.getTags().stream()
        .map(JsonTeacherAdaptedTag::new)
        .collect(Collectors.toList()));
  }

  /**
   * Converts this Jackson-friendly adapted teacher object into the model's {@code Teacher} object.
   *
   * @throws IllegalValueException if there were any data constraints violated in the adapted
   *                               teacher.
   */
  public Teacher toModelType() throws IllegalValueException {
    final List<Tag> teacherTags = new ArrayList<>();
    for (JsonTeacherAdaptedTag tag : tagged) {
      teacherTags.add(tag.toModelType());
    }

    if (name == null) {
      throw new IllegalValueException(
          String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
    }
    if (!Name.isValidName(name)) {
      throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
    }
    final Name modelName = new Name(name);

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

    final Set<Tag> modelTags = new HashSet<>(teacherTags);
    return new Teacher(modelName, modelPhone, modelEmail, modelSalary, modelAddress, modelTags);
  }

}

package seedu.address.storage.storageStaff;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.modelObjectTags.*;
import seedu.address.model.modelStaff.Staff;
import seedu.address.model.tag.Tag;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedStaff {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Staff's %s field is missing!";

    private final String name;
    private final String level;
    private final String id;
    private final String gender;
    private final String phone;
    private final String email;
    private final String salary;
    private final String address;

    private final List<JsonStaffAdaptedID> assignedCoursesID = new ArrayList<>();
    private final List<JsonStaffAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedStaff(@JsonProperty("name") String name, @JsonProperty("id") String id,
                            @JsonProperty("gender") String gender,
                            @JsonProperty("level") String level,
                            @JsonProperty("phone") String phone,
                            @JsonProperty("email") String email, @JsonProperty("salary") String salary,
                            @JsonProperty("address") String address,
                            @JsonProperty("tagged") List<JsonStaffAdaptedTag> tagged) {
        this.name = name;
        this.level = level;
        this.id = id;
        this.gender = gender;
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
        level = source.getLevel().toString();
        id = source.getId().value;
        gender = source.getGender().value;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        salary = source.getSalary().value;
        address = source.getAddress().value;
        assignedCoursesID.addAll(source.getAssignedCoursesID().stream()
                .map(JsonStaffAdaptedID::new)
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
    public Staff toModelType() throws IllegalValueException {
        final List<Tag> staffTags = new ArrayList<>();
        for (JsonStaffAdaptedTag tag : tagged) {
            staffTags.add(tag.toModelType());
        }
        final Set<Tag> modelTags = new HashSet<>(staffTags);

        final List<ID> TeacherAssignedCoursesID = new ArrayList<>();
        for (JsonStaffAdaptedID id : assignedCoursesID) {
            TeacherAssignedCoursesID.add(id.toModelType());
        }
        final Set<ID> modelAssignedCoursesID = new HashSet<>(TeacherAssignedCoursesID);

        if (name == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (gender == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Gender.class.getSimpleName()));
        }
        if (!Gender.isValidGender(gender)) {
            throw new IllegalValueException(Gender.MESSAGE_CONSTRAINTS);
        }
        final Gender modelGender = new Gender(gender);

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
        if (!ID.isValidId(id)) {
            throw new IllegalValueException(ID.MESSAGE_CONSTRAINTS);
        }
        final ID modelID = new ID(id);

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
        return new Staff(modelName, modelID, modelGender, modelLevel, modelPhone, modelEmail, modelSalary, modelAddress, modelAssignedCoursesID, modelTags);
    }
}

package seedu.address.testutil;

import seedu.address.model.modelObjectTags.*;
import seedu.address.model.modelStaff.Staff;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * A utility class to help with building Teacher objects.
 */
public class TeacherBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_LEVEL = "TEACHER";
    public static final String DEFAULT_ID = "120";
    public static final String DEFAULT_GENDER = "f";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_SALARY = "1000";
    public static final String DEFAULT_ASSIGNEDCOURSES = "";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Staff.Level level;
    private ID id;
    private Gender gender;
    private Phone phone;
    private Email email;
    private Salary salary;
    private Address address;
    private Set<ID> assignedCourses;
    private Set<Tag> tags;

    public TeacherBuilder() {
        name = new Name(DEFAULT_NAME);
        id = new ID(DEFAULT_ID);
        gender = new Gender(DEFAULT_GENDER);
        if (DEFAULT_LEVEL.equals("TEACHER")) {
            level = Staff.Level.TEACHER;
        } else {
            level = Staff.Level.ADMIN;
        }
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        salary = new Salary(DEFAULT_SALARY);
        assignedCourses = new HashSet<>();
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the TeacherBuilder with the data of {@code teacherToCopy}.
     */
    public TeacherBuilder(Staff teacherToCopy) {
        name = teacherToCopy.getName();
        level = teacherToCopy.getLevel();
        phone = teacherToCopy.getPhone();
        email = teacherToCopy.getEmail();
        salary = teacherToCopy.getSalary();
        assignedCourses = teacherToCopy.getAssignedCoursesID();
        address = teacherToCopy.getAddress();
        tags = new HashSet<>(teacherToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Teacher} that we are building.
     */
    public TeacherBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code ID} of the {@code Teacher} that we are building.
     */
    public TeacherBuilder withID(String id) {
        this.id = new ID(id);
        return this;
    }

    /**
     * Sets the {@code Level} of the {@code Staff} that we are building.
     */
    public TeacherBuilder withLevel(String level) {
        level = level.trim().toUpperCase();
        if (level.equals("TEACHER")) {
            this.level = Staff.Level.TEACHER;
        } else if (level.equals("ADMIN")) {
            this.level = Staff.Level.ADMIN;
        }
        return this;
    }

    /**
     * Sets the {@code Gender} of the {@code Staff} that we are building.
     */
    public TeacherBuilder withGender(String gender) {
        this.gender = new Gender(gender);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Teacher} that we are building.
     */
    public TeacherBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code AssignedCourses} of the {@code Teacher} that we are building.
     */
    public TeacherBuilder withAssignedCourses(String assignedCourses) {
        this.assignedCourses = SampleDataUtil.getIDSet(assignedCourses);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Teacher} that we are building.
     */
    public TeacherBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Teacher} that we are building.
     */
    public TeacherBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Teacher} that we are building.
     */
    public TeacherBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Salary} of the {@code Teacher} that we are building.
     */
    public TeacherBuilder withSalary(String salary) {
        this.salary = new Salary(salary);
        return this;
    }

    public Staff build() {
        return new Staff(name, id, gender, level, phone, email, salary, address, tags);
    }

}

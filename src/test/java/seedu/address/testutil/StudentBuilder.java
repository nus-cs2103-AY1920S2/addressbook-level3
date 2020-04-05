package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.student.Address;
import seedu.address.model.student.Attendance;
import seedu.address.model.student.Email;
import seedu.address.model.student.Name;
import seedu.address.model.student.NextOfKin;
import seedu.address.model.student.Phone;
import seedu.address.model.student.Remark;
import seedu.address.model.student.Student;
import seedu.address.model.student.Temperature;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Student objects.
 */
public class StudentBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_TEMPERATURE = "36.5";
    public static final String DEFAULT_ATTENDANCE = "Present";
    public static final String DEFAULT_REMARK = "She likes aardvarks.";
    public static final String DEFAULT_NOK = "Joseph-Father-90045722";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Temperature temperature;
    private Attendance attendance;
    private Remark remark;
    private Set<Tag> tags;
    private NextOfKin nok;

    public StudentBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        temperature = new Temperature(DEFAULT_TEMPERATURE);
        attendance = new Attendance(DEFAULT_ATTENDANCE);
        remark = new Remark(DEFAULT_REMARK);
        tags = new HashSet<>();
        nok = new NextOfKin(DEFAULT_NOK);
    }

    /**
     * Initializes the StudentBuilder with the data of {@code studentToCopy}.
     */
    public StudentBuilder(Student studentToCopy) {
        name = studentToCopy.getName();
        phone = studentToCopy.getPhone();
        email = studentToCopy.getEmail();
        address = studentToCopy.getAddress();
        temperature = studentToCopy.getTemperature();
        attendance = studentToCopy.getAttendance();
        remark = studentToCopy.getRemark();
        tags = new HashSet<>(studentToCopy.getTags());
        nok = studentToCopy.getNok();
    }

    /**
     * Sets the {@code Name} of the {@code Student} that we are building.
     */
    public StudentBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Student} that we are building.
     */
    public StudentBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Student} that we are building.
     */
    public StudentBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Student} that we are building.
     */
    public StudentBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Student} that we are building.
     */
    public StudentBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Temperature} of the {@code Student} that we are building.
     */
    public StudentBuilder withTemperature(String temperature) {
        this.temperature = new Temperature(temperature);
        return this;
    }

    /**
     * Sets the {@code Attendance} of the {@code Student} that we are building.
     */
    public StudentBuilder withAttendance(String attendance) {
        this.attendance = new Attendance(attendance);
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Student} that we are building.
     */
    public StudentBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    /**
     * Sets the {@code Nok} of the {@code Student} that we are building.
     */
    public StudentBuilder withNok(String nok) {
        this.nok = new NextOfKin(nok);
        return this;
    }

    /**
     * Builds a student.
     */
    public Student build() {
        return new Student(name, phone, email, address, temperature,
                attendance, nok, remark, tags);
    }

}

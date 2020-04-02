package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.modelAssignment.Assignment;
import seedu.address.model.modelAssignment.AssignmentAddressBook;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.modelCourse.CourseAddressBook;
import seedu.address.model.modelFinance.Finance;
import seedu.address.model.modelFinance.FinanceAddressBook;
import seedu.address.model.modelGeneric.ReadOnlyAddressBookGeneric;
import seedu.address.model.modelStaff.Staff;
import seedu.address.model.modelStaff.StaffAddressBook;
import seedu.address.model.modelStudent.Student;
import seedu.address.model.modelStudent.StudentAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Amount;
import seedu.address.model.person.Date;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Email;
import seedu.address.model.person.FinanceType;
import seedu.address.model.person.ID;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Salary;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

  public static Person[] getSamplePersons() {
    return new Person[]{
        new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
            new Address("Blk 30 Geylang Street 29, #06-40"),
            getTagSet("friends")),
        new Person(new Name("Bernice Yu"), new Phone("99272758"),
            new Email("berniceyu@example.com"),
            new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
            getTagSet("colleagues", "friends")),
    };
  }

  public static ReadOnlyAddressBook getSampleAddressBook() {
    AddressBook sampleAb = new AddressBook();
    for (Person samplePerson : getSamplePersons()) {
      sampleAb.addPerson(samplePerson);
    }
    return sampleAb;
  }

  public static Staff[] getSampleStaffs() {
    return new Staff[]{
        new Staff(new Name("Mr Harmony Moquin"), new ID("31"), Staff.Level.TEACHER ,new Phone("88283902"), new Email("harmony.moquin@ccx.edu"),
            new Salary("9000"),
            new Address("Serangoon"),
            getIDSet("2","3","5"),
            getTagSet("Friendly", "LovesArt")),
        new Staff(new Name("Mr Jack Printup"), new ID("32"), Staff.Level.TEACHER, new Phone("98765432"), new Email("jack.printup@ccx.edu"),
            new Salary("7000"),
            new Address("Clementi"),
            getIDSet("6"),
            getTagSet("WishfulThinking", "Experienced")),
        new Staff(new Name("Mr Ronald Super"), new ID("33"), Staff.Level.TEACHER, new Phone("98564432"), new Email("ronald.superp@ccx.edu"),
            new Salary("6000"),
            new Address("Bishan"),
            getIDSet("1","7"),
            getTagSet("Awesome", "Experienced")),
        new Staff(new Name("Mrs Ciara Fyksen"), new ID("34"), Staff.Level.TEACHER, new Phone("92564532"), new Email("ciara.fyksen@ccx.edu"),
            new Salary("9000"),
            new Address("Bradell"),
            getIDSet("4","10"),
            getTagSet("Awesome", "Experienced")),
        new Staff(new Name("Mrs Isabel Lahip"), new ID("35"), Staff.Level.TEACHER, new Phone("92524552"), new Email("isabel.lahip@ccx.edu"),
            new Salary("5000"),
            new Address("Bishan"),
            getIDSet("9"),
            getTagSet("Awesome", "Experienced")),
        new Staff(new Name("Mrs Scarlett Prospero"), new ID("36"), Staff.Level.TEACHER, new Phone("92424552"), new Email("scarlett.prospero@ccx.edu"),
            new Salary("7000"),
            new Address("Bradell"),
            getIDSet("8"),
            getTagSet("Awesome", "Experienced")),
    };
  }

  public static ReadOnlyAddressBookGeneric<Staff> getSampleStaffAddressBook() {
    StaffAddressBook sampleAb = new StaffAddressBook();
    for (Staff sampleStaff : getSampleStaffs()) {
      sampleAb.add(sampleStaff);
    }
    return sampleAb;
  }

  public static Student[] getSampleStudents() {
    return new Student[]{
        new Student(new Name("Zack"), new ID("11"), getIDSet("1","4","9"),
            getTagSet("Loyal", "10Year")),
        new Student(new Name("Lily"), new ID("12"), getIDSet("1","4","6"),
            getTagSet("Loyal", "10Year")),
        new Student(new Name("Calvin"), new ID("13"), getIDSet("6","9"),
            getTagSet("Loyal", "10Year")),
        new Student(new Name("Lisa"), new ID("14"), getIDSet("1","4"),
            getTagSet("Loyal", "10Year")),
        new Student(new Name("Ralph"), new ID("15"), getIDSet("1","4","6","9"),
            getTagSet("Loyal", "10Year")),
        new Student(new Name("Beatrice"), new ID("16"), getIDSet("1","4", "9"),
            getTagSet("Loyal", "10Year")),
        new Student(new Name("Daniel"), new ID("17"), getIDSet("1","4","6"),
            getTagSet("Loyal", "10Year")),

        new Student(new Name("Ellen"), new ID("18"), getIDSet("2","4","6","8","9"),
            getTagSet("Loyal", "10Year")),
        new Student(new Name("Eden"), new ID("19"), getIDSet("2","4","8","9"),
            getTagSet("Loyal", "10Year")),
        new Student(new Name("Elsa"), new ID("20"), getIDSet("2","5","8"),
            getTagSet("Loyal", "10Year")),
        new Student(new Name("Theo"), new ID("21"), getIDSet("2","3","5","8"),
            getTagSet("Loyal", "10Year")),
        new Student(new Name("Amelia"), new ID("22"), getIDSet("2","3","5","7","8","10"),
            getTagSet("Loyal", "10Year")),
        new Student(new Name("Keith"), new ID("23"), getIDSet("2","3","5","7","8","10"),
            getTagSet("Loyal", "10Year")),
        new Student(new Name("Cerys"), new ID("24"), getIDSet("2","3","5","7","10"),
            getTagSet("Loyal", "10Year")),
        new Student(new Name("Steven"), new ID("25"), getIDSet("2","3","5","7","10"),
            getTagSet("Loyal", "10Year")),
        new Student(new Name("Crystal"), new ID("26"), getIDSet("2","3","5","7","10"),
            getTagSet("Loyal", "10Year")),
        new Student(new Name("Gary"), new ID("27"), getIDSet("3","5","7","10"),
            getTagSet("Loyal", "10Year")),
        new Student(new Name("Rosie"), new ID("28"), getIDSet("7","10"),
            getTagSet("Loyal", "10Year")),
        new Student(new Name("Matthew"), new ID("29"), getIDSet("7"),
            getTagSet("Loyal", "10Year")),
    };
  }

  public static ReadOnlyAddressBookGeneric<Student> getSampleStudentAddressBook() {
    StudentAddressBook sampleAb = new StudentAddressBook();
    for (Student sampleStudent : getSampleStudents()) {
      sampleAb.add(sampleStudent);
    }
    return sampleAb;
  }

  public static Finance[] getSampleFinances() {
    return new Finance[]{
        new Finance(new Name("Renovated Staff Lounge"), new FinanceType("m"),
            new Date("2020-08-20"),
            new Amount("2000"),
            getTagSet("BLK71", "AirCon")),
        new Finance(new Name("Received Payment From NUS"), new FinanceType("m"),
            new Date("2020-08-21"),
            new Amount("1000"),
            getTagSet("Contract"))
    };
  }

  public static ReadOnlyAddressBookGeneric<Finance> getSampleFinanceAddressBook() {
    FinanceAddressBook sampleAb = new FinanceAddressBook();
    for (Finance sampleFinance : getSampleFinances()) {
      sampleAb.add(sampleFinance);
    }
    return sampleAb;
  }

  public static Course[] getSampleCourses() {
    return new Course[]{
        new Course(new Name("WorldBuilder Game Design with Minecraft"), new ID("1"), new Amount("1349"),
            new ID("33"),
            getIDSet("11", "12", "14","15","16","17"),
            getIDSet("901","903","905"),
            getTagSet("Kid", "Beginner")),
        new Course(new Name("Roblox Editor Make Your Own Obby Game"), new ID("2"), new Amount("1349"),
            new ID("31"),
            getIDSet("18","19","20","21","22","23","24","25","26"),
            getIDSet("903","906"),
            getTagSet("Kid", "Beginner")),
        new Course(new Name("CodeMaker Code and Design Games with Scratch"), new ID("3"), new Amount("1299"),
            new ID("31"),
            getIDSet("21","22","23","24","25","26","27"),
            getIDSet("901","904"),
            getTagSet("Kid", "Beginner")),
        new Course(new Name("Code a Bot AI and Robotics with Sphero BOLT"), new ID("4"), new Amount("1599"),
            new ID("34"),
            getIDSet("11","12","14","15","16","17","18","19"),
            getIDSet("903","902","905"),
            getTagSet("Kid", "Beginner")),
        new Course(new Name("Roblox Entrepreneur Imaginative Game Design"), new ID("5"), new Amount("1399"),
            new ID("31"),
            getIDSet("20","21","22","23","24","25","26","27"),
            getIDSet("901","903","906"),
            getTagSet("Kid", "Beginner")),
        new Course(new Name("Roblox Entrepreneur Lua Coding and Game Scripts"), new ID("6"), new Amount("1399"),
            new ID("32"),
            getIDSet("12","13","14","15","17","18"),
            getIDSet("904"),
            getTagSet("Kid", "Beginner")),
        new Course(new Name("Java Coding Build Mods with Minecraft"), new ID("7"), new Amount("1399"),
            new ID("33"),
            getIDSet("22","23","24","25","26","27","28","29"),
            getIDSet("902","905"),
            getTagSet("Kid", "Beginner")),
        new Course(new Name("Java Coding Custom Blocks and Maps in Minecraft"), new ID("8"), new Amount("1449"),
            new ID("36"),
            getIDSet("18","19","20","21","22","23"),
            getIDSet("906"),
            getTagSet("Kid", "Beginner")),
        new Course(new Name("Game Creation and AI with JavaScript"), new ID("9"), new Amount("1299"),
            new ID("35"),
            getIDSet("11","13","15","16","18","19"),
            getIDSet("903","905"),
            getTagSet("Kid", "Beginner")),
        new Course(new Name("Coding and Engineering"), new ID("10"), new Amount("1499"),
            new ID("34"),
            getIDSet("22","23","24","25","26","27","28"),
            getIDSet("901","904","905","906"),
            getTagSet("Kid", "Beginner")),
    };
  }

  public static ReadOnlyAddressBookGeneric<Course> getSampleCourseAddressBook() {
    CourseAddressBook sampleAb = new CourseAddressBook();
    for (Course sampleCourse : getSampleCourses()) {
      sampleAb.add(sampleCourse);
    }
    return sampleAb;
  }

  public static Assignment[] getSampleAssignments() {
    return new Assignment[]{
            new Assignment(new Name("Coding"), new ID("901"),
                    new Deadline("2020-05-28"), getTagSet("AI", "Fun")),
            new Assignment(new Name("Worksheet"), new ID("902"),
                new Deadline("2020-05-30"), getTagSet("AI", "Fun")),
            new Assignment(new Name("Lab"), new ID("903"),
                new Deadline("2020-06-30"), getTagSet("AI", "Fun")),
            new Assignment(new Name("Easy Test"), new ID("904"),
                new Deadline("2020-06-23"), getTagSet("AI", "Fun")),
            new Assignment(new Name("Medium Test"), new ID("905"),
                new Deadline("2020-06-21"), getTagSet("AI", "Fun")),
            new Assignment(new Name("Hard Test"), new ID("906"),
                new Deadline("2020-06-12"), getTagSet("AI", "Fun")),
    };
  }

  public static ReadOnlyAddressBookGeneric<Assignment> getSampleAssignmentAddressBook() {
    AssignmentAddressBook sampleAb = new AssignmentAddressBook();
    for (Assignment sampleAssignment : getSampleAssignments()) {
      sampleAb.add(sampleAssignment);
    }
    return sampleAb;
  }

  /**
   * Returns an ID set containing the list of strings given.
   */
  public static Set<ID> getIDSet(String... strings) {
    return Arrays.stream(strings)
        .map(ID::new)
        .collect(Collectors.toSet());
  }

  /**
   * Returns a tag set containing the list of strings given.
   */
  public static Set<Tag> getTagSet(String... strings) {
    return Arrays.stream(strings)
        .map(Tag::new)
        .collect(Collectors.toSet());
  }

}

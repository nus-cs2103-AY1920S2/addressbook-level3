package tatracker.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import tatracker.model.ReadOnlyTaTracker;
import tatracker.model.TaTracker;
import tatracker.model.student.Email;
import tatracker.model.student.Matric;
import tatracker.model.student.Name;
import tatracker.model.student.Phone;
import tatracker.model.student.Student;
import tatracker.model.tag.Tag;

/**
 * Contains utility methods for populating {@code TaTracker} with sample data.
 */
public class SampleDataUtil {
    public static Student[] getSampleStudents() {
        return new Student[] {
            new Student(new Matric("A0187945J"),
                    new Name("Alex Yeoh"),
                    new Phone("87438807"),
                    new Email("alexyeoh@example.com"),
                    getTagSet("friends")),
            new Student(new Matric("A0181137L"),
                    new Name("Bernice Yu"),
                    new Phone("99272758"),
                    new Email("berniceyu@example.com"),
                    getTagSet("colleagues", "friends")),
            new Student(new Matric("A0187565N"),
                    new Name("Charlotte Oliveiro"),
                    new Phone("93210283"),
                    new Email("charlotte@example.com"),
                    getTagSet("neighbours")),
            new Student(new Matric("A0186153P"),
                    new Name("David Li"),
                    new Phone("91031282"),
                    new Email("lidavid@example.com"),
                    getTagSet("family")),
            new Student(new Matric("A0180474R"),
                    new Name("Irfan Ibrahim"),
                    new Phone("92492021"),
                    new Email("irfan@example.com"),
                    getTagSet("classmates")),
            new Student(new Matric("A0187613T"),
                    new Name("Roy Balakrishnan"),
                    new Phone("92624417"),
                    new Email("royb@example.com"),
                    getTagSet("colleagues")),
            new Student(new Matric("A0195558H"),
                    new Name("Jeffry Lum"),
                    new Phone("65162727"),
                    new Email("Jeffry@u.nus.edu"),
                    getTagSet("tutors"))
        };
    }

    public static ReadOnlyTaTracker getSampleTaTracker() {
        TaTracker sampleAb = new TaTracker();
        for (Student sampleStudent : getSampleStudents()) {
            sampleAb.addStudent(sampleStudent);
        }
        return sampleAb;
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

package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.ModManager;
import seedu.address.model.ReadOnlyModManager;
import seedu.address.model.facilitator.Email;
import seedu.address.model.facilitator.Facilitator;
import seedu.address.model.facilitator.Name;
import seedu.address.model.facilitator.Office;
import seedu.address.model.facilitator.Phone;
import seedu.address.model.lesson.DayAndTime;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonType;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.task.Task;
import seedu.address.model.task.util.TaskDateTime;

/**
 * Contains utility methods for populating {@code ModManager} with sample data.
 */
public class SampleDataUtil {
    public static Module[] getSampleModules() {
        return new Module[] {
            new Module(new ModuleCode("CS2101"),
                    new Description("Effective Communication for Computing Professionals")),
            new Module(new ModuleCode("CS2103T"), new Description("Software Engineering")),
            new Module(new ModuleCode("CS2105"), new Description("Introduction to Computer Networks")),
            new Module(new ModuleCode("CS3230"), new Description("Design and Analysis of Algorithms")),
            new Module(new ModuleCode("ES2660"), new Description("Communicating in Information Age")),
            new Module(new ModuleCode("ST2334"), new Description("Probability and Statistics"))
        };
    }

    public static Facilitator[] getSampleFacilitators() {
        return new Facilitator[] {
            new Facilitator(new Name("Diana Chung"), new Phone("98765432"), new Email("diana@example.com"),
                    new Office("AS6-03-03"), getModuleCodeSet("CS2101")),
            new Facilitator(new Name("Akshay Narayan"), new Phone(null), new Email("akshay@example.com"),
                    new Office("COM2-02-02"), getModuleCodeSet("CS2103T")),
            new Facilitator(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Office(null), getModuleCodeSet("CS2103T")),
            new Facilitator(new Name("Zhou Lifeng"), new Phone(null), new Email("lifeng@example.com"),
                    new Office("COM2-03-03"), getModuleCodeSet("CS2105")),
            new Facilitator(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Office(null), getModuleCodeSet("CS2105")),
            new Facilitator(new Name("Ken Sung"), new Phone(null), new Email("diptarka@example.com"),
                    new Office("COM2-02-12"), getModuleCodeSet("CS3230")),
            new Facilitator(new Name("Diptarka Chakraborty"), new Phone(null), new Email("ken@example.com"),
                    new Office("COM2-03-13"), getModuleCodeSet("CS3230")),
            new Facilitator(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Office(null), getModuleCodeSet("CS3230")),
            new Facilitator(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Office("COM1-01-01"), getModuleCodeSet("CS2105", "CS3230")),
            new Facilitator(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Office("AS6-04-04"), getModuleCodeSet("ES2660")),
            new Facilitator(new Name("Chan Yiu Man"), new Phone(null), new Email("yiuman@example.com"),
                    new Office("S17-06-06"), getModuleCodeSet("ST2334")),
            new Facilitator(new Name("Wong Yean Ling"), new Phone(null), new Email("yeanling@example.com"),
                    new Office("S17-07-07"), getModuleCodeSet("ST2334")),
            new Facilitator(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Office("S17-05-05"), getModuleCodeSet("ST2334"))
        };
    }

    public static Task[] getSampleTasks() {
        return new Task[]{
            Task.makeScheduledTask(new Description("Oral Presentation 2"), new TaskDateTime("06/04/2020", "08:00"),
                    new ModuleCode("CS2101"), 344, true),
            Task.makeNonScheduledTask(new Description("Critical Reflection"), new ModuleCode("CS2101"), 295),
            Task.makeScheduledTask(new Description("Team Project"), new TaskDateTime("13/04/2020"),
                    new ModuleCode("CS2103T"), 970, true),
            Task.makeScheduledTask(new Description("Project Portfolio"), new TaskDateTime("13/04/2020"),
                    new ModuleCode("CS2103T"), 547),
            Task.makeScheduledTask(new Description("Demo"), new TaskDateTime("13/04/2020"),
                    new ModuleCode("CS2103T"), 241),
            Task.makeNonScheduledTask(new Description("Tutorial"), new ModuleCode("CS2105"), 395, true),
            Task.makeScheduledTask(new Description("Programming Assignment 2"), new TaskDateTime("12/04/2020", "23:59"),
                    new ModuleCode("CS2105"), 224),
            Task.makeNonScheduledTask(new Description("Tutorial"), new ModuleCode("CS3230"), 250, true),
            Task.makeScheduledTask(new Description("Programming Assignment 2"), new TaskDateTime("10/04/2020"),
                    new ModuleCode("CS3230"), 175, true),
            Task.makeScheduledTask(new Description("Assignment 3"), new TaskDateTime("24/04/2020", "23:59"),
                    new ModuleCode("CS3230"), 442),
            Task.makeScheduledTask(new Description("Challenge 3"), new TaskDateTime("13/04/2020"),
                    new ModuleCode("CS3230"), 649),
            Task.makeNonScheduledTask(new Description("Wildcard"), new ModuleCode("ES2660"), 291),
            Task.makeNonScheduledTask(new Description("Tutorial"), new ModuleCode("ST2334"), 401, true)
        };
    }

    public static Lesson[] getSampleLessons() {
        return new Lesson[] {
            new Lesson(new ModuleCode("CS2101"), LessonType.SEC, new DayAndTime("MONDAY 08:00 10:00"),
                    "AS6-0208"),
            new Lesson(new ModuleCode("CS2101"), LessonType.SEC, new DayAndTime("THURSDAY 08:00 10:00"),
                    "AS6-0208"),
            new Lesson(new ModuleCode("CS2103T"), LessonType.LEC, new DayAndTime("FRIDAY 14:00 16:00"),
                    "i3-Aud"),
            new Lesson(new ModuleCode("CS2103T"), LessonType.TUT, new DayAndTime("FRIDAY 10:00 11:00"),
                    "COM1-B103"),
            new Lesson(new ModuleCode("CS2105"), LessonType.LEC, new DayAndTime("MONDAY 14:00 16:00"),
                    "i3-Aud"),
            new Lesson(new ModuleCode("CS2105"), LessonType.TUT, new DayAndTime("MONDAY 16:00 17:00"),
                    "COM1-0203"),
            new Lesson(new ModuleCode("CS3230"), LessonType.LEC, new DayAndTime("THURSDAY 16:00 18:00"),
                    "i3-Aud"),
            new Lesson(new ModuleCode("CS3230"), LessonType.TUT, new DayAndTime("FRIDAY 11:00 12:00"),
                    "COM1-0103"),
            new Lesson(new ModuleCode("ES2660"), LessonType.SEC, new DayAndTime("MONDAY 10:00 12:00"),
                    "AS6-0620"),
            new Lesson(new ModuleCode("CS2101"), LessonType.SEC, new DayAndTime("THURSDAY 10:00 12:00"),
                    "AS6-0620"),
            new Lesson(new ModuleCode("ST2334"), LessonType.LEC, new DayAndTime("MONDAY 12:00 14:00"),
                    "LT32"),
            new Lesson(new ModuleCode("ST2334"), LessonType.LEC, new DayAndTime("WEDNESDAY 12:00 14:00"),
                    "LT32"),
            new Lesson(new ModuleCode("ST2334"), LessonType.TUT, new DayAndTime("FRIDAY 12:00 13:00"),
                    "S16-06118")
        };
    }

    public static ReadOnlyModManager getSampleModManager() {
        ModManager sampleAb = new ModManager();
        for (Facilitator sampleFacilitator : getSampleFacilitators()) {
            sampleAb.addFacilitator(sampleFacilitator);
        }
        for (Module sampleModule : getSampleModules()) {
            sampleAb.addModule(sampleModule);
        }
        for (Task sampleTask : getSampleTasks()) {
            sampleAb.addTask(sampleTask);
        }
        for (Lesson sampleLesson : getSampleLessons()) {
            sampleAb.addLesson(sampleLesson);
        }
        return sampleAb;
    }

    /**
     * Returns a module code set containing the list of strings given.
     */
    public static Set<ModuleCode> getModuleCodeSet(String... strings) {
        return Arrays.stream(strings)
                .map(ModuleCode::new)
                .collect(Collectors.toSet());
    }

}

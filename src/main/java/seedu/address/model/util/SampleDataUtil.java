package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.task.Description;
import seedu.address.model.task.Email;
import seedu.address.model.task.Name;
import seedu.address.model.task.Task;
import seedu.address.model.task.Priority;
import seedu.address.model.tag.Tag;

/** Contains utility methods for populating {@code AddressBook} with sample data. */
public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        return new Task[] {
            new Task(
                    new Name("Alex Yeoh"),
                    new Priority("87438807"),
                    new Email("alexyeoh@example.com"),
                    new Description(""),
                    getTagSet("friends")),
            new Task(
                    new Name("Bernice Yu"),
                    new Priority("99272758"),
                    new Email("berniceyu@example.com"),
                    new Description(""),
                    getTagSet("colleagues", "friends")),
            new Task(
                    new Name("Charlotte Oliveiro"),
                    new Priority("93210283"),
                    new Email("charlotte@example.com"),
                    new Description(""),
                    getTagSet("neighbours")),
            new Task(
                    new Name("David Li"),
                    new Priority("91031282"),
                    new Email("lidavid@example.com"),
                    new Description(""),
                    getTagSet("family")),
            new Task(
                    new Name("Irfan Ibrahim"),
                    new Priority("92492021"),
                    new Email("irfan@example.com"),
                    new Description(""),
                    getTagSet("classmates")),
            new Task(
                    new Name("Roy Balakrishnan"),
                    new Priority("92624417"),
                    new Email("royb@example.com"),
                    new Description(""),
                    getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Task sampleTask : getSampleTasks()) {
            sampleAb.addTask(sampleTask);
        }
        return sampleAb;
    }

    /** Returns a tag set containing the list of strings given. */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings).map(Tag::new).collect(Collectors.toSet());
    }
}

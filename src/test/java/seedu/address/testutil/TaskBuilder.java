package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;
import seedu.address.model.task.Description;
import seedu.address.model.task.Email;
import seedu.address.model.task.Name;
import seedu.address.model.task.Task;
import seedu.address.model.task.Priority;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/** A utility class to help with building Person objects. */
public class TaskBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_DESCRIPTION = "Page 1 and 2";

    private Name name;
    private Priority priority;
    private Email email;
    private Description description;
    private Set<Tag> tags;

    public TaskBuilder() {
        name = new Name(DEFAULT_NAME);
        priority = new Priority(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        description = new Description(DEFAULT_DESCRIPTION);
        tags = new HashSet<>();
    }

    /** Initializes the PersonBuilder with the data of {@code taskToCopy}. */
    public TaskBuilder(Task taskToCopy) {
        name = taskToCopy.getName();
        priority = taskToCopy.getPriority();
        email = taskToCopy.getEmail();
        description = taskToCopy.getDescription();
        tags = new HashSet<>(taskToCopy.getTags());
    }

    /** Sets the {@code Name} of the {@code Person} that we are building. */
    public TaskBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are
     * building.
     */
    public TaskBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /** Sets the {@code Address} of the {@code Person} that we are building. */
    public TaskBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /** Sets the {@code Priority} of the {@code Person} that we are building. */
    public TaskBuilder withPriority(String priority) {
        this.priority = new Priority(priority);
        return this;
    }

    /** Sets the {@code Email} of the {@code Person} that we are building. */
    public TaskBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Task build() {
        return new Task(name, priority, email, description, tags);
    }
}

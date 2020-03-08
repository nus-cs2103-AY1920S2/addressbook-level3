package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import seedu.address.model.AddressBook;
import seedu.address.model.task.Task;

/**
 * A utility class containing a list of {@code Task} objects to be used in
 * tests.
 */
public class TypicalTasks {

        public static final Task ALICE = new TaskBuilder().withName("Alice Pauline")
                        .withDescription("123, Jurong West Ave 6, #08-111").withPriority("94351253").withTags("friends")
                        .build();
        public static final Task BENSON = new TaskBuilder().withName("Benson Meier")
                        .withDescription("311, Clementi Ave 2, #02-25").withPriority("98765432")
                        .withTags("owesMoney", "friends").build();
        public static final Task CARL = new TaskBuilder().withName("Carl Kurz").withPriority("95352563")
                        .withDescription("wall street").build();
        public static final Task DANIEL = new TaskBuilder().withName("Daniel Meier").withPriority("87652533")
                        .withDescription("10th street").withTags("friends").build();
        public static final Task ELLE = new TaskBuilder().withName("Elle Meyer").withPriority("9482224")
                        .withDescription("michegan ave").build();
        public static final Task FIONA = new TaskBuilder().withName("Fiona Kunz").withPriority("9482427")
                        .withDescription("little tokyo").build();
        public static final Task GEORGE = new TaskBuilder().withName("George Best").withPriority("9482442")
                        .withDescription("4th street").build();

        // Manually added
        public static final Task HOON = new TaskBuilder().withName("Hoon Meier").withPriority("8482424")
                        .withDescription("little india").build();
        public static final Task IDA = new TaskBuilder().withName("Ida Mueller").withPriority("8482131")
                        .withDescription("chicago ave").build();

        // Manually added - Person's details found in {@code CommandTestUtil}
        public static final Task AMY = new TaskBuilder().withName(VALID_NAME_AMY).withPriority(VALID_PHONE_AMY)
                        .withDescription(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
        public static final Task BOB = new TaskBuilder().withName(VALID_NAME_BOB).withPriority(VALID_PHONE_BOB)
                        .withDescription(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

        public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

        private TypicalTasks() {
        } // prevents instantiation

    /** Returns an {@code AddressBook} with all the typical persons. */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Task task : getTypicalTasks()) {
            ab.addTask(task);
        }

        public static List<Task> getTypicalTasks() {
                return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
        }
}

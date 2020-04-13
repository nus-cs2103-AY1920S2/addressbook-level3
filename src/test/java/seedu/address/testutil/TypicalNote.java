package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_NAME_CS2103;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_TIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_URGENT;

import seedu.address.model.item.Note;

/**
 * A utility class containing a list of {@code Note} objects to be used in tests.
 */
public class TypicalNote {
    public static final Note FINISH_HOMEWORK = new NoteBuilder()
            .withName("Finish Homework")
            .withTime("03-2020")
            .withTags("noturgent")
            .build();
    public static final Note FINISH_CS_2103 = new NoteBuilder()
            .withName(VALID_NOTE_NAME_CS2103)
            .withTime(VALID_NOTE_TIME)
            .withTags(VALID_TAG_URGENT)
            .build();
    public static final Note FINISH_RESUME_2 = new NoteBuilder()
            .withName("Finish Resume 2")
            .withTime("04-2020")
            .withTags()
            .build();
}

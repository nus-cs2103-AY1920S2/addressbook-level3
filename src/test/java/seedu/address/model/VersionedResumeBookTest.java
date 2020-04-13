package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPerson.ALICE;
import static seedu.address.testutil.TypicalPerson.AMY;
import static seedu.address.testutil.TypicalPerson.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ResumeBookBuilder;

public class VersionedResumeBookTest {

    private final ReadOnlyResumeBook resumeBookWithAlice = new ResumeBookBuilder().withPerson(ALICE).build();
    private final ReadOnlyResumeBook resumeBookWithAmy = new ResumeBookBuilder().withPerson(AMY).build();
    private final ReadOnlyResumeBook resumeBookWithBob = new ResumeBookBuilder().withPerson(BOB).build();
    private final ReadOnlyResumeBook emptyResumeBook = new ResumeBookBuilder().build();

    @Test
    public void commit_singleResumeBook_noStatesRemovedCurrentStateSaved() {
        VersionedResumeBook versionedResumeBook = prepareResumeBookList(emptyResumeBook);

        versionedResumeBook.commit();
        assertResumeBookListStatus(versionedResumeBook,
                Collections.singletonList(emptyResumeBook),
                emptyResumeBook,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleResumeBookPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedResumeBook versionedResumeBook = prepareResumeBookList(
                emptyResumeBook, resumeBookWithAlice, resumeBookWithAmy);

        versionedResumeBook.commit();
        assertResumeBookListStatus(versionedResumeBook,
                Arrays.asList(emptyResumeBook, resumeBookWithAlice, resumeBookWithAmy),
                resumeBookWithAmy,
                Collections.emptyList());
    }
    @Test
    public void commit_multipleResumeBookPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedResumeBook versionedResumeBook = prepareResumeBookList(
                emptyResumeBook, resumeBookWithAlice, resumeBookWithAmy);
        shiftCurrentStatePointerLeftwards(versionedResumeBook, 2);

        versionedResumeBook.commit();
        assertResumeBookListStatus(versionedResumeBook,
                Collections.singletonList(emptyResumeBook),
                emptyResumeBook,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleResumeBookPointerAtEndOfStateList_returnsTrue() {
        VersionedResumeBook versionedResumeBook = prepareResumeBookList(
                emptyResumeBook, resumeBookWithAlice, resumeBookWithAmy);

        assertTrue(versionedResumeBook.canUndo());
    }

    @Test
    public void canUndo_multipleResumeBookPointerAtStartOfStateList_returnsTrue() {
        VersionedResumeBook versionedResumeBook = prepareResumeBookList(
                emptyResumeBook, resumeBookWithAlice, resumeBookWithAmy);
        shiftCurrentStatePointerLeftwards(versionedResumeBook, 1);

        assertTrue(versionedResumeBook.canUndo());
    }

    @Test
    public void canUndo_singleResumeBook_returnsFalse() {
        VersionedResumeBook versionedResumeBook = prepareResumeBookList(emptyResumeBook);

        assertFalse(versionedResumeBook.canUndo());
    }

    @Test
    public void canUndo_multipleResumeBookPointerAtStartOfStateList_returnsFalse() {
        VersionedResumeBook versionedResumeBook = prepareResumeBookList(
                emptyResumeBook, resumeBookWithAlice, resumeBookWithAmy);
        shiftCurrentStatePointerLeftwards(versionedResumeBook, 2);

        assertFalse(versionedResumeBook.canUndo());
    }

    @Test
    public void canRedo_multipleResumeBookPointerNotAtEndOfStateList_returnsTrue() {
        VersionedResumeBook versionedResumeBook = prepareResumeBookList(
                emptyResumeBook, resumeBookWithAlice, resumeBookWithAmy);
        shiftCurrentStatePointerLeftwards(versionedResumeBook, 1);

        assertTrue(versionedResumeBook.canRedo());
    }

    @Test
    public void canRedo_multipleResumeBookPointerAtStartOfStateList_returnsTrue() {
        VersionedResumeBook versionedResumeBook = prepareResumeBookList(
                emptyResumeBook, resumeBookWithAlice, resumeBookWithAmy);
        shiftCurrentStatePointerLeftwards(versionedResumeBook, 2);

        assertTrue(versionedResumeBook.canRedo());
    }

    @Test
    public void canRedo_singleResumeBook_returnsFalse() {
        VersionedResumeBook versionedResumeBook = prepareResumeBookList(emptyResumeBook);

        assertFalse(versionedResumeBook.canRedo());
    }

    @Test
    public void canRedo_multipleResumeBookPointerAtEndOfStateList_returnsFalse() {
        VersionedResumeBook versionedResumeBook = prepareResumeBookList(
                emptyResumeBook, resumeBookWithAlice, resumeBookWithAmy);

        assertFalse(versionedResumeBook.canRedo());
    }

    @Test
    public void undo_multipleResumeBookPointerAtEndOfStateList_success() {
        VersionedResumeBook versionedResumeBook = prepareResumeBookList(
                emptyResumeBook, resumeBookWithAlice, resumeBookWithAmy);

        versionedResumeBook.undo();
        assertResumeBookListStatus(versionedResumeBook,
                Collections.singletonList(emptyResumeBook),
                resumeBookWithAlice,
                Collections.singletonList(resumeBookWithAmy));
    }

    @Test
    public void undo_multipleResumeBookPointerNotAtStartOfStateList_success() {
        VersionedResumeBook versionedResumeBook = prepareResumeBookList(
                emptyResumeBook, resumeBookWithAlice, resumeBookWithAmy);
        shiftCurrentStatePointerLeftwards(versionedResumeBook, 1);

        versionedResumeBook.undo();
        assertResumeBookListStatus(versionedResumeBook,
                Collections.emptyList(),
                emptyResumeBook,
                Arrays.asList(resumeBookWithAlice, resumeBookWithAmy));
    }

    @Test
    public void undo_singleResumeBook_throwsNoUndoableStateException() {
        VersionedResumeBook versionedResumeBook = prepareResumeBookList(emptyResumeBook);

        assertThrows(VersionedResumeBook.NoUndoableStateException.class, versionedResumeBook::undo);
    }

    @Test
    public void undo_multipleResumeBookPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedResumeBook versionedResumeBook = prepareResumeBookList(
                emptyResumeBook, resumeBookWithAlice, resumeBookWithAmy);
        shiftCurrentStatePointerLeftwards(versionedResumeBook, 2);

        assertThrows(VersionedResumeBook.NoUndoableStateException.class, versionedResumeBook::undo);
    }

    @Test
    public void redo_multipleResumeBookPointerNotAtEndOfStateList_success() {
        VersionedResumeBook versionedResumeBook = prepareResumeBookList(
                emptyResumeBook, resumeBookWithAlice, resumeBookWithAmy);
        shiftCurrentStatePointerLeftwards(versionedResumeBook, 1);

        versionedResumeBook.redo();
        assertResumeBookListStatus(versionedResumeBook,
                Arrays.asList(emptyResumeBook, resumeBookWithAlice),
                resumeBookWithAmy,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleResumeBookPointerAtStartOfStateList_success() {
        VersionedResumeBook versionedResumeBook = prepareResumeBookList(
                emptyResumeBook, resumeBookWithAlice, resumeBookWithAmy);
        shiftCurrentStatePointerLeftwards(versionedResumeBook, 2);

        versionedResumeBook.redo();
        assertResumeBookListStatus(versionedResumeBook,
                Collections.singletonList(emptyResumeBook),
                resumeBookWithAlice,
                Collections.singletonList(resumeBookWithAmy));
    }

    @Test
    public void redo_singleResumeBook_throwsNoRedoableStateException() {
        VersionedResumeBook versionedResumeBook = prepareResumeBookList(emptyResumeBook);

        assertThrows(VersionedResumeBook.NoRedoableStateException.class, versionedResumeBook::redo);
    }

    @Test
    public void redo_multipleResumeBookPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedResumeBook versionedResumeBook = prepareResumeBookList(
                emptyResumeBook, resumeBookWithAlice, resumeBookWithAmy);

        assertThrows(VersionedResumeBook.NoRedoableStateException.class, versionedResumeBook::redo);
    }

    @Test
    public void equals() {
        VersionedResumeBook versionedResumeBook = prepareResumeBookList(resumeBookWithAlice, resumeBookWithAmy);

        // same values -> returns true
        VersionedResumeBook copy = prepareResumeBookList(resumeBookWithAlice, resumeBookWithAmy);
        assertTrue(versionedResumeBook.equals(copy));

        // same object -> returns true
        assertTrue(versionedResumeBook.equals(versionedResumeBook));

        // null -> returns false
        assertFalse(versionedResumeBook.equals(null));

        // different types -> returns false
        assertFalse(versionedResumeBook.equals(1));

        // different state list -> returns false
        VersionedResumeBook differentResumeBookList = prepareResumeBookList(resumeBookWithAmy, resumeBookWithBob);
        assertFalse(versionedResumeBook.equals(differentResumeBookList));

        // different current pointer index -> returns false
        VersionedResumeBook differentCurrentStatePointer = prepareResumeBookList(
                resumeBookWithAlice, resumeBookWithAmy);
        shiftCurrentStatePointerLeftwards(versionedResumeBook, 1);
        assertFalse(versionedResumeBook.equals(differentCurrentStatePointer));
    }



    /**
     * Asserts that {@code versionedResumeBook} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedResumeBook#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedResumeBook#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertResumeBookListStatus(VersionedResumeBook versionedResumeBook,
                                             List<ReadOnlyResumeBook> expectedStatesBeforePointer,
                                              ReadOnlyResumeBook expectedCurrentState,
                                             List <ReadOnlyResumeBook> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new ResumeBook(versionedResumeBook), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedResumeBook.canUndo()) {
            versionedResumeBook.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyResumeBook expectedResumeBook : expectedStatesBeforePointer) {
            assertEquals(expectedResumeBook, new ResumeBook(versionedResumeBook));
            versionedResumeBook.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyResumeBook expectedResumeBook : expectedStatesAfterPointer) {
            versionedResumeBook.redo();
            assertEquals(expectedResumeBook, new ResumeBook(versionedResumeBook));
        }

        // check that there are no more states after pointer
        assertFalse(versionedResumeBook.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedResumeBook.undo());
    }

    /**
     * Creates and returns a {@code VersionedResumeBook} with the {@code resumeBookStates} added into it, and the
     * {@code VersionedResumeBook#currentStatePointer} at the end of list.
     */
    private VersionedResumeBook prepareResumeBookList (ReadOnlyResumeBook... resumeBookStates) {
        assertNotEquals(0, resumeBookStates.length);

        VersionedResumeBook versionedResumeBook = new VersionedResumeBook(resumeBookStates[0]);
        for (int i = 1; i < resumeBookStates.length; i++) {
            versionedResumeBook.resetData(resumeBookStates[i]);
            versionedResumeBook.commit();
        }

        return versionedResumeBook;
    }

    /**
     * Shifts the {@code versionedResumeBook#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedResumeBook versionedResumeBook, int count) {
        for (int i = 0; i < count; i++) {
            versionedResumeBook.undo();
        }
    }
}

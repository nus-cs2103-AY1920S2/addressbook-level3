package seedu.zerotoone.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.zerotoone.model.Model.PREDICATE_SHOW_ALL_EXERCISES;
import static seedu.zerotoone.testutil.Assert.assertThrows;
// import static seedu.zerotoone.testutil.TypicalExercises.ALICE;
// import static seedu.zerotoone.testutil.TypicalExercises.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.zerotoone.commons.core.GuiSettings;
import seedu.zerotoone.model.exercise.ExerciseList;
import seedu.zerotoone.model.exercise.PredicateFilterExerciseName;
import seedu.zerotoone.model.userprefs.UserPrefs;
import seedu.zerotoone.testutil.exercise.ExerciseListBuilder;

public class ModelManagerTest {

    // private ModelManager modelManager = new ModelManager();

    // @Test
    // public void constructor() {
    //     assertEquals(new UserPrefs(), modelManager.getUserPrefs());
    //     assertEquals(new GuiSettings(), modelManager.getGuiSettings());
    //     assertEquals(new ExerciseList(), new ExerciseList(modelManager.getExerciseList()));
    // }

    // @Test
    // public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
    //     assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    // }

    // @Test
    // public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
    //     UserPrefs userPrefs = new UserPrefs();
    //     userPrefs.setExerciseListFilePath(Paths.get("address/book/file/path"));
    //     userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
    //     modelManager.setUserPrefs(userPrefs);
    //     assertEquals(userPrefs, modelManager.getUserPrefs());

    //     // Modifying userPrefs should not modify modelManager's userPrefs
    //     UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
    //     userPrefs.setExerciseListFilePath(Paths.get("new/address/book/file/path"));
    //     assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    // }

    // @Test
    // public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
    //     assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    // }

    // @Test
    // public void setGuiSettings_validGuiSettings_setsGuiSettings() {
    //     GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
    //     modelManager.setGuiSettings(guiSettings);
    //     assertEquals(guiSettings, modelManager.getGuiSettings());
    // }

    // @Test
    // public void setExerciseListFilePath_nullPath_throwsNullPointerException() {
    //     assertThrows(NullPointerException.class, () -> modelManager.setExerciseListFilePath(null));
    // }

    // @Test
    // public void setExerciseListFilePath_validPath_setsExerciseListFilePath() {
    //     Path path = Paths.get("address/book/file/path");
    //     modelManager.setExerciseListFilePath(path);
    //     assertEquals(path, modelManager.getExerciseListFilePath());
    // }

    // @Test
    // public void hasExercise_nullExercise_throwsNullPointerException() {
    //     assertThrows(NullPointerException.class, () -> modelManager.hasExercise(null));
    // }

    // @Test
    // public void hasExercise_exerciseNotInExerciseList_returnsFalse() {
    //     assertFalse(modelManager.hasExercise(ALICE));
    // }

    // @Test
    // public void hasExercise_exerciseInExerciseList_returnsTrue() {
    //     modelManager.addExercise(ALICE);
    //     assertTrue(modelManager.hasExercise(ALICE));
    // }

    // @Test
    // public void getFilteredExerciseList_modifyList_throwsUnsupportedOperationException() {
    //     assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredExerciseList().remove(0));
    // }

    // @Test
    // public void equals() {
    //     ExerciseList exerciseList = new ExerciseListBuilder().withExercise(ALICE).withExercise(BENSON).build();
    //     ExerciseList differentExerciseList = new ExerciseList();
    //     UserPrefs userPrefs = new UserPrefs();

    //     // same values -> returns true
    //     modelManager = new ModelManager(exerciseList, userPrefs);
    //     ModelManager modelManagerCopy = new ModelManager(exerciseList, userPrefs);
    //     assertTrue(modelManager.equals(modelManagerCopy));

    //     // same object -> returns true
    //     assertTrue(modelManager.equals(modelManager));

    //     // null -> returns false
    //     assertFalse(modelManager.equals(null));

    //     // different types -> returns false
    //     assertFalse(modelManager.equals(5));

    //     // different exerciseList -> returns false
    //     assertFalse(modelManager.equals(new ModelManager(differentExerciseList, userPrefs)));

    //     // different filteredList -> returns false
    //     String[] keywords = ALICE.getName().fullName.split("\\s+");
    //     modelManager.updateFilteredExerciseList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
    //     assertFalse(modelManager.equals(new ModelManager(exerciseList, userPrefs)));

    //     // resets modelManager to initial state for upcoming tests
    //     modelManager.updateFilteredExerciseList(PREDICATE_SHOW_ALL_EXERCISES);

    //     // different userPrefs -> returns false
    //     UserPrefs differentUserPrefs = new UserPrefs();
    //     differentUserPrefs.setExerciseListFilePath(Paths.get("differentFilePath"));
    //     assertFalse(modelManager.equals(new ModelManager(exerciseList, differentUserPrefs)));
    // }
}

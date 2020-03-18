package seedu.zerotoone.model.exercise;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
// import static seedu.zerotoone.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
// import static seedu.zerotoone.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
// import static seedu.zerotoone.logic.commands.CommandTestUtil.VALID_NAME_BOB;
// import static seedu.zerotoone.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
// import static seedu.zerotoone.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
// import static seedu.zerotoone.testutil.Assert.assertThrows;
// import static seedu.zerotoone.testutil.TypicalExercises.BENCH_PRESS;
// import static seedu.zerotoone.testutil.TypicalExercises.CRUNCHES;

import org.junit.jupiter.api.Test;

//import seedu.zerotoone.model.person.Person;
import seedu.zerotoone.testutil.ExerciseBuilder;
//import seedu.zerotoone.testutil.PersonBuilder;

public class ExerciseTest {

    // TODO fix_test_case
    // @Test
    // public void asObservableList_modifyList_throwsUnsupportedOperationException() {
    //     Exercise exercise = new ExerciseBuilder().build();
    //     // assertThrows(UnsupportedOperationException.class, () -> exercise.getTags().remove(0));
    // }

    // @Test
    // public void isSameExercise() {
    //     // same object -> returns true
    //     assertTrue(BENCH_PRESS.isSameExercise(BENCH_PRESS));

    //     // null -> returns false
    //     assertFalse(BENCH_PRESS.isSameExercise(null));

    //     Exercise editedBenchPress = new ExerciseBuilder(BENCH_PRESS).withExerciseSet("20", "4", "120").build();

    //     // different name -> returns false
    //     editedBenchPress = new ExerciseBuilder(BENCH_PRESS).withName("Leg Curl").build();
    //     assertFalse(BENCH_PRESS.isSameExercise(editedBenchPress));

    //     // same name, same attributes -> returns true
    //     editedBenchPress = new ExerciseBuilder(BENCH_PRESS).withName("Bench Press")
    //             .withExerciseSet("30", "3", "180").build();
    //     assertTrue(BENCH_PRESS.isSameExercise(editedBenchPress));
    // }

    // @Test
    // public void equals() {
    //     // same values -> returns true
    //     Exercise benchPressCopy = new ExerciseBuilder(BENCH_PRESS).build();
    //     assertTrue(BENCH_PRESS.equals(benchPressCopy));

    //     // same object -> returns true
    //     assertTrue(BENCH_PRESS.equals(BENCH_PRESS));

    //     // null -> returns false
    //     assertFalse(BENCH_PRESS.equals(null));

    //     // different type -> returns false
    //     assertFalse(BENCH_PRESS.equals(5));

    //     // different person -> returns false
    //     assertFalse(BENCH_PRESS.equals(CRUNCHES));

    //     // different name -> returns false
    //     Exercise editedBenchPress = new ExerciseBuilder(BENCH_PRESS).withName("Leg Curl").build();
    //     assertFalse(BENCH_PRESS.equals(editedBenchPress));

    //     // different phone -> returns false
    //     editedBenchPress = new ExerciseBuilder(BENCH_PRESS).withExerciseSet("35", "2", "120").build();
    //     assertFalse(BENCH_PRESS.equals(editedBenchPress));
    // }
}

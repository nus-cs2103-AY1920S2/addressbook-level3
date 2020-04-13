package seedu.zerotoone.logic.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.zerotoone.testutil.Assert.assertThrows;
import static seedu.zerotoone.testutil.log.TypicalLogs.getTypicalLogList;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.zerotoone.model.session.CompletedWorkout;

class WorkoutCountDataPointTest {

    private WorkoutCountDataPoint workoutCountDataPoint = new WorkoutCountDataPoint();

    @Test
    void calculate_nullWorkouts_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> workoutCountDataPoint.calculate(null));
    }

    @Test
    void calculate_emptyList_success() {
        List<CompletedWorkout> workouts = new ArrayList<>();

        workoutCountDataPoint.calculate(workouts);
        assertEquals("0 workouts", workoutCountDataPoint.getResult());
    }

    @Test
    void calculate() {
        List<CompletedWorkout> workouts = getTypicalLogList().getLogList();
        workoutCountDataPoint.calculate(workouts);
        assertEquals(workouts.size() + " workouts", workoutCountDataPoint.getResult());
    }
}

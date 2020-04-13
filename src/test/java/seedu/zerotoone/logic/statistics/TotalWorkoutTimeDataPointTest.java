package seedu.zerotoone.logic.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.zerotoone.testutil.Assert.assertThrows;
import static seedu.zerotoone.testutil.log.TypicalLogs.getTypicalLogList;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.zerotoone.model.session.CompletedWorkout;

class TotalWorkoutTimeDataPointTest {

    private TotalWorkoutTimeDataPoint totalWorkoutTimeDataPoint = new TotalWorkoutTimeDataPoint();

    @Test
    void calculate_nullWorkouts_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> totalWorkoutTimeDataPoint.calculate(null));
    }

    @Test
    void calculate_emptyList_success() {
        List<CompletedWorkout> workouts = new ArrayList<>();

        totalWorkoutTimeDataPoint.calculate(workouts);
        assertEquals("0s", totalWorkoutTimeDataPoint.getResult());
    }

    @Test
    void calculate() {
        List<CompletedWorkout> workouts = getTypicalLogList().getLogList();
        totalWorkoutTimeDataPoint.calculate(workouts);
        assertEquals("6h", totalWorkoutTimeDataPoint.getResult());
    }
}

package seedu.zerotoone.logic.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.zerotoone.testutil.Assert.assertThrows;
import static seedu.zerotoone.testutil.log.TypicalLogs.getTypicalLogList;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.zerotoone.model.session.CompletedWorkout;

class AverageWorkoutTimePerDayTest {
    private AverageWorkoutTimePerDay averageWorkoutTimePerDay =
        new AverageWorkoutTimePerDay(LocalDateTime.now(), LocalDateTime.now());

    @Test
    void calculate_nullWorkouts_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> averageWorkoutTimePerDay.calculate(null));
    }

    @Test
    void calculate_emptyList_success() {
        List<CompletedWorkout> workouts = new ArrayList<>();

        averageWorkoutTimePerDay.calculate(workouts);
        assertEquals("0s", averageWorkoutTimePerDay.getResult());
    }

    @Test
    void calculate() {
        AverageWorkoutTimePerDay averageWorkoutTimePerDay = new AverageWorkoutTimePerDay(LocalDateTime.now(),
            LocalDateTime.now().plusDays(5));

        List<CompletedWorkout> workouts = getTypicalLogList().getLogList();
        averageWorkoutTimePerDay.calculate(workouts);
        assertEquals("1h", averageWorkoutTimePerDay.getResult());
    }
}

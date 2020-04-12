package seedu.zerotoone.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.zerotoone.testutil.log.TypicalLogs.getTypicalLogList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.zerotoone.logic.statistics.StatisticsData;
import seedu.zerotoone.model.session.CompletedWorkout;

class StatisticsTest {

    @Test
    void generate() {
    }

    @Test
    void generate_noWorkouts_emptyStatisticData() {
        StatisticsData actualOutput = Statistics.generate(
            new ArrayList<>(),
            Optional.empty(),
            Optional.empty());
        StatisticsData expectedOutput = new StatisticsData();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void getEarliestWorkoutStartTime() {
        List<CompletedWorkout> workouts = getTypicalLogList().getLogList();
        StatisticsData actualOutput = Statistics.generate(
            workouts,
            Optional.empty(),
            Optional.empty());
        assertEquals(workouts.get(0).getStartTime(), actualOutput.getStartRange());
    }

    @Test
    void getLatestEndDate() {
        List<CompletedWorkout> workouts = getTypicalLogList().getLogList();
        StatisticsData actualOutput = Statistics.generate(
            workouts,
            Optional.empty(),
            Optional.empty());
        assertEquals(workouts.get(workouts.size() - 1).getEndTime(),
            actualOutput.getEndRange());
    }
}

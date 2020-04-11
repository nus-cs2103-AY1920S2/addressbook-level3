package seedu.zerotoone.model.log;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.zerotoone.testutil.log.TypicalLogs.getTypicalLogList;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class StatisticsDataTest {

    private LocalDateTime mockDateTime = LocalDateTime.now();
    private Integer workoutCount = 10;

    private StatisticsData mockData = new StatisticsData(
        getTypicalLogList().getLogList(),
        mockDateTime,
        mockDateTime,
        workoutCount,
        Duration.ZERO,
        Duration.ZERO);

    @Test
    void testEquals() {
        StatisticsData expected = new StatisticsData();
        StatisticsData actual = new StatisticsData();
        assertEquals(expected, actual);
        assertEquals(new StatisticsData(), new StatisticsData());
    }

    @Test
    void getWorkouts() {
        assertEquals(getTypicalLogList().getLogList().size(), mockData.getWorkouts().size());
    }

    @Test
    void setWorkouts() {
        mockData.setWorkouts(new ArrayList<>());
        assertEquals(new ArrayList<>(), mockData.getWorkouts());
    }

    @Test
    void getStartRange() {
        assertEquals(mockDateTime, mockData.getStartRange());
    }

    @Test
    void getEndRange() {
        assertEquals(mockDateTime, mockData.getEndRange());
    }

    @Test
    void getTotalWorkoutCount() {
        assertEquals(workoutCount, mockData.getTotalWorkoutCount());
    }

    @Test
    void getTotalTime() {
        assertEquals(Duration.ZERO, mockData.getTotalTime());
    }

    @Test
    void getAverageTimePerDay() {
        assertEquals(Duration.ZERO, mockData.getAverageTimePerDay());
    }
}

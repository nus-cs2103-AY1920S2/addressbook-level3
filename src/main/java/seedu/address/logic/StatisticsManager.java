package seedu.address.logic;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.dayData.CustomQueue.CONSTANT_SIZE;

import java.util.List;
import seedu.address.model.Statistics;
import seedu.address.model.dayData.DayData;

/** Manages logic of StatisticsDisplay */
public class StatisticsManager {
    private Statistics statistics;
    private String dailyTargetText;
    private String progressDailyText;
    private String progressBarDailyFilepathString;

    public StatisticsManager() {}

    public void setStatistics(Statistics statistics) {
        requireNonNull(statistics);
        this.statistics = statistics;
    }

    /**
     * Update StatisticsDisplay fields for user output.
     *
     * @param dayDatas current dayDatas information.
     */
    public void updateStatisticsDisplayValues(List<DayData> dayDatas) {
        requireNonNull(statistics);
        requireNonNull(dayDatas);
        assert (dayDatas.size() > 0);

        // get daily challenge target
        String currTargetText = statistics.getDailyTarget();
        int currTarget = Integer.valueOf(currTargetText);
        this.dailyTargetText = currTargetText;

        // get current progress
        DayData latestDayData = dayDatas.get(CONSTANT_SIZE - 1);
        int currProgress = latestDayData.getPomDurationData().value;
        if (currProgress >= currTarget) {
            currProgress = currTarget;
        }
        this.progressDailyText = String.valueOf(currProgress);

        // calculate percentage bar
        int expBarPerc = (currProgress * 10) / currTarget;
        expBarPerc *= 10; // 0, 10, 20, 30...
        if (expBarPerc >= 100) {
            expBarPerc = 100;
        }

        progressBarDailyFilepathString =
                "/images/progress/ProgressBar" + String.valueOf(expBarPerc) + "%.png";
    }

    public void setDailyTargetText(String dailyTargetText) {
        requireNonNull(dailyTargetText);
        statistics.setDailyTarget(dailyTargetText);
    }

    public String getDailyTargetText() {
        return statistics.getDailyTarget();
    }

    public String getProgressDailyText() {
        return progressDailyText;
    }

    public String getProgressBarDailyFilepathString() {
        return progressBarDailyFilepathString;
    }
}

package seedu.zerotoone.model.log;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.zerotoone.model.session.CompletedWorkout;

/**
 * Model for Log.
 */
public interface LogModel {
    ReadOnlyLogList getLogList();
    ArrayList<CompletedWorkout> getLogListCopyAsArrayList();
    void deleteLog(int target);
    ObservableList<CompletedWorkout> getFilteredLogList();
    void updateFilteredLogList(Predicate<CompletedWorkout> predicate);
    Path getLogListFilePath();
    Optional<LocalDateTime> getStatisticsStartDateRange();
    Optional<LocalDateTime> getStatisticsEndDateRange();
    void setLogListFilePath(Path logListFilePath);
    void setStatisticsDateRange(Optional<LocalDateTime> startRange, Optional<LocalDateTime> endRange);
    void shutdownTimer();
}

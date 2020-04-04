package seedu.zerotoone.storage.log.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.zerotoone.commons.exceptions.IllegalValueException;
import seedu.zerotoone.model.log.ReadOnlyLogList;
import seedu.zerotoone.model.session.CompletedExercise;
import seedu.zerotoone.model.log.LogList;
import seedu.zerotoone.model.session.CompletedWorkout;

/**
 * An Immutable LogList that is serializable to JSON format.
 */
@JsonRootName(value = "loglist")
public class JacksonLogList {

    private final List<JacksonCompletedWorkout> logs = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableLogList} with the given logs.
     */
    @JsonCreator
    public JacksonLogList(@JsonProperty("logs") List<JacksonCompletedWorkout> logs) {
        this.logs.addAll(logs);
    }

    /**
     * Converts a given {@code ReadOnlyLogList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableLogList}.
     */
    public JacksonLogList(ReadOnlyLogList source) {
        logs.addAll(source.getLogList().stream().map(JacksonCompletedWorkout::new).collect(Collectors.toList()));
    }

    /**
     * Converts this log list into the model's {@code LogList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public LogList toModelType() throws IllegalValueException {
        LogList logList = new LogList();
        for (JacksonCompletedWorkout jsonAdaptedLog : logs) {
            CompletedWorkout completedWorkout = jsonAdaptedLog.toModelType();
            logList.addCompletedWorkout(completedWorkout);
        }
        return logList;
    }

}

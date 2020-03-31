package seedu.zerotoone.storage.session.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.zerotoone.commons.exceptions.IllegalValueException;
import seedu.zerotoone.model.exercise.ExerciseName;
import seedu.zerotoone.model.session.Session;
import seedu.zerotoone.model.session.SessionSet;

/**
 * Jackson-friendly version of {@link Session}.
 */
public class JacksonSession {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Session's %s field is missing!";
    public static final String INVALID_TIME_FORMAT_MESSAGE = "Session's startTime or endTime field is invalid!";
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final String exerciseName;
    private final List<JacksonSessionSet> sessionSets = new LinkedList<>();
    private final String startTime;
    private final String endTime;


    /**
     * Constructs a {@code JsonAdaptedSession} with the given session details.
     */
    @JsonCreator
    public JacksonSession(@JsonProperty("sessionName") String sessionName,
                          @JsonProperty("sessionSets") List<JacksonSessionSet> sessionSets,
                          @JsonProperty("sessionStartTime") String startTime,
                          @JsonProperty("sessionEndTime") String endTime) {
        this.exerciseName = sessionName;
        this.startTime = startTime;
        this.endTime = endTime;
        if (sessionSets != null) {
            this.sessionSets.addAll(sessionSets);
        }
    }

    /**
     * Converts a given {@code Session} into this class for Jackson use.
     */
    public JacksonSession(Session source) {
        exerciseName = source.getExerciseName().fullName;
        for (SessionSet sessionSet : source.getSets()) {
            sessionSets.add(new JacksonSessionSet(sessionSet));
        }

        startTime = source.getStartTime().format(dateTimeFormatter);
        endTime = source.getEndTime().format(dateTimeFormatter);
    }

    /**
     * Converts this Jackson-friendly adapted session object into the model's {@code Session} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted session.
     */
    public Session toModelType() throws IllegalValueException {
        if (exerciseName == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ExerciseName.class.getSimpleName()));
        } else if (!ExerciseName.isValidExerciseName(exerciseName)) {

        }
        final ExerciseName modelSessionName = new ExerciseName(exerciseName);

        final List<SessionSet> modelSessionSets = new ArrayList<>();
        for (JacksonSessionSet sessionSet : sessionSets) {
            modelSessionSets.add(sessionSet.toModelType());
        }

        if (startTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "StartTime"));
        }

        if (endTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "EndTime"));
        }
        LocalDateTime start;
        LocalDateTime end;

        try {
            start = LocalDateTime.parse(startTime, dateTimeFormatter);
            end = LocalDateTime.parse(endTime, dateTimeFormatter);
        } catch (DateTimeParseException exception) {
            throw new IllegalValueException(INVALID_TIME_FORMAT_MESSAGE);
        }
        return new Session(modelSessionName, modelSessionSets, start, end);
    }

}

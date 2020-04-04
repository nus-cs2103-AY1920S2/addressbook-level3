package seedu.zerotoone.storage.log.util;

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
import seedu.zerotoone.model.session.CompletedSet;
import seedu.zerotoone.model.session.CompletedExercise;

/**
 * Jackson-friendly version of {@link CompletedExercise}.
 */
public class JacksonCompletedExercise {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Exercise's %s field is missing!";
    public static final String INVALID_TIME_FORMAT_MESSAGE = "Exercise's startTime or endTime field is invalid!";
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final String exerciseName;
    private final List<JacksonCompletedSet> exerciseSets = new LinkedList<>();
    private final String startTime;
    private final String endTime;


    /**
     * Constructs a {@code JsonAdaptedCompletedExercise} with the given exercise details.
     */
    @JsonCreator
    public JacksonCompletedExercise(@JsonProperty("exerciseName") String exerciseName,
                                    @JsonProperty("exerciseSets") List<JacksonCompletedSet> exerciseSets,
                                    @JsonProperty("exerciseStartTime") String startTime,
                                    @JsonProperty("exerciseEndTime") String endTime) {
        this.exerciseName = exerciseName;
        this.startTime = startTime;
        this.endTime = endTime;
        if (exerciseSets != null) {
            this.exerciseSets.addAll(exerciseSets);
        }
    }

    /**
     * Converts a given {@code CompletedExercise} into this class for Jackson use.
     */
    public JacksonCompletedExercise(CompletedExercise source) {
        exerciseName = source.getExerciseName().fullName;
        for (CompletedSet completedSet : source.getSets()) {
            exerciseSets.add(new JacksonCompletedSet(completedSet));
        }

        startTime = source.getStartTime().format(dateTimeFormatter);
        endTime = source.getEndTime().format(dateTimeFormatter);
    }

    /**
     * Converts this Jackson-friendly adapted exercise object into the model's {@code CompletedExercise} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted exercise.
     */
    public CompletedExercise toModelType() throws IllegalValueException {
        if (exerciseName == null || !ExerciseName.isValidExerciseName(exerciseName)) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ExerciseName.class.getSimpleName()));
        }
        final ExerciseName modelExerciseName = new ExerciseName(exerciseName);

        final List<CompletedSet> modelCompletedSets = new ArrayList<>();
        for (JacksonCompletedSet exerciseSet : exerciseSets) {
            modelCompletedSets.add(exerciseSet.toModelType());
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
        return new CompletedExercise(modelExerciseName, modelCompletedSets, start, end);
    }

}

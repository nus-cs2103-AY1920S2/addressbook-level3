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
import seedu.zerotoone.model.session.CompletedExercise;
import seedu.zerotoone.model.session.CompletedWorkout;
import seedu.zerotoone.model.workout.WorkoutName;

/**
 * Jackson-friendly version of {@link CompletedWorkout}.
 */
public class JacksonCompletedWorkout {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Workout's %s field is missing!";
    public static final String INVALID_TIME_FORMAT_MESSAGE = "Workout's startTime or endTime field is invalid!";
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final String workoutName;
    private final String startTime;
    private final String endTime;
    private final List<JacksonCompletedExercise> exercise = new LinkedList<>();


    /**
     * Constructs a {@code JsonAdaptedWorkout} with the given workout details.
     */
    @JsonCreator
    public JacksonCompletedWorkout(@JsonProperty("workoutName") String workoutName,
                                    @JsonProperty("exercises") List<JacksonCompletedExercise> exercise,
                                    @JsonProperty("workoutStartTime") String startTime,
                                    @JsonProperty("workoutEndTime") String endTime) {
        this.workoutName = workoutName;
        this.startTime = startTime;
        this.endTime = endTime;
        if (exercise != null) {
            this.exercise.addAll(exercise);
        }
    }

    /**
     * Converts a given {@code CompletedExercise} into this class for Jackson use.
     */
    public JacksonCompletedWorkout(CompletedWorkout source) {
        workoutName = source.getWorkoutName().fullName;
        for (CompletedExercise completeExercise : source.getExercises()) {
            exercise.add(new JacksonCompletedExercise(completeExercise));
        }

        startTime = source.getStartTime().format(dateTimeFormatter);
        endTime = source.getEndTime().format(dateTimeFormatter);
    }

    /**
     * Converts this Jackson-friendly adapted workout object into the model's {@code CompletedExercise} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted workout.
     */
    public CompletedWorkout toModelType() throws IllegalValueException {
        if (workoutName == null || !WorkoutName.isValidWorkoutName(workoutName)) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ExerciseName.class.getSimpleName()));
        }

        final WorkoutName modelWorkoutName = new WorkoutName(workoutName);

        final List<CompletedExercise> modelCompletedExercise = new ArrayList<>();
        for (JacksonCompletedExercise workoutExercise : exercise) {
            modelCompletedExercise.add(workoutExercise.toModelType());
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
        return new CompletedWorkout(modelWorkoutName, modelCompletedExercise, start, end);
    }

}

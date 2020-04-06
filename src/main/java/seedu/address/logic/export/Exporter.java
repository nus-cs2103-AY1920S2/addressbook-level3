package seedu.address.logic.export;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.client.Client;
import seedu.address.model.exercise.Exercise;
import seedu.address.storage.StorageReaderWriter;

/**
 * This class contains the logic behind the exporting of files.
 */
public class Exporter {

    /**
     * Exports the provided {@code client}'s exercises as a CSV file to storage
     * via @{code StorageReaderWriter}.
     *
     * @param client the {@code Client} to export
     * @throws CommandException if {@code client} does not have any exercises stored
     */
    public static String exportExercisesAsCsv(Client client) throws CommandException {
        List<Exercise> exercises = client.getExerciseList().asUnmodifiableObservableList();

        if (exercises.isEmpty()) {
            throw new CommandException("This client currently does not have any exercises to export.");
        }

        String fileName = client.getName().fullName + ".csv";
        Path filePath = Paths.get("exports", fileName);
        StorageReaderWriter storageReaderWriter = new StorageReaderWriter(filePath);

        List<String> lines = new LinkedList<>();

        String headers = "Date,Exercise Name,Reps,Weights,Sets";
        lines.add(headers);

        for (Exercise e : exercises) {
            String dateString = e.getExerciseDate().displayValue;
            String nameString = e.getExerciseName().value;
            String repsString = e.getExerciseReps().value;
            String weightString = e.getExerciseWeight().value;
            String setsString = e.getExerciseSets().value;
            String line = String.format("%s,%s,%s,%s,%s",
                    dateString, nameString, repsString, weightString, setsString);
            lines.add(line);
        }

        storageReaderWriter.writeToStorage(lines);

        return fileName;
    }
}

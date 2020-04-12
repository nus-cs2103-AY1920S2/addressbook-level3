package seedu.address.commons.util;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import seedu.address.model.exercise.Exercise;

/**
 * This class contains the logic behind the exporting of files.
 */
public class ExportUtil {

    public static final String DEFAULT_EXPORTS_DIRECTORY = "exports";

    public static final String EXERCISE_CSV_HEADERS = "Date,Exercise Name,Reps,Weights,Sets";

    /**
     * Exports the provided list of {@code exercises} as a CSV file and saves it to
     * storage as {@code fileName}.
     *
     * @param exercises client's exercises to export
     * @param fileName  name of the CSV file
     * @throws IOException if the file is unable to be saved
     */
    public static void exportExercisesAsCsv(List<Exercise> exercises, String fileName) throws IOException {
        Path filePath = Paths.get(DEFAULT_EXPORTS_DIRECTORY, fileName);

        String csvContent = EXERCISE_CSV_HEADERS + "\n";

        for (Exercise e : exercises) {
            String dateString = e.getExerciseDate().displayValue;
            String nameString = e.getExerciseName().value;
            String repsString = e.getExerciseReps().value;
            String weightString = e.getExerciseWeight().value;
            String setsString = e.getExerciseSets().value;
            String rowContent = String.format("%s,%s,%s,%s,%s\n", dateString, nameString, repsString, weightString,
                    setsString);
            csvContent += rowContent;
        }

        FileUtil.createFile(filePath);
        FileUtil.writeToFile(filePath, csvContent);

    }

}

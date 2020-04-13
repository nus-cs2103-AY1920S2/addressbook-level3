package seedu.address.model.event.tutorial;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import seedu.address.commons.util.FileUtil;
import seedu.address.model.student.Student;

/**
 * Creates a TutorialExportCsv object to export attendance data of a Tutorial into a csv file.
 */
public class TutorialExportCsv {
    private final Tutorial tutorial;
    private final Path path;
    private StringBuilder writeToCsv;

    public TutorialExportCsv(Tutorial tutorial, Path path) {
        this.tutorial = tutorial;
        this.path = path;
        this.writeToCsv = new StringBuilder();
    }

    /**
     * Exports attendance data into a CSV file.
     * @throws IOException if file cannot be found or cannot be written into.
     */
    public void exportAttendance() throws IOException {
        populateCsvStringBuilder();

        // Not sure if this can be tested
        FileUtil.createIfMissing(path);
        FileUtil.writeToFile(path, writeToCsv.toString());
    }

    /**
     * Inserts data into the StringBuilder to be written into the CSV file.
     */
    private void populateCsvStringBuilder() {
        writeToCsv = new StringBuilder();
        writeToCsv.append(tutorial.getModCode())
            .append(" ")
            .append(tutorial.getTutorialName())
            .append("\n\n")
            .append("Name,Student Number,Email,");
        for (int i = 0; i < Tutorial.NUM_OF_WEEKS; ++i) {
            writeToCsv.append("Week ")
                .append(i + 3)
                .append(",");
        }
        writeToCsv.append("\n");

        List<Student> studentList = tutorial.getEnrolledStudents();
        for (Student student: studentList) {
            writeToCsv.append(student.getName())
                .append(",")
                .append(student.getMatricNumber())
                .append(",")
                .append(student.getEmail())
                .append(",");

            List<Boolean> attendance = tutorial.getAttendanceofStudent(student);
            attendance.stream().map(x -> x ? 1 : 0).forEach(x -> writeToCsv.append(x).append(","));
            writeToCsv.append("\n");
        }
    }
}

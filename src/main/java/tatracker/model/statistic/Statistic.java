//@@author Eclmist

package tatracker.model.statistic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import tatracker.model.ReadOnlyTaTracker;
import tatracker.model.session.SessionType;
import tatracker.model.session.UniqueSessionList;
import tatracker.model.student.Rating;
import tatracker.model.student.Student;
import tatracker.model.student.UniqueStudentList;
import tatracker.ui.StatisticWindow;

/**
 * A data container that stores the statistic of TA-Tracker.
 * The UI classes will read statistics from this class to display statistics data in the Statistics window.
 */
public class Statistic {

    public static final String ALL_MODULES_STRING = "ALL MODULES";

    public final int[] numHoursPerCategory = new int [SessionType.NUM_SESSION_TYPES];
    public final int[] studentRatingBinValues = new int[Rating.RANGE];
    public final RatedStudent[] worstStudents = new RatedStudent[StatisticWindow.NUM_STUDENTS_TO_DISPLAY];
    public final String targetModuleCode;

    private final ReadOnlyTaTracker taTracker;

    public Statistic(ReadOnlyTaTracker taTracker, String targetModuleCode) {

        this.taTracker = taTracker;

        UniqueSessionList fList = new UniqueSessionList();
        UniqueStudentList sList = new UniqueStudentList();

        fList.setSessions(taTracker.getDoneSessionList());
        sList.setStudents(taTracker.getCompleteStudentList());

        // If targetModule is not null, filter by target module.
        if (targetModuleCode != null) {
            this.targetModuleCode = targetModuleCode;
            fList = fList.getSessionsOfModuleCode(targetModuleCode);
        } else {
            this.targetModuleCode = ALL_MODULES_STRING;
        }

        for (int i = 0; i < numHoursPerCategory.length; ++i) {
            this.numHoursPerCategory[i] = fList.getSessionsOfType(SessionType.getSessionTypeById(i))
                    .getTotalDuration().toHoursPart();
        }

        for (int i = 0; i < studentRatingBinValues.length; ++i) {
            this.studentRatingBinValues[i] = sList.getStudentsOfRating(new Rating(Rating.MIN_RATING + i)).size();
        }

        // Setup worst students
        List<Student> students = new ArrayList<>(taTracker.getCompleteStudentList());
        students.sort(Comparator.comparingInt((Student a) -> a.getRating().value));

        for (int i = 0; i < worstStudents.length; ++i) {
            if (i < students.size()) {
                worstStudents[i] = new RatedStudent(students.get(i));
            } else {
                worstStudents[i] = new RatedStudent();
            }
        }
    }

    public int getTotalHours() {
        int total = 0;
        for (int h : this.numHoursPerCategory) {
            total += h;
        }
        return total;
    }

    public int getTotalEarnings() {
        return getTotalHours() * taTracker.getRate();
    }

    /**
     * Represents a Statistics entry containing a Student's name and their associated rating.
     */
    public static class RatedStudent {
        private final Student student;
        private final String fullName;
        private final int rating;

        public RatedStudent() {
            this.student = null;
            this.fullName = "";
            this.rating = 0;
        }

        public RatedStudent(Student student) {
            this.student = student;
            this.fullName = student.getName().fullName;
            this.rating = student.getRating().value;
        }

        public String getFullName() {
            return fullName;
        }

        public int getRating() {
            return rating;
        }
    }
}


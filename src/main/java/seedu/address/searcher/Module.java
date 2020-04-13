package seedu.address.searcher;

import java.util.ArrayList;

/**
 * <h1>Module Class</h1>
 *
 * A simple class to hold module information
 */
public class Module {
    private String title;
    private String code;
    private String department;
    private String faculty;
    private String description;
    private String workload;
    private String preclusion;
    private int credits;
    private String sem1Exam = "NA";
    private String sem2Exam = "NA";
    private ArrayList<Classes> sem1Classes = new ArrayList<>();
    private ArrayList<Classes> sem2Classes = new ArrayList<>();
    private ArrayList<Classes> sem1Lectures = new ArrayList<>();
    private ArrayList<Classes> sem1Tutorials = new ArrayList<>();
    private ArrayList<Classes> sem1Others = new ArrayList<>();
    private ArrayList<Classes> sem2Lectures = new ArrayList<>();
    private ArrayList<Classes> sem2Tutorials = new ArrayList<>();
    private ArrayList<Classes> sem2Others = new ArrayList<>();

    /**
     * Constructor for module class
     * Parses info from http request into module
     * access information easily by using class functions
     * @param input this is raw HTTP data to be parsed
     */
    public Module(String input) throws StringIndexOutOfBoundsException {
        this.preclusion = input.substring(input.indexOf("preclusion") + 13, input.indexOf("description") - 3);
        this.description = input.substring(input.indexOf("description") + 14, input.indexOf("title\":") - 3);
        this.title = input.substring(input.indexOf("title") + 8, input.indexOf("department") - 3);
        this.department = input.substring(input.indexOf("department") + 13, input.indexOf("faculty") - 3);
        this.faculty = input.substring(input.indexOf("faculty") + 10, input.indexOf("workload") - 3);
        this.workload = input.substring(input.indexOf("workload") + 10, input.indexOf("moduleCredit") - 2);
        this.credits = Integer.parseInt(input.substring(input.indexOf("moduleCredit") + 15,
                input.indexOf("moduleCode") - 3));

        try {
            this.code = input.substring(input.indexOf("moduleCode") + 13, input.indexOf("attributes") - 3);
        } catch (StringIndexOutOfBoundsException e) {
            this.code = input.substring(input.indexOf("moduleCode") + 13, input.indexOf("semesterData") - 3);
        }

        String semesterData = input.substring(input.indexOf("semester\":"));
        semesterData = semesterData.split("prereqTree")[0];
        semesterData = semesterData.split("fulfillRequirements")[0];
        parseSemData(semesterData);
        splitClassByType();
    }

    /**
     * Helper function for search function to parse class timetable information
     * @param input string of class information
     */
    private void parseSemData(String input) {
        boolean hasSem1 = input.contains("semester\":1");
        boolean hasSem2 = input.contains("semester\":2");
        boolean hasExam = input.contains("examDate");
        String sem1Data = "";
        String sem2Data = "";

        if (hasSem1 && hasExam) {
            sem1Data = input.substring(input.indexOf("timetable") + 12, input.indexOf("examDate") - 4);
            sem1Exam = input.substring(input.indexOf("examDate") + 11, input.indexOf("examDuration") - 3).split("T")[0];
        } else if (hasSem1) {
            if (hasSem2) {
                sem1Data = input.substring(input.indexOf("timetable") + 12, input.indexOf("semester\":2"));
            } else {
                sem1Data = input.substring(input.indexOf("timetable") + 12);
            }
        }

        String[] classes = sem1Data.split("classNo");

        for (int i = 1; i < classes.length; i++) {
            String temp = classes[i];
            temp = temp.split("},\\{")[0];
            Classes myClass = new Classes(temp);
            sem1Classes.add(myClass);
        }

        if (hasSem2) {
            if (hasExam) {
                sem2Data = input.substring(input.lastIndexOf("timetable") + 12,
                        input.lastIndexOf("examDate") - 4);
                sem2Exam = input.substring(input.lastIndexOf("examDate") + 11,
                        input.lastIndexOf("examDuration") - 3).split("T")[0];
            } else {
                sem2Data = input.substring(input.lastIndexOf("timetable") + 12);
            }
        }

        classes = sem2Data.split("classNo");

        for (int i = 1; i < classes.length; i++) {
            String temp = classes[i];
            temp = temp.split("},\\{")[0];
            Classes myClass = new Classes(temp);
            sem2Classes.add(myClass);
        }
    }

    /**
     * Helper Function for Constructor
     */
    private void splitClassByType() {
        for (Classes curr : sem1Classes) {
            if (curr.getLessonType().equals("Lecture")) {
                sem1Lectures.add(curr);
            } else if (curr.getLessonType().equals("Tutorial")) {
                sem1Tutorials.add(curr);
            } else {
                sem1Others.add(curr);
            }
        }

        for (Classes curr : sem2Classes) {
            if (curr.getLessonType().equals("Lecture")) {
                sem2Lectures.add(curr);
            } else if (curr.getLessonType().equals("Tutorial")) {
                sem2Tutorials.add(curr);
            } else {
                sem2Others.add(curr);
            }
        }
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public ArrayList<Classes> getSem1Classes() {
        return sem1Classes;
    }

    public String getSem1_exam() {
        return sem1Exam;
    }

    public ArrayList<Classes> getSem2Classes() {
        return sem2Classes;
    }

    public String getSem2_exam() {
        return sem2Exam;
    }

    public int getCredits() {
        return this.credits;
    }

    public ArrayList<Classes> getSem1Lectures() {
        return this.sem1Lectures;
    }

    public ArrayList<Classes> getSem1Tutorials() {
        return this.sem1Tutorials;
    }

    public ArrayList<Classes> getSem1Others() {
        return this.sem1Others;
    }

    public ArrayList<Classes> getSem2Lectures() {
        return this.sem2Lectures;
    }

    public ArrayList<Classes> getSem2Tutorials() {
        return this.sem2Tutorials;
    }

    public ArrayList<Classes> getSem2Others() {
        return this.sem2Others;
    }

    @Override
    public String toString() {
        String output = "";

        output = output + this.code + " " + this.title + "\n";
        output = output + "Faculty: " + this.faculty + "\n";
        output = output + "Department: " + this.department + "\n";
        output = output + "Credit Units: " + this.credits + "\n";
        output = output + "Workload: " + this.workload + "\n";
        output = output + this.description + "\n";

        if (!sem1Exam.equals("")) {
            output = output + "Semester 1 Exam: " + sem1Exam + "\n";
        }

        if (!sem2Exam.equals("")) {
            output = output + "Semester 2 Exam: " + sem2Exam + "\n";
        }
        return output;
    }
}

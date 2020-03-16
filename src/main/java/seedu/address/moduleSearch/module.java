package seedu.address.moduleSearch;

import java.util.ArrayList;

public class module {
    private String title;
    private String code;
    private String department;
    private String faculty;
    private String description;
    private String workload;
    private String preclusion;
    private int credits;
    private String sem1_exam = "";
    private String sem2_exam = "";
    private ArrayList<schedule> sem1_Classes = new ArrayList<>();
    private ArrayList<schedule> sem2_Classes = new ArrayList<>();

    /**
     * Constructor for module class
     * Parses info from http request into module
     * access information easily by using class functions
     * @param input this is raw HTTP data to be parsed
     */
    public module(String input) {
        this.preclusion = input.substring(input.indexOf("preclusion") + 13, input.indexOf("description") - 3);
        this.description = input.substring(input.indexOf("description") + 14, input.indexOf("title\":") - 3);
        this.title = input.substring(input.indexOf("title") + 8, input.indexOf("department") - 3);
        this.department = input.substring(input.indexOf("department") + 13, input.indexOf("faculty") - 3);
        this.faculty = input.substring(input.indexOf("faculty") + 10, input.indexOf("workload") - 3);
        this.workload = input.substring(input.indexOf("workload") + 10, input.indexOf("moduleCredit") - 2);
        this.credits = Integer.parseInt(input.substring(input.indexOf("moduleCredit") + 15, input.indexOf("moduleCode") - 3));

        try {
            this.code = input.substring(input.indexOf("moduleCode") + 13, input.indexOf("attributes") - 3);
        } catch(StringIndexOutOfBoundsException e) {
            this.code = input.substring(input.indexOf("moduleCode") + 13, input.indexOf("semesterData") - 3);
        }

        String semester1 = "";
        String semester2 = "";

        if (input.indexOf("semester\":1") <= 0) {
            if (input.indexOf("semester\":2") <= 0) {
                semester2 = "";
            } else {
                semester2 = input.substring(input.indexOf("semester\":2") + 13, input.indexOf("fulfillRequirements") - 4);
            }
        } else {
            if (input.indexOf("semester\":2") <= 0) {
                semester1 = input.substring(input.indexOf("semester\":1") + 13, input.indexOf("fulfillRequirements") - 4);
            } else {
                semester1 = input.substring(input.indexOf("semester\":1") + 13, input.indexOf("semester\":2") - 4);
                semester2 = input.substring(input.indexOf("semester\":2") + 13, input.indexOf("fulfillRequirements") - 4);
            }
        }

        String[] sem1Temp = semester1.split("classNo");
        String[] sem2Temp = semester2.split("classNo");

        if (sem1Temp.length > 1) {
            String temp = sem1Temp[sem1Temp.length - 1];

            temp = temp.substring(temp.indexOf("examDate") + 11, temp.indexOf("examDuration") - 3);
            temp = temp.split("T")[0];
            this.sem1_exam = temp;
        }

        if (sem2Temp.length > 1) {
            String temp = sem2Temp[sem2Temp.length - 1];

            temp = temp.substring(temp.indexOf("examDate") + 11, temp.indexOf("examDuration") - 3);
            temp = temp.split("T")[0];
            this.sem2_exam = temp;
        }

        for (int i = 1; i < sem1Temp.length; i++) {
            String temp = sem1Temp[i];
            schedule myClass = new schedule(temp);
            sem1_Classes.add(myClass);
        }

        for (int i = 1; i < sem2Temp.length; i++) {
            String temp = sem2Temp[i];
            schedule myClass = new schedule(temp);
            sem2_Classes.add(myClass);
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

    public ArrayList<schedule> getSem1Classes() {
        return sem1_Classes;
    }

    public String getSem1_exam() {
        return sem1_exam;
    }

    public ArrayList<schedule> getSem2Classes() {
        return sem2_Classes;
    }

    public String getSem2_exam() {
        return sem2_exam;
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

        if (!sem1_exam.equals("")) {
            output = output + "Semester 1 Exam: " + sem1_exam + "\n";
        }

        if (!sem2_exam.equals("")) {
            output = output + "Semester 2 Exam: " + sem2_exam + "\n";
        }
        return output;
    }
}

package seedu.address.searcher;

/**
 * <h1>Classes Class</h1>
 *
 * A class to hold information about each class timetable slot
 */
public class Classes {
    private String classNo;
    private String startTime;
    private String endTime;
    private int size;
    private String venue;
    private String day;
    private String lessonType;

    public Classes(String input) {
        this.classNo = input.substring(3, input.indexOf("startTime") - 3);
        this.startTime = input.substring(input.indexOf("startTime") + 12, input.indexOf("endTime") - 3);
        this.endTime = input.substring(input.indexOf("endTime") + 10, input.indexOf("weeks") - 3);
        this.size = Integer.parseInt(input.substring(input.indexOf("size") + 6));
        this.venue = input.substring(input.indexOf("venue") + 8, input.indexOf("\"day\":") - 3);
        this.day = input.substring(input.indexOf("\"day\":") + 7, input.indexOf("lessonType") - 3);
        this.lessonType = input.substring(input.indexOf("lessonType") + 13, input.indexOf("size") - 3);
    }

    @Override
    public String toString() {
        String output = "";
        output = output + "Class ID: " + this.classNo + " " + this.lessonType + "\n";
        output = output + "Start Time: " + this.startTime + " End Time: " + this.endTime + " " + this.day + "\n";
        output = output + "Size: " + this.size + "\n";
        output = output + "Venue: " + this.venue + "\n";
        return output;
    }

    public String getLessonType() {
        return this.lessonType;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public String getClassNo() {
        return this.classNo;
    }

    public String getEndTime() {
        return this.classNo;
    }

    public String getDay() {
        return this.day;
    }

    public String getVenue() {
        return this.venue;
    }
}

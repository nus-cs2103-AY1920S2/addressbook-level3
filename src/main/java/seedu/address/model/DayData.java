package seedu.address.model;

public class DayData {

    private String date;
    private String pomDurationData;
    private String tasksDoneData;

    public DayData() {
        this.date = null;
        this.pomDurationData = "0";
        this.tasksDoneData = "0";
    }

    public DayData(String date, String pomDurationData, String tasksDoneData) {
        this.date = date;
        this.pomDurationData = pomDurationData;
        this.tasksDoneData = tasksDoneData;
    }

    public String getDate() {
        return this.date;
    }

    public String getPomDurationData() {
        return this.pomDurationData;
    }

    public String getTasksDoneData() {
        return this.tasksDoneData;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPomDurationData(String pomDurationData) {
        this.pomDurationData = pomDurationData;
    }

    public void setTasksDoneData(String tasksDoneData) {
        this.tasksDoneData = tasksDoneData;
    }
}

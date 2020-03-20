package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

public class Statistics implements ReadOnlyStatistics {
    private static final int DEFAULT_MEDALS = 0;

    private int medals;
    private List<String> pomDurationData = new ArrayList<>();
    private List<String> tasksDoneData = new ArrayList<>();

    public Statistics(int medals, List<String> pomDurationData, List<String> tasksDoneData) {
        this.medals = medals;
        this.pomDurationData = pomDurationData;
        this.tasksDoneData = tasksDoneData;
    }

    public Statistics(ReadOnlyStatistics source) {
        this(source.getMedals(), source.getPomDurationData(), source.getTasksDoneData());
    }

    public Statistics() {
        this(DEFAULT_MEDALS, null, null);
    }

    public void setMedals(int medals) {
        this.medals = medals;
    }

    @Override
    public int getMedals() {
        return this.medals;
    }

    @Override
    public List<String> getPomDurationData() {
        return this.pomDurationData;
    }

    @Override
    public List<String> getTasksDoneData() {
        return this.tasksDoneData;
    }

    @Override
    public String toString() {
        return String.format(
                "Hi I am a placeholder " + this.medals);
    }
}

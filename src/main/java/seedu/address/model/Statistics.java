package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import seedu.address.model.dayData.Date;
import seedu.address.model.dayData.DayData;
import seedu.address.model.dayData.PomDurationData;
import seedu.address.model.dayData.TasksDoneData;

/**
 * Wraps all DayData objects.
 */
public class Statistics implements ReadOnlyStatistics {

    private final ArrayList<DayData> dayDataList;
    private final int MAX_SIZE = 7;

    public Statistics() {
        dayDataList = new ArrayList<>();
        this.init();
    }

    /** Creates an DayDataList using the DayDatas in the {@code toBeCopied} */
    public Statistics(ReadOnlyStatistics toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /** Initialises empty DayData for past MAX_SIZE days */
    public void init() {
        LocalDate currDate = LocalDate.now();
        for (int i = 0; i < MAX_SIZE; i++) {
            LocalDate tempLocalDate = currDate.minusDays(i);
            Date tempDate = new Date(tempLocalDate);
            DayData dayData = new DayData(tempDate);
            dayDataList.add(dayData);
        }
    }

    //// list overwrite operations

    /** Clears list */
    public void clearList() {
        this.dayDataList.clear();
    }

    /**
     * Replaces the contents of the list with {@code dayDataList}.
     */
    public void setDayDatas(List<DayData> dayDataList) {
        this.dayDataList.clear();
        this.dayDataList.addAll(dayDataList);
    }

    /** Resets the existing data of this {@code Statistics} with {@code newData}. */
    public void resetData(ReadOnlyStatistics newData) {
        requireNonNull(newData);
        setDayDatas(newData.getDayDataList());
    }

    //// dayData-level operations

    // FUNCTIONS FOR HARDOHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH SIR

    /** reinitialises dayDataList to current day while retaining stored data. */
    public void fetch() {
        HashMap<Date, PomDurationData> pomTempStorage = new HashMap<>();
        HashMap<Date, TasksDoneData> tasksTempStorage = new HashMap<>();

        for (int i = 0; i < dayDataList.size(); i++) {
            pomTempStorage.put(dayDataList.get(i).getDate(), dayDataList.get(i).getPomDurationData());
            tasksTempStorage.put(dayDataList.get(i).getDate(), dayDataList.get(i).getTasksDoneData());
        }

        this.init();

        for (int i = 0; i < dayDataList.size(); i++) { // updated dayDatalist
            Date currTempDate = dayDataList.get(i).getDate();
            if (pomTempStorage.containsKey(currTempDate)) {
                PomDurationData tempPomDurationDate = pomTempStorage.get(currTempDate);
                TasksDoneData tempTasksDoneData = tasksTempStorage.get(currTempDate);
                DayData tempDayData = new DayData(currTempDate, tempPomDurationDate, tempTasksDoneData);
                dayDataList.remove(i);
                dayDataList.add(i, tempDayData);
            }
        }

        assert(dayDataList.size() <= MAX_SIZE);

    }

    /**
     * Auto-checks and replaces a new DayData object at the same Date
     * @param dayData
    */
    public void updatesDayData(DayData dayData) {
        requireNonNull(dayData);

        Date currDate = dayData.getDate();
        for (int i = 0; i < dayDataList.size(); i++) {
            DayData currDayData = dayDataList.get(i);
            Date currDayDataDate = currDayData.getDate();
            if (currDayDataDate.equals(currDate)) { // correct date
                dayDataList.remove(i);
                dayDataList.add(i, dayData);
                return;
            }
        }
        assert(false); // dayData not found

    }

    /**
     * Gets DayData object at current date.
     *
     * @param date
     */
    public DayData getDayDataFromDate(Date date) {
        requireNonNull(date);

        for (int i = 0; i < dayDataList.size(); i++) {
            DayData currDayData = dayDataList.get(i);
            Date currDayDataDate = currDayData.getDate();
            if (currDayDataDate.equals(date)) {
                return currDayData;
            }
        }

        return null;
    }

    // FUNCTIONS FOR HARDOHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH end

    /**
     * Returns true if a dayData with the same identity as {@code dayData} exists in the list.
     */
    public boolean hasDayData(DayData dayData) {
        requireNonNull(dayData);
        return dayDataList.contains(dayData);
    }

    /** Adds a dayData to the list. The person must not already exist in the address book. */
    public void addDayData(DayData dayData) {
        dayDataList.add(dayData);
    }

    /**
     * Removes {@code key} from this {@code TaskList}. {@code key} must exist in the address book.
     */
    public void removeTask(DayData dayData) {
        dayDataList.remove(dayData);
    }

    //// util methods

    @Override
    public String toString() {
        return dayDataList.size() + " dayDats";
        // TODO: refine later
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Statistics // instanceof handles nulls
                        && dayDataList.equals(((Statistics) other).dayDataList));
    }

    @Override
    public List<DayData> getDayDataList() {
        return dayDataList;
    }
}

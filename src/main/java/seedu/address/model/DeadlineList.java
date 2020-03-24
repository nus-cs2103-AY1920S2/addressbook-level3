package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.profile.course.module.personal.Deadline;
import seedu.address.model.profile.exceptions.DeadlineNotFoundException;

/**
 * Creates a new DeadlineList object which contains Deadline objects.
 */
public class DeadlineList {

    private ObservableList<Deadline> deadlineList;
    private List<Deadline> deadlines;

    public DeadlineList(List<Deadline> deadlines) {
        this.deadlines = deadlines;
        this.deadlineList = FXCollections.observableArrayList(this.deadlines);
    };

    public DeadlineList() {
    };


    public ObservableList<Deadline> getDeadlineList() {
        return deadlineList; // TODO: Implement read-only version of profileList, similar to address book
    }

    /**
     * Add {@code deadline} to {@code DeadlineList}.
     */
    public void addDeadline(Deadline deadline) {
        requireNonNull(deadline);

        this.deadlineList.add(deadline);
    }

    /**
     * Removes {@code deadline} from this {@code DeadlineList}.
     * deadline object is created with only moduleCode and description. Thus, need to iterate through DeadlineList
     * {@code key} must exist in the profile list.
     */
    public void deleteDeadline(Deadline deadline) {
        requireNonNull(deadline);
        //can implement throw DeadlineNotFoundException

        Iterator<Deadline> iter = this.deadlineList.iterator();
        Boolean flag = false;
        while (iter.hasNext()) {
            Deadline dl = iter.next();
            if (dl.getModuleCode().equals(deadline.getModuleCode())
                    && dl.getDescription().equals(deadline.getDescription())) {
                iter.remove();
                flag = true;
            }
        }

        if (!flag) {
            throw new DeadlineNotFoundException();
        }
    }

    /**
     * Resets the existing data of this {@code DeadlineList} with {@code newData}.
     */
    public void resetData(DeadlineList deadlineList) {
        requireNonNull(deadlineList);
        this.deadlineList.clear();
    }
}


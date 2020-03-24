package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.profile.Name;
import seedu.address.model.profile.Profile;
import seedu.address.model.profile.course.module.personal.Deadline;
import seedu.address.model.profile.exceptions.DuplicatePersonException;
import seedu.address.model.profile.exceptions.PersonNotFoundException;

/**
 * Creates a new ProfileList object which contains Profile objects.
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

    public void addDeadline(Deadline deadline) {
        requireNonNull(deadline);

        this.deadlineList.add(deadline);
    }

    public void deleteDeadline(Deadline deadline) {
        requireNonNull(deadline);
        //can implement throw DeadlineNotFoundException

        Iterator<Deadline> iter = this.deadlineList.iterator();
        while (iter.hasNext()) {
            Deadline dl = iter.next();
            if (dl.getModuleCode().equals(deadline.getModuleCode())
                    && dl.getDescription().equals(deadline.getDescription())) {
                iter.remove();
            }
        }

    }


    public void resetData(DeadlineList deadlineList) {
        requireNonNull(deadlineList);

       this.deadlineList.clear();
    }
}


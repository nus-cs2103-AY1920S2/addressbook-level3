package seedu.address.model.assignment;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Comparator;

public class TitleComparator implements Comparator<Assignment> {
    @Override
    public int compare(Assignment a1, Assignment a2) {
        return a1.getTitle().title.compareTo(a2.getTitle().title);
    }
}

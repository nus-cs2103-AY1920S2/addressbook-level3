package seedu.address.logic.aggregators;

import java.util.ArrayList;

public interface Aggregators<T> {
    public ArrayList<T> collect(ArrayList<T> initialList);
}

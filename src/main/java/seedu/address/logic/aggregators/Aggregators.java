package seedu.address.logic.aggregators;

import java.util.ArrayList;

public interface Aggregators<T, K> {
    public ArrayList<K> collect(ArrayList<T> initialList);
}

package seedu.address.model.person;

import java.util.HashMap;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * An observable list of EventDescriptor objects whose uniqueness is enforced by a
 * HashMap. This allows the list to be displayed and updated instantly on the GUI.
 */
public class FrequencyList {

    private ObservableList<EventDescriptor> frequencyList = FXCollections.observableArrayList();
    private HashMap<String, Integer> uniqueNameList;

    public FrequencyList() {
        this.uniqueNameList = new HashMap<>();
    }

    /**
     * This method adds a place or an activity to a given frequency list,
     * assuming that it does not exist already. Otherwise, it increments
     * the frequency of that place or activity by 1.
     * @param name The name of the place or activity to be added.
     */
    public void add(String name) {
        if (uniqueNameList.containsKey(name)) {
            Integer oldValue = uniqueNameList.get(name);
            int listIndex = frequencyList.indexOf(new EventDescriptor(name, oldValue));
            frequencyList.get(listIndex).increment();
            uniqueNameList.replace(name, oldValue, oldValue + 1);
        } else {
            uniqueNameList.put(name, 1);
            frequencyList.add(new EventDescriptor(name));
        }
        frequencyList.sort(new DescriptorComparator());
    }

    /**
     * Generates the items in the Frequency List from an ArrayList.
     * @param items List of items to be added.
     */
    public void generate(List<String> items) {
        for (String item : items) {
            this.add(item);
        }
    }

    public ObservableList<EventDescriptor> getFrequencyList() {
        return frequencyList;
    }

    public HashMap<String, Integer> getMap() {
        return uniqueNameList;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FrequencyList)) {
            return false;
        }
        return ((FrequencyList) o).getFrequencyList().equals(this.getFrequencyList())
                && ((FrequencyList) o).getMap().equals(this.getMap());
    }
}

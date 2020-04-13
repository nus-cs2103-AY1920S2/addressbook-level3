package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;

/**
 * A list of places
 */
public class PlaceList {
    public final List<String> placeList;

    public PlaceList(List<String> places) {
        this.placeList = places;
    }

    public List<String> getPlaceList() {
        return this.placeList;
    }


    /**
     * Adds a place to the current placeList
     * @return new PlaceList
     */
    public PlaceList addPlace(String place) {
        List<String> current = this.placeList;
        current.add(place);
        return new PlaceList(current);
    }

    public void setPlaceList(List<String> oldList) {
        List<String> newList = new ArrayList<>();
        newList.addAll(this.placeList);
        newList.addAll(oldList);
        this.placeList.clear();
        this.placeList.addAll(newList);
    }

    @Override
    public String toString() {
        String output = "";
        int i = 0;
        for (String s : placeList) {
            if (i < placeList.size() - 1) {
                output += s + ", ";
            } else {
                output += s;
            }
        }
        return output;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PlaceList)) {
            return false;
        }

        PlaceList p = (PlaceList) other;
        return this.placeList.equals(p.placeList);
    }

    @Override
    public int hashCode() {
        return placeList.hashCode();
    }
}

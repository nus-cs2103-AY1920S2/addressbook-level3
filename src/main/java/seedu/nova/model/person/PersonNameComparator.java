package seedu.nova.model.person;

import java.util.Comparator;

/**
 * Comparator to compare a person's name alphabetically and sort them in alphabetical order.
 */
public class PersonNameComparator implements Comparator<Person> {

    @Override
    public int compare(Person p1, Person p2) {
        return p1.getName().toString().compareToIgnoreCase(p2.getName().toString());
    }
}

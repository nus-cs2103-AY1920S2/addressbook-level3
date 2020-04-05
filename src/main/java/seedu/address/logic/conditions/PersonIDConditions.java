package seedu.address.logic.conditions;

import seedu.address.model.bluetooth.Person;

public class PersonIDConditions implements Conditions<Person> {
    private int personId;

    public PersonIDConditions(int personId) {
        this.personId  = personId;
    }

    @Override
    public Boolean satisfies(Person objToTest) {
        return this.personId == objToTest.getUserID();
    }
}

package seedu.address.storage;

import seedu.address.model.bluetooth.Person;
import java.util.ArrayList;

public class UserStorage extends InMemoryStorage<Person> {
    public UserStorage() {
        super();
        this.init();
    }

    /**
     * TODO: Would be nice if can read from JSON file to initialize fake data
     * @return
     */
    private ArrayList<Person> genFakeData() {
        ArrayList<Person> fakeStorage = new ArrayList<Person>();
        fakeStorage.add(new Person("John Doe", 1).withAge(25).withMobile("9991").withAddress("NUS"));
        fakeStorage.add(new Person("John Roe", 1).withAge(26).withMobile("9992").withAddress("NUS"));
        fakeStorage.add(new Person("John Snow", 1).withAge(27).withMobile("9939").withAddress("NUS"));
        fakeStorage.add(new Person("John Lim", 1).withAge(28).withMobile("9994").withAddress("NUS"));
        return fakeStorage;
    }

    public void init() {
        this.fakeStorage = this.genFakeData();
    }
}

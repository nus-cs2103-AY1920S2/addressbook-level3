package seedu.address.storage;

import seedu.address.model.bluetooth.Person;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class PersonStorage extends InMemoryStorage<Person> {
    private final AtomicInteger counter = new AtomicInteger(1);

    public PersonStorage() {
        super();
        this.init();
    }

    /**
     * TODO: Would be nice if can read from JSON file to initialize fake data
     * @return
     */
    private ArrayList<Person> genFakeData() {
        ArrayList<Person> fakeStorage = new ArrayList<Person>();
        fakeStorage.add(new Person("John Doe").withUserId(counter.getAndAdd(1)).withNric("S91111111Q")
                                                    .withAge(25).withMobile("9991"));

        fakeStorage.add(new Person("John Roe").withUserId(counter.getAndAdd(1)).withNric("S92222222J")
                                                    .withAge(26).withMobile("9992"));

        fakeStorage.add(new Person("John Snow").withUserId(counter.getAndAdd(1)).withNric("S93333333K")
                                                    .withAge(27).withMobile("9939"));

        fakeStorage.add(new Person("John Lim").withUserId(counter.getAndAdd(1)).withNric("S94444444L")
                                                    .withAge(28).withMobile("9994"));
        return fakeStorage;
    }

    /**
     * Method injects a unique user ID into a created {@code Person} object
     * This simulates a database with user id as unique ID
     *
     * @param   obj            Person object without user id
     * @throws  Exception
     */
    @Override
    public void create(Person obj) throws Exception {
        Person objWithUserId = obj.withUserId(counter.getAndAdd(1));
        super.create(objWithUserId);
    }

    public void init() {
        this.fakeStorage = this.genFakeData();
    }
}

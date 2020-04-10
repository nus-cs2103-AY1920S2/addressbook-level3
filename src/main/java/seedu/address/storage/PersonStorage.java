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

        fakeStorage.add(new Person("John Roe").withUserId(counter.getAndAdd(2)).withNric("S92222222J")
                                                    .withAge(26).withMobile("9992"));

        fakeStorage.add(new Person("John Snow").withUserId(counter.getAndAdd(3)).withNric("S93333333K")
                                                    .withAge(27).withMobile("9939"));

        fakeStorage.add(new Person("John Lim").withUserId(counter.getAndAdd(4)).withNric("S94444444L")
                                                    .withAge(28).withMobile("9994"));

        fakeStorage.add(new Person("Adam Smith").withUserId(counter.getAndAdd(5)).withNric("B82222222M")
                                                    .withAge(24).withMobile("9980"));

        fakeStorage.add(new Person("Aidan Johnson").withUserId(counter.getAndAdd(6)).withNric("D2222222L")
                                                    .withAge(29).withMobile("9987"));

        fakeStorage.add(new Person("Anthony Williams").withUserId(counter.getAndAdd(7)).withNric("L10000000C")
                                                    .withAge(32).withMobile("9983"));

        fakeStorage.add(new Person("Bob Jones").withUserId(counter.getAndAdd(8)).withNric("S93333333A")
                                                    .withAge(31).withMobile("9980"));

        fakeStorage.add(new Person("Brown Miller").withUserId(counter.getAndAdd(9)).withNric("R01111111V")
                                                    .withAge(30).withMobile("9979"));

        fakeStorage.add(new Person("Brandon Davis").withUserId(counter.getAndAdd(10)).withNric("X69999999A")
                                                    .withAge(20).withMobile("9975"));

        fakeStorage.add(new Person("Caspar Wilson").withUserId(counter.getAndAdd(11)).withNric("P03333333C")
                                                    .withAge(21).withMobile("9971"));

        fakeStorage.add(new Person("Christian Anderson").withUserId(counter.getAndAdd(12)).withNric("A21111111K")
                                                    .withAge(29).withMobile("9976"));

        fakeStorage.add(new Person("Clark Taylor").withUserId(counter.getAndAdd(13)).withNric("P34444444C")
                                                    .withAge(24).withMobile("9973"));

        fakeStorage.add(new Person("Cody Thompson").withUserId(counter.getAndAdd(14)).withNric("C67777777Z")
                                                    .withAge(29).withMobile("9970"));

        fakeStorage.add(new Person("Colin Moore").withUserId(counter.getAndAdd(15)).withNric("Q53333333I")
                                                    .withAge(30).withMobile("9969"));

        fakeStorage.add(new Person("Darwin Williams").withUserId(counter.getAndAdd(16)).withNric("D78888888C")
                                                    .withAge(31).withMobile("9963"));

        fakeStorage.add(new Person("Dick Anderson").withUserId(counter.getAndAdd(17)).withNric("L93333333C")
                                                    .withAge(27).withMobile("9960"));

        fakeStorage.add(new Person("Paul Miller").withUserId(counter.getAndAdd(18)).withNric("V50000000L")
                                                    .withAge(12).withMobile("9954"));

        fakeStorage.add(new Person("Dennis Brown").withUserId(counter.getAndAdd(19)).withNric("S61111111F")
                                                    .withAge(22).withMobile("9951"));

        fakeStorage.add(new Person("Duke Jackson").withUserId(counter.getAndAdd(20)).withNric("C54444444X")
                                                    .withAge(30).withMobile("9950"));

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

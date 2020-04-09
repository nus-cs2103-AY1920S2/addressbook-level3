package seedu.address.model.bluetooth;

public class Person {
    private String name;
    private int age;
    private int userID;

    public Person(String name, int age, int userID) {
        this.name   = name;
        this.age    = age;
        this.userID = userID;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public int getUserID() {
        return this.userID;
    }
}

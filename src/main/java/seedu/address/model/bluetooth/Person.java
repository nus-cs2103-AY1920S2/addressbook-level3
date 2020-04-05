package seedu.address.model.bluetooth;

public class Person {
    private String name;
    private String address;
    private String mobile;
    private int age;
    private int userID;

    public Person(String name, int userID) {
        this.name   = name;
        this.userID = userID;
    }

    public Person withMobile(String number) {
        this.mobile = number;
        return this;
    }

    public Person withAddress(String address) {
        this.address = address;
        return this;
    }

    public Person withAge(int age) {
        this.age = age;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public String getAddress() { return this.address; }

    public String getMobile() { return this.mobile; }

    public int getAge() {
        return this.age;
    }

    public int getUserID() {
        return this.userID;
    }
}

package seedu.address.model.bluetooth;

public class Person {
    private int userId;
    private String name;
    private String mobile;
    private String nric;
    private int age;

    public Person(String name) {
        this.name   = name;
    }

    public Person withUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public Person withNric(String nric) {
        this.nric   = nric;
        return this;
    }

    public Person withMobile(String number) {
        this.mobile = number;
        return this;
    }

    public Person withAge(int age) {
        this.age = age;
        return this;
    }

    public int getUserId() { return this.userId; }

    public String getName() {
        return this.name;
    }

    public String getNric() {
        return this.nric;
    }

    public String getMobile() { return this.mobile; }

    public int getAge() {
        return this.age;
    }

    public Boolean equals(Person obj) {
        return this.nric.equals(obj.nric);
    }
}

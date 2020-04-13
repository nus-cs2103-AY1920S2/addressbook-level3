package seedu.address.model.util;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyWallet;
import seedu.address.model.Wallet;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Date;
import seedu.address.model.transaction.Debt;
import seedu.address.model.transaction.Description;
import seedu.address.model.transaction.Expense;
import seedu.address.model.transaction.Income;
import seedu.address.model.transaction.Loan;
import seedu.address.model.transaction.TransactionList;


/**
 * Contains utility methods for populating {@code AddressBook} and {@code Wallet} with sample data.
 */
public class SampleDataUtil {

    public static Person[] getSamplePersons() {
        return new Person[]{
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    getDebtList(new Debt(new Description("Supper"), new Amount(5),
                            new Date(LocalDate.parse("2020-02-03")))),
                    getLoanList(new Loan(new Description("Breakfast"), new Amount(3),
                            new Date(LocalDate.parse("2018-08-08")))),
                    getTagSet("Debt", "Loan")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    getDebtList(new Debt(new Description("Shopping"), new Amount(102),
                            new Date(LocalDate.parse("2020-01-01")))),
                    getLoanList(new Loan(new Description("Lunch"), new Amount(12),
                                    new Date(LocalDate.parse("2019-09-09"))),
                            new Loan(new Description("Movie"), new Amount(10),
                                    new Date(LocalDate.parse("2020-02-02")))),
                    getTagSet("Debt", "Loan")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    getDebtList(new Debt(new Description("Gift for someone"), new Amount(10),
                            new Date(LocalDate.parse("2019-12-12"))),
                            new Debt(new Description("Movie"), new Amount(13),
                            new Date(LocalDate.parse("2019-12-10")))),
                    getLoanList(new Loan(new Description("Shopping"), new Amount(150),
                            new Date(LocalDate.parse("2018-08-18")))),
                    getTagSet("Debt", "Loan")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    getDebtList(),
                    getLoanList(new Loan(new Description("Supper"), new Amount(8),
                            new Date(LocalDate.parse("2020-02-24")))),
                    getTagSet("Loan")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    getDebtList(), getLoanList(), getTagSet()),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    getDebtList(), getLoanList(), getTagSet())
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    public static Expense[] getSampleExpenses() {
        return new Expense[]{
            new Expense(new Description("Laksa"), new Amount(8),
                    new Date(LocalDate.parse("2020-04-16")), new Tag("Food")),
            new Expense(new Description("Nikes"), new Amount(250),
                    new Date(LocalDate.parse("2020-04-02")), new Tag("Fashion")),
            new Expense(new Description("Top up ezlink"), new Amount(50),
                    new Date(LocalDate.parse("2020-04-03")), new Tag("Transport")),
            new Expense(new Description("Grab"), new Amount(30),
                    new Date(LocalDate.parse("2020-04-15")), new Tag("Transport")),
            new Expense(new Description("Water bill"), new Amount(170),
                    new Date(LocalDate.parse("2020-04-01")), new Tag("Utilities")),
            new Expense(new Description("Rental"), new Amount(50),
                    new Date(LocalDate.parse("2020-04-09")), new Tag("Rental"))
        };
    }

    public static Income[] getSampleIncomes() {
        return new Income[]{
            new Income(new Description("Tutoring"), new Amount(300),
                    new Date(LocalDate.parse("2020-01-01")), new Tag("Job")),
            new Income(new Description("TA"), new Amount(100),
                    new Date(LocalDate.parse("2020-02-02")), new Tag("Job")),
            new Income(new Description("Allowance"), new Amount(200),
                    new Date(LocalDate.parse("2020-03-03")), new Tag("Allowance"))
        };
    }

    public static ReadOnlyWallet getSampleWallet() {
        Wallet sampleWallet = new Wallet();

        for (Income sampleIncome : getSampleIncomes()) {
            sampleWallet.addIncome(sampleIncome);
        }

        for (Expense sampleExpense : getSampleExpenses()) {
            sampleWallet.addExpense(sampleExpense);
        }

        return sampleWallet;
    }

    // @@author cheyannesim
    /**
     * Returns a list of debts containing the {@code debts} given.
     */
    public static TransactionList<Debt> getDebtList(Debt... debts) {
        List<Debt> list = Arrays.stream(debts)
                .collect(Collectors.toList());
        TransactionList<Debt> debtList = new TransactionList<>();
        debtList.setTransactions(list);
        return debtList;
    }
    // @@author

    /**
     * Returns a list of loans containing the {@code loanss} given.
     */
    public static TransactionList<Loan> getLoanList(Loan... loans) {
        List<Loan> list = Arrays.stream(loans)
                .collect(Collectors.toList());
        TransactionList<Loan> loanList = new TransactionList<>();
        loanList.setTransactions(list);
        return loanList;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}

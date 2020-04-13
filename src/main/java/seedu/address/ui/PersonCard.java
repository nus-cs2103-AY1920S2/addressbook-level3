package seedu.address.ui;

import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Debt;
import seedu.address.model.transaction.Description;
import seedu.address.model.transaction.Loan;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private TableView<Debt> debts;
    @FXML
    private TableColumn<Debt, Description> debtDesc;
    @FXML
    private TableColumn<Debt, Amount> debtAmt;
    @FXML
    private TableColumn<Debt, LocalDate> debtDate;
    @FXML
    private TableView<Loan> loans;
    @FXML
    private TableColumn<Loan, Description> loanDesc;
    @FXML
    private TableColumn<Loan, Amount> loanAmt;
    @FXML
    private TableColumn<Loan, LocalDate> loanDate;

    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        email.setText(person.getEmail().value);
        debts.setPlaceholder(
                new PersonTablePanel<Debt>(person.getDebts().asUnmodifiableObservableList()).getRoot());
        loans.setPlaceholder(
                new PersonTablePanel<Loan>(person.getLoans().asUnmodifiableObservableList()).getRoot());
        setProperties();
    }

    private void setProperties() {
        TableColumn<Debt, String> debtTitle = new TableColumn<>("Debts");
        debts.getColumns().add(debtTitle);

        TableColumn<Loan, String> loanTitle = new TableColumn<>("Loans");
        loans.getColumns().add(loanTitle);

    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }
}

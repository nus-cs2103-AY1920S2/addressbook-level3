package seedu.address.model.event.consult;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.student.Student;

/**
 * Wraps all consult data at the Consult TAble level
 * Duplicates are not allowed
 */
public class ConsultTAble implements ReadOnlyConsult {

    private final UniqueConsultList consults;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        consults = new UniqueConsultList();
    }

    public ConsultTAble() {}

    /**
     * Creates a ConsultTAble using the consults in the {@code toBeCopied}
     */
    public ConsultTAble(ReadOnlyConsult toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
      * Creates a ConsultTAble using the consults in the {@code consultList}
     */
    public ConsultTAble(List<Consult> consultList) {
        this.consults.setConsults(consultList);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the consult list with {@code consults}.
     * {@code consults} must not contain duplicate consults.
     */
    public void setConsults(List<Consult> consults) {
        this.consults.setConsults(consults);
    }

    /**
     * Resets the existing data of this {@code ConsultTAble} with {@code newData}.
     */
    public void resetData(ReadOnlyConsult newData) {
        requireNonNull(newData);

        setConsults(newData.getAllConsults());
    }

    //// Consult-level operations

    /**
     * Returns true if a consult with the same identity as {@code consult} exists in TAble.
     */
    public boolean hasConsult(Consult consult) {
        requireNonNull(consult);
        return consults.contains(consult);
    }

    /**
     * Adds a consult to TAble.
     * The consult must not already exist in TAble.
     */
    public void addConsult(Consult consult) {
        consults.add(consult);
    }

    /**
     * Replaces the given consult {@code target} in the list with {@code editedConsult}.
     * {@code target} must exist in the ConsultTAble.
     * The consult identity of {@code editedConsult} must not be the same
     * as another existing student in the ConsultTAble.
     */
    public void setConsult(Consult target, Consult editedConsult) {
        requireNonNull(editedConsult);

        consults.setConsult(target, editedConsult);
    }

    /**
     * Removes {@code key} from this {@code ConsultTAble}.
     * {@code key} must exist in the ConsultTAble.
     */
    public void removeConsult(Consult key) {
        consults.remove(key);
    }


    /**
     * Returns {@code consult} from {@code TAble}.
     * {@code consult} must exist in ConsultTAble.
     */
    public Consult getConsult(int index) {
        return consults.getConsult(index);
    }

    /**
     * Removes all {@code consults} from this {@code ConsultTAble}.
     */
    public void clearConsults() {
        consults.clearConsults();
    }

    /**
     * Replaces the student identity in the given consult {@code target} with the identity of the
     * edited student {@code editedStuden}.
     * {@code target} must exist in the ConsultTAble.
     */
    public void editConsultStudent(Consult target, Student editedStudent) {
        consults.editConsultStudent(target, editedStudent);
    }

    //// util methods

    @Override
    public String toString() {
        return consults.asUnmodifiableObservableList().size() + " consults";
        // TODO: refine later
    }

    @Override
    public ObservableList<Consult> getAllConsults() {
        return consults.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ConsultTAble // instanceof handles nulls
                && consults.equals(((ConsultTAble) other).consults));
    }

    @Override
    public int hashCode() {
        return consults.hashCode();
    }

    public boolean hasSameDateTime(Consult consult) {
        return consults.hasSameDateTime(consult);
    }

    public boolean hasSameDateTimeEdit(Consult consult) {
        return consults.hasSameDateTimeEdit(consult);
    }
}

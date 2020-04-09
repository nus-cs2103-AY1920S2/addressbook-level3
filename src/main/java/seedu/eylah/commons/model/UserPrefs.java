package seedu.eylah.commons.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private Path myselfFilePath = Paths.get("data" , "myself.json");
    private Path foodBookFilePath = Paths.get("data" , "foodbook.json");
    private Path personAmountBookFilePath = Paths.get("data", "personamount.json");
    private Path receiptBookFilePath = Paths.get("data", "receiptbook.json");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {}

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setMyselfFilePath(newUserPrefs.getMyselfFilePath());
        setFoodBookFilePath(newUserPrefs.getFoodBookFilePath());
        setPersonAmountBookFilePath(newUserPrefs.getPersonAmountBookFilePath());
        setReceiptBookFilePath(newUserPrefs.getReceiptBookFilePath());
    }

    @Override
    public Path getMyselfFilePath() {
        return myselfFilePath;
    }

    @Override
    public void setMyselfFilePath(Path myselfFilePath) {
        requireNonNull(myselfFilePath);
        this.myselfFilePath = myselfFilePath;
    }

    /**
     * Returns the FoodBook file path.
     *
     * @return FoodBook file path
     */
    public Path getFoodBookFilePath() {
        return foodBookFilePath;
    }

    /**
     * Set the FoodBook file path based on the given path.
     *
     * @param foodBookFilePath the given file path.
     */
    public void setFoodBookFilePath(Path foodBookFilePath) {
        requireNonNull(foodBookFilePath);
        this.foodBookFilePath = foodBookFilePath;
    }

    /**
     * Returns the PersonAmount file path.
     *
     * @return PersonAmount file path
     */
    public Path getPersonAmountBookFilePath() {
        return personAmountBookFilePath;
    }

    /**
     * Set the Person Amount file path based on the given path.
     *
     * @param personAmountFilePath the given file path.
     */
    public void setPersonAmountBookFilePath(Path personAmountFilePath) {
        requireNonNull(personAmountFilePath);
        this.personAmountBookFilePath = personAmountFilePath;
    }

    /**
     * Returns the Receipt file path.
     *
     * @return Receipt file path
     */
    public Path getReceiptBookFilePath() {
        return receiptBookFilePath;
    }

    /**
     * Set the Receipt file path based on the given path.
     *
     * @param receiptBookFilePath the given file path.
     */
    public void setReceiptBookFilePath(Path receiptBookFilePath) {
        requireNonNull(receiptBookFilePath);
        this.receiptBookFilePath = receiptBookFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return myselfFilePath.equals(o.myselfFilePath) && foodBookFilePath.equals(o.foodBookFilePath)
                && personAmountBookFilePath.equals(o.personAmountBookFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myselfFilePath, foodBookFilePath, personAmountBookFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Local data file location : %s %s %s", myselfFilePath, foodBookFilePath,
                personAmountBookFilePath));
        return sb.toString();
    }
}

package seedu.eylah.commons.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class UserPrefs implements ReadOnlyUserPrefs{

    private Path foodBookFilePath = Paths.get("data" , "foodbook.json");
    private Path personAmountFilePath = Paths.get("data", "personamount.json");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {}

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(UserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setFoodBookFilePath(newUserPrefs.getFoodBookFilePath());
        setPersonAmountFilePath(newUserPrefs.getPersonAmountFilePath());
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
    public Path getPersonAmountFilePath() {
        return personAmountFilePath;
    }

    /**
     * Set the Person Amount file path based on the given path.
     *
     * @param personAmountFilePath the given file path.
     */
    public void setPersonAmountFilePath(Path personAmountFilePath) {
        requireNonNull(personAmountFilePath);
        this.personAmountFilePath = personAmountFilePath;
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

        return foodBookFilePath.equals(o.foodBookFilePath)
                && personAmountFilePath.equals(o.personAmountFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(foodBookFilePath, personAmountFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Local data file location : %s %s", foodBookFilePath, personAmountFilePath));
        return sb.toString();
    }
}

package seedu.address.logic.autocomplete;

/**
 * This is a wrapper class to hold the information returned by
 * {@code Autocomplete#execute}. The member {@code textToSet} and
 * {@code caretPositionToSet} will contain the text and caret position to set
 * for the {@code CommandBox#commandTextField} respectively. The member
 * {@code textToFeedback} will contain the text to set for the
 * {@code CommandBox#resultDisplay}. Null members indicate that they should not
 * be set or used by.
 */
public class AutocompleteResult {
    private final String textToSet;
    private final String textToFeedback;
    private final Integer caretPositionToSet;

    /**
     * Default constructor for this class. Note that {@code null} values can be used
     * to denote that the field is empty.
     */
    public AutocompleteResult(String textToSet, String textToFeedback, Integer caretPositionToSet) {
        this.textToSet = textToSet;
        this.textToFeedback = textToFeedback;
        this.caretPositionToSet = caretPositionToSet;
    }

    public String getTextToSet() {
        return textToSet;
    }

    public String getTextToFeedback() {
        return textToFeedback;
    }

    public Integer getCaretPositionToSet() {
        return caretPositionToSet;
    }

}

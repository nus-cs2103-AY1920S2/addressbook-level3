//@@author potatocombat

package tatracker.ui;

/**
 * Ensure that a custom UI element can be focused on.
 */
public interface Focusable {

    /**
     * Sets the focus on this UI element.
     */
    void requestFocus();

    /**
     * Returns true if the focus is on this UI element.
     */
    boolean isFocused();
}

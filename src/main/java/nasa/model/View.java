package nasa.model;

/**
 * Enum for views in NASA.
 */
public enum View {

    CALENDAR, MODULES, STATISTICS;

    public static final String MESSAGE_CONSTRAINTS =
            "Valid views are Modules, Calendar, Statistics.";

    /**
     * Checks if String corresponds to valid enum.
     * @param aName input to be checked
     * @return true if name is valid.
     */
    public static boolean isValidView(String aName) {
        View[] aViews = View.values();
        for (View aView : aViews) {
            if (aView.toString().equals(aName)) {
                return true;
            }
        }
        return false;
    }
}

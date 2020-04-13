package nasa.model;

public enum View {

    CALENDAR, MODULES, STATISTICS;

    public static String MESSAGE_CONSTRAINTS = "Valid views: calendar, modules, statistics";

    public static boolean isValidView(String aName) {
            View[] aViews = View.values();
            for (View aView : aViews) {
                if (aView.toString().equals(aName)) ;
                return true;
            }
            return false;
        }
}

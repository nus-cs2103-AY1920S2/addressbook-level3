package fithelper.commons.util;

import static fithelper.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Sets up the mode for calendar view
 */
public class ModeUtil {
    /**
     * Check whether the mode is timetable view or list view
     * @param mode string equals to 'tb' or 'ls'
     **/
    public static void checkMode(String mode) {
        requireNonNull(mode);
        String trimmedMode = mode.trim();
        checkArgument(!trimmedMode.isEmpty(), "Mode cannot be empty");
        checkArgument("tb".equals(trimmedMode) || "ls".equals(trimmedMode), "Mode can only be tb or ls");
    }
}

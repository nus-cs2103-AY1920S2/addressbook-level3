package nasa.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    public static final byte[] EMPTY_BYTE_ARRAY_DATA;

    static {
        EMPTY_BYTE_ARRAY_DATA = new byte[0];
    }

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /** The application should show statistics. */
    private final boolean modules;

    /** The application should show statistics. */
    private final boolean calendar;

    /** The application should show statistics. */
    private final boolean statistics;

    /** Qr code should be shown to the user. */
    private final boolean showQr;

    /** Qr code data to show */
    private final byte[] qrData;

    /**
     * The application should show quote.
     */
    private boolean quote;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     * @param feedbackToUser String
     * @param showHelp boolean
     * @param exit boolean
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit,
                         boolean modules, boolean calendar, boolean statistics, boolean showQr,
                         byte[] qrData) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.modules = modules;
        this.calendar = calendar;
        this.statistics = statistics;
        this.quote = false;
        this.showQr = showQr;
        this.qrData = qrData;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     * @param feedbackToUser String
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false, false, false, false, EMPTY_BYTE_ARRAY_DATA);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public byte[] getQrData() {
        return qrData;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isModules() {
        return modules;
    }

    public boolean isCalendar() {
        return calendar;
    }

    public boolean isStatistics() {
        return statistics;
    }

    public boolean isQuote() {
        return quote;
    }

    public boolean isShowQr() {
        return showQr;
    }

    /**
     * Make quote property true.
     */
    public void setQuote() {
        this.quote = true;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
            && showHelp == otherCommandResult.showHelp
            && exit == otherCommandResult.exit
            && showQr == otherCommandResult.showQr;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, showQr);
    }

}

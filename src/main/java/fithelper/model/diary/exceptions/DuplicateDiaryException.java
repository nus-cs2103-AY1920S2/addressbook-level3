package fithelper.model.diary.exceptions;

/**
 * Signals that the operation will result in duplicate Diaries (Diaries are considered duplicates if they have the same
 * identity).
 */
public class DuplicateDiaryException extends RuntimeException {
    public DuplicateDiaryException() {
        super("Operation would result in duplicate diaries");
    }
}

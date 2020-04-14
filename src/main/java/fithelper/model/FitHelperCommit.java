package fithelper.model;

/**
 * Represents a commit, including a FitHelper state and a commit message.
 */
public class FitHelperCommit {
    /**
     * The state of FitHelper to commit.
     */
    public final ReadOnlyFitHelper fitHelper;

    /**
     * A message describing the details of the commit.
     */
    public final String commitMessage;

    /**
     * Creates a FitHelper commit.
     */
    public FitHelperCommit(ReadOnlyFitHelper fitHelper, String commitMessage) {
        this.fitHelper = fitHelper;
        this.commitMessage = commitMessage;
    }

}

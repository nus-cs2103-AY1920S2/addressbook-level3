package seedu.jelphabot.model.task.tasklist;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;

public class PinnedTaskListTest {

    /**
     * A stub PinnedTaskList with empty arguments.
     */
    public static class PinnedTaskListStub extends PinnedTaskList {

        /**
         * Constructor for use in testing
         */
        public PinnedTaskListStub() {
            super(FXCollections.emptyObservableList(), Bindings.createIntegerBinding(() -> 0));
        }
    }

}

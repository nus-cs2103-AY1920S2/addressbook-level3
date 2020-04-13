package seedu.jelphabot.model.task.tasklist;

// @@author yaojiethng
class GroupedByDateTaskListTest {

    private final GroupedByDateTaskList dateTaskList =
        makeEmptyDateTaskList();

    private static GroupedByDateTaskList makeEmptyDateTaskList() {
        return new GroupedByDateTaskList(new PinnedTaskListTest.PinnedTaskListStub());
    }
}

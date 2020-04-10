package nasa.testutil;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import nasa.model.HistoryBook;
import nasa.model.ReadOnlyHistory;
import nasa.model.history.ModuleListHistory;

/**
 * Constructor for UiHistoryBuilder.
 */
public class UiHistoryBuilder {

    private static String atMainScreen = "null";
    private static String atActivityScreen = "deadline activity Hello GG HELLO WORLD";


    private List<String> history = new ArrayList<>();

    public UiHistoryBuilder() {
        history.add(atMainScreen);
        history.add(atActivityScreen);
    }

    /**
     * Create a new UI History Book for testing purpose.
     */
    public ReadOnlyHistory<String> build() {
        Stack<String> stack = new Stack<>();
        stack.addAll(history);
        ModuleListHistory<String> listHistory = new ModuleListHistory<>(stack);
        HistoryBook<String> book = new HistoryBook<>();
        book.setModuleListHistory(listHistory);
        return book;
    }
}

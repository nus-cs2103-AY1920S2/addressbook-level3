package nasa.testutil;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import nasa.model.HistoryBook;
import nasa.model.ReadOnlyHistory;
import nasa.model.history.ModuleListHistory;

public class UiHistoryBuilder {

    public static String atMainScreen = "null";
    public static String atActivityScreen = "deadline activity Hello GG HELLO WORLD";


    List<String> history = new ArrayList<>();

    public UiHistoryBuilder() {
        history.add(atMainScreen);
        history.add(atActivityScreen);
    }

    public ReadOnlyHistory<String> build() {
        Stack<String> stack = new Stack<>();
        stack.addAll(history);
        ModuleListHistory<String> listHistory = new ModuleListHistory<>(stack);
        HistoryBook<String> book = new HistoryBook<>();
        book.setModuleListHistory(listHistory);
        return book;
    }
}

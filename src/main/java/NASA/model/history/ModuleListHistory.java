package nasa.model.history;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nasa.model.activity.Activity;

public class ModuleListHistory<T> extends History<T>{

    public ModuleListHistory() {
        super();
    }

    public ModuleListHistory(Stack<T> moduleListStack) {
        super(moduleListStack);
    }

    public void setStackList(List<T> moduleListHistory) {
        super.setStack(moduleListHistory);
    }

    public ObservableList<T> asUnmodifiableObservableList() {
        List<T> list = new ArrayList<>(super.getStack());
        ObservableList<T> newList = FXCollections.observableArrayList(list);
        return newList;
    }

}

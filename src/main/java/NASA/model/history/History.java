package nasa.model.history;

import java.util.Collections;
import java.util.List;
import java.util.Stack;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nasa.logic.commands.Command;
import nasa.model.activity.Activity;
import nasa.model.module.Module;
import nasa.model.module.UniqueModuleList;

public abstract class History<T> {

    private Stack<T> stack;

    private T item;

    History() {
        item = null;
        stack = new Stack<T>();
    }

    History(Stack<T> history) {
        item = null;
        stack = history;
    }

    public void push(T item) {
        if (this.item != null) {
            stack.push(this.item);
        }
        this.item = item;
    }

    public boolean pop() {
        if (!isEmpty()) {
            item = stack.pop();
            return true;
        } else {
            return false;
        }
    }

    public T getPop() {
        return item;
    }

    public void setStack(List<T> stack) {
        this.stack.addAll(stack);
    }

    public Stack<T> getStack() {
        return stack;
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

}

package seedu.zerotoone.commons.util;

public class Pair<T, U> {
    private T firstObject;
    private U secondObject;

    public Pair(T firstObject, U secondObject) {
        this.firstObject = firstObject;
        this.secondObject = secondObject;
    }

    public T getFirstObject() {
        return this.firstObject;
    }

    public U getSecondObject() {
        return this.secondObject;
    }
}
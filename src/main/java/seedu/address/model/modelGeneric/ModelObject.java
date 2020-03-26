package seedu.address.model.modelGeneric;

public interface ModelObject {

    // We are overloading, not really overriding
    public boolean weakEquals(ModelObject other);

    public static RuntimeException getNotFoundException() {
        return new RuntimeException();
    };

    public static RuntimeException getDuplicateException() {
        return new RuntimeException();
    }
}

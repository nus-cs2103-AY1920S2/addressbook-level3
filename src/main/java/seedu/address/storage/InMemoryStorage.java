package seedu.address.storage;

import seedu.address.logic.aggregators.Aggregators;
import seedu.address.logic.conditions.Conditions;
import seedu.address.logic.conditions.LiterallyNoConditions;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Storage is maintained in memory
 * All transactions are done via memory
 *
 * This does not work for large scale data but suffices for this project
 *
 * @param <T>
 */
public abstract class InMemoryStorage<T> implements AppStorage<T> {
    protected ArrayList<T> fakeStorage;

    @Override
    public void delete(T templateClass) {
        // TODO: Write some code here
    }

    @Override
    public void create(T templateClass) {
        // TODO: Write some code here
    }

    @Override
    public void update(T oldTemplateClass, T newTemplateClass) {
        // TODO: write some code here
    }

    @Override
    public ArrayList<T> search(Conditions cond, Aggregators agg) {
        ArrayList<T> collection = agg.collect(this.fakeStorage);

        return collection.stream()
                        .filter(each -> cond.satisfies(each))
                        .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ArrayList<T> search(Conditions cond) {
        return  this.fakeStorage.stream()
                                .filter(each -> cond.satisfies(each))
                                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ArrayList<T> search() {
        Conditions<T> cond = new LiterallyNoConditions<T>();
        return this.search(cond);
    }
}

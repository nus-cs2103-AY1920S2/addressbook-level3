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
    public ArrayList<T> search(T obj) {
        return this.fakeStorage.stream()
                                .filter(each -> each.equals(obj))
                                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public void create(T obj) throws Exception {
        ArrayList<T> duplicates = this.search(obj);
        if (duplicates.size() > 0) {
            throw new Exception("Person exists in database");
        }
        this.fakeStorage.add(obj);
    }

    @Override
    public void update(T objPast, T objNew) {
        // TODO: write some code here
    }

    @Override
    public void delete(T obj) {
        // TODO: Write some code here
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

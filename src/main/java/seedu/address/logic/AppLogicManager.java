package seedu.address.logic;

import javafx.collections.ObservableList;
import seedu.address.logic.conditions.Conditions;
import seedu.address.storage.AppStorage;

public class AppLogicManager<T, M> implements AppLogic<T> {
    private AppStorage dao;

    /**
     * Asserts that the user must always declare type M which is subclass of {@code AppStorage}
     *
     * @param   dao        Data access object implementation
     * @throws  Exception
     */
    public AppLogicManager(Class<M> dao) throws Exception {
        M tmpObj = dao.getDeclaredConstructor().newInstance();
        assert (tmpObj instanceof AppStorage);
        this.dao = (AppStorage) tmpObj;
    }

    /**
     * Fetches all pings from the database
     *
     * @return  ObservableList<T>   List of all pings for rendering to UI
     */
    @Override
    public ObservableList<T> getPings() {
        return null;
    }

    /**
     * Fetches all pings that satisfies a conditional filter
     *
     * @param   cond                {@code seedu.address.conditions.Conditions Class} Conditional class
     * @return  ObservableList<T>   List of all pings in condition for rendering to UI
     */
    @Override
    public ObservableList<T> filterPings(Conditions<T> cond) {
        return null;
    }
}


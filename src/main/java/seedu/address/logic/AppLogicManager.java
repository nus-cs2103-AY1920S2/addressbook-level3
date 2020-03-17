package seedu.address.logic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.conditions.Conditions;
import seedu.address.storage.AppStorage;

import java.util.ArrayList;

public class AppLogicManager<T, M> implements AppLogic<T> {
    private AppStorage<T> dao;

    /**
     * Asserts that the user must always declare type M which is subclass of {@code AppStorage}
     *
     * @param   dao        Data access object implementation
     * @throws  Exception
     */
    public AppLogicManager(M dao) throws Exception {
        assert (dao instanceof AppStorage);
        this.dao = (AppStorage) dao;
    }

    /**
     * Fetches all pings from the database
     *
     * @return  ObservableList<T>   List of all pings for rendering to UI
     */
    @Override
    public ObservableList<T> getAll() {
        ArrayList<T> results = this.dao.search();
        return FXCollections.observableArrayList(results);
    }

    /**
     * Fetches all pings that satisfies a conditional filter
     *
     * @param   cond                {@code seedu.address.conditions.Conditions Class} Conditional class
     * @return  ObservableList<T>   List of all pings in condition for rendering to UI
     */
    @Override
    public ObservableList<T> filterBy(Conditions<T> cond) {
        ArrayList<T> results = this.dao.search(cond);
        return FXCollections.observableArrayList(results);
    }
}


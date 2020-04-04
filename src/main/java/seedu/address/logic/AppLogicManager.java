package seedu.address.logic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AppCommand;
import seedu.address.logic.messages.BluetoothPingsMessage;
import seedu.address.logic.conditions.Conditions;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.storage.AppStorage;
import seedu.address.logic.parser.CommandRouter;
import java.util.ArrayList;
import java.util.logging.Logger;

public class AppLogicManager<T, M> implements AppLogic<T> {
    private AppStorage<T> dao;
    private final Logger logger = LogsCenter.getLogger(AppLogicManager.class);

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

    @Override
    public BluetoothPingsMessage execute(String command) throws ParseException {
        logger.info("----------------[USER COMMAND][" + command + "]");

        AppCommand appCommand = new CommandRouter().parse(command);
        BluetoothPingsMessage result = appCommand.execute(dao);
        return result;
    }
}


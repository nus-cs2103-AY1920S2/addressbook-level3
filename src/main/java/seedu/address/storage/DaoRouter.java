package seedu.address.storage;

import seedu.address.logic.commands.AppCommand;
import seedu.address.model.bluetooth.BluetoothPings;
import seedu.address.model.bluetooth.Person;

public class DaoRouter {
    private static final InMemoryStorage<BluetoothPings> bluetoothPingStorage = new BluetoothPingsStorage();
    private static final InMemoryStorage<Person> userStorage = new UserStorage();
    private static DaoRouter routerSingleton = null;

    /**
     * Enforcing Singleton design pattern
     */
    private DaoRouter() {
    }

    /**
     * Identifies the correct storage to allocate for a given command based on a policy
     *
     * @param command {@code AppCommand}
     * @return InMemoryStorage     Instantiated DAO class
     */
    public InMemoryStorage getStorage(AppCommand command) {
        if (command instanceof BluetoothPingStorageAccess) {
            return bluetoothPingStorage;
        } else if (command instanceof UserStorageAccess) {
            return userStorage;
        } else {
            return null;
        }
    }

    public static DaoRouter getInstance(){
        if (routerSingleton == null) {
            routerSingleton = new DaoRouter();
        }
        return routerSingleton;
    }
}

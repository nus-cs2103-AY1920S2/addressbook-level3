package seedu.address.logic;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AppCommand;
import seedu.address.logic.messages.AppMessage;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.storage.AppStorage;
import seedu.address.logic.parser.CommandRouter;
import seedu.address.storage.DaoRouter;
import java.util.logging.Logger;

public class AppLogicManager<T, M> implements AppLogic<T> {
    private final Logger logger = LogsCenter.getLogger(AppLogicManager.class);

    /**
     * Asserts that the user must always declare type M which is subclass of {@code AppStorage}
     *
     * @param   dao        Data access object implementation
     * @throws  Exception
     */
    public AppLogicManager(M dao) throws Exception {
        assert (dao instanceof AppStorage);
    }

    @Override
    public AppMessage execute(String command) throws ParseException {
        logger.info("----------------[USER COMMAND][" + command + "]");

        AppCommand appCommand = new CommandRouter().parse(command);
        AppStorage<T> dao = DaoRouter.getInstance().getStorage(appCommand);
        AppMessage result = appCommand.execute(dao);
        return result;
    }
}


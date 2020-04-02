package csdev.couponstash.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import csdev.couponstash.commons.core.GuiSettings;
import csdev.couponstash.commons.core.LogsCenter;
import csdev.couponstash.commons.core.StashSettings;
import csdev.couponstash.logic.commands.Command;
import csdev.couponstash.logic.commands.CommandResult;
import csdev.couponstash.logic.commands.IndexedCommand;
import csdev.couponstash.logic.commands.exceptions.CommandException;
import csdev.couponstash.logic.parser.CouponStashParser;
import csdev.couponstash.logic.parser.exceptions.ParseException;
import csdev.couponstash.model.Model;
import csdev.couponstash.model.ReadOnlyCouponStash;
import csdev.couponstash.model.coupon.Coupon;
import csdev.couponstash.model.element.ObservableMonthView;
import csdev.couponstash.storage.Storage;

import csdev.couponstash.ui.CsTab;
import javafx.collections.ObservableList;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    public static final String INCORRECT_TAB_ERROR_MESSAGE = "This command can't be executed in this page!";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final CouponStashParser couponStashParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        couponStashParser = new CouponStashParser(model.getStashSettings().getMoneySymbol());
    }

    @Override
    public CommandResult execute(String commandText, CsTab selectedTab) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        Command command = couponStashParser.parseCommand(commandText);

        if (!selectedTab.equals(CsTab.COUPONS) && command instanceof IndexedCommand) {
            logger.info("Aborting index command execution in incorrect tab");
            throw new CommandException(INCORRECT_TAB_ERROR_MESSAGE);
        }

        CommandResult commandResult = command.execute(model, commandText);

        try {
            storage.saveCouponStash(model.getCouponStash());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyCouponStash getCouponStash() {
        return model.getCouponStash();
    }

    @Override
    public ObservableList<Coupon> getFilteredCouponList() {
        return model.getFilteredCouponList();
    }

    @Override
    public ObservableList<Coupon> getAllCouponList() {
        return model.getAllCouponList();
    }

    @Override
    public ObservableMonthView getMonthView() {
        return model.getMonthView();
    }

    @Override
    public Path getCouponStashFilePath() {
        return model.getCouponStashFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public StashSettings getStashSettings() {
        return model.getStashSettings();
    }

    @Override
    public void setStashSettings(StashSettings stashSettings) {
        model.setStashSettings(stashSettings);
    }
}

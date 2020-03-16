package seedu.eylah;

import java.util.logging.Logger;

import seedu.eylah.addressbook.logic.Logic;
import seedu.eylah.addressbook.model.Model;
import seedu.eylah.commons.core.LogsCenter;
import seedu.eylah.commons.core.Version;

public class Eylah {

    public static final Version VERSION = new Version(0, 1, 0, true);

    private static final Logger logger = LogsCenter.getLogger(Eylah.class);

    protected Logic logic;
    protected Model model;

    public Eylah() {
        logger.info("=============================[ Initializing EYLAH ]===========================");

    }

    public void run() {
        boolean isExit = false;
        while (!isExit) {

        }
    }

    public static void main(String[] args) {
        Eylah eylah = new Eylah();
        eylah.run();
    }


}

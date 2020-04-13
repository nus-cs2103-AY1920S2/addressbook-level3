package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_MODCODE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODLINK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODLINK_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODNAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODNOTE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.mod.Mod;
import seedu.address.model.mod.ModTAble;

/**
 * A utility class containing a list of {@code Mod} objects to be used in tests.
 */
public class TypicalMods {
    public static final Mod CS1010S_ALLFILLED = new ModBuilder().withModCode("CS1010S")
        .withModName("Programming Methodology")
        .withModNote("This module introduces the fundamental concepts of problem solving by"
            + " computing and programming using an imperative programming language.")
        .addModLinkPair("Coursemology", "https://coursemology.org")
        .build();

    public static final Mod CS2040C_ESSENTIALFILLED = new ModBuilder().withModCode("CS2040C")
        .withModName("Data Structures and Algorithms")
        .build();

    public static final Mod CS2103 = new ModBuilder().withModCode(VALID_MODCODE)
        .withModName(VALID_MODNAME)
        .withModNote(VALID_MODNOTE)
        .addModLinkPair(VALID_MODLINK_NAME, VALID_MODLINK)
        .build();

    private TypicalMods() {

    }

    /**
     * Returns an {@code ModTAble} with all the typical modules.
     */
    public static ModTAble getTypicalModTAble() {
        ModTAble modTAble = new ModTAble();
        for (Mod mod : getTypicalMods()) {
            modTAble.addMod(mod);
        }
        return modTAble;
    }

    public static List<Mod> getTypicalMods() {
        return new ArrayList<>(Arrays.asList(CS1010S_ALLFILLED, CS2040C_ESSENTIALFILLED, CS2103));
    }
}

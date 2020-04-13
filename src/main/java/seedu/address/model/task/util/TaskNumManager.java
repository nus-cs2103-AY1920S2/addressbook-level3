package seedu.address.model.task.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import seedu.address.model.module.ModuleCode;

/**
 * Manages IDs of all tasks across all modules.
 */
public class TaskNumManager {
    public static final String MESSAGE_USAGE_CONSTRAINTS =
            "A task must be identified by inputting a valid module code followed by a 3-digit number.\n"
            + "For example: CS2103T 848.";

    private static HashMap<ModuleCode, HashSet<Integer>> usedValues = new HashMap<>();
    private static final HashSet<Integer> emptySet = new HashSet<>();
    private static final int OFFSET = 100;
    private static final int RANGE = 899;
    private static final int BOUND = RANGE + 1;

    /**
     * Generates a taskNum number according to the exiisting ones of a Module.
     */
    public static int getNum(ModuleCode moduleCode) {
        HashSet<Integer> inPlace;
        if (usedValues.containsKey(moduleCode)) {
            inPlace = usedValues.get(moduleCode);
            assert !inPlace.isEmpty();
        } else {
            inPlace = emptySet;
        }

        int value = new Random().nextInt(BOUND);
        while (inPlace.contains(value)) {
            value = new Random().nextInt(BOUND);
        }

        return value + OFFSET;
    }

    /**
     * Checks if taskNum exist with module.
     */
    public static boolean doesNumExist(ModuleCode moduleCode, Integer id) {
        if (!usedValues.containsKey(moduleCode)) {
            return false;
        }

        return usedValues.get(moduleCode).contains(id);
    }

    /**
     * Removes the taskNum.
     */
    public static void removeNum(ModuleCode moduleCode, Integer id) {
        if (!usedValues.containsKey(moduleCode)) {
            return;
        }

        usedValues.get(moduleCode).remove(id);

        if (usedValues.get(moduleCode).isEmpty()) {
            usedValues.remove(moduleCode);
        }
    }

    /**
     * Adds the taskNum to database.
     */
    public static void addNum(ModuleCode moduleCode, Integer id) {
        if (!usedValues.containsKey(moduleCode)) {
            usedValues.put(moduleCode, new HashSet<>());
        }
        usedValues.get(moduleCode).add(id);
    }

    public static HashMap<ModuleCode, HashSet<Integer>> getMap() {
        return usedValues;
    }
}

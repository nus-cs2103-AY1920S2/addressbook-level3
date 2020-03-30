package com.notably.commons.compiler.parser.block;

import java.util.List;

/**
 * Utilities for operations regarding {@link Block}s.
 */
public class BlockUtil {
    /**
     * Gets the last element in a {@link List}.
     *
     * @param items Input list
     * @return Last element
     */
    public static <T> T getLast(List<T> items) {
        return items.get(items.size() - 1);
    }
}


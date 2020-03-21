package com.notably.commons.core.path;

import java.util.List;

/**
 * Represents a path to a {@link Block}.
 */
public interface Path {
    /**
     * Gets the string components of a path.
     * For example, a path /a/b/c have the components ["a", "b", "c"].
     *
     * @return Components of a path
     */
    List<String> getComponents();
    /**
     * Gets the string representation of a path.
     * For example, "/a/b/c" is a string representation of a path.
     *
     * @return String representation of a path
     */
    String getStringRepresentation();
}


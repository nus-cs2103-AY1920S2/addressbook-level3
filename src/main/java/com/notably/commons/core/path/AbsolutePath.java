package com.notably.commons.core.path;

import java.util.List;

import com.notably.commons.core.path.exceptions.InvalidPathException;

/**
 * TODO: Add Javadoc
 */
public class AbsolutePath implements Path {
    /**
     * TODO: Add Javadoc
     */
    public static boolean isValidAbsolutePath(String absolutePathString) {
        // TODO: Add implementation
        return false;
    }

    /**
     * TODO: Add Javadoc
     */
    public static AbsolutePath fromString(String absolutePathString) throws InvalidPathException {
        // TODO: Add implementation
        return new AbsolutePath();
    }

    /**
     * TODO: Add Javadoc
     */
    public static AbsolutePath fromRelativePath(RelativePath relativePath,
            AbsolutePath currentWorkingPath) throws InvalidPathException {
        // TODO: Add implementation
        return new AbsolutePath();
    }

    /**
     * TODO: Add Javadoc
     */
    public RelativePath toRelativePath(AbsolutePath currentWorkingPath) {
        // TODO: Add implementation
        return new RelativePath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getComponents() {
        // TODO: Add implementation
        return List.of();
    }
}


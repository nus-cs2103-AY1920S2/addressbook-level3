package com.notably.commons.core.path;

import java.util.List;

import com.notably.commons.core.path.exceptions.InvalidPathException;

/**
 * TODO: Add Javadoc
 */
public class RelativePath implements Path {
    /**
     * TODO: Add Javadoc
     */
    public static boolean isValidRelativePath(String relativePathString) {
        // TODO: Add implementation
        return false;
    }

    /**
     * TODO: Add Javadoc
     */
    public static RelativePath fromString(String relativePathString) throws InvalidPathException {
        // TODO: Add implementation
        return new RelativePath();
    }

    /**
     * TODO: Add Javadoc
     */
    public static RelativePath fromAbsolutePath(AbsolutePath absolutePath,
            AbsolutePath currentWorkingPath) throws InvalidPathException {
        // TODO: Add implementation
        return new RelativePath();
    }

    /**
     * TODO: Add Javadoc
     */
    public AbsolutePath toAbsolutePath(AbsolutePath currentWorkingPath) {
        // TODO: Add implementation
        return new AbsolutePath();
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


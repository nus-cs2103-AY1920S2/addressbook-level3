package com.notably.commons.core.path;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.notably.commons.core.path.exceptions.InvalidPathException;

/**
 * TODO: Add Javadoc
 */
public class RelativePath implements Path {
    private final List<String> paths;
    public RelativePath(String relativePathString) {
        this.paths = new ArrayList<>();
        for (String obj : relativePathString.split("/")) {
            if (!obj.trim().equals("")) {
                this.paths.add(obj.trim());
            }
        }
    }

    /**
     * Check if string is a valid relative path.
     * @param relativePathString String used to check validity.
     * @return returns true if String is a valid relative Path.
     */
    public static boolean isValidRelativePath(String relativePathString) {
        if (relativePathString.charAt(0) != '/') {
            return true;
        }
        return false;
    }

    /**
     * Instantiate a RelativePath object if the string is valid.
     * @param relativePathString used to create RelativePath.
     * @return the converted RelativePath object.
     * @throws InvalidPathException if the String is invalid (Not relative path).
     */
    public static RelativePath fromString(String relativePathString) throws InvalidPathException {
        if (isValidRelativePath(relativePathString)) {
            return new RelativePath(relativePathString);
        } else {
            throw new InvalidPathException("Not a relative path");
        }
    }

    /**
     * Convert from absolute path to relative path.
     * @param absolutePath to convert to relative path.
     * @param currentWorkingPath of the current working directory.
     * @return the converted relative path.
     * @throws InvalidPathException
     */
    public static RelativePath fromAbsolutePath(AbsolutePath absolutePath,
            AbsolutePath currentWorkingPath) throws InvalidPathException {
        return absolutePath.toRelativePath(currentWorkingPath);
    }

    /**
     * Convert relative path to absolute path.
     * @param currentWorkingPath of the current working directory.
     * @return the absolute path of the relative path.
     */
    public AbsolutePath toAbsolutePath(AbsolutePath currentWorkingPath) {
        List<String> temp = new ArrayList<>(this.paths);
        List<String> temp2 = new ArrayList<>(currentWorkingPath.getComponents());
        temp2.addAll((temp));
        return new AbsolutePath(String.join("/", temp2));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getComponents() {
        return this.paths;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Path)) {
            return false;
        }

        Path another = (Path) object;
        List<String> temp = another.getComponents();
        return this.paths.equals(temp);
    }
    @Override
    public int hashCode() {
        return Objects.hash(this.paths);
    }

    @Override
    public String toString() {
        return (Arrays.toString(this.paths.toArray()));
    }
}


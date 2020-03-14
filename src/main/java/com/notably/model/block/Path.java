package com.notably.model.block;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent a path to a @link Block.
 */
public class Path {
    private List<String> path;
    private boolean isAbsolute;

    public Path(String args, Boolean isAbsolute) {
        this.path = new ArrayList<>();
        for (String obj : args.split("/")) {
            path.add(obj.trim());
        }
        this.isAbsolute = isAbsolute;
    }

    public List<String> getPaths() {
        return this.path;
    }

    public boolean isAbsolute() {
        return this.isAbsolute;
    }

}

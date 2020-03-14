package com.notably.model.block;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent a path to a @link Block.
 */
public class Path {
    private List<String> paths;
    private boolean isAbsolute;

    public Path(String args, Boolean isAbsolute) {
        this.paths = new ArrayList<>();
        for (String obj : args.split("/")) {
            paths.add(obj.trim());
        }
        this.isAbsolute = isAbsolute;
    }

    public List<String> getPaths() {
        return this.paths;
    }

    public boolean isAbsolute() {
        return this.isAbsolute;
    }

}

package com.notably.model.person;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent a path of a block.
 */
public class Path {
    private List<String> path;
    private boolean isAbsolute;

    public Path(String args, Boolean isAbsolute) {
        this.path = new ArrayList<>();
        for (String obj : args.split("/", 0)) {
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

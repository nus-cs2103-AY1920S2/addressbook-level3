package com.notably.commons.compiler.parser.block;

import java.util.Objects;
import java.util.stream.Collectors;

public class DocumentBlock extends Block {
    @Override
    public boolean next(String line) {
        Objects.requireNonNull(line);

        if (getChildren().isEmpty()) {
            if (Block.canCreateStructuralBlock(line)) {
                addChild(Block.createStructuralBlock(line));
            }
            return true;
        }

        Block lastChild = getChildren().get(getChildren().size() - 1);
        if (!lastChild.isOpen()) {
            if (Block.canCreateStructuralBlock(line)) {
                addChild(Block.createStructuralBlock(line));
            }
            return true;
        }

        boolean successful = lastChild.next(line);
        if (!successful) {
            if (Block.canCreateStructuralBlock(line)) {
                addChild(Block.createStructuralBlock(line));
            }
            return true;
        }

        return true;
    }

    @Override
    public String toHtml() {
        return String.format("<html><body>%s</body></html>", 
                getChildren().stream().map(Block::toHtml).collect(Collectors.joining()));
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DocumentBlock)) {
            return false;
        }
        return super.equals(object);
    }
}


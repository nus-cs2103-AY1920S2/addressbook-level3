package com.notably.view.blockcontent;

import java.util.Objects;

import com.notably.model.Model;

import javafx.scene.layout.StackPane;

/**
 * View of the currently open block's content.
 */
public class BlockContent {
    private final BlockContentDisplayView blockContentDisplayView;
    private final BlockContentEditView blockContentEditView;

    public BlockContent(StackPane blockContentPlaceholder, Model model) {
        Objects.requireNonNull(blockContentPlaceholder);
        Objects.requireNonNull(model);

        blockContentDisplayView = new BlockContentDisplayView(model);
        blockContentEditView = new BlockContentEditView(model);

        // TODO: Integrate BlockContentEditView
        blockContentPlaceholder.getChildren().addAll(blockContentDisplayView.getRoot());
    }
}


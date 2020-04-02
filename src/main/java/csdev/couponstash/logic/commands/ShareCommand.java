package csdev.couponstash.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import csdev.couponstash.commons.core.Messages;
import csdev.couponstash.commons.core.index.Index;
import csdev.couponstash.logic.commands.exceptions.CommandException;
import csdev.couponstash.model.Model;
import csdev.couponstash.model.coupon.Coupon;
import csdev.couponstash.ui.CouponCard;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;

/**
 * Share a Coupon in the CouponStash.
 */
public class ShareCommand extends IndexedCommand {

    public static final String COMMAND_WORD = "share";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shares the coupon identified by the "
            + "index number with the world! Coupon will be rendered and saved as an image file "
            + "for easy sharing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SHARE_COUPON_SUCCESS = "Coupon successfully saved to: %s";

    public static final String MESSAGE_WRITE_TO_IMAGE_ERROR =
            "Problem encountered while saving to image. Please try sharing again!";

    public static final String MESSAGE_DIALOG_CLOSED =
            "Coupon was not saved, save dialog was closed without choosing a directory.";

    private static final String FORMAT = "png";

    /**
     * @param index of the coupon in the filtered coupon list to edit
     */
    public ShareCommand(Index index) {
        super(index);
    }

    @Override
    public CommandResult execute(Model model, String commandText) throws CommandException {
        requireNonNull(model);
        List<Coupon> lastShownList = model.getFilteredCouponList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_COUPON_DISPLAYED_INDEX);
        }

        Coupon couponToShare = lastShownList.get(targetIndex.getZeroBased());

        // Create new CouponCard and get the Region
        Region couponRegion = new CouponCard(
                couponToShare,
                targetIndex.getOneBased(),
                model.getStashSettings().getMoneySymbol().toString()
        ).getRoot();

        // Need to create a scene for the Region so CSS would work.
        Scene scene = new Scene(couponRegion);
        // Create a snapshot of the CouponRegion.
        WritableImage image = couponRegion.snapshot(new SnapshotParameters(), null);
        // Hacky way to ensure scene is freed from memory.
        scene = null;

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName(couponToShare.getName().toString() + "." + FORMAT);
        File file = fileChooser.showSaveDialog(null);

        // If save dialog is closed without choosing a directory.
        if (file == null) {
            throw new CommandException(MESSAGE_DIALOG_CLOSED);
        }

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), FORMAT, file);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_WRITE_TO_IMAGE_ERROR);
        }

        return new CommandResult(String.format(MESSAGE_SHARE_COUPON_SUCCESS, file.getAbsolutePath()));
    }
}

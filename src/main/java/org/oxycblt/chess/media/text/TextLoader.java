// Text loader for the custom text spritesheet

package org.oxycblt.chess.media.text;

import javafx.scene.image.ImageView;

import org.oxycblt.chess.model.ChessType;

import org.oxycblt.chess.media.images.Texture;
import org.oxycblt.chess.media.images.TextureAtlas;

public final class TextLoader {

    private TextLoader() {

        // Not called.

    }

    private static final String VALID_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-:,!?()[]";

    private static char[] chars;
    private static int index = 0;

    // Create a piece of text
    public static ImageView[] createText(final String text, final ChessType color,
                                         final int x, final int y) {

        ImageView[] views = new ImageView[text.length()];

        // There are only uppercase letters in the font texture, so change the array to fit that.
        chars = text.toUpperCase().toCharArray();

        for (int i = 0; i < views.length; i++) {

            // Add the corresponding texture for each character in the string, if the character
            // is not valid, dont load a texture.
            index = VALID_CHARS.indexOf(chars[i]);

            views[i] = TextureAtlas.getTexture(
                Texture.TEXT,
                index % 9, (index / 9) + (color.getCoordinate() * 5),
                8, 10, (i * 9 + x), y
            );

        }

        return views;

    }

}
